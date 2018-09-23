package com.lzhenxing.javascaffold.util;

/**
 * Created by gary on 16/8/20.
 */

import com.lzhenxing.javascaffold.common.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

public class FTPUtils {

	private static Logger logger = LoggerFactory.getLogger(FTPUtils.class);

	// 枚举类UploadStatus代码

	public enum UploadStatus {
		Create_Directory_Fail, // 远程服务器相应目录创建失败
		Create_Directory_Success, // 远程服务器创建目录成功
		Upload_New_File_Success, // 上传新文件成功
		Upload_New_File_Failed, // 上传新文件失败
		File_Exits, // 文件已经存在
		Remote_Bigger_Local, // 远程文件大于本地文件
		Upload_From_Break_Success, // 断点续传成功
		Upload_From_Break_Failed, // 断点续传失败
		Delete_Remote_Faild; // 删除远程文件失败
	}

	// 枚举类DownloadStatus代码
	public enum DownloadStatus {
		Remote_File_Noexist, // 远程文件不存在
		Local_Bigger_Remote, // 本地文件大于远程文件
		Download_From_Break_Success, // 断点下载文件成功
		Download_From_Break_Failed, // 断点下载文件失败
		Download_New_Success, // 全新下载文件成功
		Download_New_Failed; // 全新下载文件失败
	}

	private static FTPUtils ftpUtils = new FTPUtils(CommonConstant.FTP_HOST_NAME, CommonConstant.FTP_PROT,
			CommonConstant.FTP_USER_NAME, CommonConstant.FTP_PASSWORD);

	public FTPClient ftpClient = new FTPClient();
	private String ftpURL, username, pwd, file1, file2;
	private int ftpport;

	public static FTPUtils getInstance() {
		return ftpUtils;
	}

	public FTPUtils(String _ftpURL, int _ftpport, String _username, String _pwd) {
		// 设置将过程中使用到的命令输出到控制台
		ftpURL = _ftpURL;
		username = _username;
		pwd = _pwd;
		ftpport = _ftpport;
		// file1 = _file1;
		// file2 = _file2;
		connect(ftpURL, ftpport, username, pwd);
	}

	/**
	 * 连接到FTP服务器
	 *
	 * @param hostname 主机名
	 * @param port 端口
	 * @param username 用户名
	 * @param password 密码
	 * @return 是否连接成功
	 * @throws IOException
	 */
	public boolean connect(String hostname, int port, String username, String password) {
		try {
			ftpClient.connect(hostname, port);
			ftpClient.setControlEncoding("GBK");
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				if (ftpClient.login(username, password)) {
					return true;
				}
			}
			disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 从FTP服务器上下载文件,支持断点续传，上传百分比汇报
	 *
	 * @param remote 远程文件路径
	 * @param local 本地文件路径
	 * @return 上传的状态
	 * @throws IOException
	 */
	public DownloadStatus download(String remote, String local) throws IOException {
		// 设置被动模式
		ftpClient.enterLocalPassiveMode();
		// 设置以二进制方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		DownloadStatus result;

		// 检查远程文件是否存在
		FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes("GBK"), "iso-8859-1"));
		if (files.length != 1) {
			System.out.println("远程文件不存在");
			return DownloadStatus.Remote_File_Noexist;
		}

		long remoteSize = files[0].getSize();
		File f = new File(local);
		// 本地存在文件，进行断点下载
		if (f.exists()) {
			long localSize = f.length();
			// 判断本地文件大小是否大于远程文件大小
			if (localSize >= remoteSize) {
				System.out.println("本地文件大于远程文件，下载中止");
				return DownloadStatus.Local_Bigger_Remote;
			}

			// 进行断点续传，并记录状态
			FileOutputStream out = new FileOutputStream(f, true);
			ftpClient.setRestartOffset(localSize);
			InputStream in = ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"), "iso-8859-1"));
			byte[] bytes = new byte[1024];
			long step = remoteSize / 100;
			long process = localSize / step;
			int c;
			while ((c = in.read(bytes)) != -1) {
				out.write(bytes, 0, c);
				localSize += c;
				long nowProcess = localSize / step;
				if (nowProcess > process) {
					process = nowProcess;
					if (process % 10 == 0)
						System.out.println("下载进度：" + process);
					// TODO 更新文件下载进度,值存放在process变量中
				}
			}
			in.close();
			out.close();
			boolean isDo = ftpClient.completePendingCommand();
			if (isDo) {
				result = DownloadStatus.Download_From_Break_Success;
			} else {
				result = DownloadStatus.Download_From_Break_Failed;
			}
		} else {
			OutputStream out = new FileOutputStream(f);
			InputStream in = ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"), "iso-8859-1"));
			byte[] bytes = new byte[1024];
			long step = remoteSize / 100;
			long process = 0;
			long localSize = 0L;
			int c;
			while ((c = in.read(bytes)) != -1) {
				out.write(bytes, 0, c);
				localSize += c;
				long nowProcess = localSize / step;
				if (nowProcess > process) {
					process = nowProcess;
					if (process % 10 == 0)
						System.out.println("下载进度：" + process);
					// TODO 更新文件下载进度,值存放在process变量中
				}
			}
			in.close();
			out.close();
			boolean upNewStatus = ftpClient.completePendingCommand();
			if (upNewStatus) {
				result = DownloadStatus.Download_New_Success;
			} else {
				result = DownloadStatus.Download_New_Failed;
			}
		}
		return result;
	}

	/**
	 * 上传文件到FTP服务器，(不)支持断点续传
	 *
	 * @param local 本地文件名称，绝对路径
	 * @param remote 远程文件路径，使用/home/directory1/subdirectory/file.ext或是 http://www.guihua.org /subdirectory/file.ext
	 * 按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构
	 * @return 上传结果
	 * @throws IOException
	 */
	public boolean upload(String local, String remote) {
		try (InputStream input = new FileInputStream(new File(local))){
			// 设置PassiveMode传输
			ftpClient.enterLocalPassiveMode();
			// 设置以二进制流的方式传输
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setControlEncoding("GBK");
			if (remote.contains("/")) {
				String dir = remote.substring(0, remote.lastIndexOf("/"));
				createDirectory(dir);
			}
			// 处理是否已经上传过
			if (fileSize(remote) == new File(local).length()) {
				return true;
			}
			return ftpClient.storeFile(remote, input);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return false;
	}

    public void batchUpload(Map<String, String> localAndRemoteMap){
        try{
            // 设置PassiveMode传输
            ftpClient.enterLocalPassiveMode();
            // 设置以二进制流的方式传输
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setControlEncoding("GBK");
            for(Map.Entry entry : localAndRemoteMap.entrySet()){
                String local = (String) entry.getKey();
                String remote = (String) entry.getValue();
                if (remote.contains("/")) {
                    String dir = remote.substring(0, remote.lastIndexOf("/"));
                    createDirectory(dir);
                }
                // 处理是否已经上传过
                File file = new File(local);
                if (fileSize(remote) == file.length()) {
                    continue;
                }
                InputStream input = new FileInputStream(file);
                ftpClient.storeFile(remote, input);
                input.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

	/**
	 * 断开与远程服务器的连接
	 *
	 * @throws IOException
	 */
	public void disconnect() {
		try {
			if (ftpClient.isConnected()) {
				// log out and disconnect from the server
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 递归创建远程服务器目录
	 *
	 * @param remote 远程服务器文件绝对路径
	 * @param ftpClient FTPClient 对象
	 * @return 目录创建是否成功
	 * @throws IOException
	 */
	public UploadStatus CreateDirecroty(String remote, FTPClient ftpClient) throws IOException {
		UploadStatus status = UploadStatus.Create_Directory_Success;
		String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
		if (!directory.equalsIgnoreCase("/")
				&& !ftpClient.changeWorkingDirectory(new String(directory.getBytes("GBK"), "iso-8859-1"))) {
			// 如果远程目录不存在，则递归创建远程服务器目录
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			while (true) {
				String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
				if (!ftpClient.changeWorkingDirectory(subDirectory)) {
					if (ftpClient.makeDirectory(subDirectory)) {
						ftpClient.changeWorkingDirectory(subDirectory);
					} else {
						System.out.println("创建目录失败");
						return UploadStatus.Create_Directory_Fail;
					}
				}
				start = end + 1;
				end = directory.indexOf("/", start);
				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return status;
	}

	/** */
	/**
	 * 上传文件到服务器,新上传和断点续传
	 *
	 * @param remoteFile 远程文件名，在上传之前已经将服务器工作目录做了改变
	 * @param localFile 本地文件 File句柄，绝对路径
	 * @param processStep 需要显示的处理进度步进值
	 * @param ftpClient FTPClient 引用
	 * @return
	 * @throws IOException
	 */
	public UploadStatus uploadFile(String remoteFile, File localFile, FTPClient ftpClient, long remoteSize)
			throws IOException {
		UploadStatus status;
		// 显示进度的上传
		long step = localFile.length() / 100;
		long process = 0;
		long localreadbytes = 0L;
		RandomAccessFile raf = new RandomAccessFile(localFile, "rw");
		// 我加的内容
		// InputStream inputStream = new FileInputStream(localFile);
		OutputStream out = ftpClient.appendFileStream(new String(remoteFile.getBytes("GBK"), "iso-8859-1"));
		// 断点续传
		if (remoteSize > 0) {
			ftpClient.setRestartOffset(remoteSize);
			process = remoteSize / step;
			raf.seek(remoteSize);
			localreadbytes = remoteSize;
			// //我加的,直接再上传
			// System.out.println("start again!");
			// ftpClient.storeFile(remoteFile,inputStream);
		}
		byte[] bytes = new byte[1024];
		int c;
		while ((c = raf.read(bytes)) != -1) {
			out.write(bytes, 0, c);
			localreadbytes += c;
			if (localreadbytes / step != process) {
				process = localreadbytes / step;
				System.out.println("上传进度:" + process);
			}
		}
		out.flush();
		raf.close();
		out.close();
		boolean result = ftpClient.completePendingCommand();
		if (remoteSize > 0) {
			status = result ? UploadStatus.Upload_From_Break_Success : UploadStatus.Upload_From_Break_Failed;
		} else {
			status = result ? UploadStatus.Upload_New_File_Success : UploadStatus.Upload_New_File_Failed;
		}
		return status;
	}

	/**
	 * ftpserver create nested folder
	 * @param path (path 后面不能带上文件名)
	 * @return
	 */
	public boolean createDirectory(String path) {
		if (StringUtils.isNotBlank(path)) {
			connect(ftpURL, ftpport, username, pwd);
			try {
				String[] pathElements = path.split("/");
				if (pathElements != null && pathElements.length > 0) {
					for (String singleDir : pathElements) {
						boolean existed = ftpClient.changeWorkingDirectory(singleDir);
						if (!existed) {
							boolean created = ftpClient.makeDirectory(singleDir);
							if (created) {
								System.out.println("CREATED directory: " + singleDir);
								ftpClient.changeWorkingDirectory(singleDir);
							} else {
								System.out.println("COULD NOT create directory: " + singleDir);
								return false;
							}
						}
					}
				}
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				disconnect();
			}
		}
		return true;
	}

	/**
	 * 获取文件大小
	 * @param path 可以带文件名
	 * @return
	 */
	public long fileSize(String path) {
		try {
			connect(ftpURL, ftpport, username, pwd);
			// 检查远程文件是否存在
			FTPFile[] files = ftpClient.listFiles(new String(path.getBytes("GBK"), "iso-8859-1"));
			if (files.length != 1) {
				System.out.println("远程文件不存在");
				return 0L;
			}
			return files[0].getSize();
		} catch (IOException e) {
			e.printStackTrace();
		}
		disconnect();
		return 0L;
	}

    /**
     *单个文件上传
     * @param path
     * @param isBase64
     * @return
     */
    public static String targetPath(String path, boolean isBase64){
        StringBuilder builder = new StringBuilder();
            String[] arr = path.split("/");
            for(int i = arr.length-4; i< arr.length-1; i++){
                if(isBase64){
                    builder.append(Base64Utils.string2base64(arr[i], true)).append("/");
                }else{
                    builder.append(arr[i]).append("/");
                }
            }
            builder.append(arr[arr.length-1]);
        return builder.toString();
    }

    /**
     * 批量上传文件
     * @param folderPath
     */
    public static void batchUploadFile(String folderPath, boolean isBase64){
        File parentFolder = new File(folderPath);
        File[] files = parentFolder.listFiles();
        for(File file : files){
            String fileName = file.getName();
            String localFile = folderPath + "/" + fileName;
            if(isBase64){
                FTPUtils.getInstance().upload(localFile, targetPath(localFile, true));
            }else {
                FTPUtils.getInstance().upload(localFile, targetPath(localFile, false));
            }
        }
    }

	public static void main(String[] args) {
		String localPath = "";
        System.out.println(targetPath(localPath, false));
        //路径要base64
        String remotePath = targetPath(localPath, false);
		// FTPUtils.getInstance().createDirectory(dirs);
//		 System.out.println(FTPUtils.getInstance().fileSize("upload/icon.jpg"));
		FTPUtils.getInstance().upload(localPath, remotePath);
	}
}

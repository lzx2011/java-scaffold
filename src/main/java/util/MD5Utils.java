package util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    private static final Log logger = LogFactory.getLog(MD5Utils.class);

    static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getMD5(byte[] source) {
        String s = null;
        try {
            MessageDigest md = MessageDigest
                    .getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest();
            char str[] = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(str);

        } catch (Exception e) {
            logger.error("error msg : ", e);
        }
        return s;
    }

    /**
     * 生成md5校验码
     *
     * @param srcContent 需要加密的数据
     * @return 加密后的md5校验码。出错则返回null。
     */
    public static String makeMd5Sum(byte[] srcContent) {
        if (srcContent == null) {
            return null;
        }

        String strDes = null;

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(srcContent);
            strDes = bytes2Hex(md5.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            logger.error("error msg : ", e);
            return null;
        }
        return strDes;
    }

    /**
     * 对流进行Md5
     *
     * @param in 需要加密的流
     * @return
     */
    public static String makeStreamHash(InputStream in) {

        BufferedInputStream bfs = null;
        String hash = null;
        byte[] buffer = new byte[1024];
        int readNum = 0;
        try {
            bfs = new BufferedInputStream(in);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            while ((readNum = bfs.read(buffer)) > 0) {
                md5.update(buffer, 0, readNum);
            }
            bfs.close();
            hash = bytes2Hex(md5.digest());
        } catch (Exception e) {
            logger.error("error msg : ", e);
            return null;
        }
        return hash;
    }

    /**
     * bytes2Hex方法
     *
     * @param byteArray
     * @return
     */
    private static String bytes2Hex(byte[] byteArray) {
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (byteArray[i] >= 0 && byteArray[i] < 16) {
                strBuf.append("0");
            }
            strBuf.append(Integer.toHexString(byteArray[i] & 0xFF));
        }
        return strBuf.toString();
    }

    public static void saveMd5File(String md5fileName, InputStream inputStream)
            throws Exception {
        try {
            String strMd5 = "";
            strMd5 = MD5Utils.makeStreamHash(inputStream);
            FileWriter fileWriter = new FileWriter(md5fileName + ".md5");
            fileWriter.write(strMd5);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            logger.error("saveMd5File 保存md5文件出错了：", e);
        }
    }
    
    public static String getStrMd5(String sourceStr) {
        StringBuffer buf = new StringBuffer("");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        } catch (Exception e) { }
        return buf.toString();
    }
    
}

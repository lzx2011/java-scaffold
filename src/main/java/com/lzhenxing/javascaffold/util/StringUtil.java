package com.lzhenxing.javascaffold.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: StringUtils <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/5/13
 */
public class StringUtil {

	private static Logger logger = LoggerFactory.getLogger(StringUtil.class);

	public static final String EMPTY = "";

	public static String trimToEmpty(String str) {
		return str == null ? EMPTY : str.trim();
	}

	/**
	 *
	 * createJavaBeanToXml: 把javaBean对象堆成xml文件
	 *
	 * @param clazz
	 * @param root
	 * @param backupid
	 * @param filePath
	 * @param filename
	 * @return 是否成功
	 */
	@SuppressWarnings("rawtypes")
	public static boolean createJavaBeanToXml(Class clazz, Object root, Integer backupid, String filePath,
			String filename) {
		try {
			// 文件异常检查
			if (filename == null || "".equals(filename)) {
				throw new FileNotFoundException();
			}
			String xmlFilePath = "";
			if (filePath.endsWith("/") || filePath.endsWith("\\")) {
				xmlFilePath = filePath + backupid;
			} else {
				xmlFilePath = filePath + File.separator + backupid;
			}
			File file = new File(xmlFilePath);
			file.mkdirs();

			// 生成XML文件基本设置
			Marshaller marshaller = JAXBContext.newInstance(clazz).createMarshaller();
			// 格式化XML
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			// 设置编码方式
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			// 设置XML声明
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);

			// 生成XML文件
			marshaller.marshal(root, new File(xmlFilePath + File.separator + filename));
		} catch (JAXBException e) {
			logger.error("Convert java bean to xml file happened exception.", e);
			return false;
		} catch (FileNotFoundException e) {
			logger.error("File name is not designated.", e);
			return false;
		}
		return true;
	}

	/**
	 *
	 * createJavaBeanToTxt: 把javaBean对象转成txt文件
	 *
	 * @param root
	 * @param backupid
	 * @param filePath
	 * @param filename
	 * @return 是否成功
	 */
	public static boolean createJavaBeanToTxt(Object root, Integer backupid, String filePath, String filename) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			// 文件异常检查
			if (filename == null || "".equals(filename)) {
				throw new FileNotFoundException();
			}
			if (filePath.endsWith("/") || filePath.endsWith("\\")) {
				filePath = filePath + backupid;
			} else {
				filePath = filePath + File.separator + backupid;
			}
			File file = new File(filePath);
			file.mkdirs();

			fos = new FileOutputStream(filePath + File.separator + filename);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(root);

			fos.flush();
		} catch (FileNotFoundException e) {
			logger.error("File is not exists.", e);
			return false;
		} catch (IOException e) {
			logger.error("Out put stream to file happened exception.", e);
			return false;
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (oos != null) {
					oos.close();
				}
			} catch (IOException e) {
				logger.error("Closed the data stream happened exception.", e);
				return false;
			}
		}
		return true;
	}

	public static boolean isEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}

	public static String trim(String s) {
		return s == null ? "" : s.trim();
	}

	// 当Integer对象为null时返回空串，否则将 Integer转成字符串返回
	public static String getEmptyStringByInteger(Integer o) {
		return o == null ? "" : o + "";
	}

	/**
	 * 对比两个对象的值，相等返回fales,不相等返回true
	 *
	 * @param a 对象a
	 * @param b 对象b
	 * @return 是否相等
	 */
	public static Boolean compare2ObjectValue(Object a, Object b) {
		return !String.valueOf(a).equals(String.valueOf(b));
	}

	/**
	 * 判断list对象是否为空数组
	 *
	 * @param list
	 * @return
	 */
	public static boolean isEmptyList(List<?> list) {
		return list == null || list.isEmpty() ? true : false;
	}

	/**
	 *
	 * isEmptyMap: 判断map是否为空
	 *
	 * @param map
	 * @return
	 */
	public static boolean isEmptyMap(Map<?, ?> map) {
		return map == null || map.isEmpty() ? true : false;
	}

	/**
	 *
	 * isNotEmptyMap: 判断map是否非空
	 *
	 * @param map
	 * @return
	 */
	public static boolean isNotEmptyMap(Map<?, ?> map) {
		return map == null || map.isEmpty() ? false : true;
	}

	public static boolean isNotEmptyList(List<?> list) {
		return list == null || list.isEmpty() ? false : true;
	}

	/**
	 *
	 * getParameter: 获取参数
	 *
	 * @param request
	 * @param parameter
	 * @return 返回参数
	 */
	public static String getParameter(HttpServletRequest request, String parameter) {
		String value = null;
		try {
			value = request.getParameter(parameter);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return value;
	}

	/**
	 * 判断一个字符串是否为数字
	 * @param num
	 * @return
	 */
	public static boolean isNumber(String num) {
		if (isEmpty(num)) {
			return false;
		} else {
			try {
				Integer.parseInt(num);
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}

	/**
	 *
	 * subStringByLength:按固定长度截取字符串
	 *
	 * @param content
	 * @param num
	 * @return
	 */
	public static String[] subStringByLength(String content, int num) {
		String[] str = new String[1000];
		int j = 0;
		int length = content.length();
		int start = 0;
		int len = 0;

		while (len <= length) {

			if ((start + num) > length) // 此处设置文字长度
			{
				len = length - start;
				len = start + len;
			} else {
				len = start + num; // 此处设置文字长度，必须和上面长度一样
			}

			if (len == length) {
				str[j] = content.substring(start, len);
				break;
			}

			// for (int i = len; i > start; i--) {
			// String c = new Character(content.charAt(i)).toString();
			// if (c.equals(",")) {
			// len = i;
			// break;
			// }
			// }

			str[j] = content.substring(start, len + 1);
			start = len + 1;
			j++;
		}
		return str;
	}

}

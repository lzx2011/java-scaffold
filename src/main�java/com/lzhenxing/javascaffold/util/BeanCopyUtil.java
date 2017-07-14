package com.lzhenxing.javascaffold.util;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ClassName: BeanCopyUtil <br/>
 * Function: 浅复制bean<br/>
 *
 * @author gary.liu
 * @date 2017/7/14
 */
public class BeanCopyUtil {

	/**
	 * bean简单复制
	 * @param fromBean
	 * @param toBean
	 * @return
	 */
	public static Object copyBean(Object fromBean, Object toBean) {

		Field[] fields = fromBean.getClass().getDeclaredFields();

		for (Field f : fields) {
			f.setAccessible(true);
		}
		try {
			for (Field f : fields) {
				// 取出属性名称
				String field = f.toString().substring(f.toString().lastIndexOf(".") + 1);
				System.out.println("fromBean." + field + " --> " + f.get(fromBean));

				String[] arr = field.split("_");
				StringBuilder stringBuilder = new StringBuilder();
				for (int i = 0; i < arr.length; i++) {
					String firstLetter = arr[i].substring(0, 1).toUpperCase();
					String word = firstLetter + arr[i].substring(1);
					stringBuilder.append(word);
				}
				String targetMethod = "set" + stringBuilder.toString();
				// System.out.println("targetMethod:" + targetMethod);
				Method method = toBean.getClass().getDeclaredMethod(targetMethod.trim(), f.getType());
				method.invoke(toBean, f.get(fromBean));

			}

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return toBean;

	}

	/**
	 * 浅复制测试
	 */
	public static void copyTest() {
		FromBean fromBean = new FromBean();
		fromBean.setUser_name("liu");
		fromBean.setUser_age(20);
		System.out.println(fromBean.toString());

		ToBean toBean = new ToBean();
		copyBean(fromBean, toBean);
		System.out.println(toBean.toString());

	}

	public static void main(String[] ages) {

		copyTest();

	}

}

/**
 * DO
 */
class FromBean {

	private String user_name;
	private int user_age;

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getUser_age() {
		return user_age;
	}

	public void setUser_age(int user_age) {
		this.user_age = user_age;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}

/**
 * BO
 */
class ToBean {

	private String userName;
	private int userAge;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}

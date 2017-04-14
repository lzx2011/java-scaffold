package com.lzhenxing.javascaffold.javabase.collections;

import java.util.Arrays;

/**
 * ClassName: ArrayOperate <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/4/13
 */
public class ArrayOperate {

	public static void addElement() {

		String[] s1 = { "a", "b", "c" };
		s1 = Arrays.copyOf(s1, s1.length + 1);
		s1[s1.length - 1] = "d";
		for (String s : s1) {
			System.out.println(s);
		}
	}

	public static void main(String[] args) {
        addElement();
	}
}

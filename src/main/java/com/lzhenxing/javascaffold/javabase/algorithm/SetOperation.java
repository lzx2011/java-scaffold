package com.lzhenxing.javascaffold.javabase.algorithm;

import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

public class SetOperation {

	/**
	 * 交集
	 */
	@Test
	public void intersection() {
		Set<String> set1 = new LinkedHashSet<>();
		Set<String> set2 = new LinkedHashSet<>();
		
		set1.add("1");
		set1.add("2");
		set1.add("3");
		set1.add("4");
		
		set2.add("1");
		set2.add("3");
		set2.add("6");
		set2.add("8");
		
		System.out.println(set1);
		set1.retainAll(set2);
		System.out.println(set1);
	}
	
	/**
	 * 并集
	 */
	@Test
	public void union() {
		Set<String> set1 = new LinkedHashSet<>();
		Set<String> set2 = new LinkedHashSet<>();
		
		set1.add("1");
		set1.add("2");
		set1.add("3");
		set1.add("4");
		
		set2.add("1");
		set2.add("3");
		set2.add("6");
		set2.add("8");
		
		System.out.println(set1);
		set1.addAll(set2);
		System.out.println(set1);
	}
	
}

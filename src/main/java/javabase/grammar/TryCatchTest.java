package javabase.grammar;

/**
 * Created by gary on 2017/1/14.
 */
public class TryCatchTest {

	public static final String testReturn() {
		String t = "";

		try {
			t = "try";
			Integer.parseInt(null);
			return t;
		} catch (Exception e) {
			t = "catch";
			Integer.parseInt(null);
			return t;
		} finally {
			t = "finally";
			// 有没有return,差别很大
			// return t;
		}
	}

	public static void main(String[] args) {
		String msg = "sdfs";
		System.out.println(TryCatchTest.testReturn());

	}
}

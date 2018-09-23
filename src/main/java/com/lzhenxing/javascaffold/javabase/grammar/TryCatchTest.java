package com.lzhenxing.javascaffold.javabase.grammar;

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

	//public static void main(String[] args) {
	//	String msg = "sdfs";
	//	System.out.println(TryCatchTest.testReturn());
    //
	//}

	public static void continueTest(){
		for(int i = 0; i < 10; i++){

            if(i > 5){
                try{
                    Integer.parseInt(null);
                    //throw new RuntimeException("test");
                    continue;
                } catch (Exception e){
                    System.out.println("excepiton occur");
                    //continue;
                }
            }
            System.out.println(i);
        }
	}

	public static void main(String[] args) {
		//System.out.println("OUTPUT one");
		//System.out.println("OUTPUT two");
		//System.err.println("ERROR 1");
		//System.err.println("ERROR 2");
		//for(int i = 0; i < args.length; i++)
		//{
		//	System.out.printf("args[%d] = %s.", i, args[i]);
		//}
		continueTest();
	}
}


package com.lzhenxing.javascaffold.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * ClassName: BeanCircularReference <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/4/10
 */
public class BeanCircularReference {

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println(context.getBean("a11"));
        System.out.println(context.getBean("b"));

    }
}

@Component("a11")
class A {

	//@Autowired
	//public A(B b) {
	//}

    @Autowired
    private B b;
}

@Component
class B {
	//@Autowired
	//public B(A a) {
	//}

    @Autowired
    private A a;
}

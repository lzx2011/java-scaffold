package com.lzhenxing.javascaffold.javabase.designpattern;

import com.lzhenxing.javascaffold.util.FileUtils;
import sun.misc.ProxyGenerator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ClassName: ProxyPattern <br/>
 * Function: 静态代理和动态代理<br/>
 *
 * @author gary.liu
 * @date 2017/5/23
 */
public class ProxyPattern {

    public static final String FILE_PATH = "/Users/gary/Documents/$Proxy1.class";

    public static void main(String[] args){

        /**
         * 静态代理测试
         */
        RealSubject realSubject = new RealSubject();  //委托对象
        ProxyObject proxyObject = new ProxyObject(realSubject);  //代理对象

        proxyObject.leave();

        /**
         * 动态代理测试
         */
        ProxyHandler proxyHandler = new ProxyHandler(realSubject);
        //动态生成代理对象
        Subject proxySubject = (Subject) Proxy.newProxyInstance(RealSubject.class.getClassLoader(), RealSubject.class.getInterfaces(),
                proxyHandler);
        proxySubject.leave();

        System.out.println(proxySubject.getClass());
        /**
         * 生成代理类,可以将代理类写入文件中,然后用反编译工具查看动态生成的代理类的结构
         * 参考: http://www.jasongj.com/design_pattern/dynamic_proxy_cglib/
         *
         */
        byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy1", RealSubject.class.getInterfaces());
        FileUtils.createFile(FILE_PATH, classFile);

    }
}

interface Subject{
    /**
     * 请假接口
     */
    void leave();
}

class RealSubject implements Subject {

    @Override
    public void leave(){
        System.out.println("RealSubject leave request");
    }

}

class ProxyObject implements Subject {

    private Subject subject;

    public ProxyObject(Subject subject){
        this.subject = subject;
    }

    @Override
    public void leave(){
        System.out.println("真正对象告诉代理帮他请假");
        subject.leave();
        System.out.println("代理告诉真正对象请假成功");
    }

}

/**
 * 动态代理实现上面的例子
 *
 */
class ProxyHandler implements InvocationHandler {

    private Subject subject;

    public ProxyHandler(Subject subject){

        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args){

        Object result = null;
        System.out.println("真正对象告诉代理帮他请假");
        try{
            result = method.invoke(subject, args);

        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("代理告诉真正对象请假成功");

        return result;
    }

}






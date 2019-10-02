//package spring.aop;
//
//import com.lzhenxing.javascaffold.spring.aop.AdvisedSupport;
//import com.lzhenxing.javascaffold.spring.aop.Cglib2AopProxy;
//import com.lzhenxing.javascaffold.spring.aop.TargetSource;
//import com.lzhenxing.javascaffold.spring.context.ApplicationContext;
//import com.lzhenxing.javascaffold.spring.context.ClassPathXmlApplicationContext;
//import org.junit.Test;
//import spring.HelloWorldService;
//import spring.HelloWorldServiceImpl;
//
///**
// * @author yihua.huang@dianping.com
// */
//public class Cglib2AopProxyTest {
//
//	@Test
//	public void testInterceptor() throws Exception {
//		// --------- helloWorldService without AOP
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tinyioc.xml");
//		HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
//		helloWorldService.helloWorld();
//
//		// --------- helloWorldService with AOP
//		// 1. 设置被代理对象(Joinpoint)
//		AdvisedSupport advisedSupport = new AdvisedSupport();
//		TargetSource targetSource = new TargetSource(helloWorldService, HelloWorldServiceImpl.class,
//				HelloWorldService.class);
//		advisedSupport.setTargetSource(targetSource);
//
//		// 2. 设置拦截器(Advice)
//		TimerInterceptor timerInterceptor = new TimerInterceptor();
//		advisedSupport.setMethodInterceptor(timerInterceptor);
//
//		// 3. 创建代理(Proxy)
//        Cglib2AopProxy cglib2AopProxy = new Cglib2AopProxy(advisedSupport);
//		HelloWorldService helloWorldServiceProxy = (HelloWorldService) cglib2AopProxy.getProxy();
//
//		// 4. 基于AOP的调用
//		helloWorldServiceProxy.helloWorld();
//
//	}
//}

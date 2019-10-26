//package spring.context;
//
//import com.lzhenxing.javascaffold.spring.context.ApplicationContext;
//import com.lzhenxing.javascaffold.spring.context.ClassPathXmlApplicationContext;
//import org.junit.Test;
//import spring.HelloWorldService;
//
///**
// * @author yihua.huang@dianping.com
// */
//public class ApplicationContextTest {
//
//    @Test
//    public void test() throws Exception {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tinyioc.xml");
//        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
//        helloWorldService.helloWorld();
//    }
//
//    @Test
//    public void testPostBeanProcessor() throws Exception {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tinyioc-postbeanprocessor.xml");
//        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
//        helloWorldService.helloWorld();
//    }
//}

//package spring;
//
//
//import com.lzhenxing.javascaffold.spring.beans.BeanPostProcessor;
//
///**
// * @author yihua.huang@dianping.com
// */
//public class BeanInitializeLogger implements BeanPostProcessor {
//	@Override
//	public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
//		System.out.println("Initialize bean " + beanName + " start!");
//		return bean;
//	}
//
//	@Override
//	public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
//		System.out.println("Initialize bean " + beanName + " end!");
//		return bean;
//	}
//}

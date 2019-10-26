//package spring.beans.xml;
//
//import com.lzhenxing.javascaffold.spring.beans.BeanDefinition;
//import com.lzhenxing.javascaffold.spring.beans.io.ResourceLoader;
//import com.lzhenxing.javascaffold.spring.beans.xml.XmlBeanDefinitionReader;
//import org.junit.Assert;
//import org.junit.Test;
//
//import java.util.Map;
//
///**
// * @author yihua.huang@dianping.com
// */
//public class XmlBeanDefinitionReaderTest {
//
//	@Test
//	public void test() throws Exception {
//		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
//		xmlBeanDefinitionReader.loadBeanDefinitions("tinyioc.xml");
//		Map<String, BeanDefinition> registry = xmlBeanDefinitionReader.getRegistry();
//		Assert.assertTrue(registry.size() > 0);
//	}
//}

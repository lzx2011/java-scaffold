package com.lzhenxing.javascaffold.spring.aop;


import com.lzhenxing.javascaffold.spring.beans.factory.BeanFactory;

/**
 * @author yihua.huang@dianping.com
 */
public interface BeanFactoryAware {

    void setBeanFactory(BeanFactory beanFactory) throws Exception;
}

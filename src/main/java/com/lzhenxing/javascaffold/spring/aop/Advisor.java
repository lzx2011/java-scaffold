package com.lzhenxing.javascaffold.spring.aop;

import org.aopalliance.aop.Advice;

/**
 * @author yihua.huang@dianping.com
 */
public interface Advisor {

    Advice getAdvice();
}
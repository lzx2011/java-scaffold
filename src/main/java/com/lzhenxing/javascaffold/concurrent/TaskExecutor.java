package com.lzhenxing.javascaffold.concurrent;

/**
 * 
 * ClassName: TaskExecutor <br/>  
 * date: 2016年7月18日 下午2:41:25 <br/>  
 *  
 * @version @param <P>
 * @version @param <R>  
 * @since JDK 1.8
 */
public interface TaskExecutor<P,R> {

	R execute(P parameter) throws Exception;
}

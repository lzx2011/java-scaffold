package com.lzhenxing.javascaffold.concurrent;

import java.util.concurrent.Callable;

/**
 * 
 * ClassName: TaskExecutorTaskWrapper <br/>  
 * date: 2016年7月18日 下午2:41:37 <br/>  
 *  
 * @version @param <P>
 * @version @param <R>  
 * @since JDK 1.8
 */
public class TaskExecutorTaskWrapper<P,R> implements Callable<R> {
	
    private P param;
    
    private TaskExecutor<P,R> taskExecutor;

    public TaskExecutorTaskWrapper(TaskExecutor<P, R> taskExecutor, P param) {
        this.param = param;
        this.taskExecutor = taskExecutor;
    }

    @Override
    public R call() throws Exception {
         return taskExecutor.execute(param);
    }

}

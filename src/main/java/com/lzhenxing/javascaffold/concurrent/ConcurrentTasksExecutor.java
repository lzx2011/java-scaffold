package com.lzhenxing.javascaffold.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * ClassName: ConcurrentTasksExecutor <br/>  
 * date: 2016年7月18日 下午2:41:00 <br/>  
 *  
 * @version
 * @since JDK 1.8
 */
public class ConcurrentTasksExecutor {

	public static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentTasksExecutor.class);

	public static final ExecutorService executorService = Executors.newCachedThreadPool();

	public static final ExecutorService executorServiceToMemched = Executors.newFixedThreadPool(64);

	private ConcurrentTasksExecutor() {

	}

	/**
	 * 
	 * executeWithoutResult: 放到线程池里面执行，不需要返回接口，即异步处理
	 * 
	 * @param runnable
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static void executeWithoutResult(Runnable runnable) throws InterruptedException, ExecutionException {
		executorService.execute(runnable);
	}

	/***
	 * 
	 * executeWithoutResult: 放到线程池里面执行，不需要返回接口，即异步处理
	 * 
	 * @param executor
	 * @param parameters
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static <P, R> void executeWithoutResult(TaskExecutor<P, R> executor, List<P> parameters) throws InterruptedException,
			ExecutionException {
		if (parameters == null || parameters.isEmpty()) {
			return;
		}

		List<TaskExecutorTaskWrapper<P, R>> callables = new ArrayList<TaskExecutorTaskWrapper<P, R>>();

		int len = parameters.size();

		for (int i = 0; i < len; i++) {
			TaskExecutorTaskWrapper<P, R> taskWrapper = new TaskExecutorTaskWrapper<P, R>(executor, parameters.get(i));
			callables.add(taskWrapper);
		}

		executorService.invokeAll(callables);
	}

	/**
	 * 
	 * executeWithResult: 放到线程池里面执行，需要返回接口，即多线程同步处理
	 * 
	 * @param executor
	 * @param parameters
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static <P, R> List<R> executeWithResult(TaskExecutor<P, R> executor, List<P> parameters) throws InterruptedException,
			ExecutionException {
		if (parameters == null || parameters.isEmpty()) {
			return new ArrayList<R>();
		}

		List<TaskExecutorTaskWrapper<P, R>> callables = new ArrayList<TaskExecutorTaskWrapper<P, R>>();

		int len = parameters.size();

		for (int i = 0; i < len; i++) {
			TaskExecutorTaskWrapper<P, R> taskWrapper = new TaskExecutorTaskWrapper<P, R>(executor, parameters.get(i));
			callables.add(taskWrapper);
		}

		List<Future<R>> rs = executorService.invokeAll(callables);

		List<R> results = new ArrayList<R>();
		for (Future<R> bv : rs) {
			if (bv.get() != null) {
				results.add(bv.get());
			}
		}

		return results;
	}

	/**
	 * 放到线程池里面执行数据查询，即多线程同步处理
	 * 
	 * @param executor
	 * @param parameters
	 * @return
	 * @throws InterruptedException
	 */
	public static <P, R> List<R> executeQuery(TaskExecutor<P, R> executor, List<P> parameters) throws InterruptedException {
		if (parameters == null || parameters.isEmpty()) {
			return new ArrayList<R>();
		}

		List<TaskExecutorTaskWrapper<P, R>> callables = new ArrayList<TaskExecutorTaskWrapper<P, R>>();
		int len = parameters.size();
		for (int i = 0; i < len; i++) {
			TaskExecutorTaskWrapper<P, R> taskWrapper = new TaskExecutorTaskWrapper<P, R>(executor, parameters.get(i));
			callables.add(taskWrapper);
		}

		List<Future<R>> rs = executorService.invokeAll(callables);

		List<R> results = new ArrayList<R>();
		if (rs != null && !rs.isEmpty()) {
			for (Future<R> bv : rs) {
				try {
					if (bv.get() != null) {
						results.add(bv.get());
					}
				} catch (InterruptedException | ExecutionException e) {
					LOGGER.error("A thread is interrupted or execute exception", e);
				}
			}
		}

		return results;
	}

	/**
	 * 数据查询
	 * @param executor
	 * @param parameters
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static <P, R> List<R> saveToMemcached(TaskExecutor<P, R> executor, List<P> parameters) throws InterruptedException, ExecutionException {
		if (parameters == null || parameters.isEmpty())
			return new ArrayList<R>();

		List<TaskExecutorTaskWrapper<P, R>> callables = new ArrayList<TaskExecutorTaskWrapper<P, R>>();

		for (int i = 0; i < parameters.size(); i++) {
			TaskExecutorTaskWrapper<P, R> taskWrapper = new TaskExecutorTaskWrapper<P, R>(executor, parameters.get(i));
			callables.add(taskWrapper);
		}

		List<Future<R>> rs = executorServiceToMemched.invokeAll(callables);

		List<R> results = new ArrayList<R>();
		if (rs != null && !rs.isEmpty()) {
			for (Future<R> bv : rs) {
				if (bv.get() != null) {
					results.add(bv.get());
				}
			}
		}

		return results;
	}
}

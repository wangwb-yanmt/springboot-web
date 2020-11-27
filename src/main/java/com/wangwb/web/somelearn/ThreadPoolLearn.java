package com.wangwb.web.somelearn;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 	线程池测试
 * @author wangwb
 *
 */
public class ThreadPoolLearn {
	
	private static final int corePoolSize = 10;
	private static final int maximumPoolSize = 20;
	private static final int keepAliveTime = 200;//空闲线程等待新任务的最长时间

	public static void main(String[] args) {
		//----------------------------------------预定义线程池------------------------------------------
		//JDK预定义线程池：FixedThreadPool
		//corePoolSize与maximumPoolSize相等，全部为核心线程
		//workQueue 为LinkedBlockingQueue（无界阻塞队列
		//任务执行是无序的
		//适用场景：可用于Web服务瞬时削峰，但需注意长时间持续高峰情况造成的队列阻塞
		ExecutorService FixedThreadPool = Executors.newFixedThreadPool(10);
		
		//JDK预定义线程池：CachedThreadPool
		//corePoolSize = 0，maximumPoolSize = Integer.MAX_VALUE，即线程数量几乎无限制
		//keepAliveTime = 60s，线程空闲60s后自动结束
		//workQueue 为 SynchronousQueue 同步队列，这个队列类似于一个接力棒，入队出队必须同时传递，因为CachedThreadPool线程创建无限制，不会有队列等待，所以使用SynchronousQueue
		//适用场景：快速处理大量耗时较短的任务，如Netty的NIO接受请求时，可使用
		ExecutorService CachedThreadPool = Executors.newCachedThreadPool();
		
		//JDK预定义线程池：SingleThreadExecutor
		//和newFixedThreadPool(1)不同，SingleThreadExecutor被定以后，无法修改，做到了真正的Single
		ExecutorService SingleThreadExecutor = Executors.newSingleThreadExecutor();
		
		//JDK预定义线程池：ScheduledThreadPool
		//最后还是调用了ThreadPoolExecutor的构造方法
		//DelayedWorkQueue
		ScheduledExecutorService ScheduledThreadPool = Executors.newScheduledThreadPool(corePoolSize);
		
		//BlockingQueue常见5种实现
		//ArrayBlockingQueue、LinkedBlockingQueue、DelayQueue、SynchronousQueue 、PriorityBlockingQueue  
		
		
		//----------------------------------------自定义线程池-------------------------------------------
		//使用有界阻塞队列
		ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(5);
		//定义线程池
		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, queue);
		
		//模拟15个任务需要执行
		for(int i=0; i<15; i++){
			MyTask myTask = new MyTask(i);
			//使用线程池执行线程
			poolExecutor.execute(myTask);
			System.out.println("线程"+poolExecutor.getActiveCount()+"正在执行任务"+i);
			
		}
		//关闭线程池
		poolExecutor.shutdown();

	}

}

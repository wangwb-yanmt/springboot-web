package com.wangwb.web.somelearn;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 	阻塞队列ArrayBlockingQueue   DEMO
 * @author wangwb
 *
 */
public class ArrayBlockingQueueDemo {

	public static void main(String[] args) {
		//生产者和消费者共用这一个队列，队列容量为10，存Cookie这个对象
        ArrayBlockingQueue<Cookie> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
        
        //开启一个生产者线程
        for (int i = 0; i < 1; i++) {
            new Produce(arrayBlockingQueue).start();
        }
        
        //5个消费者线程
        for (int i = 0; i < 5; i++) {
        	//这里没用到线程池，创建新的线程执行
            new Thread(new Consume(arrayBlockingQueue)).start();
        }

	}

}

//定义一个生产者任务，将1000个cookie对象塞进arrayBlockingQueue队列;
class Produce extends Thread {
    private static int i = 0;
    private ArrayBlockingQueue<Cookie> arrayBlockingQueue;

    public Produce(ArrayBlockingQueue<Cookie> arrayBlockingQueue) {
        this.arrayBlockingQueue = arrayBlockingQueue;
    }

    public void run() {
        try {
            while (i < 1000) {
                arrayBlockingQueue.put(new Cookie("cookie" + i));
                if (++i % 100 == 0) {//每生产100个，休息10s
                    Thread.sleep(10000);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("produce queue InterruptedException");
        }
    }
}

//定义消费者，实现Runnable接口
class Consume implements Runnable {
    private ArrayBlockingQueue<Cookie> arrayBlockingQueue;

    public Consume(ArrayBlockingQueue<Cookie> arrayBlockingQueue) {
        this.arrayBlockingQueue = arrayBlockingQueue;
    }

    public void run() {
        try {
            while (true) {
                Cookie poll = arrayBlockingQueue.poll(5, TimeUnit.SECONDS);//如果queue为null，那么5秒之后再去队列中取数据
                if (poll != null)
                    System.out.println(Thread.currentThread().getName() + "--consume --" + poll);

            }
        } catch (InterruptedException e) {
            System.out.println("consume queue InterruptedException");
        }
    }
}

//队列存的东西
class Cookie {
    private String number;

    public Cookie(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number + "";
    }
}

package com.wangwb.web.somelearn;

public class MyTask implements Runnable{
	
	private int taskNum;
	
	public MyTask(int num){
		this.taskNum = num;
	}
	
	public void run(){
		System.out.println("我是任务" + taskNum);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("任务"+taskNum+"执行完毕");
	}

}

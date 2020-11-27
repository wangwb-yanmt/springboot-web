package com.wangwb.web.somelearn;

/**
 * 	单例模式懒汉式线程安全
 * @author wangwb
 *
 */
public class Singleton {
	private static Singleton instance;
	private Singleton() {}
	public static synchronized Singleton getSingleton() {
		if(instance == null) {
			instance = new Singleton();
		}
		return instance;
	}
}
/**
 * 	单例模式饿汉式
 * @author wangwb
 *
 */
public class Singleton {
	private static Singleton instance = new Singleton();
	private Singleton() {}
	public static Singleton getSingleton() {
		return instance;
	}
}
/**
 * 	单例模式静态内部类
 * @author wangwb
 *
 */
public class Singleton {
	private static class SingletonHolder {
		private static final Singleton singleton = new Singleton();
	}
	private Singleton() {}
	public static final Singleton getSingleton() {
		return SingletonHolder.singleton;
	}
}
/**
 * 	单例模式双重校验锁
 * @author wangwb
 *
 */
public class Singleton {  
	private volatile static Singleton singleton;  //变量可见性
	private Singleton (){}  
	public static Singleton getSingleton() {  
		if (singleton == null) {  
			synchronized (Singleton.class) {  
				if (singleton == null) {  
					singleton = new Singleton();  
				}  
			}  
		}  
		return singleton;  
	}  
}
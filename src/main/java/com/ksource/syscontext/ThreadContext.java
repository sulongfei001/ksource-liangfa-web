package com.ksource.syscontext;

import com.ksource.liangfa.domain.User;

/**
 *当前操作线程可访问的对象容器<br>
 *@author gengzi
 *@data 2012-4-24
 */
public class ThreadContext {

	private static final ThreadLocal<User> threadSession  = new ThreadLocal<User>();

	 /**
	 * 得到当前线程操作用户
	 * @return
	 */
	public static User getCurUser(){
		return threadSession.get();
	}
	/**
	 * 设置当前线程操作用户（除了系统设计者可用，其他任何人不得调用！）
	 * @param user
	 */
	public static void setCurUser(User user){
		threadSession.set(user);
	}
}

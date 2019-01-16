package com.ksource.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ksource.liangfa.domain.User;
import com.ksource.syscontext.Const;

public class OnlineUsersListener implements HttpSessionBindingListener {
	private static final Logger log=LogManager.getLogger(OnlineUsersListener.class);
	private Integer count=0;

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		count++;
		HttpSession session=event.getSession();
		User loginUser=(User) session.getAttribute(Const.USER_SESSION_ID);
		if (loginUser!=null) {
			log.info("当前登录人为："+loginUser.getUserName()+"("+loginUser.getAccount()+"),且当前在线用户有"+count+"人!");
		}else {
			log.info("当前在线用户有"+count+"人!");
		}
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		count--;
		log.info("退出一人，当前在线用户有"+count+"人!");
	}

}

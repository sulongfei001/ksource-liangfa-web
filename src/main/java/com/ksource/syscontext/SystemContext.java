package com.ksource.syscontext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ksource.liangfa.domain.SystemInfo;
import com.ksource.liangfa.domain.User;
import com.ksource.listener.OnlineUsersListener;
import com.lowagie2.tools.concat_pdf;

import java.net.MalformedURLException;

public class SystemContext {
	
	private static SystemInfo systemInfo;
	private static ServletContext servletContext;
	
	/** 
	 * 通过　HttpServletRequest对象得到当前用户信息
	 * @param request
	 * @return　用户对象
	 */
	public static User getCurrentUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(
				Const.USER_SESSION_ID);
	}
	/**
	 * 通过HttpSession对象得到当前用户信息
	 * @param session
	 * @return　用户对象
	 */
	public static User getCurrentUser(HttpSession session) {
		return (User) session.getAttribute(
				Const.USER_SESSION_ID);
	}
	/**
	 * 清除用户登录信息
	 * @param session
	 */
	public static void clearCurrentUser(HttpSession session) {
		session.invalidate();
	}
	/**
	 * 通过HttpServletRequest对象设置当前用户信息
	 * @param request
	 * @param user
	 */
	public static void setCurrentUser(HttpServletRequest request,User user) {
		HttpSession session=request.getSession();
		session.setAttribute(Const.USER_SESSION_ID, user);
		OnlineUsersListener onlineListener = (OnlineUsersListener) servletContext.getAttribute(Const.OLUSER_LISTENER);
		if (onlineListener==null) {
			onlineListener=new OnlineUsersListener();
			servletContext.setAttribute(Const.OLUSER_LISTENER, onlineListener);
		}
		session.setAttribute(Const.OLUSER_LISTENER_SESSION_ID, onlineListener);
	}
	public static void setCurrentUser(HttpSession session,User user) {
		session.setAttribute(Const.USER_SESSION_ID, user);
	}
	public static SystemInfo getSystemInfo() {
		return systemInfo;
	}
	public static void setSystemInfo(SystemInfo systemInfo) {
		SystemContext.systemInfo = systemInfo;
	}
	public static ServletContext getServletContext() {
		return servletContext;
	}
	public static void setServletContext(ServletContext servletContext) {
		SystemContext.servletContext = servletContext;
	}

    /**
     * 用于取代ServletContext.getRealPath()，当项目运行在weblogic服务器上时此方法返回null。
     * 原因：weblogic是以war包的形式发布的，并没有realPath
     * @return
     */
    public static String getRealPath(){
        String path;
        try {
            if((path=servletContext.getRealPath("/"))==null){
                path=servletContext.getResource("/").getPath().substring(1);  //去掉第一个字符-"/"
            }
            return path;
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return "";
    }
}

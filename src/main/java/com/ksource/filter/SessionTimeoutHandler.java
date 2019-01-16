package com.ksource.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.ksource.liangfa.domain.User;
import com.ksource.syscontext.SystemContext;
import com.ksource.syscontext.ThreadContext;
/**
 * session超时验证
 * @author gengzi
 *
 */
public class SessionTimeoutHandler implements Filter {

	private String handlePage;//超时处理页面
	private String filterExcludes;//排除验证的页面列表
	
	public void init(FilterConfig filterConfig) {
		handlePage = filterConfig.getInitParameter("handlePage");
		filterExcludes = filterConfig.getInitParameter("filterExcludes");
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = null;
        HttpServletResponse response = null;
        if (servletRequest instanceof HttpServletRequest) {
            request = (HttpServletRequest) servletRequest;
            response = (HttpServletResponse) servletResponse;
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
		if(isLogon(request)){
				filterChain.doFilter(request, response);
				return;
		}else if(isExcludeURL(request)){
			filterChain.doFilter(servletRequest, servletResponse);
	        return;
		}else if(isAppRequest(request)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", false);
            jsonObject.put("msg", "登陆超时！");
            jsonObject.put("errorCode", 1);
            response.setCharacterEncoding("UTF-8");  
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().print(jsonObject.toJSONString());
        }else{
		     response.sendRedirect(request.getContextPath()+handlePage);  
		}
	}

	/**
     * 判断请求的URL是否是过滤器排除的URL，如果是则返回true<br>
     *	语法规则：servletPath1(paramName1=paramVal1&paramName2=paramVal2,paramName3=paramVal3);servletPath2(paramName1=paramVal1,paramName2=paramVal2) <br>
     *	解释: ";"和","是或的关系,"&"是并且的关系。servletPath不能重复配置<br>
     * @return 是否是排除的url
     */
    private boolean isExcludeURL(HttpServletRequest request) {
    	String uri = request.getServletPath();
    	if(uri.equals("/")){//请求登录页面
        	return true;
        }
    	if(uri.equals(handlePage)){//访问超时页面
    		return true;
    	}
        boolean ret = false;
        String[] excludeServlets = filterExcludes.split(";");
        boolean servletYes=false;//本次循环的servletPath，参数不匹配
        for(String excludeServlet :excludeServlets){
        	if(ret){
        		break;
        	}
        	if(servletYes){
        		break;
        	}
        	String requestServletPath = "";
        	if(excludeServlet.indexOf("(")<0){
        		requestServletPath=excludeServlet;
        	}else{
        		requestServletPath = excludeServlet.substring(0,excludeServlet.indexOf("("));
        	}
        	//1.判断是否满足servletPath
        	if(!uri.startsWith(requestServletPath)){
        		continue;
        	}
        	if(excludeServlet.indexOf("(")<0 || excludeServlet.indexOf(")")<0){
        		ret=true;
        		break;
        	}
    		String orExcludeParams_lang=excludeServlet.substring(excludeServlet.indexOf("(")+1,excludeServlet.indexOf(")")).trim();
    		if("".equals(orExcludeParams_lang)){
    			ret=true;
    			break;
    		}
			String[] orExcludeParams=orExcludeParams_lang.split(",");
			for(String orExcludeParam : orExcludeParams){//2是否满足任一个参数配置
				String[]  andExcludeParams = orExcludeParam.split("&");
				boolean isParamsRequired=true;;//是否符合所有参数配置
				for(String andExcludeParam : andExcludeParams){//3是否满足某个参数配置
					String[] requestParams = andExcludeParam.split("=");
					if(requestParams.length!=2){
						continue;
					}
					String paramName=requestParams[0];
					String paramValue=requestParams[1];
					if("".equals(paramName) || "".equals(paramValue)
							|| !paramValue.equals(request.getParameter(paramName))){
						isParamsRequired=false;
						break;
					}
				}
				if(isParamsRequired){
					ret=true;
					break;
				}
			}
			servletYes=true;//本次循环的servletPath匹配，但是参数没有匹配
        }
        return ret;
    }

    /**
     * 判断是否正常登陆
     *
     * @return 是否是排除的url
     */
    private boolean isLogon(HttpServletRequest request) {
    	boolean flag = false;
    	
    	User user = SystemContext.getCurrentUser(request);
    	if(user!=null && !"".equals(user.getUserId())){
    		flag=true;
    		//将对象放入当前线程供其他地方使用
    		ThreadContext.setCurUser(user);
    	}
    	
    	return flag;
    }
    
    
    /**
     * 判断是否app请求
     *
     * @param request
     * @return
     */
    private boolean isAppRequest(HttpServletRequest request) {
        String uri = request.getServletPath();
        //1.判断是否满足servletPath
        if(uri.startsWith("/app")){
            return true;
        }else{
            return false;
        }
    }
    
	@Override
	public void destroy() {
	}
	public String getFilterExcludes() {
		return filterExcludes;
	}

	public void setFilterExcludes(String filterExcludes) {
		this.filterExcludes = filterExcludes;
	}

	public String getHandlePage() {
		return handlePage;
	}

	public void setHandlePage(String handlePage) {
		this.handlePage = handlePage;
	}
	
}

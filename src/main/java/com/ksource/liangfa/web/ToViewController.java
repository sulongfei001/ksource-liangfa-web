package com.ksource.liangfa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 跳转到视图页面，需要参数view
 * @author gengzi
 *
 */
@Controller
public class ToViewController  {

	private static final String viewName = "view";
	@RequestMapping("/toView")
	protected String handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String view = request.getParameter(viewName);
		return view;
	}

}

package com.ksource.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.upload.UploadException;

/**
 * 自定义异常处理
 * @author gengzi
 *
 */
public class ExceptionHandler implements HandlerExceptionResolver {
	public String getBusinessError() {
		return businessError;
	}

	public void setBusinessError(String businessError) {
		this.businessError = businessError;
	}

	public String getDefaultError() {
		return defaultError;
	}

	public void setDefaultError(String defaultError) {
		this.defaultError = defaultError;
	}
	
	public String getUploadError() {
		return uploadError;
	}

	public void setUploadError(String uploadError) {
		this.uploadError = uploadError;
	}

	private String businessError;
	private String defaultError;
	private String uploadError;
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception ex) {
		//TODO:日志记录
		ex.printStackTrace();
		
		ModelAndView mView = new ModelAndView();
		String viewName=defaultError;//系统程序异常
		
		if(ex instanceof BusinessException){//1业务异常--用户看
			viewName=businessError;
		}
		if(ex instanceof MaxUploadSizeExceededException){//1文件大小限制
			viewName=uploadError;
		}	
		if(ex instanceof UploadException){//1.文件上传异常
			viewName=uploadError;
			mView.addObject("error_msg", ex.getMessage());
		}
		mView.setViewName(viewName);
		mView.addObject("msg", ex.getMessage());
		
		return mView;
	}

}

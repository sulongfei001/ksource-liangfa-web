package com.ksource.common.upload;
/**
 * 此类为 上传异常类。
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2012-2-8
 * time   下午5:33:30
 */
public class UploadException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UploadException() {
		super("上传异常");
	}

	public UploadException(String message) {
		super(message);
	}

	public UploadException(String message, Throwable cause) {
		super(message, cause);
	}

	public UploadException(Throwable cause) {
		super(cause);
	}
}

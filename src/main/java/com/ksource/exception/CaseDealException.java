package com.ksource.exception;

/**
 * 案件处理异常
 * @author rengeng
 *
 */
public class CaseDealException extends RuntimeException {

	private static final long serialVersionUID = 3669979265336344165L;

	public CaseDealException() {
		super();
	}
	
	public CaseDealException(String msg) {
		super(msg);
	}
}

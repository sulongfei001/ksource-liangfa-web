package com.ksource.liangfa.service.casehandle;

import javax.servlet.http.HttpServletResponse;

import com.ksource.liangfa.domain.User;

public interface CaseExportService{

	public void export(User user,String districtCode,HttpServletResponse response) throws Exception;

	public void exportAttr(String districtCode, HttpServletResponse response);
	
}

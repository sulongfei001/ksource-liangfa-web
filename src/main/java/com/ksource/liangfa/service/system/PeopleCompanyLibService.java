package com.ksource.liangfa.service.system;

import java.util.List;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.CaseXianyiren;
import com.ksource.liangfa.domain.CaseXianyirenExample;
import com.ksource.liangfa.domain.CompanyLib;
import com.ksource.liangfa.domain.PeopleLib;

/**
 * 描述：个人库、企业库 service<br>
 * 
 * @author gengzi
 * @data 2012-4-11
 */
public interface PeopleCompanyLibService {

	/**
	 * 分页查询
	 * 
	 * @param peopleLib
	 * @param page
	 * @return
	 */
	PaginationHelper<PeopleLib> findPeople(PeopleLib peopleLib, String page);

	/**
	 * 分页查询
	 * 
	 * @param peopleLib
	 * @param page
	 * @return
	 */
	PaginationHelper<CompanyLib> findCompany(CompanyLib companyLib, String page);

	/**
	 * 添加企业库信息
	 * 
	 * @param peopleLib
	 * @return
	 */
	ServiceResponse insertCompany(CompanyLib companyLib);

	/**
	 * 修改企业库信息
	 * 
	 * @param peopleLib
	 * @return
	 */
	ServiceResponse updateCompanyByPrimaryKey(CompanyLib companyLib);

	/**
	 * 删除企业库信息
	 * 
	 * @param regNo
	 * @return
	 */
	ServiceResponse deleteCompanyByPrimaryKey(String regNo);

	/**
	 * 修改个人库信息
	 * 
	 * @param peopleLib
	 * @return
	 */
	ServiceResponse updateByPrimaryKey(PeopleLib peopleLib);

	/**
	 * 删除个人库信息
	 * 
	 * @param idsNo
	 * @return
	 */
	ServiceResponse deleteByPrimaryKey(String idsNo);

	/**
	 * 添加个人库信息
	 * 
	 * @param peopleLib
	 * @return
	 */
	ServiceResponse insertPeopleLib(PeopleLib peopleLib);

	/**
	 * 查询嫌疑人
	 * 
	 * @param caseXianyirenExample
	 * @return
	 */
	List<CaseXianyiren> getXianyirenCaseId(
			CaseXianyirenExample caseXianyirenExample);

	/**
	 * 查询涉案企业
	 * 
	 * @param registractionNum
	 * @return
	 */
	List<CaseCompany> getCaseCompanyHistoryCase(String registractionNum);
}

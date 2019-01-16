package com.ksource.liangfa.service.opinion;

import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.LawPerson;

public interface LawPersonService {
	/**
	 * 根据id删除（执法人员信息）
	 * @param personId
	 */
	public ServiceResponse del(Long personId);
	/**
	 * 批量 删除（执法人员信息）
	 * @param personId
	 */
	public ServiceResponse batchDelete(String[] check);
	/**
	 * 根据id查找（执法人员信息）
	 * @param personId
	 * @return
	 */
	public LawPerson getById(Long personId);
	/**
	 * 分页查询（执法人员信息）
	 * @param lawPerson
	 * @return
	 */
	public PaginationHelper<LawPerson> queryLawPerson(LawPerson lawPerson,String page,Map<String,Object> model);
	/**
	 * 保存
	 * @param lawPerson
	 * @return
	 */
	public ServiceResponse save(LawPerson lawPerson,MultipartHttpServletRequest attachmentFile);
	/**
	 * 修改执法人员信息
	 * @param lawPerson
	 * @return
	 */
	public ServiceResponse updateByPersonId(LawPerson lawPerson, MultipartHttpServletRequest attachmentFile);
	
	/**
	 * 查询执法人员信息(统计报表钻取)
	 * @param page
	 * @param model
	 * @return
	 */
	public PaginationHelper<LawPerson> queryLawPersons(String page,Map<String,Object> param);
}

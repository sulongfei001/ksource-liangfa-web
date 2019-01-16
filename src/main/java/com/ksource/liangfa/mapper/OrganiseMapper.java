package com.ksource.liangfa.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.OrganiseExample;

public interface OrganiseMapper {
    /**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ORGANISE
	 * @mbggenerated  Fri Mar 17 09:58:19 CST 2017
	 */
	int countByExample(OrganiseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ORGANISE
	 * @mbggenerated  Fri Mar 17 09:58:19 CST 2017
	 */
	int deleteByExample(OrganiseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ORGANISE
	 * @mbggenerated  Fri Mar 17 09:58:19 CST 2017
	 */
	int deleteByPrimaryKey(Integer orgCode);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ORGANISE
	 * @mbggenerated  Fri Mar 17 09:58:19 CST 2017
	 */
	int insert(Organise record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ORGANISE
	 * @mbggenerated  Fri Mar 17 09:58:19 CST 2017
	 */
	int insertSelective(Organise record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ORGANISE
	 * @mbggenerated  Fri Mar 17 09:58:19 CST 2017
	 */
	List<Organise> selectByExample(OrganiseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ORGANISE
	 * @mbggenerated  Fri Mar 17 09:58:19 CST 2017
	 */
	Organise selectByPrimaryKey(Integer orgCode);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ORGANISE
	 * @mbggenerated  Fri Mar 17 09:58:19 CST 2017
	 */
	int updateByExampleSelective(@Param("record") Organise record,
			@Param("example") OrganiseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ORGANISE
	 * @mbggenerated  Fri Mar 17 09:58:19 CST 2017
	 */
	int updateByExample(@Param("record") Organise record,
			@Param("example") OrganiseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ORGANISE
	 * @mbggenerated  Fri Mar 17 09:58:19 CST 2017
	 */
	int updateByPrimaryKeySelective(Organise record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ORGANISE
	 * @mbggenerated  Fri Mar 17 09:58:19 CST 2017
	 */
	int updateByPrimaryKey(Organise record);

	List<Organise> find(@Param("id") Integer id,@Param("hasDept") boolean hasDept, @Param("districtCode") String districtCode);

	List<Organise> findByDistrictId(String districtId);
	
	List<Organise> findHasTaskAssignSettingByTask(@Param("procDefId") String procDefId,@Param("taskDefId") String taskDefId,
			@Param("districtCode") String districtCode,@Param("orgCode") Integer orgCode);

    List<Organise> findSubordinateOrgs(Map<String,Object> paramMap);
    
    List<Organise> getClueTree(Map<String,Object> paramMap);
    /**
     * 根据userId获取该用户下组织机构（包括子组织机构�?
     * @param userId
     * @return
     */
    List<Organise> findOrgsByUserId(String userId);
    
    /**
     * 查询出机构名称的拼接字符串，如人民检察院(两法办公�?
     * @param acceptUnite
     * @return
     */
    String getSpliceOrgName(String acceptUnit);
    
    /**
     * 根据组织机构唯一标识查询组织机构信息
     */
    Organise findOrgBySiOrgId(String siOrgId);
    /**
     * 根据组织机构唯一标识删除组织机构信息
     * @param siOrgId
     * @return
     */
	int deleteBySiOrgId(String siOrgId);
	
	/**
	 * 查询同一区划下已设置任务办理岗位的组织机�?
	 * @param procDefId
	 * @param targetTaskDefId
	 * @param districtCode
	 * @param object
	 * @return
	 */
	List<Organise> findDistrictHasTaskAssignSettingOrg(@Param("procDefId") String procDefId,
			@Param("taskDefId")String targetTaskDefId, @Param("districtCode")String districtCode);
	
	//以下方法为组织机构初始化�?���?
	List<Organise> queryOrgByOrgType(Map<String, Object> map);
		
	//更新叶子节点
	int updateIsParent(Map<String, Object> param);
		
	//更新父节�?
	int relevanceOrg(Map<String, Object> map);
		
	//更新path
	//int updatePathByPrimaryKey(Organise org);
	
	List<Organise> findAllOrg(Map<String, Object> param);
	
	List<Organise> findAllOrgNoParam();

	List<Organise> getByOrgCodes(Map<String, Object> param);
	
	//根据行政区划查询对应的公安机构，“生成要求公安说明不立案理由通知书�?使用
	List<Organise> findPoliceByDistrictId(Map<String, Object> param);
	
	int updateOrderNo(Organise org);

	List<Organise> queryIndustryOrgTree(String orgPath);
	
	List<Organise> getAcceptOrgTree(Map<String,Object> paraMap);

	Organise getYisongOrg(Map<String, Object> paramMap);

	List<Organise> getAcceptUserIdByOrgCode(Integer orgCode);

	Organise selectByorgCode(Integer createOrg);

	List<Organise> findIndustryList();
	
	/**
	 * 根据父节点查询子节点组织机构
	 * @param paramMap
	 * @return
	 */
	List<Organise> findChildOrgs(Map<String, Object> paramMap);
	
	List<Organise> getAcceptOrg(Map<String, Object> paraMap);
	
	List<Organise> getAcceptOrgByOrgCodeAndDistrictId(Map<String, Object> paraMap);

	List<Organise> findParticipantsOrg(Map<String, Object> paramMap);

	Organise findOrgByUserId(@Param("inputer")String inputer);

    List<Organise> getForestPolice(@Param("districtCode")String districtCode);
    
    List<Organise> getDeptByOrgCode(Integer upOrgCode);

}
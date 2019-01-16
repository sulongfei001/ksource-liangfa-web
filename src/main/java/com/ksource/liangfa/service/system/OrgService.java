package com.ksource.liangfa.service.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.Organise;

/**
  * 此类为 组织机构业务 接口(服务层)
  * 
  * @author zxl :)
  * @version 1.0   
  * date   2011-5-10
  * time   下午02:46:41
  */ 
public interface OrgService {

	

	/**
	 * 通过父级组织机构代码得到组织机构信息集合
	 * @param parentOrgId
	 * @return 组织机构集合
	 */
	List<Organise> findByParentPk(Integer parentOrgId);

	/**
	 * 生成组织机构树（本机构及下属机构，不含部门）
	 * @param parentId
	 * @param sb
	 * @param orgList 
	 */
	void buildOrgTree(Integer parentId, StringBuffer sb, ArrayList<Integer> orgList);
	/**
	 * 生成整个组织机构树（不含部门）
	 * @param parentId
	 * @param sb
	 * @param orgList 
	 */
	void buildAllOrgTree(Integer parentId, StringBuffer sb, ArrayList<Integer> orgList, String districtCode);

	/**
	 * 根据组织机构唯一标示删除一条组织机构记录。
	 * 删除之后
	 * 如果有父节点并且被删除机构的父组织机构没有子组织机构的话，修改其节点状态为叶子节点。
	 * 如果没有父节点不做任何操作。
	 * @param orgId
	 * @return
	 */
	ServiceResponse del(Integer orgId);
	
	/**
	 * 根据父组织机构信息查询子组织机构信息。
	 * <br/>特别的是每一个组织机构信息中都包含了此组织机构信息下的总岗位数。
	 * @param id 
	 * @param hasDept 查询结果是够包含下属部门
	 * @return
	 */
	List<Organise> find(Integer id,boolean hasDept);
    
	/**
	 * 添加组织机构信息，如果父组织机构信息的节点状态为叶子节点，修改为非叶子节点.
	 * <br/><strong>注意:如果添加的是机构就修改父节点状态,如果添加的是部门就不修改了(组织机构和部门不属于父子节点结构)</strong>
	 * @param org
	 * @return
	 */
	ServiceResponse add(Organise org);
	
	/*按行政编码查询*/
	List<Organise> findByDistrict(String districtCode);

	/**
	 * 通过行政区划查询组织机构集合。
	 * 查询所给行政区划下的所有组织机构以及这些组织机构的子机构。
	 * @param districtId
	 * @return
	 */
	List<Organise> findByDistrictId(String districtId);

	
	/**
	*功能：根据用户所在机构的行政区划和机构类别来查找用户能够看到的机构
	 * @param orgType 
	 * @param organise
	*@return 
	*/
	List<Organise> findOrgsByDistrictCodeAndOrgType(Organise currOrganise, String orgType);

	/**
	 * 查询所有的非部门的组织机构
	 * @return
	 */
	List<Organise> findAllOrgByNoDept();
	/**
	 * 删除部门
	 * @param deptId
	 * @return
	 */
	ServiceResponse delDept(Integer deptId);
	
	/**
	 * 根据当前用户组织机构信息和组织机构类型查询组织集合。
	 * 如果当前用户是行政机关则查询当前用户所在组织机构下的集合，
	 * 否则查询当前用户所在行政区划下的集合。
	 * @param org     当前用户组织机构信息
	 * @param orgType 要查询的组织集合的组织机构类型(作为查询条件)
	 * @return
	 */
	List<Organise> findSubordinateOrgs(Organise org, String orgType);
	List<Organise> getClueTree(Organise org, String orgType);
	ServiceResponse update(Organise org);
	
	/**
	 * 根据userId获取该用户下组织机构（包括子组织机构）
	 * @param userId
	 * @return
	 */
	List<Organise> findOrgsByUserId(String userId);
	
	List<Organise> findOrgsTree(Organise org);
	
	/**
	 * 根据组织机构唯一标识查询组织机构信息
	 * @param siOrgId
	 * @return
	 */
	Organise findOrgBySiOrgId(String siOrgId);
	
	/**
	 * 根据组织机构唯一标识删除数据
	 * @param siOrgId
	 * @return
	 */
	int deleteBySiOrgId(String siOrgId);
	
	int findBySuperId(Integer superId);
	
	/**
	 * 一键添加用户信息
	 * @param org
	 * @return
	 */
	ServiceResponse addUser(Organise org,String currentUserId);
	
	/**
	 * 组织机构树全选当前条件下的所有组织机构
	 * @param param
	 * @return
	 */
	List<Organise> findAllOrg(Map<String, Object> param);
	
	/**
	 * 根据orgType查询org信息
	 * @param map
	 * @return
	 */
	List<Organise> queryOrgByOrgType(Map<String, Object> map);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	int relevanceOrg(Map<String, Object> map);
	
	List<Organise> findOrgByParentId(Integer parentOrgId);

	PaginationHelper<Organise> findAllNoSelftByDistrict(Map<String, Object> param, String page);
	
	List<Organise> findPoliceByDistrictId(String districtCode);
	
	ServiceResponse updateOrderNo(Organise org);

	List<Organise> buildIndutryOrgTree(Integer orgCode,ArrayList<String> orgList);

	PaginationHelper<Organise> findOrgs(Map<String, Object> param, String page);

	Organise getYisongOrg(Organise currentOrganise);

	List<Organise> getAcceptUserIdByOrgCode(Integer orgCode);

	PaginationHelper<Organise> getChildOrg(Organise org,String page);
	
	PaginationHelper<Organise> getTurnOverOrg(Map<String, Object> paramMap,String page);
	PaginationHelper<Organise> getClueOrgTree(Organise org,String page);

	Organise selectByorgCode(Integer createOrg);
	
	/**
	 * 根据orgCode查询org信息
	 * @param orgCode
	 * @return
	 */
	Organise selectByPrimaryKey(Integer orgCode);
	
	/**
	 * 根据父节点查询子节点组织机构信息
	 * @param org
	 * @param orgType
	 * @param parentId
	 * @return
	 */
	List<Organise> findChildOrgs(Organise org, String orgType,Integer parentId);

	List<Organise> findParticipantsOrg(Organise org, String orgType,String id);

	PaginationHelper<Organise> findYiSongOtherOrgs(Map<String, Object> param, String page);
	
	Organise getDeptByOrgCode(Integer upOrgCode);
}

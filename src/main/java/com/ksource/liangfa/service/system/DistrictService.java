package com.ksource.liangfa.service.system;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.District;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2011-8-22
 * time   下午04:44:20
 */
public interface DistrictService {
	
	List<District> getDescendants(String districtId);
	/**
	 * 获取已经使用过（系统已接入）的行政区划
	 * @return
	 */
	List<District> getUsedXingzheng();
	/**
	 * 获取已经使用过（系统已接入）的行政区划数量
	 * @return
	 */
	 int getUsedXingzhengCount();
	 
	 /**
	  * 根据districtCode或upDistrictCode查询行政区划
	  * @param map
	  * @return
	  */
	 List<District> districtTreeManage(Map<String, Object> map);
	 
	 /**
	  * 根据条件查询行政区划，并分页
	  * @param district
	  * @return
	  */
	 PaginationHelper<District> districtManage(District district, String page);

    /**
     * 根据行政区划生成对应的组织机构，岗位和用户。
     * @return
     */
    ServiceResponse initAuthority();
    
    /**
     * 查询本级和下级区划（两级）
     * @param districtId
     * @return
     */
    List<District> findDistrictByParentId(String districtId);
    
	 
	List<District> queryByParentId(String districtCode);
	
	District selectByPrimaryKey(String districtCode);
	
	List<District> districtTreeCommunion(Map<String, Object> map);
	List<District> loadChildAsync(District district);
	/**
	 * 查询组织机构树为专项活动
	 * @author: LXL
	 * @return:List<District>
	 * @createTime:2017年10月18日 上午11:24:48
	 */
	List<District> getDistrictListForSpecialAct(Map<String, Object> paramMap);
}

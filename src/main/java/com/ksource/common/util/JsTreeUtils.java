package com.ksource.common.util;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;

import com.ksource.liangfa.domain.AccuseType;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.DistrictExternal;
import com.ksource.liangfa.domain.DqdjCategory;
import com.ksource.liangfa.domain.IndustryInfo;
import com.ksource.liangfa.domain.JudgeCondition;
import com.ksource.liangfa.domain.Module;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.SpecialActivity;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.WebArticleType;
import com.ksource.liangfa.domain.WebPrograma;
import com.ksource.liangfa.domain.cms.CmsChannel;
import com.ksource.syscontext.Const;

public class JsTreeUtils {

	public static String orgJsonztree(List<Organise> orgs) {
		return orgJsonztree(orgs, false, true);
	}

	public static String orgJsonztree(List<Organise> orgs, boolean hasDept) {
		return orgJsonztree(orgs, hasDept, true);
	}

	public static String orgJsonztree(List<Organise> orgs, boolean hasDept,
			boolean isClose) {
		return orgJsonztree(orgs,hasDept,isClose,null);
	}
	public static String orgJsonztree(List<Organise> orgs, boolean hasDept,
			boolean isClose,String orgIds) {
		if (orgs == null || orgs.isEmpty())
			return null;
		String[]orgIdArr=null;
		if(StringUtils.isNotBlank(orgIds)){
			orgIdArr= orgIds.split(",");
		}
		StringBuffer jsTreeJsonString = new StringBuffer("[");
		for (Organise org : orgs) {
			jsTreeJsonString.append("{ \"name\" : \"").append(org.getOrgName());
			jsTreeJsonString.append("\",\"id\" : \"").append(org.getOrgCode());
			jsTreeJsonString.append("\",\"orgPath\" : \"").append(org.getOrgPath());
			jsTreeJsonString.append("\",\"postNum\" : ").append(
					org.getPostNum());
			jsTreeJsonString.append(",\"deptNum\" : ").append(org.getDeptNum());
			jsTreeJsonString.append(",\"districtNum\" : ").append(
					org.getDistrictNum());
			jsTreeJsonString.append(",\"upId\" : \"")
					.append(org.getUpOrgCode()).append("\"")
					.append(",\"isDept\":").append(org.getIsDept());
			if (org.getIsLeaf() == Const.LEAF_NO
					|| (hasDept && org.getDeptNum() != 0)) {
				jsTreeJsonString.append(", \"isParent\" : true");

			}
			if (!isClose) {
				jsTreeJsonString.append(",\"open\" : true");
			}
			if(orgIdArr!=null){
				for(String orgId:orgIdArr){
					if(org.getOrgCode().equals(Integer.parseInt(orgId))){
						jsTreeJsonString.append(",\"checked\" : true");
						break;
					}
				}
			}
			jsTreeJsonString.append("},");
		}

		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0,
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");

		return jsTreeJsonString.toString();
	}
	public static String orgDeptJsonztree(List<Organise> orgs, boolean hasDept) {
		if (orgs == null || orgs.isEmpty())
			return null;
		StringBuffer jsTreeJsonString = new StringBuffer("[");
		for (Organise org : orgs) {
			if (!hasDept && org.getIsDept() == 0) {
				jsTreeJsonString.append("{ \"name\" : \"").append(
						org.getOrgName());
				jsTreeJsonString.append("\",\"id\" : \"").append(
						org.getOrgCode());
				jsTreeJsonString.append("\",\"postNum\" : ").append(
						org.getPostNum());
				jsTreeJsonString.append(",\"deptNum\" : ").append(
						org.getDeptNum());
				jsTreeJsonString.append(",\"districtNum\" : ").append(
						org.getDistrictNum());
				jsTreeJsonString.append(",\"upId\" : \"")
						.append(org.getUpOrgCode()).append("\"")
						.append(",\"isDept\":").append(org.getIsDept());
				if (org.getIsLeaf() == Const.LEAF_NO) {
					jsTreeJsonString.append(", \"isParent\" : true");

				}
				jsTreeJsonString.append("},");
			} else if (hasDept && org.getIsDept() == 1) {
				jsTreeJsonString.append("{ \"name\" : \"").append(
						org.getOrgName());
				jsTreeJsonString.append("\",\"id\" : \"").append(
						org.getOrgCode());
				jsTreeJsonString.append("\",\"postNum\" : ").append(
						org.getPostNum());
				jsTreeJsonString.append(",\"deptNum\" : ").append(
						org.getDeptNum());
				jsTreeJsonString.append(",\"districtNum\" : ").append(
						org.getDistrictNum());
				jsTreeJsonString.append(",\"upId\" : \"")
						.append(org.getUpOrgCode()).append("\"")
						.append(",\"isDept\":").append(org.getIsDept());
				jsTreeJsonString.append("},");
			}
		}

		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0,
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");

		return jsTreeJsonString.toString();
	}

	public static String consultationOrgJsonztree(List<Organise> orgs) {
		if (orgs == null || orgs.isEmpty())
			return null;
		StringBuffer jsTreeJsonString = new StringBuffer("[");
		for (Organise org : orgs) {

			jsTreeJsonString.append("{ \"name\" : \"").append(org.getOrgName());
			jsTreeJsonString.append("\",\"id\" : \"").append(org.getOrgCode());
			jsTreeJsonString.append("\",\"isParent\" : true");
			jsTreeJsonString.append("},");
		}

		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0,
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");

		return jsTreeJsonString.toString();
	}

	public static String consultationUserJsonztree(List<User> users) {
		if (users == null || users.isEmpty())
			return null;
		StringBuffer jsTreeJsonString = new StringBuffer("[");
		for (User user : users) {
			jsTreeJsonString.append("{ \"name\" : \"").append(user.getUserName());
//					user.getUserName()+"("+user.getUserId()+")");
			jsTreeJsonString.append("\",\"id\" : \"").append(user.getUserId());
			jsTreeJsonString.append("\",\"upId\" : \"").append(user.getOrgId());
			jsTreeJsonString.append("\",\"isParent\" : false");
			jsTreeJsonString.append("},");
		}
		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0,
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");
		return jsTreeJsonString.toString();
	}

	public static String taskFenpaiUserJsonztree(List<User> users) {
		if (users == null || users.isEmpty())
			return null;
		StringBuffer jsTreeJsonString = new StringBuffer("[");
		for (User user : users) {
			jsTreeJsonString.append("{ \"name\" : \"").append(
					user.getUserName());
			jsTreeJsonString.append("\",\"id\" : \"").append(user.getUserId());
			jsTreeJsonString.append("\",\"upId\" : \"")
					.append(user.getDeptId());
			jsTreeJsonString.append("\",\"isParent\" : false");
			jsTreeJsonString.append("},");
		}
		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0,
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");
		return jsTreeJsonString.toString();
	}

	public static String moduleJsonztree(List<Module> list) {
		if (list == null || list.isEmpty())
			return null;
		StringBuffer jsTreeJsonString = new StringBuffer("[");
		for (Module module : list) {
			jsTreeJsonString.append("{ \"name\" : \"").append(
					module.getModuleName());
			jsTreeJsonString.append("\",\"id\" : \"").append(
					module.getModuleId());
			jsTreeJsonString.append("\",\"moduleUrl\" : \"").append(
					module.getModuleUrl());
			jsTreeJsonString.append("\",\"upId\" : \"")
					.append(module.getParentId()).append("\"");
			if (module.getIsLeaf() == Const.LEAF_NO) {
				jsTreeJsonString.append(", \"isParent\" : true");
			}
			jsTreeJsonString.append("},");
		}

		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0,
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");
		return jsTreeJsonString.toString();
	}

	public static String districtJsonztree(List<District> list) {
		if (list == null || list.isEmpty())
			return null;
		StringBuffer jsTreeJsonString = new StringBuffer("[");
		for (District district : list) {
			jsTreeJsonString.append("{ \"name\" : \"").append(
					district.getDistrictName());
			jsTreeJsonString.append("\",\"id\" : \"").append(
					district.getDistrictCode());
			if(district.getUpDistrictCode() != null){
				jsTreeJsonString.append("\",\"upId\" : \"")
				.append(district.getUpDistrictCode()).append("\"");
			}else{
				jsTreeJsonString.append("\",\"upId\" : \"")
				.append("-1").append("\"");
			}
			if (district.getJb() != Const.DISTRICT_JB_2
					&& district.getChirdNum() != 0) {
				jsTreeJsonString.append(", \"isParent\" : true");
			}
			// jsTreeJsonString.append(",\"open\" : true");
			jsTreeJsonString.append("},");
		}
		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0,
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");
		return jsTreeJsonString.toString();
	}
	
	public static String accuseTypeJsonztree(List<AccuseType> list){
		if(list == null || list.isEmpty()){
			return null;
		}
		StringBuffer jsTreeJsonString = new StringBuffer("[");
		for(AccuseType accuseType : list){
			jsTreeJsonString.append("{ \"name\" : \"").append(
					accuseType.getAccuseName());
			jsTreeJsonString.append("\",\"id\" : ").append(
					accuseType.getAccuseId());
			jsTreeJsonString.append(",\"upId\" : ").append(
					accuseType.getParentId());
			jsTreeJsonString.append(",\"lev\" : ").append(
					accuseType.getAccuseLevel());
			jsTreeJsonString.append(",\"accuseNum\" : ").append(
					accuseType.getAccuseNum());
			jsTreeJsonString.append(",\"order\" : ").append(accuseType.getAccuseOrder());
			if(accuseType.getNodeNum()!=0){
				jsTreeJsonString.append(", \"isParent\" : true");
			}
			jsTreeJsonString.append("},");
		}
		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0, 
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");
		return jsTreeJsonString.toString();
	}

	public static <T> String timeOutWarningJsonztree(List<User> list,List<T> domains) throws InstantiationException, IllegalAccessException {
		if (list == null || list.isEmpty())
			return null;
		StringBuffer jsTreeJsonString = new StringBuffer("[");
		for (User user : list) {
			if(jsTreeJsonString.indexOf(String.valueOf(user.getOrgId())) != -1) { // 判断是否存在已有的父节点
				jsTreeJsonString.append("{ \"name\" : \"")
				.append(user.getUserName()+"("+user.getAccount()+")")
				.append("\",\"id\" : \"")
				.append(user.getUserId())
				.append("\",\"pId\" : \"")
				.append(user.getOrgId())
				.append("\"");
			}else {
				jsTreeJsonString.append("{ \"name\" : \"")
				.append(user.getOrgName())
				.append("\",\"id\" : \"")
				.append(user.getOrgId())
				.append("\",\"pId\" : 0,open:true},")
				.append("{ \"name\" : \"")
				.append(user.getUserName()+"("+user.getAccount()+")")
				.append("\",\"id\" : \"")
				.append(user.getUserId())
				.append("\",\"pId\" : \"")
				.append(user.getOrgId())
				.append("\"");
			}
			
			for (Iterator<T> domainIterator = domains.iterator() ; domainIterator.hasNext();) {                 
				T domain = domainIterator.next() ; 
				Class<T> class1 = (Class<T>) domain.getClass() ;
				Method method = ReflectionUtils.findMethod(class1, "getUserId") ;
				String userId = ReflectionUtils.invokeMethod(method,domain).toString() ;
				if(userId.equals(user.getUserId())) {
//					比对userId是否相等，如相等则处于选中状态，其父节点也处于选中状态
					jsTreeJsonString.append(",\"checked\" : true") ;
//					修改父节点，处于选中状态
					String pId = "\"id\" : \"" + String.valueOf(user.getOrgId()) + "\"" ;
					int length = jsTreeJsonString.indexOf(pId) + pId.length() ;
					jsTreeJsonString.insert(length, ",\"checked\" : true") ;
					domainIterator.remove() ;
				}
			}
			jsTreeJsonString.append("},");
		}
		
		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0,
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");
		
		return jsTreeJsonString.toString();
	}

	public static String loadLiangfaLeader(List<Organise> orgs) {
		if (orgs == null || orgs.isEmpty())
			return null;
		String[]orgIdArr=null;
		
		StringBuffer jsTreeJsonString = new StringBuffer("[");
		for (Organise org : orgs) {
			jsTreeJsonString.append("{ \"name\" : \"").append(org.getOrgName());
			jsTreeJsonString.append("\",\"id\" : \"").append(org.getOrgCode());
			jsTreeJsonString.append("\",\"postNum\" : ").append(
					org.getPostNum());
			jsTreeJsonString.append(",\"deptNum\" : ").append(org.getDeptNum());
			jsTreeJsonString.append(",\"districtNum\" : ").append(
					org.getDistrictNum());
			jsTreeJsonString.append(",\"upId\" : \"")
					.append(org.getUpOrgCode()).append("\"")
					.append(",\"isDept\":").append(org.getIsDept());
			if(orgIdArr!=null){
				for(String orgId:orgIdArr){
					if(org.getOrgCode().equals(Integer.parseInt(orgId))){
						jsTreeJsonString.append(",\"checked\" : true");
						break;
					}
				}
			}
			jsTreeJsonString.append("},");
		}

		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0,
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");

		return jsTreeJsonString.toString();
	}

	public static String getAllOrg(List<Organise> orgs,
			String orgIds) {
		if (orgs == null || orgs.isEmpty())
			return null;
		String[]orgIdArr=null;
		if(orgIds!=null){
			orgIdArr= orgIds.split(",");
		}
		StringBuffer jsTreeJsonString = new StringBuffer("[");
		for (Organise org : orgs) {
			jsTreeJsonString.append("{ \"name\" : \"").append(org.getOrgName());
			jsTreeJsonString.append("\",\"id\" : \"").append(org.getOrgCode());
			jsTreeJsonString.append("\",\"postNum\" : ").append(
					org.getPostNum());
			jsTreeJsonString.append(",\"deptNum\" : ").append(org.getDeptNum());
			jsTreeJsonString.append(",\"districtNum\" : ").append(
					org.getDistrictNum());
			jsTreeJsonString.append(",\"upId\" : \"")
					.append(org.getUpOrgCode()).append("\"")
					.append(",\"isDept\":").append(org.getIsDept());
			if(orgIdArr!=null){
				for(String orgId:orgIdArr){
					if(org.getOrgCode().equals(Integer.parseInt(orgId))){
						jsTreeJsonString.append(",\"checked\" : true");
						break;
					}
				}
			}
			jsTreeJsonString.append("},");
		}

		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0,
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");

		return jsTreeJsonString.toString();
	}
	
	public static String webProgramaTree(List<WebPrograma> programas,boolean isParent){
		if(programas == null || programas.isEmpty()){
			return null;
		}
		StringBuffer jsTreeJsonString = new StringBuffer("[");
		for(WebPrograma webPrograma: programas){
			jsTreeJsonString.append("{ \"name\" : \"").append(
					webPrograma.getProgramaName());
			jsTreeJsonString.append("\",\"id\" : \"").append(
					webPrograma.getProgramaId());
			if(isParent){
				jsTreeJsonString.append("\",\"isParent\" : false");
			}else{
				jsTreeJsonString.append("\"");
			}
			jsTreeJsonString.append("},");
		}
		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0, 
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");
		return jsTreeJsonString.toString();
	}

	public static String webArticleTypeTree(List<WebArticleType> webArticleTypes) {
		if (webArticleTypes == null || webArticleTypes.isEmpty())
			return null;
		StringBuffer jsTreeJsonString = new StringBuffer("[");
		for (WebArticleType webArticleType: webArticleTypes) {
			jsTreeJsonString.append("{ \"name\" : \"").append(
					webArticleType.getTypeName());
			jsTreeJsonString.append("\",\"id\" : \"").append(webArticleType.getTypeId());
			jsTreeJsonString.append("\",\"upId\" : \"").append(webArticleType.getProgramaId());
			jsTreeJsonString.append("\",\"isParent\" : false");
			jsTreeJsonString.append("},");
		}
		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0,
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");
		return jsTreeJsonString.toString();
	}
	public static String getLeafOrg(List<Organise> organises) {
		if (organises == null || organises.isEmpty())
			return null;
		StringBuffer jsTreeJsonString = new StringBuffer("[");
		for (Organise organise: organises) {
			jsTreeJsonString.append("{ \"name\" : \"").append(
					organise.getOrgName());
			jsTreeJsonString.append("\",\"id\" : \"").append(organise.getOrgCode());
			if(organise.getIsLeaf() == 0){
				jsTreeJsonString.append("\",\"isParent\" : true},");
			}else{
				jsTreeJsonString.append("\"},");
			}
		}
		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0,
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");
		return jsTreeJsonString.toString();
	}
	
	//构建专项活动树数据
	public static String loadActivity(List<SpecialActivity> actiList,Integer id) {
		if (actiList == null || actiList.isEmpty())
			return null;
			StringBuffer jsTreeJsonString = new StringBuffer("[");
		for (SpecialActivity sa: actiList) {
			jsTreeJsonString.append("{ \"name\" : \"").append(
					sa.getName());
			jsTreeJsonString.append("\",\"id\" : \"").append(sa.getId());
		if(sa.getId()==id){
			jsTreeJsonString.append("\",\"checked\" : true},");
		}else{
			jsTreeJsonString.append("\"},");
			}
		}
		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0,
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");
		return jsTreeJsonString.toString();
	}
	
	
	public static String getDqdjCategoryTree(List<DqdjCategory> dqdjCategories) {
		if(dqdjCategories == null || dqdjCategories.isEmpty()) {
			return null;
		}
		StringBuffer jsTreeJsonString = new StringBuffer("[");
		for (DqdjCategory dqdjCategory: dqdjCategories) {
			jsTreeJsonString.append("{ \"name\" : \"").append(dqdjCategory.getName());
			jsTreeJsonString.append("\",\"id\" : \"").append(dqdjCategory.getCategoryId());
			jsTreeJsonString.append("\",\"upId\" : \"").append(dqdjCategory.getParentId());
			if(dqdjCategory.getChildNum() > 0){
				jsTreeJsonString.append("\",\"isParent\" : true},");
			}else{
				jsTreeJsonString.append("\"},");
			}
		}
		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0,
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");
		return jsTreeJsonString.toString();
	}
	
	public static String districtTreeManage(List<District> list) {
		if (list == null || list.isEmpty())
			return null;
		StringBuffer jsTreeJsonString = new StringBuffer("[");
		for (District district : list) {
			jsTreeJsonString.append("{ \"name\" : \"").append(
					district.getDistrictName());
			jsTreeJsonString.append("\",\"id\" : \"").append(
					district.getDistrictCode());
			if(district.getUpDistrictCode() != null){
				jsTreeJsonString.append("\",\"upId\" : \"")
				.append(district.getUpDistrictCode()).append("\"");
			}else{
				jsTreeJsonString.append("\",\"upId\" : \"")
				.append("-1").append("\"");
			}
			if(district.getChirdNum() != null && district.getChirdNum() > 0) {
				jsTreeJsonString.append(", \"isParent\" : true");
			}
			jsTreeJsonString.append("},");
		}
		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0,
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");
		return jsTreeJsonString.toString();
	}

	public static String getJudgeConditionTree(List<JudgeCondition> conditionList) {
		if(conditionList == null || conditionList.isEmpty()) {
			return null;
		}
		StringBuffer jsTreeJsonString = new StringBuffer("[");
		for (JudgeCondition jc: conditionList) {
			jsTreeJsonString.append("{ \"name\" : \"").append(
					jc.getConditionName());
			jsTreeJsonString.append("\",\"id\" : \"").append(jc.getConditionId());
			if(jc.getIsParent() == 1){
				jsTreeJsonString.append("\",\"isParent\" : true},");
			}else{
				jsTreeJsonString.append("\"},");
			}
		}
		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0,
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");
		return jsTreeJsonString.toString();
	}
	
	//网站栏目
	public static String channelJsonztree(List<CmsChannel> list) {
		if (list == null || list.isEmpty())
			return null;
		StringBuffer jsTreeJsonString = new StringBuffer("[");
		for (CmsChannel channel : list) {
			jsTreeJsonString.append("{ \"name\" : \"").append(
					channel.getName());
			jsTreeJsonString.append("\",\"id\" : \"").append(
					channel.getChannelId());
			jsTreeJsonString.append("\",\"path\" : \"").append(
					channel.getPath());
			jsTreeJsonString.append("\",\"channelFrom\" : \"").append(
					channel.getChannelFrom());
			jsTreeJsonString.append("\",\"upId\" : \"")
					.append(channel.getParentId()).append("\"");
			if (channel.getIsLeaf() == Const.LEAF_NO) {
				jsTreeJsonString.append(", \"isParent\" : true");
			}
			jsTreeJsonString.append("},");
		}

		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0,
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");
		return jsTreeJsonString.toString();
	}
	
	
	//行业信息
	public static String industryJsonztree(List<IndustryInfo> list) {
		if(list == null || list.isEmpty()) {
			return null;
		}
		StringBuffer jsTreeJsonString = new StringBuffer("[");
		int i=1;
		for (IndustryInfo industryInfo: list) {
			jsTreeJsonString.append("{ \"name\" : \"").append(industryInfo.getIndustryName());
			jsTreeJsonString.append("\",\"id\" : \"").append(industryInfo.getIndustryType());
			jsTreeJsonString.append("\",\"keyId\" : ").append(i);
			jsTreeJsonString.append(",\"keyPId\" : 0");
			jsTreeJsonString.append(",\"isParent\" : false");
			jsTreeJsonString.append("},");
			i++;
		}
		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0,
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");
		return jsTreeJsonString.toString();
	}
	
	public static String districtExternalTreeManage(List<DistrictExternal> list) {
		if (list == null || list.isEmpty())
			return null;
		StringBuffer jsTreeJsonString = new StringBuffer("[");
		for (DistrictExternal district : list) {
			jsTreeJsonString.append("{ \"name\" : \"").append(
					district.getDistrictName());
			jsTreeJsonString.append("\",\"id\" : \"").append(
					district.getDistrictCode());
			if(district.getUpDistrictCode() != null){
				jsTreeJsonString.append("\",\"upId\" : \"")
				.append(district.getUpDistrictCode()).append("\"");
			}else{
				jsTreeJsonString.append("\",\"upId\" : \"")
				.append("-1").append("\"");
			}
			if(district.getChirdNum() > 0) {
				jsTreeJsonString.append(", \"isParent\" : true");
			}
			jsTreeJsonString.append("},");
		}
		jsTreeJsonString = new StringBuffer(jsTreeJsonString.substring(0,
				jsTreeJsonString.toString().length() - 1));
		jsTreeJsonString.append("]");
		return jsTreeJsonString.toString();
	}	
	
}
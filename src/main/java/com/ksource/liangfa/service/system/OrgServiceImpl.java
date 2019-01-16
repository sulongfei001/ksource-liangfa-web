package com.ksource.liangfa.service.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.OrganiseExample;
import com.ksource.liangfa.domain.Post;
import com.ksource.liangfa.domain.PostExample;
import com.ksource.liangfa.domain.TaskBind;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.PostMapper;
import com.ksource.liangfa.mapper.TaskBindMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.workflow.TaskAssignService;
import com.ksource.liangfa.util.PinyinUtil;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;

/**
 * 此类为 组织机构业务 实现类(服务层)
 * 
 * @author zxl :)
 * @version 1.0 date 2011-5-10 time 下午02:47:28
 */
@Service
public class OrgServiceImpl implements OrgService {
	@Autowired
	private OrganiseMapper organiseMapper;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private TaskBindMapper taskBindMapper;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private TaskAssignService taskAssignService;
	@Autowired
	private PostService postService;
	@Autowired
	private UserService userService;
	@Autowired
	private DistrictMapper districtMapper;

	// 日志
	static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public List<Organise> findByParentPk(Integer parentOrgId) {
		OrganiseExample example = new OrganiseExample();
		// TODO: 硬编码排序
		example.setOrderByClause("ORG_CODE desc");
		example.createCriteria().andUpOrgCodeEqualTo(parentOrgId);

		try {
			return organiseMapper.selectByExample(example);
		} catch (Exception e) {
			logger.error("查询组织机构失败：" + e.getMessage());
			throw new BusinessException("查询组织机构失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public void buildOrgTree(Integer id, StringBuffer buffer,
			ArrayList<Integer> orgs) {
		// 1查本身，2查下级
		try {
			boolean isParent = false;
			buffer.append("[");
			Organise org = organiseMapper.selectByPrimaryKey(id);
			buffer.append("{\"id\":\"").append(org.getOrgCode())
					.append("\",\"name\":\"").append(org.getOrgName());
			if (org.getIsLeaf() == Const.LEAF_NO) {
				isParent = true;
			}
			buffer.append("\",\"isParent\":").append(isParent);
			buffer.append(",\"open\":").append(isParent);
			if (orgs != null && orgs.contains(org.getOrgCode())) {
				buffer.append(",\"checked\":true");
			}
			// 添加子节点
			if (isParent) {
				buffer.append(",\"nodes\":");
				OrganiseExample example = new OrganiseExample();
				example.createCriteria().andUpOrgCodeEqualTo(id)
						.andIsDeptEqualTo(Const.STATE_INVALID);
				List<Organise> list = organiseMapper.selectByExample(example);
				Iterator<Organise> it = list.iterator();
				buffer.append("[");
				for (int i = 1; it.hasNext(); i++) {
					boolean isParent2 = false;
					Organise orgT = it.next();
					buffer.append("{\"id\":\"").append(orgT.getOrgCode())
							.append("\",\"name\":\"").append(orgT.getOrgName());
					if (orgT.getIsLeaf() == Const.LEAF_NO) {
						isParent2 = true;
					}
					buffer.append("\",\"isParent\":").append(isParent2);
					buffer.append(",\"open\":").append(isParent2);
					if (orgs != null && orgs.contains(org.getOrgCode())) {
						buffer.append(",\"checked\":true");
					}
					buffer.append("}");
					if (list.size() > 1 && i < list.size()) {
						buffer.append(",");
					}
				}
				buffer.append("]");
				buffer.append("}");
			} else {
				buffer.append("}");
			}
			buffer.append("]");
		} catch (Exception e) {
			logger.error("机构菜单显示失败：" + e.getMessage());
			throw new BusinessException("机构菜单显示失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "删除组织机构", db_opt_type = LogConst.LOG_DB_OPT_TYPE_DELETE, target_domain_mapper_class = OrganiseMapper.class, target_domain_class = Organise.class, target_domain_code_position = 0)
	public ServiceResponse del(Integer orgId) {
		ServiceResponse res = new ServiceResponse();
		res.setResult(true);

		PostExample example = new PostExample();
		example.createCriteria().andOrgIdEqualTo(orgId);

		try {
			// 判断此节点是否可以删除(如果组织机构下有岗位信息，此组织机构不能删除)
			int size = postMapper.countByExample(example);

			if (size > 0) {
				res.setingError("此组织机构下已存在岗位信息，不能删除!");

				return res;
			}
			OrganiseExample example2 = new OrganiseExample();
			example2.createCriteria().andUpOrgCodeEqualTo(orgId);
			size = organiseMapper.countByExample(example2);
			if (size > 0) {
				res.setingError("此组织机构下存在部门信息，不能删除!");

				return res;
			}
			// 存储要删除的组织机构数据
			Organise temp_org = organiseMapper.selectByPrimaryKey(orgId);
			// 删除组织机构节点
			organiseMapper.deleteByPrimaryKey(orgId);

			// 判断父节点还有没有子节点，如果没有修改父节点为叶子节点
			if (temp_org.getUpOrgCode() == null) {
				return res;
			}

			OrganiseExample orgExample = new OrganiseExample();
			orgExample.createCriteria().andUpOrgCodeEqualTo(
					temp_org.getUpOrgCode());
			size = organiseMapper.countByExample(orgExample);

			if (size == 0) {
				// 修改节点状态
				Organise tempOrg = new Organise();
				tempOrg.setOrgCode(temp_org.getUpOrgCode());
				tempOrg.setIsLeaf(Const.LEAF_YES);
				organiseMapper.updateByPrimaryKeySelective(tempOrg);
			}

			return res;
		} catch (Exception e) {
			logger.error("组织机构删除失败：" + e.getMessage());
			throw new BusinessException("组织机构删除失败");
		}
	}

	
	@Override
	@Transactional(readOnly = true)
	public int findBySuperId(Integer superId){
		OrganiseExample example = new OrganiseExample();
		example.createCriteria().andUpOrgCodeEqualTo(superId);
		int count = organiseMapper.countByExample(example);
		return count;
	}
	@Override
	@Transactional(readOnly = true)
	public List<Organise> find(Integer id, boolean hasDept) {
		try {
			return organiseMapper.find(id, hasDept, null);
		} catch (Exception e) {
			logger.error("组织机构查询失败：" + e.getMessage());
			throw new BusinessException("组织机构查询失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organise> findByDistrict(String districtCode) {
		try {
			OrganiseExample example = new OrganiseExample();
			example.createCriteria().andDistrictCodeEqualTo(districtCode).andIsDeptEqualTo(Const.IS_DEPT_NO);
			return organiseMapper.selectByExample(example);
		} catch (Exception e) {
			logger.error("组织机构查询失败：" + e.getMessage());
			throw new BusinessException("组织机构查询失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organise> findOrgsByDistrictCodeAndOrgType(
			Organise currOrganise, String orgType) {
		try {
			OrganiseExample example = new OrganiseExample();
			example.createCriteria()
					.andDistrictCodeEqualTo(currOrganise.getDistrictCode())
					.andOrgTypeEqualTo(orgType)
					.andIsDeptEqualTo(Const.STATE_INVALID);
			return organiseMapper.selectByExample(example);
		} catch (Exception e) {
			logger.error("组织机构查询失败：" + e.getMessage());
			throw new BusinessException("组织机构查询失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organise> findAllOrgByNoDept() {
		try {
			OrganiseExample example = new OrganiseExample();
			example.createCriteria().andIsDeptEqualTo(Const.STATE_INVALID);
			return organiseMapper.selectByExample(example);
		} catch (Exception e) {
			logger.error("组织机构查询失败：" + e.getMessage());
			throw new BusinessException("组织机构查询失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "添加组织机构",business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK,  db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_mapper_class = OrganiseMapper.class, target_domain_position = 0)
	public ServiceResponse add(Organise org) {
		ServiceResponse res = new ServiceResponse();

		try {
			// 1.2添加
			Integer orgCode = Integer.valueOf(systemDAO.getSeqNextVal(Const.TABLE_ORGANISE));
			org.setOrgCode(orgCode);
			//配置orgPath
			if(StringUtils.isBlank(org.getUpOrgPath())){
				org.setOrgPath(Const.TOP_ORG_ID+"."+orgCode);
			}else{
				org.setOrgPath(org.getUpOrgPath()+"."+orgCode);
			}
			organiseMapper.insert(org);
			// 如果添加的是机构就修改父节点状态,如果添加的是部门就不修改了.
			if (org.getIsDept() != null
					&& org.getIsDept().equals(Const.STATE_INVALID)) {
				// 2.修改父节点节点状态
				Organise tempOrg = new Organise();
				tempOrg.setOrgCode(org.getUpOrgCode());
				tempOrg.setIsLeaf(Const.LEAF_NO);
				organiseMapper.updateByPrimaryKeySelective(tempOrg);
			}
		} catch (Exception e) {
			logger.error("组织机构查询失败：" + e.getMessage());
			throw new BusinessException("组织机构查询失败");
		}

		res.setingSucess("添加成功");

		return res;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organise> findByDistrictId(String districtId) {
		try {
            return organiseMapper.findByDistrictId(StringUtils.rightTrim0(districtId));
		} catch (Exception e) {
			logger.error("组织机构查询失败：" + e.getMessage());
			throw new BusinessException("组织机构查询失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "删除部门", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_DELETE, target_domain_code_position = 0, target_domain_mapper_class = OrganiseMapper.class)
	public ServiceResponse delDept(Integer deptId) {
		ServiceResponse response = new ServiceResponse(true, "删除成功！");
		PostExample example = new PostExample();
		example.createCriteria().andDeptIdEqualTo(deptId);
		// 判断此节点是否可以删除(如果部门下有岗位信息，此部门不能删除)
		int size = postMapper.countByExample(example);
		if (size > 0) {
			response.setingError("此组织机构下已存在岗位信息，不能删除!");
			return response;
		}
		organiseMapper.deleteByPrimaryKey(deptId);
		return response;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organise> findSubordinateOrgs(Organise org, String orgType) {
		Map<String,Object> paramMap= new HashMap<String,Object>();
        List<String> orgTypes= Arrays.asList(orgType.split(","));
        paramMap.put("orgTypes",orgTypes);
        if(org!=null){
            if (!Const.ORG_TYPE_XINGZHENG.equals(org.getOrgType()) ||org.isLiangfaLeaderOrg()) {//非行政机关或牵头单位
                paramMap.put("districtCode", StringUtils.rightTrim0(org.getDistrictCode()));
            } else {
                paramMap.put("orgCode",org.getOrgCode());
            }
        }
		try {
			return organiseMapper.findSubordinateOrgs(paramMap);
		} catch (Exception e) {
			logger.error("组织机构查询失败：" + e.getMessage());
			throw new BusinessException("组织机构查询失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organise> getClueTree(Organise org,String orgType) {
		Map<String,Object> paramMap= new HashMap<String,Object>();
		List<String> orgTypes= Arrays.asList(orgType.split(","));
	    paramMap.put("orgTypes",orgTypes);
		if(org!=null){
			paramMap.put("districtCode", org.getDistrictCode());
		}
		try {
			return organiseMapper.getClueTree(paramMap);
		} catch (Exception e) {
			logger.error("组织机构查询失败：" + e.getMessage());
			throw new BusinessException("组织机构查询失败");
		}
	}
	@Override
	@LogBusiness(operation = "修改机构信息", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_UPDATE, target_domain_position = 0, target_domain_mapper_class = OrganiseMapper.class)
	public ServiceResponse update(Organise org) {
		ServiceResponse response = new ServiceResponse(true, "更新成功！");
		organiseMapper.updateByPrimaryKeySelective(org);
		return response;
	}

	@Override
	@Transactional(readOnly = true)
	public void buildAllOrgTree(Integer id, StringBuffer buffer,
			ArrayList<Integer> orgs, String districtCode) {

		try {
			List<Organise> list = organiseMapper.find(id, false, districtCode);
			Iterator<Organise> it = list.iterator();
			buffer.append("[");
			for (int i = 1; it.hasNext(); i++) {
				boolean isParent = false;
				Organise org = it.next();
				buffer.append("{\"id\":\"").append(org.getOrgCode())
						.append("\",\"name\":\"").append(org.getOrgName());
				if (org.getIsLeaf() == Const.LEAF_NO) {
					isParent = true;
				}
				buffer.append("\",\"isParent\":").append(isParent);
				buffer.append(",\"open\":").append(isParent);
				if (orgs != null && orgs.contains(org.getOrgCode())) {
					buffer.append(",\"checked\":true");
				}
				// 添加子节点
				if (isParent) {
					buffer.append(",\"nodes\":");
					this.buildAllOrgTree(org.getOrgCode(), buffer, orgs, null);
				}
				buffer.append("}");
				if (list.size() > 1 && i < list.size()) {
					buffer.append(",");
				}
			}
			buffer.append("]");
		} catch (Exception e) {
			logger.error("机构菜单显示失败：" + e.getMessage());
			throw new BusinessException("机构菜单显示失败");
		}
	}

	/**
	 * 根据userId获取该用户下组织机构（包括子组织机构）
	 */
	@Override
	public List<Organise> findOrgsByUserId(String userId) {
		return organiseMapper.findOrgsByUserId(userId);
	}
	
	/**
	 * 根据行政区划查询组织机构树(打侵打假(按录入机构)报表用)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Organise> findOrgsTree(Organise org) {
		Map<String,Object> paramMap= new HashMap<String,Object>();
        if(org!=null){
        	paramMap.put("districtCode", StringUtils.rightTrim0(org.getDistrictCode()));
        }
		try {
			return organiseMapper.findSubordinateOrgs(paramMap);
		} catch (Exception e) {
			logger.error("组织机构查询失败：" + e.getMessage());
			throw new BusinessException("组织机构查询失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Organise findOrgBySiOrgId(String siOrgId) {
		return organiseMapper.findOrgBySiOrgId(siOrgId);
	}

	@Override
	@Transactional
	@LogBusiness(operation = "删除组织机构", db_opt_type = LogConst.LOG_DB_OPT_TYPE_DELETE, target_domain_mapper_class = OrganiseMapper.class, target_domain_class = Organise.class, target_domain_code_position = 0)
	public int deleteBySiOrgId(String siOrgId) {
		return this.organiseMapper.deleteBySiOrgId(siOrgId);
	}
	
	@Override
	@Transactional
	@LogBusiness(operation = "一键添加用户",business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK,  db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_mapper_class = OrganiseMapper.class, target_domain_position = 0)
	public ServiceResponse addUser(Organise org,String currentUserId) {
		ServiceResponse res = new ServiceResponse();
		try {
			//1.增加组织机构
			org.setCreateUser(currentUserId);
			org.setCreateTime(new Date());
			this.add(org);
			
			//2.增加部门机构
			//查询刚添加的组织机构信息
			Organise orgTemp=new Organise();
			OrganiseExample example = new OrganiseExample();
			example.createCriteria().andOrgNameLike(org.getOrgName());
			List<Organise> orgList = mybatisAutoMapperService.selectByExample(
					OrganiseMapper.class, example, Organise.class);
			orgTemp =orgList.get(0);
			
			Organise dept=new Organise();
			dept.setDistrictCode(org.getDistrictCode());
			dept.setOrgName(orgTemp.getOrgName()+"两法办");
			dept.setOrgType(orgTemp.getOrgType());
			//行业类型
			dept.setIndustryType(orgTemp.getIndustryType());
			dept.setIndustryName(orgTemp.getIndustryName());
			if(orgTemp!=null && orgTemp.getOrgCode()>0){
				dept.setUpOrgCode(orgTemp.getOrgCode());
			}
			dept.setIsDept(Const.STATE_VALID);
			dept.setIsLeaf(Const.LEAF_YES);
			dept.setIsLiangfaLeader(Const.STATE_INVALID);
			dept.setCreateUser(currentUserId);
			dept.setCreateTime(new Date());
			this.add(dept);
			
			//3.增加岗位信息
			//查询刚添加的部门信息
			Organise deptTemp=new Organise();
			OrganiseExample deptexample = new OrganiseExample();
			deptexample.createCriteria().andUpOrgCodeEqualTo(orgTemp.getOrgCode());
			deptexample.createCriteria().andDistrictCodeEqualTo(org.getDistrictCode());
			List<Organise> deptList = mybatisAutoMapperService.selectByExample(
					OrganiseMapper.class, deptexample, Organise.class);
			deptTemp =deptList.get(0);
			
			Post post=new Post();
			Integer postId=systemDAO.getSeqNextVal(Const.TABLE_POST);
			post.setPostId(postId);
			post.setPostName("操作人");
			
			if(deptTemp!=null && deptTemp.getOrgCode()>0){
				post.setDeptId(deptTemp.getOrgCode());
			}
			if(orgTemp!=null && orgTemp.getOrgCode()>0){
				post.setOrgId(orgTemp.getOrgCode());
			}
			postService.insert(post);
			
			//4.添加用户
			User user=new User();
			//获得组织机构名称的首字母
			user.setAccount(PinyinUtil.getPinYinHeadChar(org.getOrgName())+"01");
			if(orgTemp!=null && orgTemp.getOrgCode()>0){
				user.setOrgId(orgTemp.getOrgCode());
			}
			user.setUserName(org.getOrgName()+"用户");
			user.setPassword("000000");
			user.setIdCard("111111111111111");
			user.setCreateUserId("systemAdmin");
			user.setIsValid(Const.STATE_VALID);
			user.setUserType(0);
			user.setIsLogin(0);
			if(deptTemp!=null && deptTemp.getOrgCode()>0){
				user.setDeptId(deptTemp.getOrgCode());
			}
			user.setPostId(postId);
			// 去掉空格(与验证，查询保持一致)
			user.setUserId(user.getUserId());
			userService.insert(user);
			
			//5.设置任务办理
			//查询任务表单
			List<TaskBind> taskBindList=taskBindMapper.getNewTaskBindList();
			for(TaskBind taskBind:taskBindList){
				if(taskBind.getAssignTarget().endsWith(org.getOrgType())){
					List<List<Integer>> orgCode_post_list = new ArrayList<List<Integer>>();
					List<Integer> orgCode_post = new ArrayList<Integer>();
					orgCode_post.add(Integer.valueOf(orgTemp.getOrgCode()));
					orgCode_post.add(Integer.valueOf(deptTemp.getOrgCode()));
					orgCode_post.add(Integer.valueOf(postId));
					orgCode_post_list.add(orgCode_post);
					taskAssignService.taskAssignBatch(taskBind.getProcDefId(), taskBind.getTaskDefId(),
							orgCode_post_list);
				}
			}
		} catch (Exception e) {
			logger.error("一键添加用户失败：" + e.getMessage());
			throw new BusinessException("一键添加用户失败");
		}

		res.setingSucess("添加成功");

		return res;
	}
	
	

	@Override
	public List<Organise> findAllOrg(Map<String, Object> param) {
		try {
			return organiseMapper.findAllOrg(param);
		} catch (Exception e) {
			logger.error("查询组织机构失败：" + e.getMessage(), e);
			throw new BusinessException("查询组织机构失败：" + e.getMessage());
		}
	}

	@Override
	public List<Organise> queryOrgByOrgType(Map<String, Object> map) {
		try {
			return organiseMapper.queryOrgByOrgType(map);
		} catch (Exception e) {
			logger.error("查询组织机构失败：" + e.getMessage(), e);
			throw new BusinessException("查询组织机构失败：" + e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public int relevanceOrg(Map<String, Object> map) {
		int result=0;
		Integer orgCode=Integer.valueOf(map.get("orgCode").toString());
		try {
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("orgCode", map.get("upOrgCode").toString());
			param.put("isLeaf", Const.LEAF_NO);
			//修改选中的组织机构树的isLeaf属性
			organiseMapper.updateIsParent(param);
			
			//更新组织机构的父节点
			result= organiseMapper.relevanceOrg(map);
			 
			//获取信息
			 Organise org=new Organise();
			 OrganiseExample example = new OrganiseExample();
			 example.createCriteria().andOrgCodeEqualTo(orgCode);
			 List<Organise> orgList = mybatisAutoMapperService.selectByExample(OrganiseMapper.class, example, Organise.class);
			 org =orgList.get(0);
			 
			/*if(org.getUpOrgCode() == null){
				org.setPath(Const.TOP_ORG_PATH);//添加根节点
			}else if(org.getUpOrgCode() == Const.TOP_ORG_ID){//增加省级机构
				org.setPath(Const.TOP_ORG_PATH+org.getOrgCode()+".");
			}else{
				Organise organise = organiseMapper.selectByPrimaryKey(org.getUpOrgCode());
				org.setPath(organise.getPath()+org.getOrgCode()+".");
			}
			result= organiseMapper.updatePathByPrimaryKey(org);*/
			
			//设置任务办理
			//根据组织机构查询部门和岗位信息
			Organise deptTemp=new Organise();
			OrganiseExample deptexample = new OrganiseExample();
			deptexample.createCriteria().andUpOrgCodeEqualTo(orgCode);
			deptexample.createCriteria().andIsDeptEqualTo(1);
			List<Organise> deptList = mybatisAutoMapperService.selectByExample(
					OrganiseMapper.class, deptexample, Organise.class);
			deptTemp =deptList.get(0);
			
			//岗位信息
			Post postTemp=new Post();
			PostExample postexample = new PostExample();
			postexample.createCriteria().andOrgIdEqualTo(orgCode);
			postexample.createCriteria().andDeptIdEqualTo(deptTemp.getOrgCode());
			List<Post> postList = mybatisAutoMapperService.selectByExample(
					PostMapper.class, postexample, Post.class);
			postTemp =postList.get(0);
			
			//查询任务表单
			List<TaskBind> taskBindList=taskBindMapper.getNewTaskBindList();
			for(TaskBind taskBind:taskBindList){
				if(taskBind.getAssignTarget().endsWith(org.getOrgType())){
					List<List<Integer>> orgCode_post_list = new ArrayList<List<Integer>>();
					List<Integer> orgCode_post = new ArrayList<Integer>();
					orgCode_post.add(orgCode);
					orgCode_post.add(Integer.valueOf(deptTemp.getOrgCode()));
					orgCode_post.add(Integer.valueOf(postTemp.getPostId()));
					orgCode_post_list.add(orgCode_post);
					taskAssignService.taskAssignBatch(taskBind.getProcDefId(), taskBind.getTaskDefId(),
							orgCode_post_list);
				}
			}
		} catch (Exception e) {
			logger.error("查询组织机构失败：" + e.getMessage(), e);
			throw new BusinessException("查询组织机构失败：" + e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 根据父节点查询子节点组织机构信息
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Organise> findOrgByParentId(Integer parentOrgId) {
		OrganiseExample example = new OrganiseExample();
		example.setOrderByClause("ORDER_NO ");
		example.createCriteria().andUpOrgCodeEqualTo(parentOrgId).andIsDeptEqualTo(Const.STATE_INVALID);
		try {
			return organiseMapper.selectByExample(example);
		} catch (Exception e) {
			logger.error("查询组织机构失败：" + e.getMessage());
			throw new BusinessException("查询组织机构失败");
		}
	}

	@Override
	public PaginationHelper<Organise> findAllNoSelftByDistrict(
			Map<String, Object> param, String page) {
		try {
			
		return systemDAO.find(param, page, 
				"com.ksource.liangfa.mapper.OrganiseMapper.findAllNoSelftByDistrictCount",
				"com.ksource.liangfa.mapper.OrganiseMapper.findAllNoSelftByDistrictList");
		} catch (Exception e) {
			logger.error("查询组织机构失败：" + e.getMessage(), e);
		throw new BusinessException("查询组织机构失败：" + e.getMessage());
		}
	}

	@Override
	public List<Organise> findPoliceByDistrictId(String districtCode) {
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("districtCode", districtCode);
		return organiseMapper.findPoliceByDistrictId(param);
	}

	@Override
	@LogBusiness(operation = "修改机构信息", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_UPDATE, target_domain_position = 0, target_domain_mapper_class = OrganiseMapper.class)
	public ServiceResponse updateOrderNo(Organise org) {
		ServiceResponse response = new ServiceResponse(true, "更新成功！");
		organiseMapper.updateOrderNo(org);
		return response;
	}

	@Override
	public List<Organise> buildIndutryOrgTree(Integer orgCode,ArrayList<String> orgList) {
		List<Organise> list = new ArrayList<Organise>();
		try {
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("orgCode", orgCode);
			list = organiseMapper.findSubordinateOrgs(params);
			for (Organise org:list) {
				if (orgList != null && orgList.contains(org.getOrgCode())) {
					org.setIsChecked("true");
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("机构显示失败：" + e.getMessage());
			throw new BusinessException("机构显示失败");
		}
	}

	@Override
	public PaginationHelper<Organise> findOrgs(
			Map<String, Object> param, String page) {
		try {
		return systemDAO.find(param, page, 
				"com.ksource.liangfa.mapper.OrganiseMapper.getOrgCount",
				"com.ksource.liangfa.mapper.OrganiseMapper.getOrgList");
		} catch (Exception e) {
			logger.error("查询外部组织机构失败：" + e.getMessage(), e);
		throw new BusinessException("查询外部组织机构失败：" + e.getMessage());
		}
	}

	@Override
	public Organise getYisongOrg(Organise currentOrganise) {
		Map<String,Object> paramMap= new HashMap<String,Object>();
        
        if(currentOrganise!=null){
        	
                //paramMap.put("districtCode", StringUtils.rightTrim0(currentOrganise.getDistrictCode()));
                paramMap.put("districtCode",currentOrganise.getDistrictCode());
        }
		try {
			return organiseMapper.getYisongOrg(paramMap);
		} catch (Exception e) {
			logger.error("组织机构查询失败：" + e.getMessage());
			throw new BusinessException("组织机构查询失败");
		}
	}

	@Override
	public List<Organise> getAcceptUserIdByOrgCode(Integer orgCode) {
		return organiseMapper.getAcceptUserIdByOrgCode(orgCode);
	}
	
	@Transactional
	public PaginationHelper<Organise> getChildOrg(Organise org, String page) {
		return systemDAO.find(org,null,page,
				"com.ksource.liangfa.mapper.OrganiseMapper.getChildOrgCount",
				"com.ksource.liangfa.mapper.OrganiseMapper.getChildOrgList");
	}
	
	@Override
	@Transactional
	public PaginationHelper<Organise> getTurnOverOrg(Map<String, Object> paramMap,String page) {
		try {
			return systemDAO.find(paramMap, page, 
					"com.ksource.liangfa.mapper.OrganiseMapper.getTurnOverOrgCount",
					"com.ksource.liangfa.mapper.OrganiseMapper.getTurnOverOrgList");
			} catch (Exception e) {
				logger.error("查询移送管辖组织机构失败：" + e.getMessage(), e);
			throw new BusinessException("查询移送组织机构失败：" + e.getMessage());
			}
	}
	
	@Override
	@Transactional
	public PaginationHelper<Organise> getClueOrgTree(Organise org, String page) {
		try {
			return systemDAO.find(org,null,page, 
					"com.ksource.liangfa.mapper.OrganiseMapper.getClueOrgTreeCount",
					"com.ksource.liangfa.mapper.OrganiseMapper.getClueOrgTreeList");
			} catch (Exception e) {
				logger.error("查询线索分派组织机构失败：" + e.getMessage(), e);
			throw new BusinessException("查询线索分派组织机构失败：" + e.getMessage());
			}
	}

	@Override
	public Organise selectByorgCode(Integer createOrg) {
		return organiseMapper.selectByorgCode(createOrg);
	}

	@Override
	public Organise selectByPrimaryKey(Integer orgCode) {
		return organiseMapper.selectByPrimaryKey(orgCode);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Organise> findChildOrgs(Organise org, String orgType,Integer parentId) {
		Map<String,Object> paramMap= new HashMap<String,Object>();
        paramMap.put("orgType",orgType);
        if(parentId==null){
        	//当前组织为非行政机关或牵头单位时，查询顶级节点下的所有单位信息，parentId默认为顶级节点。当前组织为行政单位时，只查询本单位信息
        	if(!Const.ORG_TYPE_XINGZHENG.equals(org.getOrgType()) ||org.isLiangfaLeaderOrg()){
        		District district=districtMapper.selectByPrimaryKey(org.getDistrictCode());
        		//如果当前区划的父节点为空，说明当前组织为顶级组织
        		if(StringUtils.isBlank(district.getUpDistrictCode())){
        			parentId=Const.TOP_ORG_ID;
        		}
        	}else{
        		paramMap.put("orgCode",org.getOrgCode());
        	}
        }
        paramMap.put("upOrgCode",parentId);
        
        //当前组织为非行政机关或牵头单位时，增加区划查询条件
        if(org!=null){
            if (!Const.ORG_TYPE_XINGZHENG.equals(org.getOrgType()) ||org.isLiangfaLeaderOrg()) {
                paramMap.put("districtCode", StringUtils.rightTrim0(org.getDistrictCode()));
            }
        }
       
		try {
			return organiseMapper.findChildOrgs(paramMap);
		} catch (Exception e) {
			logger.error("组织机构查询失败：" + e.getMessage());
			throw new BusinessException("组织机构查询失败");
		}
	}

	/**
	 * 案件咨询参与人组织机构查询
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Organise> findParticipantsOrg(Organise org, String orgType,String id) {
		Map<String,Object> paramMap= new HashMap<String,Object>();
		paramMap.put("upOrgCode",id);
		paramMap.put("orgType",orgType);
        paramMap.put("districtCode",org.getDistrictCode());
         
		try {
			return organiseMapper.findParticipantsOrg(paramMap);
		} catch (Exception e) {
			logger.error("案件咨询参与人组织机构查询失败：" + e.getMessage());
			throw new BusinessException("案件咨询参与人组织机构查询失败");
		}
	}
	/**
	 * 行政单位移送其他组织机构树列表
	 * 
	 * @author: LXL
	 * @createTime:2017年11月8日 上午9:44:28
	 */
	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<Organise> findYiSongOtherOrgs(Map<String, Object> param, String page) {
		try {
		return systemDAO.find(param, page, 
				"com.ksource.liangfa.mapper.OrganiseMapper.findYiSongOtherOrgCount",
				"com.ksource.liangfa.mapper.OrganiseMapper.findYiSongOtherOrgList");
		} catch (Exception e) {
			logger.error("查询外部组织机构失败：" + e.getMessage(), e);
		throw new BusinessException("查询外部组织机构失败：" + e.getMessage());
		}
	}

	@Override
	public Organise getDeptByOrgCode(Integer upOrgCode) {
		Organise organise=new Organise();
		List<Organise> list=organiseMapper.getDeptByOrgCode(upOrgCode);
		if(list.size()>0){
			organise=list.get(0);
		}
		return organise;
	}
}

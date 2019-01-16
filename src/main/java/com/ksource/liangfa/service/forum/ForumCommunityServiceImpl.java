package com.ksource.liangfa.service.forum;

import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.domain.ForumCommunity;
import com.ksource.liangfa.mapper.ForumCommunityMapper;

@Service
public class ForumCommunityServiceImpl implements ForumCommunityService {
	
	@Autowired
	private ForumCommunityMapper fcMapper ;
	
	// 日志
	private static final Logger log = LogManager
			.getLogger(ForumCommunityServiceImpl.class);
	
	@Override
	@Transactional(readOnly=true)
	public List<ForumCommunity> seach(ForumCommunity forumCommunity) {
		try {
			List<ForumCommunity> list = fcMapper.seach(forumCommunity) ;
			return list;
		} catch (Exception e) {
			log.error("查询论坛版块信息失败：" + e.getMessage());
			throw new BusinessException("查询论坛版块信息失败!") ;
		}
	}

	@Override
	@Transactional(readOnly=true)
	public int findMAXSort() {
		try {
			return fcMapper.findMAXSort()+1;
		} catch (Exception e) {
			log.error("查询最大排序数信息失败：" + e.getMessage());
			throw new BusinessException("查询最大排序数信息失败!") ;
		}
	}

	@Override
	@Transactional
	public void updateSort(String[] newArray,ForumCommunity forumCommunity) {
		try {
			int[][] ints = resolveStringToInt(newArray) ;
			for(int i=0;i<ints.length;i++) {
				for(int j=0;j<ints[i].length;j+=2) {
					forumCommunity.setId(ints[i][j]) ;
					forumCommunity.setSort(ints[i][j+1]) ;
					fcMapper.updateByPrimaryKeySelective(forumCommunity) ;
				}
			}
		} catch (Exception e) {
			log.error("排序失败：" + e.getMessage());
			throw new BusinessException("排序失败!") ;
		}
	}
	
	public static int[][] resolveStringToInt(String[] strings) {
		try {
			int intLength = strings.length ;
			int[][] ints = new int[intLength][2] ;
			for(int i=0;i<intLength;i++) {
				String[] strings2 = strings[i].split("/") ;
				int j = 0 ;
				for(String ssString : strings2) {
					ints[i][j] = Integer.valueOf(ssString) ;
					j++ ;
				}
			}
			return ints ;
		} catch (NumberFormatException e) {
			log.error("分解字符串失败：" + e.getMessage());
			throw new BusinessException("分解字符串失败!") ;
		}
	}

	
	@Override
	@Transactional
	@LogBusiness(operation="添加论坛版块",business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_INSERT,target_domain_mapper_class = ForumCommunityMapper.class,target_domain_position=0)
	public ServiceResponse insertForumCommunity(ForumCommunity forumCommunity) {
		try {
			ServiceResponse response = new ServiceResponse(true, "添加论坛版块成功！");
			fcMapper.insert(forumCommunity) ;
			return response ;
		} catch (Exception e) {
			log.error("添加论坛版块失败：" + e.getMessage());
			throw new BusinessException("添加论坛版块失败!") ;
		} 
	}

	@Override
	@Transactional
	@LogBusiness(operation="更新论坛版块",business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_UPDATE,target_domain_position = 0, target_domain_mapper_class = ForumCommunityMapper.class)
	public ServiceResponse updateForumCommunity(ForumCommunity forumCommunity) {
		try {
			ServiceResponse response = new ServiceResponse(true, "更新论坛版块成功！");
			fcMapper.updateByPrimaryKeySelective(forumCommunity) ;
			return response;
		} catch (Exception e) {
			log.error("更新论坛版块失败：" + e.getMessage());
			throw new BusinessException("更新论坛版块失败!") ;
		} 		
	}

	@Override
	@Transactional
	@LogBusiness(operation="删除论坛版块",business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_DELETE,target_domain_code_position = 0, target_domain_mapper_class = ForumCommunityMapper.class)
	public ServiceResponse deleteForumCommunity(Integer forumCommunityId) {
		try {
			ServiceResponse response = new ServiceResponse(true, "删除论坛版块成功！");
			fcMapper.deleteByPrimaryKey(forumCommunityId) ;
			return response;
		} catch (Exception e) {
			log.error("删除论坛版块失败：" + e.getMessage());
			throw new BusinessException("删除论坛版块失败!") ;
		}
	}
}

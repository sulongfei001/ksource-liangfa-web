package com.ksource.liangfa.service.specialactivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.ActivityCase;
import com.ksource.liangfa.domain.ActivityCaseExample;
import com.ksource.liangfa.domain.ActivityMember;
import com.ksource.liangfa.domain.ActivityMemberExample;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.SpecialActivity;
import com.ksource.liangfa.mapper.ActivityCaseMapper;
import com.ksource.liangfa.mapper.ActivityMemberMapper;
import com.ksource.liangfa.mapper.SpecialActivityMapper;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PaginationContext;

/**
 * 此类为 TODO:类描述
 * 
 * @author zxl :)
 * @version 1.0 date 2012-7-17 time 下午5:40:39
 */
@Service
public class SpecialActivityServiceImpl implements SpecialActivityService {
	// 日志
	private static final Logger log = LogManager
			.getLogger(SpecialActivityServiceImpl.class);
	@Autowired
	SpecialActivityMapper specialActivityMapper;
	@Autowired
	ActivityMemberMapper activityMemberMapper;
	@Autowired
	SystemDAO systemDao;
	@Autowired
	ActivityCaseMapper activityCaseMapper;
	
	@Override
	@Transactional(readOnly = true)
	public List<SpecialActivity> find(SpecialActivity acti,
			Map<String, Object> map) {
		try {
			PaginationContext<SpecialActivity> con = new PaginationContext<SpecialActivity>();
			Map<String, Object> paramMap = con.getConditionMap(acti);
			if (map != null) {
				paramMap.putAll(map);
			}
			return specialActivityMapper.find(paramMap);
		} catch (Exception e) {
			log.error("查询专项活动失败：" + e.getMessage());
			throw new BusinessException("查询专项活动失败");
		}
	}

	@Override
	@Transactional
	public void add(SpecialActivity acti, String orgIds) {
		try {
			// 保存专项活动
			int actiId = systemDao.getSeqNextVal(Const.TABLE_SPECIAL_ACTIVITY);
			acti.setId(actiId);
			ActivityMember mem = new ActivityMember();
			mem.setActivityId(actiId);
			specialActivityMapper.insertSelective(acti);
			String[] ids = orgIds.split(",");
			// 保存专项活动参与人员
			for (String id : ids) {
				int memId = systemDao
						.getSeqNextVal(Const.TABLE_ACTIVITY_MEMBER);
				mem.setId(memId);
				mem.setMemberCode(Integer.parseInt(id));
				activityMemberMapper.insert(mem);
			}
			//  保存专项活动案件关联关系
			//查询案件
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("startTime",acti.getStartTime());
			paramMap.put("endTime",acti.getEndTime());
			paramMap.put("procKey",Const.CASE_CHUFA_PROC_KEY);
			paramMap.put("id", actiId);
			//oracle:IN中的数据不能超过1000条
			if(orgIds!=null) {
				
				final int subSize = 999;
				int count = ids.length % subSize == 0 ? ids.length / subSize : ids.length /subSize + 1;
				String orgIdStr;
				int start = 0;
				for(int i = 0; i < count; i++) {
					int index = (i + 1) * subSize;
					if(index >= ids.length) {
						orgIdStr = orgIds.substring(start);
					} else {
						int end = orgIds.indexOf(ids[index]);
						orgIdStr = orgIds.substring(start, end);
						start = end;
					}
					if(orgIdStr.endsWith(",")){
						orgIdStr=orgIdStr.substring(0,orgIdStr.length()-1);
					}
					
					paramMap.put("orgIds",orgIdStr);
					specialActivityMapper.addActivityCase(paramMap);
				}
			}
		} catch (Exception e) {
			log.error("添加专项活动失败：" + e.getMessage());
			throw new BusinessException("添加专项活动失败");
		}
	}

	@Override
	@Transactional
	public SpecialActivity findByPk(Integer id) {
		try {
			return specialActivityMapper.findByPk(id);
		} catch (Exception e) {
			log.error("查询专项活动失败：" + e.getMessage());
			throw new BusinessException("查询专项活动失败");
		}
	}

	@Override
	@Transactional
	public void update(SpecialActivity acti, String orgIds) {
		try {
			specialActivityMapper.updateByPrimaryKeySelective(acti);
		
			// 删除原有的专项活动参与机构 
			ActivityMemberExample example = new ActivityMemberExample();
			example.createCriteria().andActivityIdEqualTo(acti.getId());
			activityMemberMapper.deleteByExample(example);
			//添加修改后的项活动参与机构 
			ActivityMember mem = new ActivityMember();
			mem.setActivityId(acti.getId());
			String[] ids = orgIds.split(",");
			for (String id : ids) {
				int memId = systemDao
						.getSeqNextVal(Const.TABLE_ACTIVITY_MEMBER);
				mem.setId(memId);
				mem.setMemberCode(Integer.parseInt(id));
				activityMemberMapper.insert(mem);
			}
			// 删除原有'专项活动和案件'关联关系
			ActivityCaseExample activityCaseExample = new ActivityCaseExample();
			activityCaseExample.createCriteria().andActivityIdEqualTo(acti.getId());
			activityCaseMapper.deleteByExample(activityCaseExample);
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("startTime",acti.getStartTime());
			paramMap.put("endTime",acti.getEndTime());
			paramMap.put("procKey",Const.CASE_CHUFA_PROC_KEY);
			paramMap.put("id", acti.getId());
			//oracle:IN中的数据不能超过1000条
			if(orgIds!=null) {
				
				final int subSize = 999;
				int count = ids.length % subSize == 0 ? ids.length / subSize : ids.length /subSize + 1;
				String orgIdStr;
				int start = 0;
				for(int i = 0; i < count; i++) {
					int index = (i + 1) * subSize;
					if(index >= ids.length) {
						orgIdStr = orgIds.substring(start);
					} else {
						int end = orgIds.indexOf(ids[index]);
						orgIdStr = orgIds.substring(start, end);
						start = end;
					}
					if(orgIdStr.endsWith(",")){
						orgIdStr=orgIdStr.substring(0,orgIdStr.length()-1);
					}
					
					paramMap.put("orgIds",orgIdStr);
					//添加修改后'专项活动和案件'的关联关系
					specialActivityMapper.insertAcivityCase(paramMap);
				}
			}
			
		} catch (Exception e) {
			log.error("修改专项活动失败：" + e.getMessage());
			throw new BusinessException("修改专项活动失败");
		}

	}

	@Override
	@Transactional
	public void del(int id) {
		ActivityMemberExample example = new ActivityMemberExample();
		ActivityCaseExample activityCaseExample = new ActivityCaseExample();
		try {
			specialActivityMapper.deleteByPrimaryKey(id);
			example.createCriteria().andActivityIdEqualTo(id);
			activityMemberMapper.deleteByExample(example);
			activityCaseExample.createCriteria().andActivityIdEqualTo(id);
			activityCaseMapper.deleteByExample(activityCaseExample);
		} catch (Exception e) {
			log.error("删除案件失败：" + e.getMessage());
			throw new BusinessException("删除案件失败");
		}
	}

	@Override
	public PaginationHelper<CaseBasic> queryActivityCase(String page,
			Map<String, Object> paramMap) {
		try {
			return systemDao
					.find(paramMap,
							page,
							"com.ksource.liangfa.mapper.SpecialActivityMapper.getActivityCaseCount",
							"com.ksource.liangfa.mapper.SpecialActivityMapper.getActivityCaseList");
		} catch (Exception e) {
			log.error("查询案件失败：" + e.getMessage());
			throw new BusinessException("查询案件失败");
		}
	}

	@Override
	@Transactional
	public boolean saveChangeActivity(ActivityCase activityCase) {
		boolean flag = false;
		ActivityCaseExample activityCaseExample =new ActivityCaseExample();
		activityCaseExample.createCriteria().andCaseIdEqualTo(activityCase.getCaseId());
		Integer j = activityCaseMapper.deleteByExample(activityCaseExample);
		Integer i = activityCaseMapper.insert(activityCase);
		if(i>0){
			flag = true;
		}
		return flag;
	}

	@Override
	public List<SpecialActivity> getSpecialActivityName() {
		try {
			return specialActivityMapper.getSpecialActivityName();
		} catch (Exception e) {
			log.error("专项活动名称查询失败：" + e.getMessage());
			throw new BusinessException("专项活动名称查询失败");
		}
	}
}
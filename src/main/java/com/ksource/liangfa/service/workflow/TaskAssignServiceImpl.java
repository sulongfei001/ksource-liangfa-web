package com.ksource.liangfa.service.workflow;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.OrganiseExample;
import com.ksource.liangfa.domain.Post;
import com.ksource.liangfa.domain.PostExample;
import com.ksource.liangfa.domain.TaskAssign;
import com.ksource.liangfa.domain.TaskAssignExample;
import com.ksource.liangfa.domain.TaskAssignKey;
import com.ksource.liangfa.domain.TaskBind;
import com.ksource.liangfa.domain.TaskBindKey;
import com.ksource.liangfa.domain.TimeoutWarn;
import com.ksource.liangfa.domain.TimeoutWarnReminder;
import com.ksource.liangfa.domain.TimeoutWarnReminderExample;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.PostMapper;
import com.ksource.liangfa.mapper.TaskAssignMapper;
import com.ksource.liangfa.mapper.TaskBindMapper;
import com.ksource.liangfa.mapper.TimeoutWarnMapper;
import com.ksource.liangfa.mapper.TimeoutWarnReminderMapper;
import com.ksource.liangfa.web.bean.OrgTaskAssign;
import com.ksource.syscontext.Const;

@Service("TaskAssignService")
public class TaskAssignServiceImpl implements TaskAssignService {

	@Autowired
	TaskBindMapper taskBindMapper;
	@Autowired
	OrganiseMapper organiseMapper;
	@Autowired
	PostMapper postMapper;
	@Autowired
	TaskAssignMapper taskAssignMapper;
	@Autowired
	TimeoutWarnMapper timeoutWarnMapper;
	@Autowired
	TimeoutWarnReminderMapper timeoutWarnReminderMapper ;
	
	@Override
	@Transactional(readOnly=true)
	public List<Organise> getOrgTaskAssignList(String procDefId,
			String taskDefId, String districtCode) {
		TaskBindKey taskBindKey = new TaskBindKey();
		taskBindKey.setProcDefId(procDefId);
		taskBindKey.setTaskDefId(taskDefId);
		TaskBind taskBind = taskBindMapper.selectByPrimaryKey(taskBindKey);
		
		String orgType=taskBind.getAssignTarget();
		OrganiseExample organiseExample = new OrganiseExample();
		com.ksource.liangfa.domain.OrganiseExample.Criteria criteria = organiseExample.createCriteria().andDistrictCodeEqualTo(districtCode).andIsDeptEqualTo(Const.STATE_INVALID);
		if(!orgType.equals(Const.TASK_ASSGIN_EQUALS_INPUTER)){
			criteria.andOrgTypeEqualTo(orgType);
		}
		List<Organise> organiseList = organiseMapper.selectByExample(organiseExample);
		for(Organise organise : organiseList){
			List<OrgTaskAssign> orgTaskAssignList = new ArrayList<OrgTaskAssign>();
			organise.setOrgTaskAssignList(orgTaskAssignList);
			organiseExample = new OrganiseExample();
			organiseExample.createCriteria().andIsDeptEqualTo(Const.STATE_VALID).andUpOrgCodeEqualTo(organise.getOrgCode());
			
			List<Organise> deptList = organiseMapper.selectByExample(organiseExample);
			for(Organise dept : deptList){
				OrgTaskAssign orgTaskAssign = new OrgTaskAssign();
				orgTaskAssign.setDept(dept);
				//岗位列表
				PostExample postExample = new PostExample();
				postExample.createCriteria().andDeptIdEqualTo(dept.getOrgCode());
				List<Post> postList = postMapper.selectByExample(postExample);
				orgTaskAssign.setPostList(postList);
				//任务分配
				TaskAssignKey taskAssignKey = new TaskAssignKey();
				taskAssignKey.setProcDefId(procDefId);
				taskAssignKey.setTaskDefId(taskDefId);
				taskAssignKey.setDeptId((dept.getOrgCode()));
				TaskAssign taskAssign = taskAssignMapper.selectByPrimaryKey(taskAssignKey);
				orgTaskAssign.setTaskAssign(taskAssign);
				
				orgTaskAssignList.add(orgTaskAssign);
			}
		}
		return organiseList;
	}


	@Override
	@Transactional
	public void taskAssignBatch(String procDefId, String taskDefId,
			List<List<Integer>> orgCode_post_list) {

		TaskAssignExample forDelete = new TaskAssignExample();
		TaskAssign taskAssign = new TaskAssign();
		taskAssign.setProcDefId(procDefId);
		taskAssign.setTaskDefId(taskDefId);
		taskAssign.setAssignType(Const.TASK_ASSIGN_TYPE_GROUP);
		for(List<Integer> orgCode_post : orgCode_post_list){
			Integer orgCode=orgCode_post.get(0);
			Integer deptId=orgCode_post.get(1);
			Integer postId=orgCode_post.get(2);
			//先删除
			forDelete.clear();
			forDelete.createCriteria().andProcDefIdEqualTo(procDefId).andTaskDefIdEqualTo(taskDefId)
			.andDeptIdEqualTo(deptId);
			taskAssignMapper.deleteByExample(forDelete);
			//再添加
			if(postId!=-1){
			taskAssign.setOrgCode(orgCode);
			taskAssign.setDeptId(deptId);
			taskAssign.setAssignGroup(postId.toString());
			taskAssignMapper.insert(taskAssign);
			}
		}
	}


	public void timeOutWarningBatch(String procDefId, String taskDefId,String userIdString,String districtCode,String dueTime) {
		//添加超时预警信息(先删除再添加)
		TimeoutWarn warn = new TimeoutWarn();
		warn.setProcDefId(procDefId);
		warn.setTaskDefId(taskDefId);
		warn.setDueDate(dueTime);
		timeoutWarnMapper.deleteByPrimaryKey(warn);
		//判断是否预警时间是否空时间(00:00:00)
		boolean isInsert = false;
		if(dueTime!=null&&!dueTime.isEmpty()){
			String[] strs = dueTime.split(":");
			for(String str:strs){
				if(!str.equals("00")){
					isInsert = true;
					break;
				}
			}
		}
		if(isInsert){
			timeoutWarnMapper.insert(warn);
		}
		
//		添加超时提醒人（先删除在添加）
//		删除
		TimeoutWarnReminderExample timeoutWarnReminderExample = new TimeoutWarnReminderExample() ;
		timeoutWarnReminderExample.createCriteria().andProcDefIdEqualTo(procDefId)
		.andTaskDefIdEqualTo(taskDefId)
		.andDistrictCodeEqualTo(districtCode) ;
		timeoutWarnReminderMapper.deleteByExample(timeoutWarnReminderExample) ;
//		添加
		if(StringUtils.isBlank(userIdString)){
			return;
		}
		String[] userIds = userIdString.split("&") ;
		
		TimeoutWarnReminder timeoutWarnReminderKey = new TimeoutWarnReminder() ;
		timeoutWarnReminderKey.setProcDefId(procDefId) ;
		timeoutWarnReminderKey.setTaskDefId(taskDefId) ;
		timeoutWarnReminderKey.setDistrictCode(districtCode) ;
		for(String userId : userIds) {
			timeoutWarnReminderKey.setUserId(userId) ;
			timeoutWarnReminderMapper.insert(timeoutWarnReminderKey) ;
		}
		
	}
	
	
	
	
	@Override
	@Transactional
	public void timeOutWarning(String procDefId, String taskDefId,
			String fromTaskDefId, String userIdString, String districtCode,
			String dueTime) {
		//添加超时预警信息(先删除再添加)
		TimeoutWarn warn = new TimeoutWarn();
		warn.setProcDefId(procDefId);
		warn.setTaskDefId(taskDefId);
		warn.setFromTaskDefId(fromTaskDefId);
		warn.setDueDate(dueTime);
		timeoutWarnMapper.deleteByPrimaryKey(warn);
		//判断是否预警时间是否空时间(00:00:00)
		boolean isInsert = false;
		if(dueTime!=null&&!dueTime.isEmpty()){
			String[] strs = dueTime.split(":");
			for(String str:strs){
				if(!str.equals("00")){
					isInsert = true;
					break;
				}
			}
		}
		if(isInsert){
			timeoutWarnMapper.insert(warn);
		}
		
	}


	@Override
	@Transactional
	public void timeOutWarningReminder(String procDefId, String taskDefId,
			String userIdString, String districtCode) {
//		添加超时提醒人（先删除在添加）
//		删除
		TimeoutWarnReminderExample timeoutWarnReminderExample = new TimeoutWarnReminderExample() ;
		timeoutWarnReminderExample.createCriteria().andProcDefIdEqualTo(procDefId)
		.andTaskDefIdEqualTo(taskDefId)
		.andDistrictCodeEqualTo(districtCode) ;
		timeoutWarnReminderMapper.deleteByExample(timeoutWarnReminderExample) ;
//		添加
		if(StringUtils.isBlank(userIdString)){
			return;
		}
		String[] userIds = userIdString.split("&") ;
		
		TimeoutWarnReminder timeoutWarnReminderKey = new TimeoutWarnReminder() ;
		timeoutWarnReminderKey.setProcDefId(procDefId) ;
		timeoutWarnReminderKey.setTaskDefId(taskDefId) ;
		timeoutWarnReminderKey.setDistrictCode(districtCode) ;
		for(String userId : userIds) {
			timeoutWarnReminderKey.setUserId(userId) ;
			timeoutWarnReminderMapper.insert(timeoutWarnReminderKey) ;
		}
		
	}


	
	@Transactional
	public TaskBind getTaskBind(String procDefId, String taskDefId) {
		TaskBindKey taskBindKey = new TaskBindKey();
		taskBindKey.setProcDefId(procDefId);
		taskBindKey.setTaskDefId(taskDefId);
		TaskBind taskBind = taskBindMapper.selectByPrimaryKey(taskBindKey);
		return taskBind;
	}
}

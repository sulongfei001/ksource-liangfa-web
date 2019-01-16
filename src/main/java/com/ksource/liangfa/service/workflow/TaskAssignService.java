package com.ksource.liangfa.service.workflow;

import java.util.List;

import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.TaskBind;

/**
 * 流程任务办理人配置
 * @author gengzi
 *
 */
public interface TaskAssignService {

	/**
	 * 查询某个任务节点的所有机构的任务办理配置信息、和岗位信息
	 * List(Organise(List(OrgTaskAssign)))
	 * @param procDefId		流程定义ID
	 * @param taskDefId		流程任务定义ID
	 * @param districtCode	行政区划代码
	 * @return
	 */
	List<Organise> getOrgTaskAssignList(String procDefId,String taskDefId,String districtCode);

	/**
	 * 批量配置任务办理人（同一任务）
	 * @param procDefId		流程定义id
	 * @param taskDefId		任务定义id
	 * @param orgCode_post_list		[[orgCode,postId],...]
	 */
	void taskAssignBatch(String procDefId, String taskDefId,
			List<List<Integer>> orgCode_post_list);

	/**
	 * 设置预警时间
	 * @param procDefId
	 * @param taskDefId
	 * @param fromTaskDefId
	 * @param userIdString
	 * @param districtCode
	 * @param dueTime
	 * @author XT 2014-3-8
	 */
	void timeOutWarning(String procDefId, String taskDefId,
			String fromTaskDefId, String userIdString, String districtCode,
			String dueTime);
	
	/**
	 * 设置预警提醒人
	 * @param procDefId
	 * @param taskDefId
	 * @param userIdString
	 * @param districtCode
	 * @author XT 2014-3-8
	 */
	void timeOutWarningReminder(String procDefId, String taskDefId,
			String userIdString, String districtCode);
	
	
	/**
	 * 查询某个任务节点的绑定信息
	 * @param procDefId
	 * @param taskDefId
	 * @return
	 */
	public TaskBind getTaskBind(String procDefId, String taskDefId);
}

package com.ksource.liangfa.service.workflow;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseParty;
import com.ksource.liangfa.domain.CaseStep;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.workflow.ProcBusinessEntityImpl;
import com.ksource.liangfa.workflow.task.TaskVO;
/**
 * 工作流管理接口 
 * @author junxy
 *
 */
public interface WorkflowService {
	
	/**
	 * 获取登录用户的待办任务，应该包含直接分配给该登录用户及用户所在组的任务  集合
	 * @param user   当前登录人
	 * @param size	  任务数量（0表示获得全部任务）
	 * @return  待办任务集合
	 */
	public PaginationHelper<TaskVO> queryToDoTasks(User user,int size,String page,Map<String,Object> paramMap);
	
	/**
	 * 任务办理（TODO：动态表单数据的保存、数据上传）
	 * @param userId	任务办理人
	 * @param taskId	任务ID
	 * @param actionId	任务提交动作id
	 * @param assignTarget	任务提交目标（下一步任务分配目标）
	 * @param parameterMap	保存所有的表单字段（key：file name，value：value array）
	 * @param multipartFileMap	保存所有file类型的字段值（key：file name，value：MultipartFile）
	 */
	public ServiceResponse taskDeal(String userId,String taskId,Integer actionId,String assignTarget,
			Map<String, String[]> parameterMap,Map<String,MultipartFile> multipartFileMap);
	
	/**
	 * 获取登录用户的已办任务信息
	 * @param user  登录用户
	 * @return 流程任务信息及案件信息
	 */
	public PaginationHelper<ProcBusinessEntityImpl> queryCompletedTasks(ProcBusinessEntityImpl procBusinessEntity,String userId,Integer page);
	
	/**
	 * 根据案件id，查询案件处理步骤和任务步骤的流程图设计信息
	 * @param caseId
	 * @return
	 */
	public List<CaseStep> queryStepInfoAndProcDiagramByCaseId(String caseId,String procKey);
	/**
	 * 根据步骤id，查询案件处理步骤和办理信息
	 * @param stepId
	 * @return
	 */
	public CaseStep queryStepInfoAndDeal(Long stepId);

    /**
	  * 回退案件流程(如果案件此时案件流程已经结束或上一节点是录入案件将不做回退)
	 * @param caseId
	 */
    ServiceResponse rollBack(String caseId,User user);
    
    /**
	 * 获取登录用户的待办任务，应该包含直接分配给该登录用户及用户所在组的任务  集合
	 * @param user   当前登录人
	 * @param size	  任务数量（0表示获得全部任务）
	 * @return  待办任务集合(具有违法情形的案件，不包含普通案件)
	 */
	public PaginationHelper<TaskVO> queryIllegalToDoTasks(User user,int size,String page,Map<String,Object> paramMap);

	/**
	 * 查询公安受理之后的待办(诉讼信息案件录入)
	 *
	 * @param user
	 * @param i
	 * @param page
	 * @param paramMap
	 * @return
	 */
	public PaginationHelper<TaskVO> queryLawsuitTodoTasks(User user, String page, Map<String, Object> paramMap);

	/**
	 * 任务办理（TODO：动态表单数据的保存、数据上传）
	 * @param userId	任务办理人
	 * @param taskId	任务ID
	 * @param actionId	任务提交动作id
	 * @param assignTarget	任务提交目标（下一步任务分配目标）
	 * @param parameterMap	保存所有的表单字段（key：file name，value：value array）
	 * @param multipartFileMap	保存所有file类型的字段值（key：file name，value：MultipartFile）
	 */
	public ServiceResponse xingzhenglianTaskDeal(String userId,String taskId,Integer actionId,String assignTarget);
	
	
	/**
	 * 没有启动流程的案件回退
	 * @param caseId
	 * @param rollBackType
	 * @param user
	 * @return
	 */
   ServiceResponse noProcRollBack(String caseId,Integer rollBackType,User user);
}

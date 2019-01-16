package com.ksource.liangfa.workflow;

import java.util.Map;

import com.ksource.liangfa.domain.*;
import com.ksource.liangfa.workflow.proc.CaseProcessUtil;
import com.ksource.liangfa.workflow.proc.ChufaCaseProcUtil;
import com.ksource.liangfa.workflow.proc.DutyCaseProcUtil;
import com.ksource.liangfa.workflow.proc.WeijiCaseProcUtil;
import com.ksource.liangfa.workflow.stepView.*;
import com.ksource.liangfa.workflow.task.ChufaTaskVO;
import com.ksource.liangfa.workflow.task.DutyTaskVO;
import com.ksource.liangfa.workflow.task.TaskVO;
import com.ksource.liangfa.workflow.task.WeijiTaskVO;
import com.ksource.liangfa.workflow.task.view.ChufaTaskDealView;
import com.ksource.liangfa.workflow.task.view.DutyTaskDealView;
import com.ksource.liangfa.workflow.task.view.WeijiTaskDealView;
import com.ksource.liangfa.workflow.view.CaseProcView;
import com.ksource.liangfa.workflow.view.CaseProcViewCHufa;
import com.ksource.liangfa.workflow.view.CaseProcViewCaseDuty;
import com.ksource.liangfa.workflow.view.CaseProcViewWeiji;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.ThreadContext;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: zxl
 * Date: 13-3-11
 * Time: 上午9:00
 * <br>与流程相关的类(流程办理查看，流程办理，流程查看，流程步骤查看等)的创建工厂
 * <br>TODO:之后可考虑对这些对象添加对象池或是单例特性。
 */
public final class ProcessFactory {
    /**
     * 走流程：与案件相关的流程处理对象生成方法(依据procKey生成相应的流程处理对象)
     *
     * @param caseDomain
     * @param <T>
     * @return 如果参数为空或都参数对象中的procKey字段为空，则返回null
     */
    public static <T extends ProcBusinessEntity> CaseProcessUtil createProcessUtil(T caseDomain) {
        String procKey;
        if (caseDomain == null || (procKey = caseDomain.getProcKey()) == null) {
            return null;
        }
        CaseProcessUtil caseProcessUtil = null;
        if (Const.CASE_CHUFA_PROC_KEY.equals(procKey) || Const.CASE_CRIME_PROC_KEY.equals(procKey) || Const.CASE_YISONGXINGZHENG_PROC_KEY.equals(procKey)) {
            caseProcessUtil = new ChufaCaseProcUtil((CaseBasic) caseDomain);
        } else if (Const.CASE_ZHIWUFANZUI_PROC_KEY.equals(procKey)) {
            caseProcessUtil = new DutyCaseProcUtil((CaseDutyWithBLOBs) caseDomain);
        } else if (Const.CASE_WEIJI_PROC_KEY.equals(procKey)) {
            caseProcessUtil = new WeijiCaseProcUtil((CaseWeijiWithBLOBs) caseDomain);
        }
        return caseProcessUtil;
    }


    /**
     * 案件详情处加载步骤：与案件相关的流程步骤查看对象生成方法(依据procKey生成相应的流程步骤查看对象)
     *
     * @param caseStep
     * @param <T>
     * @return 如果参数为空或都参数对象中的procDefKey字段为空，则返回null
     */
    public static <T extends ProcBusinessEntity> CaseProcStepView createCaseProcStepView(CaseStep caseStep) {
        String procKey = null;
        if (caseStep == null || StringUtils.isBlank((procKey = caseStep.getProcDefKey()))) {
            return null;
        }
        CaseProcStepView caseProcStepView = null;
        User optUser = ThreadContext.getCurUser();
        if (Const.CASE_CHUFA_PROC_KEY.equals(procKey)||Const.PENALTY_PROC_KEY.equals(procKey)) {
            caseProcStepView = new ChufaProcStepView(caseStep, optUser);
        } else if (Const.CASE_CRIME_PROC_KEY.equals(procKey)) {
            caseProcStepView = new CrimeProcStepView(caseStep, optUser);
        } else if (Const.CASE_YISONGXINGZHENG_PROC_KEY.equals(procKey)) {
            caseProcStepView = new YisongProcStepView(caseStep, optUser);
        } else if (Const.CASE_WEIJI_PROC_KEY.equals(procKey)) {
            caseProcStepView = new WeijiProcStepView(caseStep, optUser);
        } else if (Const.CASE_ZHIWUFANZUI_PROC_KEY.equals(procKey)) {
            caseProcStepView = new DutyProcStepView(caseStep, optUser);
        }
        return caseProcStepView;
    }

    /**
     * 案件详情：与案件相关的流程查看对象生成方法(依据procKey生成相应的流程查看对象)
     *
     * @param procKey    流程类型
     * @param caseId     要查看的案件的id
     * @param viewStepId 第一次打开流程查看界面默认显示的案件步骤的id
     * @return procKey或caseId为null或空字符串值时, 则返回null.
     */
    public static CaseProcView createCaseProcView(String procKey, String caseId, String viewStepId) {
        if (StringUtils.isBlank(procKey) || StringUtils.isBlank(caseId)) {
            return null;
        }
        CaseProcView caseProcView = null;
        if (Const.CASE_CHUFA_PROC_KEY.equals(procKey)
                || Const.CASE_YISONGXINGZHENG_PROC_KEY.equals(procKey)
                || Const.CASE_CRIME_PROC_KEY.equals(procKey)
                ) {
            caseProcView = new CaseProcViewCHufa(caseId, viewStepId);
        } else if (Const.CASE_WEIJI_PROC_KEY.equals(procKey)) {
            caseProcView = new CaseProcViewWeiji(caseId, viewStepId);
        } else if (Const.CASE_ZHIWUFANZUI_PROC_KEY.equals(procKey)) {
            caseProcView = new CaseProcViewCaseDuty(caseId, viewStepId);
        }
        return caseProcView;
    }

    /**
     * 待办：与案件相关的流程待办查看对象生成方法(依据procKey生成相应的流程待办查看对象)
     *
     * @param procKeyObj 流程类型对象
     * @param task    任务对象
     * @param caseId  案件id
     * @return 一旦有一个参数无效(null或空字符串值), 则返回null.
     */
    public static TaskVO createTaskVO(ProcKey procKeyObj, Task task, String caseId,Map<String,Object> paraMap) {
        if (procKeyObj == null || task == null || StringUtils.isBlank(caseId)) {
            return null;
        }
        String userId = ThreadContext.getCurUser().getUserId();
        TaskVO taskVO = null;
        String procKey = procKeyObj.getProcKey();
        if (Const.CASE_CHUFA_PROC_KEY.equals(procKey)
                || Const.CASE_YISONGXINGZHENG_PROC_KEY.equals(procKey)
                || Const.CASE_CRIME_PROC_KEY.equals(procKey)) {
            taskVO = new ChufaTaskVO(task, procKeyObj, caseId, userId,paraMap);
        } else if (Const.CASE_WEIJI_PROC_KEY.equals(procKey)) {
            taskVO = new WeijiTaskVO(task, procKeyObj, caseId, userId,paraMap);
        } else if (Const.CASE_ZHIWUFANZUI_PROC_KEY.equals(procKey)) {
            taskVO = new DutyTaskVO(task, procKeyObj, caseId, userId,paraMap);
        }
        return taskVO;
    }

    /**
     * 办理界面：查询案件办理界面所需要的数据及相应视图并封装成ModelAndView
     *
     * @param procKey  流程类型key
     * @param taskInfo 与案件相关的流程任务信息
     * @param caseId   案件id
     * @return  一旦有一个参数无效(null或空字符串值), 则返回null.
     */
    public static ModelAndView createTaskDealView(String procKey, Task taskInfo, String caseId) {
        if (taskInfo == null || StringUtils.isBlank(procKey) || StringUtils.isBlank(caseId)) {
            return null;
        }
        ModelAndView view = null;
        if (Const.CASE_CHUFA_PROC_KEY.equals(procKey)
                || Const.CASE_YISONGXINGZHENG_PROC_KEY.equals(procKey)
                || Const.CASE_CRIME_PROC_KEY.equals(procKey)
                ) {
            view = new ChufaTaskDealView(taskInfo, caseId).getModelView();
        } else if (Const.CASE_WEIJI_PROC_KEY.equals(procKey)) {
            view = new WeijiTaskDealView(taskInfo, caseId).getModelView();
        } else if (Const.CASE_ZHIWUFANZUI_PROC_KEY.equals(procKey)) {
            view = new DutyTaskDealView(taskInfo, caseId).getModelView();
        }
        return view;
    }
}

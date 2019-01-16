package com.ksource.liangfa.web.basic;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.WarnCondition;
import com.ksource.liangfa.service.casehandle.WarnConditionService;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping(value="/basic/warnCondition")
public class WarnConditionController {
    
    @Autowired
    private WarnConditionService warnConditionService;
    
    /**
     * 预警规则列表
     *
     * @param warnCondition
     * @param page
     * @param request
     * @return
     */
    @RequestMapping(value="list")
    public ModelAndView list(WarnCondition warnCondition,String page,HttpServletRequest request){
        ModelAndView mv = new ModelAndView("basic/warnCondition/warnConditionList");
        PaginationHelper<WarnCondition> conditionList = warnConditionService.queryCondition(warnCondition, null, page);
        mv.addObject("conditionList", conditionList);
        return mv;
    }
    
    
    /**
     * 跳转预警规则添加、编辑页面
     *
     * @param conditionId
     * @param request
     * @return
     */
    @RequestMapping(value="edit")
    public ModelAndView edit(Integer conditionId,HttpServletRequest request){
        ModelAndView mv = new ModelAndView("basic/warnCondition/warnConditionEdit");
        if(conditionId != null){
            WarnCondition warnCondition = warnConditionService.selectByPrimaryKey(conditionId);
            mv.addObject("warnCondition", warnCondition);
        }
        return mv;
    }
    
    /**
     * 保存预警规则信息
     *注意：修改时先删除旧的规则，再添加新的规则
     *
     * @param warnCondition
     * @param request
     * @return
     */
    @RequestMapping(value="save")
    public ModelAndView save(WarnCondition warnCondition,HttpServletRequest request){
        ModelAndView mv = new ModelAndView("redirect:/basic/warnCondition/list");
        if(warnCondition.getConditionId() != null){
            warnConditionService.deleteByPrimaryKey(warnCondition.getConditionId());
        }
        User currUser = SystemContext.getCurrentUser(request);
        warnCondition.setCreateUser(currUser.getUserId());
        warnCondition.setCreateTime(new Date());
        warnConditionService.save(warnCondition);
        return mv;
    }
    
    /**
     * 删除预警规则
     *
     * @param conditionId
     * @return
     */
    @RequestMapping(value="delete")
    @ResponseBody
    public boolean delete(Integer conditionId){
        int count = warnConditionService.deleteByPrimaryKey(conditionId);
        return count > 0;
    }
    
    
    /**
     *根据预警类型查询预警规则数量，同一种类型只能添加一条规则配置
     *
     * @param warnType
     * @param conditionId 如果conditionId不为空，则为修改操作
     * @return
     */
    @RequestMapping(value="getCountByWarType")
    @ResponseBody
    public boolean getCountByWarType(Integer warnType,Integer conditionId){
        if(conditionId != null){
            return true;
        }
        WarnCondition warnCondition = null;
        warnCondition = warnConditionService.getByWarnType(warnType);
        if (warnCondition != null) {
            return false;
        } else {
            return true;
        }
    }
}

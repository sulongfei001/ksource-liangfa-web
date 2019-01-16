package com.ksource.liangfa.service.casehandle;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.WarnCondition;
import com.ksource.liangfa.mapper.WarnConditionMapper;
import com.ksource.syscontext.Const;

@Service
public class WarnConditionServiceImpl implements WarnConditionService{

    @Autowired
    private WarnConditionMapper warnConditionMapper;
    @Autowired
    private SystemDAO systemDAO;
    
    @Override
    public PaginationHelper<WarnCondition> queryCondition(WarnCondition warnCondition,Map<String,Object> paramMap,String page) {
        return systemDAO.find(warnCondition, paramMap, page, "queryConditionCount", "queryConditionList");
    }
    
    @Override
    public WarnCondition selectByPrimaryKey(Integer conditionId){
        return warnConditionMapper.selectByPrimaryKey(conditionId);
    }
    
    @Override
    public int deleteByPrimaryKey(Integer conditionId){
        return warnConditionMapper.deleteByPrimaryKey(conditionId);
    }

    @Override
    public void save(WarnCondition warnCondition) {
        warnCondition.setConditionId(systemDAO.getSeqNextVal(Const.TABLE_WARN_CONDITION));
        String formula = "";
        //构建超时预警数据库公式：当前时间-最后办理时间 > 60 天
        //floor(to_number(sysdate-(create_time+0))) > 60 //oracle 数据库timestamp类型加0转换位date类型
        //办理超时、滞留超时
        if(warnCondition.getWarnType().intValue() == Const.WARN_CONDITION_TYPE_1 
                || warnCondition.getWarnType().intValue() == Const.WARN_CONDITION_TYPE_3){
            formula = "floor(to_number(sysdate"+warnCondition.getOptType()+"("+warnCondition.getJudgeCol()+"+ 0))) "+warnCondition.getJudgeType()+ " "+warnCondition.getJudgeValue();
        }else if(warnCondition.getWarnType().intValue() == Const.WARN_CONDITION_TYPE_2){
            //构建涉案金额预警数据库公式：涉案金额 大于 50000 元
            //amount_involved > 50000 元
            formula = warnCondition.getJudgeCol() + warnCondition.getJudgeType() + warnCondition.getJudgeValue();
        }
        warnCondition.setConditionFormula(formula);
        warnConditionMapper.insert(warnCondition);
    }

    @Override
    public WarnCondition getByWarnType(int warnType) {
        return warnConditionMapper.getByWarnType(warnType);
    }

    @Override
    public List<WarnCondition> queryAll() {
        return warnConditionMapper.queryAll();
    }
    
}


package com.ksource.liangfa.service.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.util.EncryptUtil;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.AccuseRule;
import com.ksource.liangfa.domain.AccuseRuleInfo;
import com.ksource.liangfa.domain.AccuseRuleRelation;
import com.ksource.liangfa.domain.CaseAccuseRuleRelation;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.AccuseRuleMapper;
import com.ksource.liangfa.mapper.AccuseRuleRelationMapper;
import com.ksource.liangfa.mapper.CaseAccuseRuleRelationMapper;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.util.AemanticAnalysisUtil;
import com.ksource.liangfa.util.StringUtil;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Service
public class AccuseRuleServiceImpl implements AccuseRuleService {
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(AccuseRuleServiceImpl.class);

	@Autowired
	private SystemDAO systemDao;
	@Autowired
	private AccuseRuleMapper accuseRuleMapper;
	@Autowired
	private AccuseRuleRelationMapper accuseRuleRelationMapper;
	
	@Autowired
    private CaseAccuseRuleRelationMapper  caseAccuseRuleRelationMapper;
	@Autowired
	private CaseBasicMapper caseBasicMapper;
	@Autowired
	private DistrictService districtService;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private InstantMessageService instantMessageService;
	
	@Override
	public PaginationHelper<AccuseRule> find(AccuseRule accuseRule, String page) {
		try {
			return systemDao.find(accuseRule, page);
		} catch (Exception e) {
			log.error("查询失败：" + e.getMessage());
			throw new BusinessException("查询失败");
		}
	}

	@Override
	@Transactional
	public ServiceResponse insert(AccuseRule accuseRule) {
		ServiceResponse response = new ServiceResponse(true, "添加规则成功!");
		try {
			accuseRule.setId(Long.valueOf(systemDao.getSeqNextVal(Const.ACCUSE_RULE)));
			//添加规则和罪名关系
			String[] accuseId=accuseRule.getAccuseId().split(",");
			if(accuseId.length>0){
				for(String i:accuseId){
					AccuseRuleRelation accuseRuleRelation=new AccuseRuleRelation();
					accuseRuleRelation.setAccuseId(Long.valueOf(i));
					accuseRuleRelation.setRuleId(accuseRule.getId());
					accuseRuleRelationMapper.insert(accuseRuleRelation);
				}
			}
			accuseRuleMapper.insert(accuseRule);
			return response;
		} catch (Exception e) {
			log.error("添加规则失败：" + e.getMessage());
			throw new BusinessException("添加规则失败");
		}
	}

	@Override
	public AccuseRule selectById(String id) {
		return accuseRuleMapper.selectById(Long.valueOf(id));
	}
	
	/**
	 * 删除 需要级联删除 删除关联信息表 accuse_rule_relation
	 */
	@Override
	@Transactional
	public void del(String id) {
		try{
			if(StringUtil.isNotEmpty(id)){
				 accuseRuleMapper.deleteByPrimaryKey(Long.valueOf(id));
				 accuseRuleRelationMapper.delByRuleId(Long.valueOf(id));
			}
			
		}catch(Exception e){
			log.error("删除罪名规则信息失败：" + e.getMessage());
			throw new BusinessException("删除罪名规则信息失败");
		}
	}

	@Override
	@Transactional
	public ServiceResponse updateByPrimaryKeySelective(AccuseRule accuseRule) {
		ServiceResponse response = new ServiceResponse(true, "修改规则成功!");
		try {
			//先删除规则罪名中间表，再重新添加中间表数据，再更新规则表
			accuseRuleRelationMapper.delByRuleId(Long.valueOf(accuseRule.getId()));
			//添加规则和罪名关系
			String[] accuseId=accuseRule.getAccuseId().split(",");
			AccuseRuleRelation accuseRuleRelation=new AccuseRuleRelation();
			if(accuseId.length>0){
				for(String i:accuseId){
					if(StringUtil.isNotEmpty(i)){
						accuseRuleRelation.setAccuseId(Long.valueOf(i));
						accuseRuleRelation.setRuleId(accuseRule.getId());
						accuseRuleRelationMapper.insert(accuseRuleRelation);
					}
				}
			}
			accuseRuleMapper.updateByPrimaryKeySelective(accuseRule);
			return response;
		} catch (Exception e) {
			log.error("修改规则失败：" + e.getMessage());
			throw new BusinessException("修改规则失败");
		}
	}

	/**
	 * 校验规则名是否已经存在
	 * 
	 * @author: LXL
	 * @createTime:2017年9月27日 下午6:08:46
	 */
	@Override
	public boolean checkRuleName(Map<String,String> paramMap) {
		try {
			int i = accuseRuleMapper.checkRuleName(paramMap);
			if (i == 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			log.error("规则名称异步校验失败：" + e.getMessage());
			throw new BusinessException("规则名称异步校验失败");
		}
	}
	 /**
     * 罪名规则匹配案件基本信息并且保存案件罪名规则关联信息
     * 查出当前用户所属的行业的所有的规则信息
     * 用有效的案件字段信息对比获取罪名规则信息
     * @param caseBasic
     */
	@Override
	public CaseBasic matchCaseRule(CaseBasic caseBasic, String industryType,HttpServletRequest request) {
    	List<AccuseRule> accuseRules = accuseRuleMapper.getByIndustryType(industryType);
    	//获取案件罪名规则信息
    	for(AccuseRule accuseRule:accuseRules) {
    		boolean matchSuccess = analysisRelation(caseBasic, accuseRule);
        	if(matchSuccess) {
        		CaseAccuseRuleRelation accuseRuleRelation = new CaseAccuseRuleRelation();
            	accuseRuleRelation.setCaseId(caseBasic.getCaseId());
            	accuseRuleRelation.setRuleId(accuseRule.getId());
            	caseAccuseRuleRelationMapper.insert(accuseRuleRelation);
            	caseBasic.setIsSuspectedCriminal(1);
            	
                User currentUser = SystemContext.getCurrentUser(request);
                Organise org=currentUser.getOrganise();
                Integer districtJb=0;
                District district=districtService.selectByPrimaryKey(org.getDistrictCode().toString());
                if(district!=null && district.getJb()>0){
                    districtJb=district.getJb();
                }
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("districtJB", districtJb);
                List<User> userList=new ArrayList<User>();
                userList=userMapper.getUserByDistrictJb(map);
                instantMessageService.addCrimeCaseMessage(caseBasic,userList);
        	}
    	}
		return caseBasic;
	}
	
	
	/**
	 * 传入匹配值和区间 匹配成功返回true 区间传入0-0返回false,区间相等为固定值
	 * @param number
	 * @param numberBefore
	 * @param numberAfter
	 * @return
	 */
	private boolean numberMacth(Double number,Double numberBefore,Double numberAfter){
		boolean matchSuccess = false;
		if(number != null && numberBefore != null && numberAfter != null){
			if(number.compareTo(numberBefore) > 0 && number.compareTo(numberAfter) < 0) {
				matchSuccess = true;
			}
		}else if(number != null && numberBefore != null){// money < after
			if(number.compareTo(numberBefore) > 0) {
				matchSuccess = true;
			}
		}else if(number != null && numberAfter != null){// before < money < after
			if(number.compareTo(numberAfter)<0) {
				matchSuccess = true;
			}
		}
		return matchSuccess;
	}

	@Override
	public List<CaseBasic> analysis(Long accuseRuleId, String industryType) {
		List<CaseBasic> caseList = new ArrayList<CaseBasic>();
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("caseState", Const.CHUFA_PROC_2);
		param.put("industryType", industryType);
		List<CaseBasic> caseBasicList= caseBasicMapper.getCaseByIndustryType(param);
		//查询规则
    	AccuseRule accuseRule = accuseRuleMapper.selectById(accuseRuleId);
	    for (CaseBasic caseBasic : caseBasicList) {
	    	boolean matchSuccess = analysisRelation(caseBasic, accuseRule);
	        if(matchSuccess) {
	        	CaseAccuseRuleRelation accuseRuleRelation = new CaseAccuseRuleRelation();
	        	accuseRuleRelation.setCaseId(caseBasic.getCaseId());
	        	accuseRuleRelation.setRuleId(accuseRuleId);
	        	caseAccuseRuleRelationMapper.insert(accuseRuleRelation);
	        	CaseBasic basic = new CaseBasic();
	        	basic.setCaseId(caseBasic.getCaseId());
	        	basic.setIsSuspectedCriminal(1);
	        	caseBasicMapper.updateByPrimaryKeySelective(basic);
	        	caseList.add(caseBasic);
	        }else {
	            List<CaseAccuseRuleRelation> list = caseAccuseRuleRelationMapper.selectByCaseId(caseBasic.getCaseId());
	            if(list == null || list.size() == 0){
	                CaseBasic basic = new CaseBasic();
	                basic.setCaseId(caseBasic.getCaseId());
	                basic.setIsSuspectedCriminal(0);
	                caseBasicMapper.updateByPrimaryKeySelective(basic);  
	            }
	        }
	    }
	    return caseList;
	}

	/**
	 * 量刑标准研判标准：
	 * 一个标准包含多个规则：各规则之间是或的关系，即满足一个就认定为疑似犯罪案件。
	 * 一个规则中包含多个条件(可扩展其他条件)：1.案件详情的分析、2.涉案金额的大小、3.涉案物品数量的大小组成，各条件之间是且的关系，
	 * 即需要同时满足3个条件才能认定为符合该规则。
	 * 1.案件详情需大于50个字才会进行分析
	 * */
	private boolean analysisRelation(CaseBasic caseBasic, AccuseRule accuseRule) {
		Double liAnAmountInvolved = caseBasic.getAmountInvolved();//立案时涉案金额
    	String goodsCount  =  caseBasic.getGoodsCount();//涉案物品数量
    	String caseDetail  = caseBasic.getCaseDetailName();
    	List<AccuseRuleInfo> ruleItemList = accuseRule.getRuleInfos();
    	for(AccuseRuleInfo ruleItem:ruleItemList) {
    		String caseDetailKeyWord = ruleItem.getCaseDetail();//案件简介关键字
    		Double moneyNumberBefore = ruleItem.getMoneyNumberBefore();//涉案金额大于
    		Double moneyNumberAfter  = ruleItem.getMoneyNumberAfter();//涉案金额小于
    		Double goodsCountNumberBefore = ruleItem.getGoodsCountNumberBefore();//违法物品大于
    		Double goodsCountNumberAfter  = ruleItem.getGoodsCountNumberAfter();//违法物品小于
    		Double detailThreshold = ruleItem.getDetailThreshold();
    		//案件详情的分析
    		int matchNum = 0;
			if(StringUtil.isNotEmpty(caseDetailKeyWord)){
			   if(StringUtil.isNotEmpty(caseDetail)){
	               caseDetail =  EncryptUtil.decryptByAES(caseDetail);//案件详情解密
	                if(caseDetail.length() > 50) {
	                    double result = AemanticAnalysisUtil.aemanticAnalysis(caseDetail, caseDetailKeyWord);
	                    if(detailThreshold == null) {
	                        detailThreshold = AemanticAnalysisUtil.DETAIL_JIZHI;
	                    }
	                    if(!Double.isNaN(result) && result > detailThreshold) {
	                        matchNum += 1;
	                        caseBasic.setScore(result);
	                    }else {
	                        return false;
	                    }
	                }else{
	                    return false;
	                } 
			   }else{
			       return false;
			   }
			}
			//立案时涉案金额判断
			if(moneyNumberBefore != null || moneyNumberAfter != null) {
			    if(liAnAmountInvolved != null){
		             if(numberMacth(liAnAmountInvolved, moneyNumberBefore, moneyNumberAfter)) {
		                    matchNum += 1;
		                }else {
		                    return false;
		                }
			    }else{
			        return false;
			    }
			}
			//涉案物品数量判断
			if(goodsCountNumberBefore != null || goodsCountNumberAfter != null){
		         if(StringUtils.isNotEmpty(goodsCount)){
	                 try {
	                    Double goodsCountValue = Double.valueOf(goodsCount);
	                    if(goodsCountValue != null) {
	                        if(numberMacth(goodsCountValue, goodsCountNumberBefore, goodsCountNumberAfter)) {
	                            matchNum += 1;
	                        }else {
	                            return false;
	                        }
	                    }else{
	                        return false;
	                    }
	                  } catch (Exception e) {
	                      log.debug("涉案物品数量格式转换异常："+e.getMessage());
	                  }
	            }else{
	                return false;
	            }
			}
			if(matchNum > 0) {
				return true;
			}else {
				return false;
			}
		}
    	return false;
	}
}

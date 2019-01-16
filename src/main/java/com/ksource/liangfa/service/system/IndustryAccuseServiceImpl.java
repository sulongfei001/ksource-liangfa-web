package com.ksource.liangfa.service.system;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.IndustryAccuse;
import com.ksource.liangfa.domain.IndustryAccuseExample;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.IndustryAccuseMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class IndustryAccuseServiceImpl implements IndustryAccuseService {
	
	@Autowired
	private SystemDAO systemDao;
	@Autowired
	private IndustryAccuseMapper industryAccuseMapper;
	@Autowired
	private CaseBasicMapper caseBasicMapper;
	@Autowired
	private OrganiseMapper orgMapper;
	// 日志
	private static final Logger log = LogManager
			.getLogger(IndustryAccuseServiceImpl.class);

	@Override
	public PaginationHelper<IndustryAccuse> find(
			IndustryAccuse industryAccuse, String page) {
		try {
			return systemDao.find(industryAccuse, page);
		} catch (Exception e) {
			log.error("查询失败：" + e.getMessage());
			throw new BusinessException("查询失败");
		}
	}

	@Override
	@Transactional
	//@LogBusiness(operation = "添加违法情形", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_position = 0, target_domain_mapper_class = IllegalSituationMapper.class)
	public ServiceResponse insert(IndustryAccuse industryAccuse) {
		ServiceResponse response = new ServiceResponse(true, "添加成功!");
		try {
			if(StringUtils.isNotBlank(industryAccuse.getAccuseId())){
				String[] accuse = industryAccuse.getAccuseId().split(",");
				for (int i = 0; i < accuse.length; i++) {
					String accuseId = accuse[i].trim().toString();
					//循环插入接收表
					industryAccuse.setAccuseId(accuseId);
					//根据industryType和accuseId查询数据，如果信息已存在，则不再添加
					IndustryAccuseExample example=new IndustryAccuseExample();
					example.createCriteria().andAccuseIdEqualTo(accuseId).andIndustryTypeEqualTo(industryAccuse.getIndustryType());
					List<IndustryAccuse> list=industryAccuseMapper.selectByExample(example);
					IndustryAccuse temp=null;
					if(list!=null && list.size()>0){
						temp=list.get(0);
					}
					if(temp==null ){
						industryAccuseMapper.insert(industryAccuse);
					}
				}
			}
			return response;
		} catch (Exception e) {
			log.error("添加行业罪名信息失败：" + e.getMessage());
			throw new BusinessException("添加行业罪名信息失败");
		}
	}

	
	@Override
	@Transactional
	//@LogBusiness(operation = "删除违法情形", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_DELETE, target_domain_code_position = 0, target_domain_mapper_class = IllegalSituationMapper.class)
	public int del(String industryType,String accuseId) {
		try {
			IndustryAccuseExample example=new IndustryAccuseExample();
			example.createCriteria().andIndustryTypeEqualTo(industryType).andAccuseIdEqualTo(accuseId);
			return industryAccuseMapper.deleteByExample(example);
		} catch (Exception e) {
			log.error("删除行业罪名信息失败：" + e.getMessage());
			throw new BusinessException("删除行业罪名信息失败");
		}
	}

	@Override
	public List<IndustryAccuse> queryAccuseListByIndustry(
			String caseId) {
		CaseBasic c=caseBasicMapper.selectByPrimaryKey(caseId);
		Organise o=null;
		if(c!=null && c.getInputUnit() != null){
			o=orgMapper.selectByPrimaryKey(c.getInputUnit());
		}
		IndustryAccuse industryAccuse=new IndustryAccuse();
		if(o!=null && StringUtils.isNotBlank(o.getIndustryType())){
			industryAccuse.setIndustryType(o.getIndustryType());
			//获取当前登录用户的组织机构类型
			List<IndustryAccuse> list=industryAccuseMapper.queryAccuseListByIndustry(industryAccuse);
			return list;
		}else{
			return null;
		}
	}

	
}

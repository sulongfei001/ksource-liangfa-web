package com.ksource.liangfa.service.system;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.IllegalSituation;
import com.ksource.liangfa.domain.IllegalSituationAccuse;
import com.ksource.liangfa.domain.IllegalSituationAccuseExample;
import com.ksource.liangfa.mapper.IllegalSituationAccuseMapper;
import com.ksource.liangfa.mapper.IllegalSituationMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.syscontext.Const;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IllegalSituationServiceImpl implements IllegalSituationService {
	
	@Autowired
	private SystemDAO systemDao;
	@Autowired
	private IllegalSituationMapper illegalSituationMapper;
	@Autowired
	private IllegalSituationAccuseMapper illegalSituationAccuseMapper;
	// 日志
	private static final Logger log = LogManager
			.getLogger(IllegalSituationServiceImpl.class);

	@Override
	public PaginationHelper<IllegalSituation> find(
			IllegalSituation illegalSituation, String page) {
		try {
			return systemDao.find(illegalSituation, page);
		} catch (Exception e) {
			log.error("查询失败：" + e.getMessage());
			throw new BusinessException("查询失败");
		}
	}

	@Override
	@Transactional
	//@LogBusiness(operation = "添加违法情形", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_position = 0, target_domain_mapper_class = IllegalSituationMapper.class)
	public ServiceResponse insert(IllegalSituation illegalSituation) {
		ServiceResponse response = new ServiceResponse(true, "添加违法情形成功!");
		try {
			illegalSituation.setId(String.valueOf(systemDao.getSeqNextVal(Const.TABLE_ILLEGAL_SITUATION)));
			//添加违法情形和罪名关联表信息
			IllegalSituationAccuse illegalSituationAccuse=new IllegalSituationAccuse();
			String[] accuseId=illegalSituation.getAccuseId().split(",");
			if(accuseId.length>0){
				for(String i:accuseId){
					illegalSituationAccuse.setIllegalSituationId(illegalSituation.getId());
					illegalSituationAccuse.setAccuseId(Integer.valueOf(i));
					illegalSituationAccuseMapper.insert(illegalSituationAccuse);
				}
			}
			//添加违法情形信息
			illegalSituation.setAccuseId("");//把违法情形表的accuse_id设置为空，不添加数据
			illegalSituationMapper.insert(illegalSituation);
			return response;
		} catch (Exception e) {
			log.error("添加违法情形失败：" + e.getMessage());
			throw new BusinessException("添加违法情形失败");
		}
	}

	@Override
	public ServiceResponse updateByPrimaryKeySelective(
			IllegalSituation illegalSituation) {
		ServiceResponse response = new ServiceResponse(true, "修改违法情形成功!");
		try {
			//先删除违法情形罪名中间表，再重新添加中间表数据，再更新违法情形表
			IllegalSituationAccuseExample example=new IllegalSituationAccuseExample();
			example.createCriteria().andIllegalSituationIdEqualTo(illegalSituation.getId());
			illegalSituationAccuseMapper.deleteByExample(example);
			//添加关联表
			//添加违法情形和罪名关联表信息
			IllegalSituationAccuse illegalSituationAccuse=new IllegalSituationAccuse();
			String[] accuseId=illegalSituation.getAccuseId().split(",");
			if(accuseId.length>0){
				for(String i:accuseId){
					illegalSituationAccuse.setIllegalSituationId(illegalSituation.getId());
					illegalSituationAccuse.setAccuseId(Integer.valueOf(i));
					illegalSituationAccuseMapper.insert(illegalSituationAccuse);
				}
			}
			//更新违法情形表
			illegalSituation.setAccuseId("");//把违法情形表的accuse_id设置为空，不更新数据
			illegalSituationMapper.updateByPrimaryKeySelective(illegalSituation);
			return response;
		} catch (Exception e) {
			log.error("修改违法情形失败：" + e.getMessage());
			throw new BusinessException("修改违法情形失败");
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public IllegalSituation selectById(String id) {
		try {
			List<IllegalSituation> list= illegalSituationMapper.selectById(id);
			return list.get(0);
		} catch (Exception e) {
			log.error("查询违法情形失败：" + e.getMessage());
			throw new BusinessException("查询违法情形失败");
		}
	}
	
	@Override
	@Transactional
	//@LogBusiness(operation = "删除违法情形", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_DELETE, target_domain_code_position = 0, target_domain_mapper_class = IllegalSituationMapper.class)
	public int del(String id) {
		try {
			IllegalSituationAccuseExample example=new IllegalSituationAccuseExample();
			example.createCriteria().andIllegalSituationIdEqualTo(id);
			//先删除关联表
			illegalSituationAccuseMapper.deleteByExample(example);
			//删除违法情形表
			return illegalSituationMapper.deleteByPrimaryKey(id);
			
		} catch (Exception e) {
			log.error("删除违法情形失败：" + e.getMessage());
			throw new BusinessException("删除违法情形失败");
		}
	}

	@Override
	public List<IllegalSituation> selectByIndustryType(String industryType) {
		Map<String,String> param = new HashMap<String, String>();
		param.put("industryType", industryType);
		return illegalSituationMapper.selectByOrgCode(param);
	}
}

package com.ksource.liangfa.service.system;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseParty;
import com.ksource.liangfa.domain.IllegalSituationAccuseExample;
import com.ksource.liangfa.domain.PunishBasis;
import com.ksource.liangfa.domain.PunishBasisTerm;
import com.ksource.liangfa.domain.PunishBasisTermExample;
import com.ksource.liangfa.mapper.DiscretionStandardMapper;
import com.ksource.liangfa.mapper.PunishBasisMapper;
import com.ksource.liangfa.mapper.PunishBasisTermMapper;
import com.ksource.syscontext.Const;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PunishBasisServiceImpl implements PunishBasisService {
	
	@Autowired
	private SystemDAO systemDao;
	@Autowired
	private PunishBasisMapper punishBasisMapper;
	@Autowired
	private PunishBasisTermMapper punishBasisTermMapper;
	@Autowired
	private DiscretionStandardMapper discretionStandardMapper;
	// 日志
	private static final Logger log = LogManager
			.getLogger(PunishBasisServiceImpl.class);

	@Override
	public PaginationHelper<PunishBasis> find(
			PunishBasis punishBasis, String page) {
		try {
			return systemDao.find(punishBasis, page);
		} catch (Exception e) {
			log.error("查询失败：" + e.getMessage());
			throw new BusinessException("查询失败");
		}
	}

	@Override
	@Transactional
	public ServiceResponse insert(PunishBasis punishBasis,List<String> termInfoAry) {
		ServiceResponse response = new ServiceResponse(true, "添加行政处罚依据成功!");
		try {
			punishBasis.setBasisId(systemDao.getSeqNextVal(Const.TABLE_PUNISH_BASIS));
			punishBasisMapper.insert(punishBasis);
			//添加行政处罚依据项
			PunishBasisTerm punishBasisTerm=new PunishBasisTerm();
			if(termInfoAry!=null){
				for(String temp:termInfoAry){
					punishBasisTerm.setBasisId(punishBasis.getBasisId());
					punishBasisTerm.setTermId(systemDao.getSeqNextVal(Const.TABLE_PUNISH_BASIS_TERM));
					punishBasisTerm.setTermInfo(temp);
					punishBasisTermMapper.insert(punishBasisTerm);
				}
			}
			return response;
		} catch (Exception e) {
			log.error("添加行政处罚依据失败：" + e.getMessage());
			throw new BusinessException("添加行政处罚依据失败");
		}
	}

	@Override
	@Transactional
	public ServiceResponse update(PunishBasis punishBasis) {
		ServiceResponse response = new ServiceResponse(true, "修改行政处罚依据成功!");
		try {
			punishBasisMapper.updateByPrimaryKeySelective(punishBasis);
			//如果行政处罚依据项id不为空，则修改，如果为空则添加
			List<PunishBasisTerm> termList = null;
			if (StringUtils.isNotBlank(punishBasis.getTermJson())) {
				termList = JSON.parseArray(punishBasis.getTermJson(),PunishBasisTerm.class);
	        }
			PunishBasisTerm punishBasisTerm=new PunishBasisTerm();
			if(termList!=null){
				//对行政处罚依据先进行删除再添加！
				punishBasisTermMapper.deleteByBasisId(punishBasis.getBasisId());
				
				for(PunishBasisTerm term:termList){
					if(term.getTermId()!=null && term.getTermId()!=0){
						term.setBasisId(punishBasis.getBasisId());
						punishBasisTermMapper.insert(term);
					}else{
						punishBasisTerm.setBasisId(punishBasis.getBasisId());
						punishBasisTerm.setTermId(systemDao.getSeqNextVal(Const.TABLE_PUNISH_BASIS_TERM));
						punishBasisTerm.setTermInfo(term.getTermInfo());
						punishBasisTermMapper.insert(punishBasisTerm);
					}
				}
			}
			return response;
		} catch (Exception e) {
			log.error("修改行政处罚依据失败：" + e.getMessage());
			throw new BusinessException("修改行政处罚依据失败");
		}
	}

	@Override
	@Transactional
	public int del(Integer basisId) {
		try {
			//先删除行政处罚裁量标准信息,再删除行政处罚依据项信息,再删除处罚依据信息
			discretionStandardMapper.deleteByBasisId(basisId);
			punishBasisTermMapper.deleteByBasisId(basisId);
			return punishBasisMapper.deleteByPrimaryKey(basisId);
		} catch (Exception e) {
			log.error("删除行政处罚依据失败：" + e.getMessage());
			throw new BusinessException("删除行政处罚依据失败");
		}
	}

	@Override
	public PunishBasis selectByPrimaryKey(Integer id) {
		return punishBasisMapper.selectByPrimaryKey(id);
	}

}

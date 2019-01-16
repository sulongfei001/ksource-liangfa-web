package com.ksource.liangfa.service.system;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.IllegalSituation;
import com.ksource.liangfa.domain.IndustryInfo;
import com.ksource.liangfa.mapper.IndustryInfoMapper;
import com.ksource.syscontext.Const;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class IndustryInfoServiceImpl implements IndustryInfoService {
	
	@Autowired
	private SystemDAO systemDao;
	@Autowired
	private IndustryInfoMapper industryInfoMapper;
	// 日志
	private static final Logger log = LogManager
			.getLogger(IndustryInfoServiceImpl.class);

	
	@Override
	@Transactional(readOnly = true)
	public List<IndustryInfo> selectAll() {
		try {
			List<IndustryInfo> list= industryInfoMapper.selectAll();
			return list;
		} catch (Exception e) {
			log.error("查询违法情形失败：" + e.getMessage());
			throw new BusinessException("查询违法情形失败");
		}
	}


	@Override
	public ServiceResponse insert(IndustryInfo industryInfo) {
		ServiceResponse response = new ServiceResponse(true, "添加行业信息成功!");
		try {
			industryInfoMapper.insert(industryInfo);
			return response;
		} catch (Exception e) {
			log.error("添加行业信息失败：" + e.getMessage());
			throw new BusinessException("添加行业信息失败");
		}
	}


	@Override
	public ServiceResponse updateByPrimaryKeySelective(IndustryInfo industryInfo) {
		ServiceResponse response = new ServiceResponse(true, "修改行业信息成功!");
		try {
			industryInfoMapper.updateByPrimaryKeySelective(industryInfo);
			return response;
		} catch (Exception e) {
			log.error("修改行业信息失败：" + e.getMessage());
			throw new BusinessException("修改行业信息失败");
		}
	}


	@Override
	public int del(String industryType) {
		try {
			return industryInfoMapper.deleteByPrimaryKey(industryType);
		} catch (Exception e) {
			log.error("删除行业信息失败：" + e.getMessage());
			throw new BusinessException("删除行业信息失败");
		}
	}


	@Override
	public IndustryInfo selectById(String industryType) {
		try {
			IndustryInfo industryInfo= industryInfoMapper.selectByPrimaryKey(industryType);
			return industryInfo;
		} catch (Exception e) {
			log.error("查询行业信息失败：" + e.getMessage());
			throw new BusinessException("查询行业信息失败");
		}
	}


	@Override
	public PaginationHelper<IndustryInfo> find(IndustryInfo industryInfo,
			String page) {
		try {
			return systemDao.find(industryInfo, page);
		} catch (Exception e) {
			log.error("查询行业信息失败：" + e.getMessage());
			throw new BusinessException("查询行业信息失败");
		}
	}


	@Override
	public List<IndustryInfo> findIndustryList() {
		try {
			List<IndustryInfo> list= industryInfoMapper.findIndustryList();
			return list;
		} catch (Exception e) {
			log.error("查询行业信息失败：" + e.getMessage());
			throw new BusinessException("查询行业信息失败");
		}
	}
	
}

package com.ksource.liangfa.service.website.maintain;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.WebPrograma;
import com.ksource.liangfa.mapper.WebProgramaMapper;

/**
 *@author wangzhenya
 *@2012-10-26 上午11:41:52
 */
@Service
public class WebProgramaServiceImpl implements WebProgramaService {

	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private WebProgramaMapper webProgramaMapper;
	
	private static final Logger LOG = Logger.getLogger(WebProgramaServiceImpl.class);
	
	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<WebPrograma> find(WebPrograma webPrograma,String page) {
		try {
			return systemDAO.find(webPrograma, page);
		} catch (Exception e) {
			LOG.error("栏目管理查询失败！" + e.getMessage());
			throw new BusinessException("栏目管理查询失败！");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<WebPrograma> webProgramaTree() {
		try {
			return webProgramaMapper.webProgramaTree();
		} catch (Exception e) {
			LOG.error("栏目管理Tree查询失败！" + e.getMessage());
			throw new BusinessException("栏目管理Tree查询失败！");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<WebPrograma> selectHomeLocation() {
		try {
			return webProgramaMapper.selectHomeLocation();
		} catch (Exception e) {
			LOG.error("根据栏目在首页的显示位置字段排序查询栏目失败！" + e.getMessage());
			throw new BusinessException("根据栏目在首页的显示位置字段排序查询栏目失败！");
		}
	}

}

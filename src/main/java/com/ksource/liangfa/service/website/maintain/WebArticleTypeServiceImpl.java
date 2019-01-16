package com.ksource.liangfa.service.website.maintain;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.WebArticleType;

/**
 *@author wangzhenya
 *@2012-10-26 下午5:06:31
 */
@Service
public class WebArticleTypeServiceImpl implements WebArticleTypeService {

	@Autowired
	private SystemDAO systemDAO;
	
	private static final Logger LOG = Logger.getLogger(WebArticleTypeServiceImpl.class);
	
	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<WebArticleType> find(WebArticleType webArticleType,
			String page) {
		try {
			return systemDAO.find(webArticleType, page);
		} catch (Exception e) {
			LOG.error("查询文章类型失败！" + e.getMessage());
			throw new BusinessException("查询文章类型失败！");
		}
	}

}

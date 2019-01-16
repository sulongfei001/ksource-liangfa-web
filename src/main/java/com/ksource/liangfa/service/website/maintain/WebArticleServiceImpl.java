package com.ksource.liangfa.service.website.maintain;

import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.util.FileUtil;
import com.ksource.common.util.RegExpUtil;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.WebArticle;
import com.ksource.liangfa.mapper.WebArticleMapper;
import com.ksource.liangfa.service.system.UserServiceImpl;
import com.ksource.syscontext.Const;

@Service
public class WebArticleServiceImpl implements WebArticleService {

	@Autowired
	private SystemDAO systemDao;
	
	@Autowired
	private WebArticleMapper webArticleMapper;

	// 日志
	private static final Logger log = LogManager
			.getLogger(UserServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<WebArticle> find(WebArticle webArticle, String page) {
		try {
			return systemDao.find(webArticle, page);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询文章失败：" + e.getMessage());
			throw new BusinessException("查询文章失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public WebArticle find(Integer articleId) {
		try {
			WebArticle article = webArticleMapper.findByPrimaryKey(articleId);
			return article;
			
		} catch (Exception e) {
			log.error("查询文章失败：" + e.getMessage());
			throw new BusinessException("查询文章失败");
		}
	}


	@Override
	@Transactional
	@LogBusiness(operation = "添加文章", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_position = 0, target_domain_mapper_class = WebArticleMapper.class)
	public ServiceResponse add(WebArticle webArticle,
			MultipartHttpServletRequest attachmentFile) {
		List<MultipartFile> files = attachmentFile.getFiles("file");
		ServiceResponse response = new ServiceResponse(true, "添加文章成功!");
		try {
			webArticle.setArticleId(systemDao.getSeqNextVal(Const.TABLE_WEB_ARTICLE));
			
			for (int i = 0; i < files.size(); i++) {
				MultipartFile mFile = files.get(i);
				if (mFile != null && !mFile.isEmpty()) {
					UpLoadContext upLoad = new UpLoadContext(
							new UploadResource());
					String url = upLoad.uploadFileApp(mFile, null);
					webArticle.setImagePath(url);
				}
			}
			webArticleMapper.insert(webArticle);
			return response;
		} catch (Exception e) {
			log.error("文章添加失败" + e.getMessage());
			throw new BusinessException("文章添加失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "修改文章", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_UPDATE, target_domain_position = 0, target_domain_mapper_class = WebArticleMapper.class)
	public ServiceResponse updateByPrimaryKeySelective(WebArticle article) {
		ServiceResponse response = new ServiceResponse(true, "修改文章成功!");
		try {
			webArticleMapper.updateByPrimaryKeySelective(article);
			return response;
		} catch (Exception e) {
			log.error("修改文章失败" + e.getMessage());
			throw new BusinessException("修改文章失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "删除文章", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_DELETE, target_domain_code_position = 0, target_domain_mapper_class = WebArticleMapper.class)
	public int del(Integer articleId) {
		try {
			WebArticle article = webArticleMapper.selectByPrimaryKey(articleId);
			// 1.删除图片文件
			FileUtil.deleteFile(RegExpUtil.getImgSrcFromHtml(article.getArticleContent()));
			// 2.删除附件
			String imagePath = article.getImagePath();
			if(imagePath!=null&&imagePath!=""){
				FileUtil.deleteFileInDisk(imagePath);
			}
			// 3.删除公告信息
			return webArticleMapper.deleteByPrimaryKey(articleId);
		} catch (Exception e) {
			log.error("删除文章失败：" + e.getMessage());
			throw new BusinessException("删除文章失败");
		}
	}
	
	@Override
	@Transactional
	@LogBusiness(operation = "批量删除文章", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_BATCH_DELETE, target_domain_code_position = 0, target_domain_mapper_class = WebArticleMapper.class)
	public ServiceResponse batchDelete(Integer[] ids) {
		ServiceResponse response = new ServiceResponse(true, "批量删除文章成功！");
		WebArticle webArticle = null;
		try {
			for(Integer id : ids) {
				webArticle = webArticleMapper.selectByPrimaryKey(id);
				if(webArticle != null) {
					// 1.删除图片文件
					FileUtil.deleteFile(RegExpUtil.getImgSrcFromHtml(webArticle.getArticleContent()));
					// 2.删除附件
					String imagePath = webArticle.getImagePath();
					if(imagePath!=null&&imagePath!=""){
						FileUtil.deleteFileInDisk(imagePath);
					}
					// 3.删除公告信息
					webArticleMapper.deleteByPrimaryKey(id);
				}
			}
			return response;
		} catch (Exception e) {
			log.error("批量删除文章失败：" + e.getMessage());
			throw new BusinessException("批量删除文章失败");
		}
	}
}
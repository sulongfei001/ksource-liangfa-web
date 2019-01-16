package com.ksource.liangfa.service.info;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.common.util.FileUtil;
import com.ksource.common.util.RegExpUtil;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.LayInfo;
import com.ksource.liangfa.domain.ZhifaInfo;
import com.ksource.liangfa.mapper.LayInfoMapper;
import com.ksource.liangfa.mapper.ZhifaInfoMapper;

@Service
public class InfoServiceImpl implements InfoService {
	@Autowired
	private SystemDAO systemDao;
	@Autowired
	private LayInfoMapper layInfoMapper;
	@Autowired
	private ZhifaInfoMapper zhifaInfoMapper;
	// 日志
	private static final Logger log = LogManager
			.getLogger(InfoServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<LayInfo> queryInfos(LayInfo info, String page) {
		try {
			return systemDao.find(info, page);
		} catch (Exception e) {
			log.error("查询法律法规失败：" + e.getMessage());
			throw new BusinessException("查询法律法规失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<LayInfo> queryInfos(LayInfo layInfo, int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", 0);
		map.put("end", pageSize);

		try {
			return systemDao.getList(map, layInfo, null);
		} catch (Exception e) {
			log.error("查询法律法规失败：" + e.getMessage());
			throw new BusinessException("查询法律法规失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<ZhifaInfo> queryZhiFaInfos(ZhifaInfo info,
			String page) {
		try {
			return systemDao.find(info, page);
		} catch (Exception e) {
			log.error("查询执法动态失败：" + e.getMessage());
			throw new BusinessException("查询执法动态失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<LayInfo> queryLayInfos(LayInfo layInfo) {
		try {
			return systemDao.find(layInfo);
		} catch (Exception e) {
			log.error("查询法律法规失败：" + e.getMessage());
			throw new BusinessException("查询法律法规失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "删除法律法规", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_DELETE, target_domain_code_position = 0, target_domain_mapper_class = LayInfoMapper.class)
	public ServiceResponse delLay(String infoId) {
		ServiceResponse response = new ServiceResponse(true, "删除法律法规成功!");
		try {
			LayInfo layInfo = layInfoMapper.selectByPrimaryKey(infoId);
			layInfoMapper.deleteByPrimaryKey(infoId);
			// 删除图片文件
//			FileUtil.deleteFile(RegExpUtil.getImgSrcFromHtml(layInfo.getContent()));
			return response;
		} catch (Exception e) {
			log.error("删除法律法规失败：" + e.getMessage());
			throw new BusinessException("删除法律法规失败");
		}

	}

	@Override
	@Transactional
	@LogBusiness(operation = "删除执法动态信息", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_DELETE, target_domain_code_position = 0, target_domain_mapper_class = ZhifaInfoMapper.class)
	public ServiceResponse delZhifa(String infoId) {
		ServiceResponse response = new ServiceResponse(true, "删除执法动态信息成功!");
		try {
			ZhifaInfo zhifaInfo = zhifaInfoMapper.selectByPrimaryKey(infoId);
			zhifaInfoMapper.deleteByPrimaryKey(infoId);
			// 删除图片文件
			FileUtil.deleteFile(RegExpUtil.getImgSrcFromHtml(zhifaInfo
					.getContent()));
			return response;
		} catch (Exception e) {
			log.error("删除执法动态信息失败：" + e.getMessage());
			throw new BusinessException("删除执法动态信息失败");
		}

	}

	@Override
	@Transactional
	@LogBusiness(operation = "添加法律法规", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_position = 0, target_domain_mapper_class = LayInfoMapper.class)
	public ServiceResponse insertLay(LayInfo layInfo) {
		ServiceResponse response = new ServiceResponse(true, "添加法律法规成功!");
		try {
			layInfoMapper.insert(layInfo);
			return response;
		} catch (Exception e) {
			log.error("添加法律法规失败：" + e.getMessage());
			throw new BusinessException("添加法律法规失败");
		}

	}

	@Override
	@Transactional
	@LogBusiness(operation = "修改法律法规", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_UPDATE, target_domain_position = 0, target_domain_mapper_class = LayInfoMapper.class)
	public ServiceResponse updateLayByPrimaryKeySelective(LayInfo layInfo) {
		ServiceResponse response = new ServiceResponse(true, "修改法律法规成功!");
		try {
			layInfoMapper.updateByPrimaryKeySelective(layInfo);
			return response;
		} catch (Exception e) {
			log.error("修改法律法规失败：" + e.getMessage());
			throw new BusinessException("修改法律法规失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "添加执法动态", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_position = 0, target_domain_mapper_class = ZhifaInfoMapper.class)
	public ServiceResponse insertZhifaInfo(ZhifaInfo info) {
		ServiceResponse response = new ServiceResponse(true, "添加执法动态信息成功!");
		try {
			zhifaInfoMapper.insertSelective(info);
			return response;
		} catch (Exception e) {
			log.error("添加执法动态信息失败：" + e.getMessage());
			throw new BusinessException("添加执法动态信息失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "修改执法动态", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_UPDATE, target_domain_position = 0, target_domain_mapper_class = ZhifaInfoMapper.class)
	public ServiceResponse updateZhifaByPrimaryKeySelective(ZhifaInfo info) {
		ServiceResponse response = new ServiceResponse(true, "修改执法动态信息成功!");
		try {
			zhifaInfoMapper.updateByPrimaryKeySelective(info);
			return response;
		} catch (Exception e) {
			log.error("修改执法动态信息失败：" + e.getMessage());
			throw new BusinessException("修改执法动态信息失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "批量删除法律法规", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_BATCH_DELETE, target_domain_code_position = 0, target_domain_mapper_class = LayInfoMapper.class)
	public ServiceResponse batchDeleteLay(String[] infoIds) {
		ServiceResponse response = new ServiceResponse(true, "批量删除法律法规成功!");
		try {
			LayInfo layInfo = null;
			for(String infoId:infoIds){
				layInfo = layInfoMapper.selectByPrimaryKey(infoId);
				if(layInfo!=null){
					// 删除图片文件
//					List<String> urls = RegExpUtil.getImgSrcFromHtml(layInfo.getContent());
					layInfoMapper.deleteByPrimaryKey(infoId);
//					FileUtil.deleteFile(urls);	
				}
				
			}
			return response;
		} catch (Exception e) {
			log.error("批量删除法律法规失败：" + e.getMessage());
			throw new BusinessException("批量删除法律法规失败");
		}

	}

	@Override
	@Transactional
	@LogBusiness(operation = "批量删除执法动态信息", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_BATCH_DELETE, target_domain_code_position = 0, target_domain_mapper_class = ZhifaInfoMapper.class)
	public ServiceResponse batchDeleteZhifa(String[] check) {
			ServiceResponse response = new ServiceResponse(true, "批量删除执法动态信息成功!");
			try {
				ZhifaInfo zhifaInfo = null;
				for(String infoId:check){
					zhifaInfo = zhifaInfoMapper.selectByPrimaryKey(infoId);
					if(zhifaInfo != null){
					zhifaInfoMapper.deleteByPrimaryKey(infoId);
					// 删除图片文件
					FileUtil.deleteFile(RegExpUtil.getImgSrcFromHtml(zhifaInfo
							.getContent()));
					}
				}
				return response;
			} catch (Exception e) {
				log.error("批量删除执法动态信息失败：" + e.getMessage());
				throw new BusinessException("批量删除执法动态信息失败");
			}
	}

	@Override
	public LayInfo selectByPrimaryKey(String infoId) {
		return layInfoMapper.selectByPrimaryKey(infoId);
	}
}
/**
 * 
 */
package com.ksource.liangfa.service.casehandle;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.AdmdivLicenseApprove;
import com.ksource.liangfa.domain.AdmdivLicenseInfo;
import com.ksource.liangfa.domain.AdmdivLicenseReply;
import com.ksource.liangfa.domain.AdmdivLicenseReplyExample;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.AdmdivLicenseApproveMapper;
import com.ksource.liangfa.mapper.AdmdivLicenseInfoMapper;
import com.ksource.liangfa.mapper.AdmdivLicenseReplyMapper;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.syscontext.Const;

/**
 * @author XT
 * 2013-1-7
 */
@Service
public class AdmdivLicenseServiceImpl implements AdmdivLicenseService {
	
	Logger logger=LogManager.getLogger(AdmdivLicenseServiceImpl.class);
	
	@Autowired
	SystemDAO systemDAO;
	@Autowired
	AdmdivLicenseInfoMapper infoMapper;
	@Autowired
	AdmdivLicenseApproveMapper approveMapper;
	@Autowired
	DistrictMapper districtMapper;
	@Autowired
	AdmdivLicenseReplyMapper replyMapper;
	@Autowired
	UserMapper userMapper;

	@Override
	public void add(AdmdivLicenseApprove approve, AdmdivLicenseInfo info,
			MultipartHttpServletRequest attachmentFiles) {
		try {
			Integer id = getLicenseSeq();
			info.setLicenseId(id);
			info.setReplyStatus(Const.REPLY_NO);
			approve.setLicenseId(id);
			//上传申请书
			MultipartFile file = attachmentFiles.getFile("applyForm");
			if (file!=null) {
				UpLoadContext upLoadContext=new UpLoadContext(new UploadResource());
				String path = upLoadContext.uploadFile(file, null);
				info.setFilePath(path);
				info.setFileName(file.getOriginalFilename());
			}
			infoMapper.insertSelective(info);
			approveMapper.insertSelective(approve);
		} catch (Exception e) {
			logger.error("行政许可添加失败！"+e.getMessage());
			throw new BusinessException("行政许可添加失败！");
		}
	}

	@Override
	@Transactional(readOnly=true)
	public Integer getLicenseSeq() {
		try {
			return systemDAO.getSeqNextVal(Const.TABLE_ADMDIV_LICENSE_INFO);
		} catch (Exception e) {
			logger.error("生成行政许可ID失败"+e.getMessage());
			throw new BusinessException("生成行政许可ID失败");
		}
	}

	/**
	 * @return
	 * @author XT
	 */
	@Override
	@Transactional(readOnly=true)
	public Integer getFiledSeq() {
		return systemDAO.getSeqNextVal(Const.TABLE_ADMDIV_LICENSE_FILED);
	}


	@Override
	@Transactional(readOnly=true)
	public Integer getOptionSeq() {
		return systemDAO.getSeqNextVal(Const.TABLE_ADMDIV_LICENSE_OPTION);
	}

	@Override
	@Transactional(readOnly=true)
	public AdmdivLicenseInfo detail(Integer licenseId) {
		AdmdivLicenseInfo info = infoMapper.selectByPrimaryKey(licenseId);
		District district=districtMapper.selectByPrimaryKey(info.getDistrictCode());
		info.setDistrictName(district.getDistrictName());
		return info;
	}

	@Override
	@Transactional(readOnly=true)
	public Integer getReplySeq() {
		return systemDAO.getSeqNextVal(Const.TABLE_ADMDIV_LICENSE_REPLY);
	}
	
	@Override
	@Transactional
	public Boolean reply(AdmdivLicenseReply reply) {
		try {
			AdmdivLicenseInfo info=new AdmdivLicenseInfo();
			reply.setReplyId(getReplySeq());
			replyMapper.insertSelective(reply);
			//更新行政许可信息表的状态
			info.setLicenseId(reply.getLicenseId());
			if (reply.getReplyType()==Const.REPLY_TYPE_FILED) {
				info.setReplyStatus(Const.REPLY_YES);
			}
			if (reply.getReplyType()==Const.REPLY_TYPE_OPTION) {
				info.setReplyStatus(Const.REPLY_OPTION);
			}
			infoMapper.updateByPrimaryKeySelective(info);
		} catch (Exception e) {
			logger.error("行政许可批复添加失败"+e.getMessage());
			throw new BusinessException("行政许可批复添加失败!");
		}
		return true;
	}

	@Override
	@Transactional
	public AdmdivLicenseReply queryReply(AdmdivLicenseReply reply) {
		AdmdivLicenseReplyExample example=new AdmdivLicenseReplyExample();
		example.createCriteria().andLicenseIdEqualTo(reply.getLicenseId());
		List<AdmdivLicenseReply> list=replyMapper.selectByExample(example);
		AdmdivLicenseReply result = list.get(0);
		User user = userMapper.selectByPrimaryKey(result.getReplyUser());
		result.setReplyerName(user.getUserName());
		SimpleDateFormat formtDate=new SimpleDateFormat("yyyy-MM-dd");
		result.setFormtDate(formtDate.format(result.getReplyTime()));
		return result;
	}

}

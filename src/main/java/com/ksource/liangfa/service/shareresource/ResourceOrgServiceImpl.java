package com.ksource.liangfa.service.shareresource;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.exception.BusinessException;
import com.ksource.liangfa.domain.FileOrg;
import com.ksource.liangfa.domain.FileOrgExample;
import com.ksource.liangfa.domain.FileOrgKey;
import com.ksource.liangfa.mapper.FileOrgMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;

@Service
public class ResourceOrgServiceImpl implements ResourceOrgService {

	// 日志
	private static final Logger log = LogManager
			.getLogger(ResourceOrgServiceImpl.class);

	@Autowired
	OrganiseMapper organiseMapper;
	@Autowired
	FileOrgMapper fileOrgMapper;

	@Override
	@Transactional(readOnly=true)
	public FileOrg find(Integer fileId)  {
		FileOrg fo = new FileOrg();
		FileOrgExample example = new FileOrgExample();
		List<FileOrgKey> list = null;
		String orgs = "";
		try {
			example.createCriteria().andFileIdEqualTo(fileId);
			list = fileOrgMapper.selectByExample(example);
		} catch (Exception e) {
			
		}
		if (list!=null&&list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				FileOrgKey key = list.get(i);
				orgs += key.getOrgId() + ",";
			}
		}
		fo.setOrgs(orgs);
		return fo;
	}
	
	
	
	@Override
	@Transactional
	public void authorize(FileOrg fo)  {
		try {
			Integer fileId = fo.getFileId();
			String orgs = fo.getOrgs();
			del(fileId);
			if (!"".equals(orgs)) {
				String[] org = orgs.split(",");
				for (int i = 0; i < org.length; i++) {
					Integer str = Integer.parseInt(org[i]);
					FileOrgKey key=new FileOrgKey();
					key.setFileId(fo.getFileId());
					key.setOrgId(str);
					fileOrgMapper.insertSelective(key);
				}
			}
		} catch (Exception e) {
			log.error("关联部门失败：" + e.getMessage());
			throw new BusinessException("关联部门失败");
		}
	}
	
	@Override
	@Transactional
	public void del(Integer fileId)  {
		FileOrgExample example = new FileOrgExample();
		try {
			example.createCriteria().andFileIdEqualTo(fileId);
			fileOrgMapper.deleteByExample(example);
		} catch (Exception e) {
			log.error("删除部门失败：" + e.getMessage());
			throw new BusinessException("删除部门失败");
		}
	}
}

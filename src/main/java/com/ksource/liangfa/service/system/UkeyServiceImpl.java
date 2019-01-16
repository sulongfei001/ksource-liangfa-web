package com.ksource.liangfa.service.system;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.liangfa.domain.UkeyUser;
import com.ksource.liangfa.mapper.UkeyUserMapper;
/**
 *@see com.ksource.liangfa.service.system.UkeyService
 *@author gengzi
 *@data 2012-6-7
 */
@Service("UkeyService")
@Transactional
public class UkeyServiceImpl implements UkeyService{

	@Autowired
	UkeyUserMapper ukeyUserMapper;
	
	@Override
	public void create(UkeyUser user) {
		ukeyUserMapper.deleteByPrimaryKey(user.getSerialNumber());
		ukeyUserMapper.insert(user);
	}

	@Override
	public void del(String serialNumber) {
		ukeyUserMapper.deleteByPrimaryKey(serialNumber);
	}

	@Override
	public void update(String serialNumber, String username,
			String newPassword) {
		UkeyUser user = new UkeyUser();
		user.setSerialNumber(serialNumber);
		if(StringUtils.isNotBlank(username)){
			user.setUserName(username);
		}
		if(StringUtils.isNotBlank(newPassword)){
			user.setAdminPass(newPassword);
		}
		ukeyUserMapper.updateByPrimaryKeySelective(user);
	}

}

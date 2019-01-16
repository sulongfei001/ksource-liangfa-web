package com.ksource.liangfa.service.system;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.common.util.PasswordUtil;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.Module;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.UkeyUser;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.UkeyUserMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.syscontext.Const;

/**
 *描述：认证服务实现<br>
 *@author gengzi
 *@data 2012-4-17
 */
@Service("AuthorityServiceImpl")
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private OrganiseMapper organiseMapper;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private UkeyUserMapper ukeyUserMapper;
	@Autowired
	private DistrictMapper districtMapper;
	
	@Override
	@LogBusiness(operation="用户登录",business_opt_type=LogConst.LOG_OPERATION_TYPE_LOGIN,
	db_opt_type=LogConst.LOG_DB_OPT_TYPE_UPDATE,target_domain_position=0, target_domain_mapper_class = UserMapper.class)
	public ServiceResponse login(User user, String securityCodeT, String pwd, String securityCode) {
		ServiceResponse response = new ServiceResponse(true,"");
		String msg="";
		Integer isValid=null;
		String userPwd=null;
		if(user!=null){
			isValid = user.getIsValid();
			userPwd = user.getPassword();
		}
		if (user == null || user.getUserId().equals("")) {
			msg="用户不存在!";
		}else if(isValid==Const.STATE_INVALID){
			msg="此用户已被冻结,请联系管理员!";
		}else if (!PasswordUtil.encrypt(pwd).equals(userPwd)) {
			msg="密码不正确!";
		}else if(!securityCodeT.equals(securityCode)){
			msg="校验码不正确!";
		}else{
			 //判断权限  
			boolean isAdmin = false;
			if(Const.SYSTEM_ADMIN_ID.equals(user.getAccount())){
				isAdmin = true;
			}
			List<Module> modules =moduleService.getChildModule(user.getUserId(), Const.TOP_MODULE_ID,isAdmin);
		    if(modules==null||modules.isEmpty()){
		    	msg="此用户没有权限，请联系管理员！";
		    }
		
		}
		if(!msg.equals("")){
			response.setingError(msg);
			return response;
		}
        //if(!Const.SYSTEM_ADMIN_ID.equals(user.getAccount()))
		settingUserSessionInfo(user);
		return response;
	}

	private void settingUserSessionInfo(User user){
		// 登录成功，添加机构信息
		Organise organise = organiseMapper.selectByPrimaryKey(user.getOrgId());
		user.setOrganise(organise);
		Organise dept = organiseMapper.selectByPrimaryKey(user.getDeptId());
		user.setDept(dept);
		District district = districtMapper.selectByPrimaryKey(organise.getDistrictCode());
        user.setOrgType(organise.getOrgType());
        user.setOrgName(organise.getOrgName());
        user.setDistrictCode(organise.getDistrictCode());
        user.setDistrictJB(district.getJb());
		//更新登录时间
		User tempUser = new User();
		tempUser.setLastTime(new Date());
		tempUser.setUserId(user.getUserId());
		userMapper.updateByPrimaryKeySelective( tempUser);
	}
	
	@Override
	@LogBusiness(operation="退出登录",business_opt_type=LogConst.LOG_OPERATION_TYPE_LOGOUT,
	db_opt_type=LogConst.LOG_DB_OPT_TYPE_UPDATE,target_domain_position=0, target_domain_mapper_class = UserMapper.class)
	public void logout(User user) {
		//什么也不做
	}

	@Override
	@LogBusiness(operation="用户登录",business_opt_type=LogConst.LOG_OPERATION_TYPE_LOGIN,
	db_opt_type=LogConst.LOG_DB_OPT_TYPE_UPDATE,target_domain_position=0, target_domain_mapper_class = UserMapper.class)
	public ServiceResponse loginWithUkey(User user,
			String iID_SecureWeb_I_SerialNumber, String iID_SecureWeb_I_Random,
			String iID_SecureWeb_I_MD5Result, String securityCodeSession,
			String securityCode) {
		ServiceResponse response = new ServiceResponse(true,"");
		String msg="";
		if (user == null || user.getUserId().equals("")) {
			msg="用户不存在！";
		}else if(user.getIsValid()==Const.STATE_INVALID){
			msg="此用户已被冻结，请联系管理员！";
		}else if(!securityCodeSession.equals(securityCode)){
			msg="校验码不正确！";
		}else{
			boolean isAdmin = false;
			if(Const.SYSTEM_ADMIN_ID.equals(user.getAccount())){
				isAdmin = true;
			}
			List<Module> modules =moduleService.getChildModule(user.getUserId(), Const.TOP_MODULE_ID,isAdmin);
		    if(modules==null||modules.isEmpty()){ //判断权限  
		    	msg="此用户没有权限，请联系管理员！";
		    }else{//验证u-key设备
		    	UkeyUser pu = ukeyUserMapper.selectByPrimaryKey(iID_SecureWeb_I_SerialNumber);
		    	
		    	if(pu==null || !pu.getUserName().equals(user.getAccount())){
		    		msg="确认身份失败：未经确认的IKEY硬件!";
		    	}
		    }
		}
		if(!msg.equals("")){
			response.setingError(msg);
			return response;
		}
        if(!Const.SYSTEM_ADMIN_ID.equals(user.getAccount()))
		settingUserSessionInfo(user);
		return response;
	}
	
	@Override
	@LogBusiness(operation="用户登录",business_opt_type=LogConst.LOG_OPERATION_TYPE_LOGIN,
	db_opt_type=LogConst.LOG_DB_OPT_TYPE_UPDATE,target_domain_position=0, target_domain_mapper_class = UserMapper.class)
	public ServiceResponse loginWithIAkey(User user,String securityCodeSession,String serialNumber, String securityCode) {
		ServiceResponse response = new ServiceResponse(true,"");
		String msg="";
		if (user == null || user.getUserId().equals("")) {
			msg="用户不存在！";
		}else if(user.getIsValid()==Const.STATE_INVALID){
			msg="此用户已被冻结，请联系管理员！";
		}else if(!securityCodeSession.equals(securityCode)){
			msg="校验码不正确！";
		}else{
			boolean isAdmin = false;
			if(Const.SYSTEM_ADMIN_ID.equals(user.getAccount())){
				isAdmin = true;
			}
			List<Module> modules =moduleService.getChildModule(user.getUserId(), Const.TOP_MODULE_ID,isAdmin);
		    if(modules==null||modules.isEmpty()){ //判断权限  
		    	msg="此用户没有权限，请联系管理员！";
		    }/*else{//验证u-key设备
		    	UkeyUser pu = ukeyUserMapper.selectByPrimaryKey(serialNumber);
		    	
		    	if(pu==null || !pu.getUserName().equals(user.getAccount())){
		    		msg="确认身份失败：未经确认的UKEY硬件!";
		    	}
		    }*/
		}
		if(!msg.equals("")){
			response.setingError(msg);
			return response;
		}
        //if(!Const.SYSTEM_ADMIN_ID.equals(user.getAccount()))
		settingUserSessionInfo(user);
		return response;
	}

    @Override
    @LogBusiness(operation="用户登录",business_opt_type=LogConst.LOG_OPERATION_TYPE_LOGIN,
    db_opt_type=LogConst.LOG_DB_OPT_TYPE_UPDATE,target_domain_position=0, target_domain_mapper_class = UserMapper.class)
    public void loginWithApp(User user) {
        settingUserSessionInfo(user);
    }
    
}

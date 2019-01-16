package com.ksource.liangfa.service.system;

import javax.servlet.http.HttpServletRequest;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.User;

/**
 *描述：认证服务<br>
 *@author gengzi
 *@data 2012-4-17
 */
public interface AuthorityService {

	/**
	 * 用户登录，执行校验，设置用户机构和部门信息,更新最后登录时间<br>
	 * user 不能空！
	 * @param user
	 */
	ServiceResponse login(User user,String securityCodeT, String pwd, String securityCode);
	/**
	 * 用户退出
	 * @param user
	 * @param iID_SecureWeb_I_SerialNumber：ukey设备序列号
	 * @param iID_SecureWeb_I_Random：随机数
	 * @param iID_SecureWeb_I_MD5Result：u-key设备生成的加密数据
	 * @param securityCodeSession：session保存的验证码
	 * @param securityCode：用户输入的验证码
	 */
	void logout(User user);
	ServiceResponse loginWithUkey(User user,
			String iID_SecureWeb_I_SerialNumber, String iID_SecureWeb_I_Random,
			String iID_SecureWeb_I_MD5Result, String securityCodeSession,
			String securityCode);
	ServiceResponse loginWithIAkey(User user, String securityCodeSession,
			String serialNumber, String securityCode);
	
	void loginWithApp(User user);
}

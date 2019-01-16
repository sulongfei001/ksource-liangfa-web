package com.ksource.liangfa.service.system;
import com.ksource.liangfa.domain.UkeyUser;
/**
 *描述：灵购ukey管理<br>
 *@author gengzi
 *@data 2012-6-7
 */
public interface UkeyService {
	/**
	 * 创建ukey账户
	 * @param user
	 * @return
	 */
	void create(UkeyUser user);
	
	/**
	 * 删除ukey账户
	 * @param user
	 * @return
	 */
	void del(String serialNumber);
	
	/**
	 * 更新ukey账户
	 * @param user
	 * @return
	 */
	void update(String serialNumber,String username,String newPassword);
}

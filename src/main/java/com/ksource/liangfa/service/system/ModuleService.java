package com.ksource.liangfa.service.system;

import java.util.ArrayList;
import java.util.List;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.Module;
import com.ksource.liangfa.domain.User;

/**
  * 此类为 权限业务 接口(服务层)
  * 
  * @author zxl :)
  * @version 1.0   
  * date   2011-5-10
  * time   下午02:44:50
  */ 
public interface ModuleService {

	
	
	
	/**
	 * 判断节点下是否包含有子节点。
	 * @param id
	 * @return
	 */
	public  Boolean isNoChildren(Integer id) ;
	
	/**
	 * 得到用户菜单。
	 * 通过用户ID和父菜单ID得到子菜单集合，如果用户系统管理员则返回所有菜单。
	 * 否则按用户权限返回菜单。
	 * @param userId  用户ID
	 * @param parentModuleId  父菜单ID
	 * @return 菜单集合
	 */
	List<Module> getChildModule(String userId, Integer parentModuleId,boolean isAdmin);
	
	/**
	 * 加载用户首页全部菜单。
	 * 通过用户ID和父菜单ID得到子菜单集合，如果用户系统管理员则返回所有菜单。
	 * 否则按用户权限返回菜单。
	 * @param userId  用户ID
	 * @param parentModuleId  父菜单ID
	 * @param moduleCategory 菜单分类
	 * @param moduleList 菜单保存
	 */
	void getHomeModule(String userId,Integer parentModuleId, List<Module> moduleList,int moduleCategory,boolean isAdmin);
	
	/**
	 * 得到用户菜单。
	 * 如果是当前用户是超级管理员则返回所有菜单，如果当前用户是用户管理员则返回所有非维护菜单，普通用户则按
	 * 权限返回菜单。
	 * @param user　　当前用户信息
	 * @param parentModuleId　父菜单ID
	 * @return　菜单集合
	* @see #getChildModule(String userId, Integer parentModuleId)
	 */
	List<Module> getChildModule(User user, Integer parentModuleId);
	

	/**
	 * 删除菜单。
	 * 删除指定ID的菜单，如果此菜单没有兄弟（并列）菜单，则把其父菜单的节点类型修改为叶子节点。
	 * @param moduleid 被删除菜单的ID
	 * @param parentid 被删除菜单的父ID
	 */
	public void del(Integer moduleid, Integer parentid);

	/**
	 * 生成zTree树所需的的Json，用于角色授权。
	 * @param user 当前用户信息
	 * @param id
	 * @param buffer
	 * @param list
	 */
	public  void buildModuleTree(User user,Integer id,StringBuffer buffer, ArrayList<Integer> moduleList) ;

	/**
	 *添加菜单信息。
	 *如果添加菜单的父菜单的节点状态是叶子节点，则修改父菜单节点状态。
	 * @param module
	 */
	public ServiceResponse insert(Module module);

	public List<Module> getModules(String userId,boolean isAdmin);

	List<Module> getAppModules(String userId, boolean isAdmin);


}
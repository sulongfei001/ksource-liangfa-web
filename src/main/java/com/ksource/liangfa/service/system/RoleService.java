package com.ksource.liangfa.service.system;

import java.util.List;

import com.ksource.liangfa.domain.Module;
import com.ksource.liangfa.domain.Role;

public interface RoleService {

	/**
	 * 角色设置菜单
	 * @param roleId
	 * @param menuIds
	 */
	public void saveMenus(Integer roleId,Integer[] menuIds);
	
	/**
	 * 角色设置资源
	 * @param authId
	 * @param resourceIds
	 */
	//public void saveResources(Integer roleId,Integer[] resourceIds);
	
	/**
	 * 查询已授权的资源
	 * @param userId
	 */
	//TODO: 资源暂时先不写  List<Resource> queryGrantedResources(Integer roleId);
	/**
	 * 查询已授权的所有菜单
	 * @param roleId
	 * @return
	 */
	public List<Module> queryGrantMenus(Integer roleId);
	/**
	 * 删除角色
	 * @param roleId
	 * @return
	 */
	public int del(Integer roleId);

	/**
	 * 查询所有角色信息及该角色是否被使用.
	 * 在角色领域类中添加了userCount字段用于表示该角色被多少个用户使用。
	 * @return  角色集合
	 */
	public List<Role> find();
	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
	public void insert(Role role);
	
	
}

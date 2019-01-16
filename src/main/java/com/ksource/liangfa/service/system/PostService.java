package com.ksource.liangfa.service.system;

import java.util.List;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.Post;
import com.ksource.liangfa.domain.Role;

/**
 * 岗位管理 接口
 * 
 * @author junxy
 * 
 */
public interface PostService {

	/**
	 * 岗位设置角色
	 * 
	 * @param postId
	 *            岗位id
	 * @param roleIds
	 *            角色ids
	 */
	public void saveRoles(Integer postId, Integer[] roleIds);

	/**
	 * 获取岗位已分配的角色
	 * 
	 * @param postId
	 *            岗位id
	 * @return
	 */
	public List<Role> queryGrantedRoles(Integer postId);

	/**
	 * 获取岗位未分配的角色
	 * 
	 * @param postId
	 * @return
	 */
	public List<Role> queryNotGrantedRoles(Integer postId);

	/**
	 * 查询岗位并查询拥有该岗位的用户数量
	 */
	public List<Post> find(Integer deptId);

	/**
	 * 删除岗位及其关联的岗位-角色信息
	 */
	public int del(Integer postId);

	/**
	 * 添加岗位
	 * 
	 * @param post
	 * @return
	 */
	public ServiceResponse insert(Post post);
	/**
	 * 修改岗位
	 * 
	 * @param post
	 * @return
	 */
	public ServiceResponse updateByPrimaryKeySelective(Post post);
}
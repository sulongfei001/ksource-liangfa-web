package com.ksource.liangfa.service.system;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.exception.BusinessException;

import com.ksource.liangfa.domain.Post;
import com.ksource.liangfa.domain.PostRoleExample;
import com.ksource.liangfa.domain.PostRoleKey;
import com.ksource.liangfa.domain.Role;
import com.ksource.liangfa.domain.UserExample;
import com.ksource.liangfa.mapper.PostMapper;
import com.ksource.liangfa.mapper.PostRoleMapper;
import com.ksource.liangfa.mapper.RoleMapper;
import com.ksource.liangfa.mapper.UserMapper;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRoleMapper postRoleMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private UserMapper userMapper;
	// 日志
	private static final Logger log = LogManager
			.getLogger(PostServiceImpl.class);

	@Override
	@Transactional
	public void saveRoles(Integer postId, Integer[] roleIds) {
		// 判断一下postId不能为空 AssertUtil 暂时未做

		/** 先删除该岗位对应的角色 */
		PostRoleExample example = new PostRoleExample();
		example.createCriteria().andPostIdEqualTo(postId);

		try {
			postRoleMapper.deleteByExample(example);

			/** 保存岗位角色关联关系 */
			PostRoleKey key = null;

			for (Integer roleId : roleIds) {
				key = new PostRoleKey();
				key.setPostId(postId);
				key.setRoleId(roleId);
				postRoleMapper.insert(key);
			}
		} catch (Exception e) {
			log.error("添加岗位失败：" + e.getMessage());
			throw new BusinessException("添加岗位失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> queryGrantedRoles(Integer postId) {
		try {
			return roleMapper.roleInPost(postId);
		} catch (Exception e) {
			log.error("查询岗位失败：" + e.getMessage());
			throw new BusinessException("查询岗位失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> queryNotGrantedRoles(Integer postId) {
		try {
			return roleMapper.roleNotInPost(postId);
		} catch (Exception e) {
			log.error("查询岗位失败：" + e.getMessage());
			throw new BusinessException("查询岗位失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Post> find(Integer deptId) {
		try {
			return postMapper.find(deptId);
		} catch (Exception e) {
			log.error("查询岗位失败：" + e.getMessage());
			throw new BusinessException("查询岗位失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "删除岗位", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_DELETE, target_domain_code_position = 0, target_domain_mapper_class = PostMapper.class)
	public int del(Integer postId) {
		PostRoleExample example = new PostRoleExample();
		example.createCriteria().andPostIdEqualTo(postId);
		UserExample userExample = new UserExample();
		userExample.createCriteria().andPostIdEqualTo(postId);
		try {
			// 1.判断此岗位是否可以删除(是否有用户在用此岗位)
			int size = userMapper.countByExample(userExample);
			if (size == 0) {
				// 2.删除与岗位相关的岗位-角色信息
				postRoleMapper.deleteByExample(example);
				// 3.删除岗位信息
				return postMapper.deleteByPrimaryKey(postId);
			}
			return 0;
		} catch (Exception e) {
			log.error("删除岗位失败：" + e.getMessage());
			throw new BusinessException("删除岗位失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "添加岗位", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_position = 0, target_domain_mapper_class = PostMapper.class)
	public ServiceResponse insert(Post post) {
		ServiceResponse response = new ServiceResponse(true, "添加岗位成功!");
		try {
			postMapper.insertSelective(post);
			return response;
		} catch (Exception e) {
			log.error("添加岗位失败：" + e.getMessage());
			throw new BusinessException("添加岗位失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "修改岗位", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_UPDATE, target_domain_position = 0, target_domain_mapper_class = PostMapper.class)
	public ServiceResponse updateByPrimaryKeySelective(Post post) {
		ServiceResponse response = new ServiceResponse(true, "修改岗位成功!");
		try {
			postMapper.updateByPrimaryKeySelective(post);
			return response;
		} catch (Exception e) {
			log.error("修改岗位失败：" + e.getMessage());
			throw new BusinessException("修改岗位失败");
		}
	}
}

package com.ksource.liangfa.service.system;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.domain.Module;
import com.ksource.liangfa.domain.Role;
import com.ksource.liangfa.domain.RolesModulesExample;
import com.ksource.liangfa.domain.RolesModulesKey;
import com.ksource.liangfa.domain.UsersRolesExample;
import com.ksource.liangfa.mapper.ModuleMapper;
import com.ksource.liangfa.mapper.RoleMapper;
import com.ksource.liangfa.mapper.RolesModulesMapper;
import com.ksource.liangfa.mapper.UsersRolesMapper;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RolesModulesMapper rolesModulesMapper;
	@Autowired
	private ModuleMapper moduleMapper;
	@Autowired
	private UsersRolesMapper usersRolesMapper;
	// 日志
	private static final Logger log = LogManager
			.getLogger(UserServiceImpl.class);

	@Transactional
	public void saveMenus(Integer roleId, Integer[] menuIds) {
		/* 先删除该角色对应的菜单 */
		RolesModulesExample example = new RolesModulesExample();
		example.createCriteria().andRoleIdEqualTo(roleId);

		/* 保存所有菜单 */
		RolesModulesKey key = new RolesModulesKey();
		key.setRoleId(roleId);
		try {
			rolesModulesMapper.deleteByExample(example);

			for (Integer menuId : menuIds) {
				key.setModuleId(menuId);
				rolesModulesMapper.insert(key);
			}
		} catch (Exception e) {
			log.error("保存菜单失败：" + e.getMessage());
			throw new BusinessException("保存菜单失败");
		}

	}

	/*
	 * public void saveResources(Integer roleId, Integer[] resourceIds) {
	 * 
	 * //1.先删除该角色对应的所有资源 roleMapper.deleteResources(roleId); //2.保存所有的资源
	 * roleMapper.saveResource(roleId, resourceIds); }
	 * 
	 * public List<Resource> queryGrantedResources(Integer roleId) {
	 * 
	 * return roleMapper.queryGrantedResources(roleId); }
	 */
	@Transactional(readOnly = true)
	public List<Module> queryGrantMenus(Integer roleId) {
		try {
			return moduleMapper.queryGrantedMenus(roleId);
		} catch (Exception e) {
			log.error("查询菜单失败：" + e.getMessage());
			throw new BusinessException("查询菜单失败");
		}
	}

	/**
	 * 根据角色id删除角色(正在被用户使用的角色不能被删除)
	 */
	@Transactional
	@LogBusiness(operation = "删除角色", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_DELETE, target_domain_code_position = 0, target_domain_mapper_class = RoleMapper.class)
	public int del(Integer roleId) {
		UsersRolesExample example = new UsersRolesExample();
		example.createCriteria().andRoleIdEqualTo(roleId);
		try {
			/** 1、看角色是否已被用户使用 */
			int size = usersRolesMapper.countByExample(example);
			if (size > 0) {
				// throw new ServiceException("该角色已被用户使用，不能删除");
				throw new Exception();
			}
			// 2、未被使用 能删除
			// 2.1 删除此角色下对应的所有模块
			RolesModulesExample example2 = new RolesModulesExample();
			example2.createCriteria().andRoleIdEqualTo(roleId);
			rolesModulesMapper.deleteByExample(example2);
			// 2.2 删除角色-> 资源关联

			// 2.3 删除该角色
			return roleMapper.deleteByPrimaryKey(roleId);
		} catch (Exception e) {
			log.error("删除角色失败：" + e.getMessage());
			throw new BusinessException("删除角色失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> find() {
		try {
			return roleMapper.find();
		} catch (Exception e) {
			log.error("查询角色失败：" + e.getMessage());
			throw new BusinessException("查询角色失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "添加角色", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_position = 0, target_domain_mapper_class = RoleMapper.class)
	public void insert(Role role) {
		try {
			roleMapper.insert(role);
		} catch (Exception e) {
			log.error("添加角色失败：" + e.getMessage());
			throw new BusinessException("添加角色失败");
		}

	}
}

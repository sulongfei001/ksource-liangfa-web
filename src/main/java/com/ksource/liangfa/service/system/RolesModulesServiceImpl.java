package com.ksource.liangfa.service.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.exception.BusinessException;
import com.ksource.liangfa.domain.Role;
import com.ksource.liangfa.domain.RolesModulesExample;
import com.ksource.liangfa.domain.RolesModulesKey;
import com.ksource.liangfa.mapper.RoleMapper;
import com.ksource.liangfa.mapper.RolesModulesMapper;

/**
 * 角色分配权限服务层实现
 * 
 * @author zxl
 * 
 */
@Service
public class RolesModulesServiceImpl implements RolesModulesService {

	@Autowired
	private RolesModulesMapper rolesModulesMapper;
	@Autowired
	private RoleMapper roleMapper;
	// 日志
	static final Logger logger = LogManager
			.getLogger(RolesModulesServiceImpl.class);

	@Override
	@Transactional
	public void authorize(RolesModulesKey rp)  {
		try {
			int roleId = rp.getRoleId();
			String modules = rp.getModules();
			del(roleId);
			// 将modules字符串转化成list
			ArrayList<Integer> list = new ArrayList<Integer>();
			if (!"".equals(modules)) {
				String[] mod = modules.split(",");
				for (int i = 0; i < mod.length; i++) {
					Integer str = Integer.parseInt(mod[i]);
					list.add(str);
				}
			}
			for (int i = 0; i < list.size(); i++) {
				rp.setModuleId(list.get(i));
				rolesModulesMapper.insert(rp);
			}
		} catch (Exception e) {
			logger.error("分配权限失败：" + e.getMessage());
			throw new BusinessException("分配权限失败");
		}
	}

	@Override
	@Transactional
	public RolesModulesKey find(int roleId)  {
		RolesModulesKey rp = new RolesModulesKey();
		RolesModulesExample example = new RolesModulesExample();
		List<RolesModulesKey> list = null;
		String modules = "";
		try {
			// 获取指定ID的角色基本信息和权限
			Role role = roleMapper.selectByPrimaryKey(roleId);
			if (role != null) {
				rp.setRoleName(role.getRoleName());
				rp.setRoleId(role.getRoleId());
			}
			// 获取角色已有的权限列表
			example.createCriteria().andRoleIdEqualTo(roleId);
			 list = rolesModulesMapper.selectByExample(example);
		} catch (Exception e) {
			logger.error("查询角色及权限信息失败：" + e.getMessage());
			throw new BusinessException("查询角色及权限信息失败");
		}
		if (list!=null&&list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				RolesModulesKey key = list.get(i);
				modules += key.getModuleId() + ",";
			}
		}
		rp.setModules(modules);
		return rp;
	}

	@Override
	@Transactional
	public void del(int roleId)  {
		RolesModulesExample example = new RolesModulesExample();
		try {
			example.createCriteria().andRoleIdEqualTo(roleId);
			rolesModulesMapper.deleteByExample(example);
		} catch (Exception e) {
			logger.error("删除角色及权限信息失败：" + e.getMessage());
			throw new BusinessException("删除角色及权限信息失败");
		}
	}

}

package com.ksource.liangfa.service.system;

import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.common.util.PasswordUtil;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.Role;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.UserExample;
import com.ksource.liangfa.domain.UsersRolesExample;
import com.ksource.liangfa.domain.UsersRolesKey;
import com.ksource.liangfa.mapper.RoleMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.mapper.UsersRolesMapper;
import com.ksource.syscontext.Const;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UsersRolesMapper usersRolesMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private SystemDAO systemDao;

	// 日志
	private static final Logger log = LogManager
			.getLogger(UserServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public boolean checkUser(User user) {
		try {
			return userMapper.checkUser(user);
		} catch (Exception e) {
			log.error("查询用户失败：" + e.getMessage());
			throw new BusinessException("查询用户失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<User> find(User user, String page) {
		try {
			return systemDao.find(user, page);
		} catch (Exception e) {
			log.error("查询用户失败：" + e.getMessage());
			throw new BusinessException("查询用户失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<User> find(User user, String page,
			Map<String, Object> map) {
		try {
			return systemDao.find(user, page, map);
		} catch (Exception e) {
			log.error("查询用户失败：" + e.getMessage());
			throw new BusinessException("查询用户失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> find(User user) {
		try {
			return systemDao.find(user);
		} catch (Exception e) {
			log.error("查询用户失败：" + e.getMessage());
			throw new BusinessException("查询用户失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> findUserdRoleByUser(String selectId) {
		try {
			return roleMapper.roleInUser(selectId);
		} catch (Exception e) {
			log.error("查询用户失败：" + e.getMessage());
			throw new BusinessException("查询用户失败");
		}
	}
	
	@Override
	public List<Role> findRoleByUserWithPost(String userId) {
		try {
			return roleMapper.roleInUserWithPost(userId);
		} catch (Exception e) {
			log.error("查询用户失败：" + e.getMessage());
			throw new BusinessException("查询用户失败");
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Role> findNotUserdRoleByUser(String selectId) {
		try {
			return roleMapper.roleNotInUser(selectId);
		} catch (Exception e) {
			log.error("查询用户角色表失败：" + e.getMessage());
			throw new BusinessException("查询用户角色表失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "密码重设", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_UPDATE, target_domain_code_position = 0, target_domain_mapper_class = UserMapper.class)
	public ServiceResponse resetPwd(String userId, String password) {
		ServiceResponse response = new ServiceResponse(true, "设置密码成功!");
		try {
			User record = new User();
			String pass = PasswordUtil.encrypt(password);
			record.setPassword(pass);
			record.setUserId(userId);
			userMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			log.error("更新用户密码失败：" + e.getMessage());
			throw new BusinessException("更新用户密码失败");
		}
		return response;
	}

	@Override
	@Transactional
	public ServiceResponse updateRole(User user) {
		ServiceResponse response = new ServiceResponse(true, "设置角色成功!");
		UsersRolesExample example = new UsersRolesExample();
		try {
			String userId = user.getUserId();
			String roleIds = user.getRoleIds();
			// 首先根据用户标识 查找出对应的所有角色 并全部删除
			UsersRolesKey key = new UsersRolesKey();
			key.setUserId(userId);
			example.createCriteria().andUserIdEqualTo(userId);
			usersRolesMapper.deleteByExample(example);
			String[] roleList = roleIds.split(",");
			if (roleList != null && roleList.length > 0) {
				for (int i = 0; i < roleList.length; i++) {
					if (roleList[i] != "") {
						key.setRoleId(Integer.valueOf(roleList[i]));
						usersRolesMapper.insert(key);
					}
				}
			}
			return response;
		} catch (Exception e) {
			log.error("用户更新角色表失败：" + e.getMessage());
			throw new BusinessException("用户更新角色表失败");
		}
	}
	
	@Override
	@Transactional
	public ServiceResponse deleteRole(String userId) {
		ServiceResponse response = new ServiceResponse(true, "删除用户角色成功!");
		UsersRolesExample example = new UsersRolesExample();
		try {
			// 首先根据用户标识 查找出对应的所有角色 并全部删除
			UsersRolesKey key = new UsersRolesKey();
			key.setUserId(userId);
			example.createCriteria().andUserIdEqualTo(userId);
			usersRolesMapper.deleteByExample(example);
			return response;
		} catch (Exception e) {
			log.error("删除用户角色表失败：" + e.getMessage());
			throw new BusinessException("删除用户角色失败");
		}
	}
	

	@Override
	@Transactional(readOnly = true)
	public User find(String userId) {
		try {
			User user = userMapper.selectByPrimaryKey(userId);
			if(user!=null){
				if(!Const.SYSTEM_ADMIN_ID.equals(user.getAccount())){
	            	return userMapper.selectByPk(userId);
	            }
			}
            return user;
		} catch (Exception e) {
			log.error("用户查询失败：" + e.getMessage());
			throw new BusinessException("用户查询失败");
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public User loginByAccount(String account) {
		try {
            if(Const.SYSTEM_ADMIN_ID.equals(account)){
               return  userMapper.selectByAccount(account);
            }
			return userMapper.selectFullByAccount(account);
		} catch (Exception e) {
			log.error("用户查询失败：" + e.getMessage());
			throw new BusinessException("用户查询失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "添加用户", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_position = 0, target_domain_mapper_class = UserMapper.class)
	public ServiceResponse insert(User user) {
		ServiceResponse response = new ServiceResponse(true, "添加用户成功!");
		try {
			user.setUserId(String.valueOf(systemDao.getSeqNextVal(Const.TABLE_USER)));
			userMapper.insert(user);
			return response;
		} catch (Exception e) {
			log.error("添加用户失败：" + e.getMessage());
			throw new BusinessException("添加用户失败");
		}

	}

	@Override
	@Transactional
	@LogBusiness(operation = "修改用户", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_UPDATE, target_domain_position = 0, target_domain_mapper_class = UserMapper.class)
	public ServiceResponse updateByPrimaryKeySelective(User user) {
		ServiceResponse response = new ServiceResponse(true, "修改用户成功!");
		try {
			userMapper.updateByPrimaryKeySelective(user);
			return response;
		} catch (Exception e) {
			log.error("修改用户失败：" + e.getMessage());
			throw new BusinessException("修改用户失败");
		}

	}
	
	

	@Override
	public List<User> getTimeOutWarningTree(Map<String, Object> map) {
		return userMapper.getTimeOutWarningTree(map) ;
	}

	@Override
	public List<User> getUserAndOrgan(String userId) {
		return userMapper.getUserAndOrgan(userId) ;
	}

	@Override
	public User getBySiUserId(String siUserId) {
		return userMapper.getBySiUserId(siUserId);
	}

	@Override
	public ServiceResponse updateValidBySiUserId(String siUserId) {
		ServiceResponse response = new ServiceResponse(true, "修改用户状态成功!");
		try {
			userMapper.updateValidBySiUserId(siUserId);
			return response;
		} catch (Exception e) {
			log.error("修改用户状态失败：" + e.getMessage());
			throw new BusinessException("修改用户状态失败");
		}
	}

	@Override
	public ServiceResponse deleteBySiUserId(String siUserId) {
		ServiceResponse response = new ServiceResponse(true, "删除用户成功!");
		try {
			userMapper.deleteBySiUserId(siUserId);
			return response;
		} catch (Exception e) {
			log.error("删除用户失败：" + e.getMessage());
			throw new BusinessException("删除用户失败");
		}
	}
	
	@Override
	public ServiceResponse deleteByUserId(String userId) {
		ServiceResponse response = new ServiceResponse(true, "删除用户成功!");
		try {
			userMapper.deleteByUserId(userId);
			return response;
		} catch (Exception e) {
			log.error("删除用户失败：" + e.getMessage());
			throw new BusinessException("删除用户失败");
		}
	}

	@Override
	public int findByOrgId(Integer orgId) {
		// TODO Auto-generated method stub
		
		UserExample example = new UserExample();
		example.createCriteria().andOrgIdEqualTo(orgId);
		int count = userMapper.countByExample(example);
		return count;
	}

	@Override
	public User selectByPk(String userId) {
		return userMapper.selectByPk(userId);
	}

	@Override
	public User selectOrgDistrictByUserId(String userId) {
		return userMapper.selectOrgDistrictByUserId(userId);
	}

	@Override
	public List<User> findByOrgCode(Integer orgCode) { 
		return userMapper.selectByOrgCode(orgCode);
	}

	/**
	 * 根据用户名查询用户列表
	 * 
	 * @author: LXL
	 * @createTime:2017年9月23日 上午11:46:55
	 */
	@Override
	@Transactional(readOnly = true)
	public List<User> searchUserByUserNameForApp(String userName) {
		return userMapper.selectUserByUserNameForApp(userName);
	}

	@Override
	public List<User> findByOrgCodeForApp(Integer orgCode) {
		return userMapper.findByOrgCodeForApp(orgCode);
	}

	/**
	 * 查询案件咨询参与人信息
	 */
	@Override
	public PaginationHelper<Organise> getParticipants(
			Map<String, Object> param, String page) {
		try {
			return systemDAO.find(param,page, 
					"com.ksource.liangfa.mapper.UserMapper.getParticipantsCount",
					"com.ksource.liangfa.mapper.UserMapper.getParticipantsList");
			} catch (Exception e) {
				log.error("查询案件咨询参与人失败：" + e.getMessage(), e);
			throw new BusinessException("查询案件咨询参与人失败：" + e.getMessage());
			}
	}
}

package com.ksource.liangfa.service.system;

import java.util.List;
import java.util.Map;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.Role;
import com.ksource.liangfa.domain.User;

public interface UserService {

	/**
	 * 验证用户名密码 
	 * 
	 * @param username
	 * @param pwd
	 * @return
	 */
	public boolean checkUser(User user);

	/**
	 * 得到已经分配给用户的角色
	 * 
	 * @param selectId
	 * @return 查询结果集合
	 */
	public List<Role> findUserdRoleByUser(String selectId);
	
	/**
	 * 得到用户的岗位所分配的角色
	 * 
	 * @param selectId
	 * @return 查询结果集合
	 */
	public List<Role> findRoleByUserWithPost(String userId);

	/**
	 * 得到未分配给用户的角色
	 * 
	 * @param selectId
	 * @return 查询结果集合
	 */
	public List<Role> findNotUserdRoleByUser(String selectId);

	/**
	 * 设置用户密码
	 * 
	 * @param userId
	 * @param password
	 */
	public ServiceResponse resetPwd(String userId, String password);

	/**
	 * 对用户重新分配角色
	 * 
	 * @param hrEmploye
	 */
	public ServiceResponse updateRole(User user);
	
	/**
	 * 根据用户Id删除用户所对应的角色
	 * @param userId
	 * @return
	 */
	public ServiceResponse deleteRole(String userId);

	/**
	 * 数据库分页，按page得到对应的数据集合. <br/>
	 * 如果想在domain对象的属性之外添加其它的查询条件,请查看
	 * {@link UserService#find(User, String, Map)}
	 * 
	 * @param user
	 *            用user对象封装的查询条件.
	 * @param page
	 * @return page对应的数据集合
	 */
	public PaginationHelper<User> find(User user, String page);

	/**
	 * 内存分页
	 * 
	 * @param user
	 * @return
	 */
	List<User> find(User user);

	/**
	 * 通过主键查询单个用户信息。 在查出用户的同时还查出了用户所属的组织机构名和岗位名。
	 * 
	 * <pre>
	 * method test
	 * </pre>
	 * 
	 * @param userId
	 * @return
	 */
	User find(String userId);

	/**
	 * 数据库分页，按page得到对应的数据集合
	 * 
	 * @param user
	 *            用user对象封装的查询条件
	 * @param page
	 * @param map
	 *            用map封装的查询条件
	 * @return
	 */
	public PaginationHelper<User> find(User user, String page,
			Map<String, Object> map);
	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	public ServiceResponse insert(User user);
	/**
	 * 修改用户信息
	 * 
	 * @param user
	 * @return
	 */
	public ServiceResponse updateByPrimaryKeySelective(User user);
	
	/**
	 * 
	 * @param map
	 * @return 用户信息集合
	 */
	List<User> getTimeOutWarningTree(Map<String, Object> map);
	
	/**
	 * 根据用户ID查找用户信息
	 * @param userId
	 * @return
	 */
	List<User> getUserAndOrgan(String userId) ;
	
	/**
	 * 根据集成用户唯一标识获取用户
	 * @param siUserId
	 * @return
	 */
	public User getBySiUserId(String siUserId);
	
	/**
	 * 根据集成用户唯一标识更新用户状态（冻结）
	 * @param siUserId
	 * @return
	 */
	public ServiceResponse updateValidBySiUserId(String siUserId);
	
	/**
	 * 根据集成用户唯一标识删除用户
	 * @param siUserId
	 * @return
	 */
	public ServiceResponse deleteBySiUserId(String siUserId);
	/**
	 * 根据用户ID删除用户
	 * @param siUserId
	 * @return
	 */
	public ServiceResponse deleteByUserId(String UserId);
	/**
	 * 查询某个组织机构下面的用户数量
	 * @param orgId
	 * @return
	 */
	public int findByOrgId(Integer orgId);

	/**
	 * 登录时根据用户名获取对象
	 * @param account
	 * @return
	 */
	User loginByAccount(String account);

	public User selectByPk(String inputer);
	
	public User selectOrgDistrictByUserId(String userId);
	
	public List<User> findByOrgCode(Integer orgCode);

	/**
	 * 根据用户姓名查询用户列表
	 * 
	 * @author: LXL
	 * @return:List<User>
	 * @createTime:2017年9月23日 上午11:45:57
	 */
	public List<User> searchUserByUserNameForApp(String userName);

	public List<User> findByOrgCodeForApp(Integer orgCode);

	/**
	 * 查询案件咨询参与人信息
	 * @param param
	 * @param page
	 * @return
	 */
	public PaginationHelper<Organise> getParticipants(
			Map<String, Object> param, String page);
}

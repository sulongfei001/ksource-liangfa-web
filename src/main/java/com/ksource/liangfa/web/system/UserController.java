package com.ksource.liangfa.web.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.common.util.PasswordUtil;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.OrganiseExample;
import com.ksource.liangfa.domain.Post;
import com.ksource.liangfa.domain.PostExample;
import com.ksource.liangfa.domain.Role;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.PostMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.UserService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PromptType;
import com.ksource.syscontext.SystemContext;

/**
 * 
 * @author zxl :)
 * 
 */
@Controller
@RequestMapping("/system/user")
public class UserController {
	/** 用来标示请求是从head.jsp发出的 */
	private static final String HEAD_REQUEST = "head";

	/** 用于保存查询条件 */
	private static final String SEARCH_CONDITION = UserController.class
			.getName() + "conditionObj";

	/** 用于保存分页的标志 */
	private static final String PAGE_MARK = UserController.class.getName()
			+ "page";

	/** 新增视图 */
	private static final String ADD_VIEW = "system/user/userAdd";

	/** 重定向到 新增界面 视图 */
	private static final String REDI_ADDUI_VIEW = "redirect:/system/user/addUI";

	/** 重定向到 用户管理界面 视图 */
	private static final String REDI_BACK_VIEW = "redirect:/system/user/back";

	/** 重定向到 选择角色界面 视图 */
	private static final String REDI_SELECTUI_VIEW = "redirect:/system/user/updateRoleUI/";

	/** 重定向到 设置密码界面 视图 */
	private static final String REDI_RESTEPAWUI_VIEW = "redirect:/system/user/setPwdUI/";

	/** 重定向到 首页顶部设置密码界面 视图 */
	private static final String REDI_TOPRESTEPAWUI_VIEW = "redirect:/system/user/setTopPwdUI/";

	/** 选择角色视图 */
	private static final String SELECT_ROLE_VIEW = "system/user/userSelect";

	/** 设置密码视图 */
	private static final String RESETPWD_VIEW = "system/user/userResetPwd";

	/** 首页顶部设置密码视图 */
	private static final String TOPRESETPWD_VIEW = "system/user/userTopResetPwd";

	/** 修改视图 */
	private static final String UPDATE_VIEW = "system/user/userUpdate";

	/** 主界面视图 */
	private static final String MAIN_VIEW = "system/user/userMain";
	/** 主界面视图 */
	private static final String DETAIL_VIEW = "system/user/userDetail";

	/** 主界面视图 */
	private static final String REDI_UPDATEUI_VIEW = "redirect:/system/user/updateUI/";

	/** spring 注入 DAO控制对象 */
	@Autowired
	private UserService userService;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;

	// 新增 用户
	@RequestMapping(value = "add")
	public String add(HttpServletRequest request, User user) {
		String path;
		String addView = request.getParameter("view");
		User loginUser = SystemContext.getCurrentUser(request);
		user.setCreateUserId((loginUser.getUserId()));
		user.setIsValid(Const.STATE_VALID);
		user.setPassword(PasswordUtil.encrypt(user.getPassword()));
		// 去掉空格(与验证，查询保持一致)
		user.setAccount(StringUtils.trim(user.getAccount()));
		userService.insert(user);
		// mybatisAutoMapperService.insert(UserMapper.class, user);
		if ("getList".equals(addView)) { // 根据前台条件跳转
			path = REDI_BACK_VIEW;
		} else if ("add".equals(addView)) {
			path = REDI_ADDUI_VIEW;
		} else {
			return null;
		}

		return ResponseMessage.addPromptTypeForPath(path, PromptType.add);
	}

	// 进入 新增用户界面
	@RequestMapping(value = "addUI")
	public ModelAndView addUI(ResponseMessage res, HttpSession session) {
		ModelAndView view = new ModelAndView(ADD_VIEW);
		User user = SystemContext.getCurrentUser(session);
		Organise org = mybatisAutoMapperService.selectByPrimaryKey(
				OrganiseMapper.class, user.getOrgId(), Organise.class);
		view.addObject("districtId", org.getDistrictCode());
		view.addObject("msg", ResponseMessage.parseMsg(res));
		return view;
	}

	// 冻结用户
	@RequestMapping(value = "del")
	public String del(String[] check) {
		User user = new User();
		user.setIsValid(Const.STATE_INVALID);

		if (check != null) {
			// TODO: 删除是一个一个删除的，一个删除是一个事务
			for (String id : check) {
				user.setUserId(id);
				// mybatisAutoMapperService.updateByPrimaryKeySelective(
				// UserMapper.class, user);
				userService.updateByPrimaryKeySelective(user);
			}
		}

		return ResponseMessage.addPromptTypeForPath(REDI_BACK_VIEW,
				PromptType.del);
	}
	
	// 删除 用户
		@RequestMapping(value = "delUser")
		public String delUser(String[] check) {
		
			if (check != null) {
				// TODO: 删除是一个一个删除的，一个删除是一个事务
				for (String id : check) {
					userService.deleteByUserId(id);
				}
			}
			return ResponseMessage.addPromptTypeForPath(REDI_BACK_VIEW,
					PromptType.del);
		}

	// 查询 用户
	@RequestMapping(value = "search")
	public ModelAndView search(HttpSession session, User user, String page,
			ResponseMessage res) {
		ModelAndView view = new ModelAndView(MAIN_VIEW);
		// 保存查询条件,用于返回使用
		session.setAttribute(SEARCH_CONDITION, user);
		session.setAttribute(PAGE_MARK, page);

		// 此处的条件用于查询出非当前用户的其它用户
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentUserId", SystemContext.getCurrentUser(session)
				.getUserId());

		PaginationHelper<User> userList = userService.find(user, page, map);
		view.addObject("userList", userList);
		view.addObject("msg", ResponseMessage.parseMsg(res));

		User loginUser = SystemContext.getCurrentUser(session); // TODO:考虑到市，县版本，此处需要修改
		Organise org = mybatisAutoMapperService.selectByPrimaryKey(
				OrganiseMapper.class, loginUser.getOrgId(), Organise.class);
        OrganiseExample example = new OrganiseExample();
        if(org!=null){
            example.createCriteria().andDistrictCodeEqualTo(org.getDistrictCode());
        }
		List<Organise> orgList = mybatisAutoMapperService.selectByExample(
				OrganiseMapper.class, example, Organise.class);
		view.addObject("orgList", orgList);
		view.addObject("page", page);
		view.addObject("user", user);
		return view;
	}

	// 用户更新角色
	@RequestMapping(value = "updateRole")
	public String updateRole(User user) {
		String path = REDI_SELECTUI_VIEW + user.getUserId();
		userService.updateRole(user);

		return ResponseMessage
				.addPromptTypeForPath(path, PromptType.updateRole);
	}

	// 进入 用户更新角色界面
	@RequestMapping(value = "updateRoleUI/{userId}")
	public ModelAndView updateRoleUI(@PathVariable String userId,
			ResponseMessage res) {
		ModelAndView view = new ModelAndView(SELECT_ROLE_VIEW);
		Map<String, List<Role>> list = new HashMap<String, List<Role>>(2);
		List<Role> usedByUserList = userService.findUserdRoleByUser(userId);
		List<Role> usedByUserWithPostList = userService.findRoleByUserWithPost(userId);
		List<Role> notUsedByUserList = userService
				.findNotUserdRoleByUser(userId);
		//List<Role> notUsedByUserList = userService.    findNotUserdRoleByUser(userId);
		User user = mybatisAutoMapperService.selectByPrimaryKey(
				UserMapper.class, userId, User.class);
		list.put("inUser", usedByUserList);
		list.put("outUser", notUsedByUserList);
		list.put("inUserWithPost", usedByUserWithPostList);
		view.addObject("getRole", list);
		view.addObject("user", user);
		view.addObject("info", ResponseMessage.parseMsg(res));

		return view;
	}

	// 用户更新密码
	@RequestMapping(value = "setPwd")
	public String setPwd(User user) {
		String path = REDI_RESTEPAWUI_VIEW + user.getUserId();
		String userId = user.getUserId();
		String password = user.getPassword();
		userService.resetPwd(userId, password);
		return ResponseMessage.addPromptTypeForPath(path, PromptType.updatePwd);
	}

	// 用户在首页顶部更新密码
	@RequestMapping(value = "setTopPwd")
	public String setTopPwd(User user) {
		String path = REDI_TOPRESTEPAWUI_VIEW + "?userId=" + user.getUserId();
		String userId = user.getUserId();
		String password = user.getPassword();
		userService.resetPwd(userId, password);
		return ResponseMessage.addPromptTypeForPath(path, PromptType.updatePwd);
	}

	// 进入 用户更新密码界面
	@RequestMapping(value = "setPwdUI/{userId}")
	public ModelAndView setPwdUI(@PathVariable String userId,
			ResponseMessage res) {
		ModelAndView view = new ModelAndView(RESETPWD_VIEW);
		User user = mybatisAutoMapperService.selectByPrimaryKey(
				UserMapper.class, userId, User.class);
		view.addObject("user", user);
		view.addObject("info", ResponseMessage.parseMsg(res));
		return view;
	}

	// 进入 首页顶部用户更新密码界面
	@RequestMapping(value = "setTopPwdUI")
	public ModelAndView setTopPwdUI(String userId, ResponseMessage res) {
		ModelAndView view = new ModelAndView(TOPRESETPWD_VIEW);
		User user = mybatisAutoMapperService.selectByPrimaryKey(
				UserMapper.class, userId, User.class);
		view.addObject("user", user);
		view.addObject("info", ResponseMessage.parseMsg(res));
		return view;
	}

	// 进行验证旧密码操作
	@RequestMapping(value = "checkOldPwd")
	@ResponseBody
	public boolean checkOldPwd(String userId, String oldPassword) {
		User user = mybatisAutoMapperService.selectByPrimaryKey(
				UserMapper.class, userId, User.class);
		String pwd = PasswordUtil.encrypt(oldPassword);
		if (!pwd.equals(user.getPassword())) {
			return false;
		}
		return true;
	}

	// 修改 用户
	@RequestMapping(value = "update")
	public String update(User user, String showBackBtn,
			HttpServletRequest request) {
		
		// mybatisAutoMapperService.updateByPrimaryKeySelective(UserMapper.class,
		// user);
		userService.updateByPrimaryKeySelective(user);
		String path = REDI_BACK_VIEW;
		if (showBackBtn != null && showBackBtn.equals("false")) {
			// 更新内存中当前用户的信息
			User tempUser = userService.find(user.getUserId());
			User currentUser = SystemContext.getCurrentUser(request);
            if(currentUser.getOrganise()!=null){
                tempUser.setOrganise(currentUser.getOrganise());
            }
			SystemContext.setCurrentUser(request, tempUser);

			path = REDI_UPDATEUI_VIEW + HEAD_REQUEST;
		}
		return ResponseMessage.addPromptTypeForPath(path, PromptType.update);
	}

	// 进入 修改用户界面
	@RequestMapping(value = "updateUI/{userId}")
	public ModelAndView updateUI(@PathVariable String userId,
			HttpSession session, ResponseMessage res) {
		ModelAndView view = new ModelAndView(UPDATE_VIEW);
		User user;
		if (userId.equals(HEAD_REQUEST)) {// 如果是head.jsp请求过来的。
			user = SystemContext.getCurrentUser(session);
			view.addObject("showBackBtn", false);
			view.addObject("msg", ResponseMessage.parseMsg(res));
		} else {
			user = mybatisAutoMapperService.selectByPrimaryKey(
					UserMapper.class, userId, User.class);
		}
		PostExample example = new PostExample();
		example.createCriteria().andDeptIdEqualTo(user.getDeptId());
		List<Post> postList = mybatisAutoMapperService.selectByExample(
				PostMapper.class, example, Post.class);
		view.addObject("user", user);
		view.addObject("postList", postList);
		return view;
	}

	@RequestMapping(value = "detail/{userId}")
	public ModelAndView detail(@PathVariable String userId) {
		ModelAndView view = new ModelAndView(DETAIL_VIEW);
		User user = mybatisAutoMapperService.selectByPrimaryKey(
				UserMapper.class, userId, User.class);
		Post post = mybatisAutoMapperService.selectByPrimaryKey(
				PostMapper.class, user.getPostId(), Post.class);
		Organise org = mybatisAutoMapperService.selectByPrimaryKey(
				OrganiseMapper.class, user.getOrgId(), Organise.class);
		user.setPost(post);
		user.setOrganise(org);
		view.addObject("user", user);
		return view;
	}

	@RequestMapping(value = "checkUserId", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkUserId(String account) {
		User user = userService.loginByAccount(account);

		if (user != null) {
			return false;
		}

		return true;
	}

	// 解冻用户，使用户有效
	@RequestMapping(value = "updateValidity/{userId}", method = RequestMethod.GET)
	public String updateValidity(@PathVariable String userId) {
		User user = new User();
		user.setUserId(userId);
		user.setIsValid(Const.STATE_VALID);
		// mybatisAutoMapperService.updateByPrimaryKeySelective(UserMapper.class,
		// user);
        userService.updateByPrimaryKeySelective(user);
		return REDI_BACK_VIEW;
	}

	// 用户模块 返回
	@RequestMapping(value = "back")
	public ModelAndView back(HttpSession session, ResponseMessage res) {
		// 有查询条件,按查询条件返回
		String page;
		User user = (User) session.getAttribute(SEARCH_CONDITION);
		if (session.getAttribute(PAGE_MARK) != null) {
			page = (String) session.getAttribute(PAGE_MARK);
		} else {
			page = "1";
		}
		return this.search(session, user, page, res);
	}
	
	
	// 禁止用户登录操作
		@RequestMapping(value = "updateUserLoginState")
		public String updateUserLoginState(String[] check) {
			User user = new User();
			user.setIsLogin(0);//禁止用户登录

			if (check != null) {
				for (String id : check) {
					user.setUserId(id);
					userService.updateByPrimaryKeySelective(user);
				}
			}
			return ResponseMessage.addPromptTypeForPath(REDI_BACK_VIEW,
					PromptType.update);
		}
		
		
		// 允许用户登录操作
		@RequestMapping(value = "updateLogin/{userId}", method = RequestMethod.GET)
		public String updateLogin(@PathVariable String userId) {
			User user = new User();
			user.setUserId(userId);
			user.setIsLogin(1);//允许用户登录
	        userService.updateByPrimaryKeySelective(user);
			return REDI_BACK_VIEW;
		}
		
		
		/**
		 * 获取案件咨询参与人员信息
		 * @param request
		 * @param isSingle
		 * @return
		 */
		@RequestMapping(value = "participantsList")
		public ModelAndView participantsList(String orgCode,HttpServletRequest request,String page){
			ModelAndView mv = new ModelAndView("tree/participantsList");
			Map<String,Object> param = new HashMap<String,Object>();
			User currUser = SystemContext.getCurrentUser(request);
			Organise currOrg = currUser.getOrganise();
			
			param.put("orgCode", orgCode);
			param.put("districtCode", currOrg.getDistrictCode());
			
			PaginationHelper<Organise> participantsList = userService.getParticipants(param,page);
			mv.addObject("participantsList", participantsList);
			mv.addObject("page", page);
			return mv;
		}
}
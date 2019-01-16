package com.ksource.liangfa.web.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.ResponseMessage;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.Role;
import com.ksource.liangfa.domain.RoleExample;
import com.ksource.liangfa.domain.RoleExample.Criteria;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.RoleMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.RoleService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PromptType;
import com.ksource.syscontext.SystemContext;

/**
 * copyright Copyright 2010 company 金明源 time 2010-8-17
 * 
 * @author zxl
 */
@Controller
@RequestMapping("/system/role")
public class RoleController{
	
	/** 新增视图 */
	private static final String ADD_VIEW = "system/role/roleAdd";
	/** 重定向到 角色管理界面 视图*/
	private static final String REDI_MAIN_VIEW = "redirect:/system/role/getList";
	/** 主界面视图 */
	private static final String MAIN_VIEW = "system/role/roleMain";
	
	/** spring 注入 DAO控制对象 */
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private SystemDAO systemDAO;
    //进入 角色管理界面 
	@RequestMapping(value="getList")
	public ModelAndView getList(ResponseMessage res)  {
		ModelAndView view = new ModelAndView(MAIN_VIEW);
		List<Role> list = 
		roleService.find();
		view.addObject("roleList", list); 
		//add prompt info
		view.addObject("info",ResponseMessage.parseMsg(res));
		return view;
	}
	
    // 新增 角色 
	@RequestMapping(value="add")
	public String add(HttpServletRequest request, Role role,ModelMap model)  {
		String path =REDI_MAIN_VIEW;
		User user = SystemContext.getCurrentUser(request);
		role.setUserId((user.getUserId()));
		role.setRoleId(systemDAO.getSeqNextVal(Const.TABLE_ROLE));	
		roleService.insert(role);
		return ResponseMessage.addPromptTypeForPath(path,PromptType.add);
	}
	
	// 进入 新增角色界面 
	@RequestMapping(value="addUI")
	public ModelAndView addUI(ResponseMessage res)  {
		ModelAndView view = new ModelAndView(ADD_VIEW);
		view.addObject("msg",ResponseMessage.parseMsg(res));
		return view;
	}
	
    //删除 角色
	@RequestMapping(value="del")
	public String del(HttpServletRequest request,Role role)  {
		String[] strs = request.getParameterValues("check");
		
		for(String id:strs){
		 roleService.del(Integer.valueOf(id));
		}
		return ResponseMessage.addPromptTypeForPath(REDI_MAIN_VIEW,PromptType.del);
	}
	
	//修改 角色 
	@RequestMapping(value="update")
	public String update(Role role,HttpSession session)  {
		mybatisAutoMapperService.updateByPrimaryKey(RoleMapper.class, role);
		return ResponseMessage.addPromptTypeForPath(REDI_MAIN_VIEW,PromptType.update);
	}
	
	//进入 修改角色界面
	@RequestMapping(value="updateUI/{roleId}")
	@ResponseBody
	public Role updateUI(@PathVariable Integer roleId,ResponseMessage res)  {
		Role role = mybatisAutoMapperService.selectByPrimaryKey(RoleMapper.class, roleId, Role.class);
		return role;
	}
	//验证角色名是否重复
	@RequestMapping(value="checkName")
	@ResponseBody
	public boolean checkName(String roleName,Integer roleId){
		String rolename = roleName.trim();
		RoleExample example = new RoleExample();
		Criteria cri =example.createCriteria().andRoleNameEqualTo(rolename);
		if(roleId!=null){
			cri.andRoleIdNotEqualTo(roleId);
		}
		int size=mybatisAutoMapperService.countByExample(RoleMapper.class, example);
		if(size>0){
			return false;
		}
		return true;
	}
}
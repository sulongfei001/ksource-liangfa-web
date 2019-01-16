package com.ksource.liangfa.web.system;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksource.common.bean.ResponseMessage;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.Post;
import com.ksource.liangfa.domain.PostExample;
import com.ksource.liangfa.domain.PostExample.Criteria;
import com.ksource.liangfa.domain.Role;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.UserExample;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.PostMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.PostService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PromptType;
import com.ksource.syscontext.SystemContext;

/**
 * 此类为 岗位业务 控制器
 * 
 * @author zxl :)
 * @version 1.0 date 2011-5-11 time 下午02:31:20
 */
@Controller
@RequestMapping("/system/post")
public class PostController {
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private PostService postService;
	@Autowired
	private SystemDAO systemDAO;

	// 岗位维护主界面
	private static final String POST_ORG_VIEW = "/system/post/org";

	// 查询请求
	private static final String POST_SEARCH_VIEW = "redirect:/system/post/list";

	// 列表页面
	private static final String POST_LIST_VIEW = "/system/post/list";

	// 新增页面
	private static final String POST_ADD_VIEW = "/system/post/add";

	// 设置角色页面
	private static final String POST_SET_ROLE_UI = "/system/post/setrole";

	// redirect到设置角色页面
	private static final String POST_SET_ROLE = "redirect:/system/post/setRoleUI";

	// 修改角色页面
	private static final String POST_UPDATE_ROLE = "/system/post/update";

	// 重定向到修改角色页面
	private static final String REDI_POST_UPDATE_ROLE = "redirect:/system/post/updateUI";

	// 重定向到添加角色页面
	private static final String REDI_POST_ADD_ROLE = "redirect:/system/post/addUI";

	/**
	 * 岗位维护主界面
	 * 
	 * @param orgId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/manage")
	public String manage(HttpSession session, Map<String, Object> model) {
		User loginUser = SystemContext.getCurrentUser(session);
		Organise organise = mybatisAutoMapperService.selectByPrimaryKey(
				OrganiseMapper.class, loginUser.getOrgId(), Organise.class);
		model.put("organise", organise);

		return POST_ORG_VIEW;
	}

	/**
	 * 根据部门获取岗位
	 * 
	 * @param deptId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String getPostsByOrg(Integer deptId, Map<String, Object> model,
			ResponseMessage res) {
		model.put("posts", getPostsByDept(deptId));
		model.put("msg", ResponseMessage.parseMsg(res));
		return POST_LIST_VIEW;
	}

	/**
	 * 根据部门id获取岗位信息
	 * 
	 * @param orgId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getPostsByDept/{deptId}")
	@ResponseBody
	public List<Post> getPostsByDept(@PathVariable Integer deptId) {

		List<Post> posts = postService.find(deptId);
		return posts;
	}

	/**
	 * 打开岗位新增页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI(ModelMap map, ResponseMessage res) {
		map.addAttribute("info", ResponseMessage.parseMsg(res));
		return POST_ADD_VIEW;
	}

	/**
	 * 新增岗位
	 * 
	 * @param post
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Post post) {
		post.setPostId(systemDAO.getSeqNextVal(Const.TABLE_POST));
		// mybatisAutoMapperService.insert(PostMapper.class, post);
		postService.insert(post);
		return ResponseMessage.addPromptTypeForPath(REDI_POST_ADD_ROLE
				+ "?orgId=" + post.getOrgId() + "&deptId=" + post.getDeptId(),
				PromptType.add);
	}

	/**
	 * 打开更新页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateUI")
	public String updateUI(Integer postId, ModelMap map, ResponseMessage res) {
		Post post = mybatisAutoMapperService.selectByPrimaryKey(
				PostMapper.class, postId, Post.class);
		map.addAttribute("post", post);
		map.addAttribute("info", ResponseMessage.parseMsg(res));
		return POST_UPDATE_ROLE;
	}

	/**
	 * 更新岗位
	 * 
	 * @param post
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Post post) {
		// mybatisAutoMapperService.updateByPrimaryKeySelective(PostMapper.class,
		// post);
		postService.updateByPrimaryKeySelective(post);
		return ResponseMessage.addPromptTypeForPath(REDI_POST_UPDATE_ROLE
				+ "?postId=" + post.getPostId(), PromptType.update);
	}

	/**
	 * 删除岗位
	 * 
	 * @param postId
	 * @return
	 */
	@RequestMapping(value = "/delete")
	public String delete(Integer postId, Integer orgId, Integer deptId) {
		// 先查询被删除岗位下是否有用户，如果有不能删除此岗位
		UserExample example = new UserExample();
		example.createCriteria().andPostIdEqualTo(postId);
		int size = mybatisAutoMapperService.countByExample(UserMapper.class,
				example);
		if (size == 0) {
			postService.del(postId);
			return POST_SEARCH_VIEW + "?deptId=" + deptId + "&orgId=" + orgId;
		}
		return ResponseMessage.addPromptTypeForPath(POST_SEARCH_VIEW
				+ "?orgId=" + orgId, PromptType.delPostFail);
	}

	/**
	 * 打开设置角色界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "setRoleUI")
	public String setRoleUI(Integer postId, Map<String, Object> model,
			ResponseMessage res) {
		// 1.获取已选中的角色
		List<Role> grantedRoles = postService.queryGrantedRoles(postId);

		// 2.该岗位未设置的角色
		List<Role> notGrantedRoles = postService.queryNotGrantedRoles(postId);

		// 3.岗位的详细信息
		Post post = mybatisAutoMapperService.selectByPrimaryKey(
				PostMapper.class, postId, Post.class);

		model.put("grantedRoles", grantedRoles);
		model.put("notGrantedRoles", notGrantedRoles);
		model.put("post", post);
		model.put("info", ResponseMessage.parseMsg(res));

		return POST_SET_ROLE_UI;
	}

	/**
	 * 岗位设置角色
	 * 
	 * @param postId
	 *            岗位id
	 * @param roleIds
	 *            角色id
	 * @return
	 */
	@RequestMapping(value = "setrole")
	public String setRole(Integer postId, String roleIds) {
		String[] ids = StringUtils.split(roleIds, ",");
		Integer[] roles = new Integer[ids.length];

		for (int i = 0; i < ids.length; i++) {
			roles[i] = Integer.parseInt(ids[i]);
		}
		postService.saveRoles(postId, roles);
		return ResponseMessage.addPromptTypeForPath(POST_SET_ROLE + "?postId="
				+ postId, PromptType.updateRole);
	}

	// 验证岗位名是否重复
	@RequestMapping(value = "checkName")
	@ResponseBody
	public boolean checkName(String postName, Integer postId, Integer deptId) {
		String postname = postName.trim();
		PostExample example = new PostExample();
		Criteria criteria = example.createCriteria().andPostNameEqualTo(
				postname);
		if (deptId != null) {
			criteria.andDeptIdEqualTo(deptId);
		}
		if (postId != null) {
			criteria.andPostIdNotEqualTo(postId);
		}
		int size = mybatisAutoMapperService.countByExample(PostMapper.class,
				example);
		if (size > 0) {
			return false;
		}
		return true;
	}
}
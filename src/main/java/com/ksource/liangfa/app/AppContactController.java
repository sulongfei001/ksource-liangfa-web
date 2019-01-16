package com.ksource.liangfa.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.DistrictService;
import com.ksource.liangfa.service.system.OrgService;
import com.ksource.liangfa.service.system.UserService;

/**
 * 通讯录
 * 
 * @author 符家鑫
 * @date 2017
 */
@Controller
@RequestMapping("/app/contact")
public class AppContactController {

	@Autowired
	private DistrictService districtService;
	@Autowired
	private OrgService orgService;
	@Autowired
	private UserService userService;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 相应全部行政区划
	 * 
	 * @author: LXL
	 * @return:String
	 * @createTime:2017年9月23日 上午11:13:06
	 */
	@RequestMapping("listByDistrict")
	@ResponseBody
	public String listByDistrict(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();// 封装异常信息或者没有districtCode的请求提醒
		try {
			List<District> dis = districtService.districtTreeManage(null);
			if (dis.size() == 0) {
				jsonObject.put("msg", "行政区划列表为空");
			} else {
				jsonObject.put("msg", "获取行政区划列表成功");
			}
			jsonObject.put("success", true);
			jsonObject.put("districtList", dis);
			return jsonObject.toJSONString();
		} catch (Exception e) {
			logger.error("相应行政区划列表失败!");
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "行政区划列表获取发生异常！!");
			return jsonObject.toJSONString();
		}
	}

	/**
	 * 根据行政区划id查询组织机构
	 * 
	 * @author: LXL
	 * @return:String
	 * @createTime:2017年9月23日 上午11:02:36
	 */
	@RequestMapping("orgList")
	@ResponseBody
	public String orgList(String districtCode, HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();// 封装异常信息或者没有districtCode的请求提醒
		if (districtCode != null) {
			try {
				logger.info("查询组织机构!");
				List<Organise> organiseList = orgService.findByDistrict(districtCode);
				if (organiseList.size() == 0) {
					jsonObject.put("msg", "组织机构列表为空");
				} else {
					jsonObject.put("msg", "获取组织机构列表成功");
				}
				jsonObject.put("success", true);
				jsonObject.put("organiseList", organiseList);
				return jsonObject.toJSONString();
			} catch (Exception e) {
				logger.error("响应机构时发生异常!");
				e.printStackTrace();
				jsonObject.put("msg", "组织机构获取发生异常！！");
				return jsonObject.toJSONString();
			}
		}
		jsonObject.put("msg", "请输入行政区划ID");
		return jsonObject.toJSONString();
	}

	/**
	 * 获取机构下所有用户
	 * 
	 * @author: LXL
	 * @return:String
	 * @createTime:2017年9月23日 上午11:28:46
	 */
	@RequestMapping("userList")
	@ResponseBody
	public String userList(Integer orgCode, HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();// 封装异常信息或者没有districtCode的请求提醒
		try {
			logger.info("根据组织机构查询用户!");
			List<User> users = userService.findByOrgCodeForApp(orgCode);
			jsonObject.put("msg", "获取用户列表成功");
			jsonObject.put("success", true);
			jsonObject.put("users", users);
			return jsonObject.toJSONString();
		} catch (Exception e) {
			logger.error("响应用户列表时发生异常!");
			e.printStackTrace();
			jsonObject.put("msg", "用户列表获取发生异常！！");
			jsonObject.put("success", false);
			return jsonObject.toJSONString();
		}
	}

	/**
	 * 获取用户信息
	 * 
	 * @author: LXL
	 * @return:String
	 * @createTime:2017年9月23日 上午11:34:25
	 */
	@RequestMapping("getUser")
	@ResponseBody
	public String getUser(String userId, HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();// 封装异常信息或者没有districtCode的请求提醒
		try {
			logger.info("根据id查询用户!");
			User user = userService.selectOrgDistrictByUserId(userId);
			jsonObject.put("msg", "用户获取成功");
			jsonObject.put("success", true);
			jsonObject.put("user", user);
			return jsonObject.toJSONString();
		} catch (Exception e) {
			logger.error("响应用户时发生异常!");
			e.printStackTrace();
			jsonObject.put("msg", "用户信息获取发生异常！！");
			jsonObject.put("success", false);
			return jsonObject.toJSONString();
		}
	}

	/**
	 * 根据用户姓名查找用户列表
	 * 
	 * @author: LXL
	 * @return:String
	 * @createTime:2017年9月23日 下午12:02:28
	 */
	@RequestMapping("query")
	@ResponseBody
	public JSONObject query(String userName, HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();// 封装异常信息或者没有districtCode的请求提醒
		try {
			List<User> users = userService.searchUserByUserNameForApp(userName);
			if (users.size() == 0) {
				jsonObject.put("msg", "用户列表为空");
			} else {
				jsonObject.put("msg", "获取用户列表成功");
			}
			jsonObject.put("success", true);
			jsonObject.put("users", users);
			return jsonObject;
		} catch (Exception e) {
			logger.error("响应用户列表时发生异常!",e.getMessage());
			e.printStackTrace();
			jsonObject.put("msg", "用户信息列表获取发生异常!!");
			jsonObject.put("success", false);
			return jsonObject;
		}
	}
}
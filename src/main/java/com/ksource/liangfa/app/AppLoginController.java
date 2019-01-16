package com.ksource.liangfa.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.util.JsonMapper;
import com.ksource.common.util.PasswordUtil;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.system.AuthorityService;
import com.ksource.liangfa.service.system.UserService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 移动端验证用户登录
 * @author 符家鑫
 * @date 2017
 */
@Controller
@RequestMapping("/app")
public class AppLoginController {
	
    @Autowired
    private UserService userService;
    @Autowired
    AuthorityService authorityService;
	
	/**
	 * @param username 用户名或手机号
	 * @param passwd 密码
	 */
	@RequestMapping("login")
	@ResponseBody
	public String login(String username,String password,String deviceCode,HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        boolean allow = false;
        if(StringUtils.isBlank(username)||StringUtils.isBlank(password)){
            jsonObject.put("success", false);
            jsonObject.put("msg", "账号或密码不能为空！");
            allow = false;
        }else{
            User user = userService.loginByAccount(username);
            if(user == null) {
                jsonObject.put("success", false);
                jsonObject.put("msg", "该账号不存在！");
                allow = false;
            }else if(user.getIsLogin() == null || user.getIsLogin()==0){
                jsonObject.put("success", false);
                jsonObject.put("msg", "该账号被禁止登陆！");
                allow = false;
            }else if(user.getIsValid() != null && user.getIsValid() == Const.STATE_INVALID){
                jsonObject.put("success", false);
                jsonObject.put("msg", "该账号被冻结！");    
                allow = false;
            }else if(!PasswordUtil.encrypt(password).equals(user.getPassword())) {
                jsonObject.put("success", false);
                jsonObject.put("msg","密码错误！");   
                allow = false;
            }else if(StringUtils.isNotBlank(user.getDeviceCode()) && !user.getDeviceCode().equals(deviceCode)){
                jsonObject.put("success", false);
                jsonObject.put("msg","该账号禁止在此手机登陆，如需登陆请联系管理员!");
            }else{
                if(StringUtils.isBlank(user.getDeviceCode())){
                    User userParm = new User();
                    userParm.setUserId(user.getUserId());
                    userParm.setDeviceCode(deviceCode);
                    userService.updateByPrimaryKeySelective(user);
                }
                SystemContext.setCurrentUser(request, user);
                authorityService.loginWithApp(user);
                jsonObject.put("success", true);
                jsonObject.put("msg","登陆成功！"); 
                jsonObject.put("user", user);
                }
            }
		return jsonObject.toJSONString();
	}
	
	/**
	 * 用户退出
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("logout")
	@ResponseBody
	public String logout(HttpSession session){
        User user = SystemContext.getCurrentUser(session);
        if (user != null) {
            authorityService.logout(user);
        }
        SystemContext.clearCurrentUser(session);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("msg","退出成功！"); 
        return jsonObject.toJSONString();
	}

}

package com.ksource.liangfa.web.system;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.IA300.IA300Lib;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.util.ServletResponseUtil;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.DistrictExample;
import com.ksource.liangfa.domain.UkeyUser;
import com.ksource.liangfa.domain.UkeyUserIa;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.UkeyUserIaMapper;
import com.ksource.liangfa.mapper.UkeyUserMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.AuthorityService;
import com.ksource.liangfa.service.system.UserService;
import com.ksource.license.LicenseInfo;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("/system/authority")
public class LoginIAKeyController {
	 private static final String OPEN_HOME = "/openhome";
	@Autowired
	AuthorityService authorityService;
    @Autowired
    MybatisAutoMapperService mybatisAutoMapperService;
    @Autowired
    private UserService userService;
	
	@RequestMapping(value = "login-iakey")
	public ModelAndView loginIAKey(HttpSession session,  
			String uid, String Random, String ClientDigest,String securityCode) {
	    ModelAndView view = new ModelAndView("/login-main");
	
	    //系统授权验证
	    LicenseInfo licenseInfo = LicenseInfo.getLicenseInfo();
	    if (licenseInfo == null) {
	        view.setViewName("/licError");
	        view.addObject("errorMsg", "未授权！");
	        return view;
	    }
	    DistrictExample example = new DistrictExample();
	    example.createCriteria().andUpDistrictCodeIsNull();
	    List<District> districtList = mybatisAutoMapperService.selectByExample(DistrictMapper.class, example, District.class);
	    if (districtList.size() != 1) {
	        view.setViewName("/licError");
	        view.addObject("errorMsg", "未授权！");
	        return view;
	    }
	    LicenseInfo.LicError licError = licenseInfo.check();
	    if (licError == null || licError == LicenseInfo.LicError.LicError) {
	        view.setViewName("/licError");
	        view.addObject("errorMsg", "未授权！");
	        return view;
	    }
	    
        if (licError.equals(LicenseInfo.LicError.Overdue)) {
            view.setViewName("/licError");
            view.addObject("errorMsg", "授权已过期！");
            return view;
          }
	
	    //session保存的校验码
	    String securityCodeSession = "";
	    if (session.getAttribute("securityCode") != null) {
	        securityCodeSession = session.getAttribute("securityCode").toString();
	    } else {
	        return view;
	    }
	    
	    if(StringUtils.isBlank(uid)){
	    	 return view;
	    }
	    
	    //执行校验
	    UkeyUserIa ukeyUserIa = mybatisAutoMapperService.selectByPrimaryKey(UkeyUserIaMapper.class, uid, UkeyUserIa.class);
	    if (ukeyUserIa == null || ukeyUserIa.getServerIaguid().equals("")) {
	        view.addObject("error", "用户不存在,u-key设备未授权！");
	        return view;
	    }
	    User user = userService.loginByAccount(ukeyUserIa.getAccount());
        if (user == null || user.getAccount().equals("")) {
            view.addObject("error", "用户不存在,u-key设备未授权！");
            return view;
        }
        ServiceResponse response = authorityService.loginWithIAkey(user, securityCodeSession, uid, securityCode);
        if (!response.getResult()) {
            view.addObject("error", response.getMsg());
            return view;
        }
        
        SystemContext.setCurrentUser(session, user);
        view.setViewName(OPEN_HOME);
		return view;
	}
	
	
	@RequestMapping(value = "remoteResetUserPin")
	@ResponseBody
	public ServiceResponse remoteResetUserPin(String uid,String RequestInfo,String defaultPassWord,HttpServletResponse response){
		ServiceResponse serviceResponse = new ServiceResponse();
		UkeyUserIa ukeyUserIa = mybatisAutoMapperService.selectByPrimaryKey(UkeyUserIaMapper.class, uid, UkeyUserIa.class);
	    if (ukeyUserIa == null || ukeyUserIa.getServerIaguid().equals("")) {
	    	serviceResponse.setResult(false);
	    	serviceResponse.setMsg("用户不存在,u-key设备未授权！");
	    	return serviceResponse;
	    }
		String TriDESKey = ukeyUserIa.getDeskey();		//数据库中取3DES密钥
		IA300Lib ia300 = new IA300Lib();
		String responseinfo = ia300.ResetUserPin(TriDESKey, RequestInfo, defaultPassWord);
    	serviceResponse.setResult(true);
    	serviceResponse.setMsg(responseinfo);
		return serviceResponse;
	}
	
    // 退出
    @RequestMapping(value = "logout_iakey")
    public String logout(HttpSession session) {
        User user = SystemContext.getCurrentUser(session);
        if (user != null) {   //防止重复退出时业务日志拦截失败
            authorityService.logout(user);
        }
        SystemContext.clearCurrentUser(session);
        return "/login-main";
    }
}


package com.ksource.liangfa.web.system;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.UkeyUserIa;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.UkeyUserIaMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.UserService;

@Controller
@RequestMapping(value="/system/iakey")
public class IAKeyController {

	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/main")
	public ModelAndView main(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("system/iakey/main");
		return mv;
	}
	
	@RequestMapping(value="/create")
	@ResponseBody
	public String create(String uid,String deskey,String seed,String account){
		UkeyUserIa ukeyUserIa = new UkeyUserIa();
		ukeyUserIa.setAccount(account);
		ukeyUserIa.setDeskey(deskey);
		ukeyUserIa.setServerSeed(seed);
		ukeyUserIa.setServerIaguid(uid);
		int count = 0;
		try{
		    mybatisAutoMapperService.deleteByPrimaryKey(UkeyUserIaMapper.class, uid);
			count = mybatisAutoMapperService.insert(UkeyUserIaMapper.class, ukeyUserIa);
			if(count > 0){
			    User user = userService.loginByAccount(account);
			    if(user != null && StringUtils.isNotBlank(user.getOrgName())){
			        return user.getOrgName();
			    }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
        return "";
	}
	
	/**
	 * 删除iakey信息
	 * @param uid
	 * @return
	 */
	@RequestMapping(value="/del")
	@ResponseBody
	public boolean del(String uid){
		UkeyUserIa ukeyUserIa = new UkeyUserIa();
		ukeyUserIa.setServerIaguid(uid);
		int count = 0;
		try{
			count = mybatisAutoMapperService.deleteByPrimaryKey(UkeyUserIaMapper.class, uid);
		}catch(Exception e){
			e.printStackTrace();
		}
		return count > 0;
	}
}

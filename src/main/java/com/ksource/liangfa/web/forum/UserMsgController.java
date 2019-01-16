package com.ksource.liangfa.web.forum;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.util.JsTreeUtils;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.OrganiseExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.UserExample;
import com.ksource.liangfa.domain.UserMsg;
import com.ksource.liangfa.domain.UserMsgExample;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.mapper.UserMsgMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.forum.UserMsgService;
import com.ksource.liangfa.service.system.UserService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * @author fcy
 */

@Controller
@RequestMapping("/usermsg")
public class UserMsgController {
//	跳转到消息发送页面
	private static final String USERMSG_USER = "/forum/usermsg/userMsgAdd" ;
//	发送消息后跳转回当前页
	private static final String USERMSG_ADD  = "redirect:/usermsg/getUser/" ;
//	跳转到未读消息页面
	private static final String USERMSG_NOTREADMSG = "/forum/usermsg/notReadMsg" ;
//	保存页面的页数，适用于跳转修改信息的状态后
	private static final String PAGE = "page" ;
//	回复消息页面
	private static final String USERMSG_REPLY = "/forum/usermsg/userMsgReply" ;
//	保存发信人姓名
	private static final String USERMSG_FROMNAME = "fromName" ;
//	保存userMsg对象
	private static final String USERMSG_OBJECT = "userMsgObject" ;
//	跳转到历史记录查看页面
	private static final String USERMSG_HISTORY = "/forum/usermsg/userMsgHistory" ;
//	保存历史记录集合变量
	private static final String USERMSG_HISTORY_LISTS = "userMsgHistoryLists" ;
	@Autowired
	private UserService userService ;
	@Autowired
	private UserMsgService userMsgService ;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService ;
	@Autowired
	private SystemDAO systemDAO;
	
//	  根据USER_表查找信息用户的基本
    @RequestMapping("/checkUser")
    public void checkUser(HttpServletResponse response,Integer id) {
    	response.setContentType("application/json");
        PrintWriter out = null;
    	String trees ;

    	if(id == null) {
    	  OrganiseExample organiseExample = new OrganiseExample() ;
    	  organiseExample.createCriteria().andIsDeptEqualTo(Const.STATE_INVALID) ;
          List<Organise> organises = mybatisAutoMapperService.selectByExample(OrganiseMapper.class, organiseExample, Organise.class);
          trees = JsTreeUtils.orgJsonztree(organises);
    	}else {
    		UserExample userExample = new UserExample() ;
    		userExample.createCriteria().andOrgIdEqualTo(id) ;
            List<User> users = mybatisAutoMapperService.selectByExample(UserMapper.class, userExample, User.class);
            trees = JsTreeUtils.taskFenpaiUserJsonztree(users);
    	}
    	
        try {
            out = response.getWriter();
            out.print(trees);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
    
//    点击用户名得到用户的基本信息同时可以进行发送信息
    @RequestMapping("/getUser/{id}")
    public String getUser(@PathVariable String id,Integer sendMsgSucess,ModelMap map) {
    	map.addAttribute("user", userService.find(id)) ;
    	if(sendMsgSucess!=null && sendMsgSucess==1){
    		map.addAttribute("sendMsgSucess", sendMsgSucess) ;
    	}
    	return USERMSG_USER ;
    }
    
//    发送信息
    @RequestMapping("/addUserMsg")
    public String addUserMsg(UserMsg userMsg,HttpSession session) {
    	userMsg.setFrom(SystemContext.getCurrentUser(session).getUserId()) ;
    	userMsg.setDataTime(new Date()) ;
    	userMsg.setReplyMsgId(Const.STATE_INVALID) ;
    	userMsg.setReadState(Const.STATE_INVALID) ;
    	userMsg.setId(systemDAO.getSeqNextVal(Const.TABLE_USER_MSG));
    	userMsgService.insertUserMsg(userMsg) ;
    	return USERMSG_ADD +  userMsg.getTo()+"?sendMsgSucess=1" ;
    }
    
//    查看未读的消息
    @RequestMapping("/notReadMsg")
    public String notReadMsg(HttpSession session,ModelMap map,String page) {
    	String userId = SystemContext.getCurrentUser(session).getUserId() ;
    	map.addAttribute(PAGE, page) ;
    	map.addAttribute("userMsgList",userMsgService.getNotReadMsg(userId)) ;
    	map.addAttribute("userId", userId) ;
    	return USERMSG_NOTREADMSG ;
    }
    
//    ajax请求，用于修改信息的状态，然后返回过去一个json对象，如果json对象中有中文字符串，这个地方需要有所变更
    @RequestMapping("/updateReadState")
    @ResponseBody
    public ServiceResponse updateReadState(int checkID) {
    	ServiceResponse sr = new ServiceResponse(true,"");
    		UserMsg userMsg = new UserMsg() ;
    		userMsg.setId(checkID) ;
    		userMsg.setReadState(Const.STATE_VALID);
    		UserMsgExample example = new UserMsgExample();
    		example.createCriteria().andIdEqualTo(checkID).andReadStateEqualTo(Const.STATE_VALID);
    		int count =mybatisAutoMapperService.countByExample(UserMsgMapper.class, example);
    		if(count!=0){
    			sr.setResult(false);
    		}else{
    			mybatisAutoMapperService.updateByPrimaryKeySelective(UserMsgMapper.class, userMsg) ;
    		}
    	 return sr ;
    }
    
//    回复信息
    @RequestMapping("/replyUserMsg/{userMsgID}")
    public String replyUserMsg(@PathVariable int userMsgID,String userId,ModelMap map) {
    	UserMsg userMsg = mybatisAutoMapperService.selectByPrimaryKey(UserMsgMapper.class, userMsgID, UserMsg.class) ;
    	map.addAttribute(USERMSG_OBJECT,userMsg) ;
    	map.addAttribute(USERMSG_FROMNAME,mybatisAutoMapperService.selectByPrimaryKey(UserMapper.class, userId,User.class).getUserName()) ;
    	return USERMSG_REPLY ;
    }
    
//    回复保存
    @RequestMapping("/userMsgReplyAdd")
    @ResponseBody
    public ServiceResponse userMsgReplyAdd(UserMsg userMsg,HttpSession session) {
    	userMsg.setDataTime(new Date()) ;
    	userMsg.setFrom(SystemContext.getCurrentUser(session).getUserId()) ;
    	userMsg.setReadState(Const.STATE_INVALID) ;
 
    	userMsgService.add(userMsg) ;
    	
    	ServiceResponse sr = new ServiceResponse();
    	sr.setResult(true) ;
    	return sr ;
    }
    
//    查看历史记录
    @RequestMapping("/userMsgHistory")
    public String userMsgHistory(String fromID,Map<String, Object> map,HttpSession session) {
    	map.put("from", fromID) ;
    	map.put("to",SystemContext.getCurrentUser(session).getUserId()) ;
    	map.put("userName", mybatisAutoMapperService.selectByPrimaryKey(UserMapper.class, fromID,User.class).getUserName()) ;
    	map.put("loginName", SystemContext.getCurrentUser(session).getUserName()) ;
    	map.put(USERMSG_HISTORY_LISTS, userMsgService.findUserMsgHistroy(map)) ;
    	return USERMSG_HISTORY ;
    }
    @RequestMapping("getSendMsg")
    @ResponseBody
    public List<UserMsg> getSendMsg(Integer index,HttpSession session){
    	UserMsg msg = new UserMsg();
    	msg.setFrom(SystemContext.getCurrentUser(session).getUserId());
    	//查询发送信息
        return userMsgService.find(msg,index,10);
    }
    @RequestMapping("getReceiveMsg")
    @ResponseBody
    public List<UserMsg> receiveMsg(Integer index,HttpSession session){
    	//查询已读接收信息
    	UserMsg msg = new UserMsg();
    	msg.setReadState(Const.STATE_VALID);
    	msg.setTo(SystemContext.getCurrentUser(session).getUserId());
        return userMsgService.find(msg,index,10);
    }
    
  @RequestMapping("/notReadReceiveMsg")
  @ResponseBody
  public List<UserMsg> notReadReceiveMsg(Integer index,HttpSession session) {
	  //查询未读接收信息
	UserMsg msg = new UserMsg();
	msg.setReadState(Const.STATE_INVALID);
	msg.setTo(SystemContext.getCurrentUser(session).getUserId());
      return userMsgService.find(msg,index,10);
  }
  @RequestMapping("/del/{id}")
  @ResponseBody
  public ServiceResponse del(@PathVariable Integer id) {
	 ServiceResponse res = new ServiceResponse(true,"");
	 mybatisAutoMapperService.deleteByPrimaryKey(UserMsgMapper.class, id);
	return res;
  }
}

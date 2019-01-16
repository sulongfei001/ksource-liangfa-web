package com.ksource.liangfa.service.forum;

import java.util.List;
import java.util.Map;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.UserMsg;

public interface UserMsgService {
	 /**
	  * 检索出未读信息
	  * @param userID
	  * @return
	  */
	List<UserMsg> getNotReadMsg(String userID) ;
	 /**
	 *  根据回复信息的ID检索出所有的该ID的回复信息
	 * @param id
	 * @return
	 */
	List<UserMsg> userMsgList(int id) ;
	 /**
	  * 信息保存
	  * @param userMsg
	  */
	void add(UserMsg userMsg) ;

	/**
	 * 通过参数map内的from,to两个参数查询双方的电子邮件历史信息
	 * @param map
	 * @return
	 */
	List<UserMsg> findUserMsgHistroy(Map<String, Object> map) ;
	/**
	 * 得到某一用户所发送的信息集合
	 * @param string
	 * @return
	 */
	List<UserMsg> getSendMsg(String string);
	/**
	 *  得到某一用户所接收的信息集合
	 * @param string
	 * @return
	 */
	List<UserMsg> getReceiveMsg(String string);
	/**
	 * 根据页码和查询条件查询数据。
	 * @param msg
	 * @param limit
	 * @return
	 */
	List<UserMsg> find(UserMsg msg, Integer page,Integer limit);
	
	/**
	 * 发送消息
     * @param msg
     * @return
     */
	ServiceResponse insertUserMsg(UserMsg msg) ;
}

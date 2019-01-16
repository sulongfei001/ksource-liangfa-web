package com.ksource.liangfa.service.forum;


import java.util.List;
import java.util.Map;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.common.util.JsonMapper;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.UserMsg;
import com.ksource.liangfa.domain.UserMsgExample;
import com.ksource.liangfa.mapper.UserMsgMapper;
import com.ksource.syscontext.Const;

@Service
public class UserMsgServiceImpl implements UserMsgService {
	private static JsonMapper binder = JsonMapper.buildNonNullMapper();
	@Autowired
	private UserMsgMapper userMsgMapper ;
	@Autowired
	private SystemDAO systemDAO;
	//日志
		private static final Logger log = LogManager.getLogger(UserMsgServiceImpl.class);
	@Override
	@Transactional(readOnly=true)
	public List<UserMsg> getNotReadMsg(String userID) {
		return userMsgMapper.notReadMsg(userID);
	}

	@Override
	@Transactional(readOnly=true)
	public List<UserMsg> userMsgList(int id) {
		return userMsgMapper.userMsgList(id) ;
	}

	@Override
	@Transactional
	@LogBusiness(operation="发送邮件",business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_INSERT,target_domain_mapper_class = UserMsgMapper.class,target_domain_position=0)
	public void add(UserMsg userMsg) {
		try {
			userMsg.setId(systemDAO.getSeqNextVal(Const.TABLE_USER_MSG));
			userMsgMapper.insert(userMsg) ;
		} catch (Exception e) {
			log.error("发送邮件失败：" + e.getMessage());
			throw new BusinessException("发送邮件失败");
		}
	}
	@Override
	@Transactional
	public List<UserMsg> findUserMsgHistroy(Map<String, Object> map) {
		return userMsgMapper.findUserMsgID(map) ;
	}

	@Override
	@Transactional(readOnly=true)
	public List<UserMsg> getSendMsg(String userId) {
		UserMsgExample example = new UserMsgExample();
		example.createCriteria().andFromEqualTo(userId);
		try{
			return userMsgMapper.selectByExample(example);
		}catch (Exception e) {
			log.error("查询邮件失败：" + e.getMessage());
			throw new BusinessException("查询邮件失败");
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<UserMsg> getReceiveMsg(String userId) {
		UserMsgExample example = new UserMsgExample();
		example.createCriteria().andToEqualTo(userId);
		try{
			return userMsgMapper.selectByExample(example);
		}catch (Exception e) {
			log.error("查询邮件失败：" + e.getMessage());
			throw new BusinessException("查询邮件失败");
		}
	}

	@Override
	public List<UserMsg> find(UserMsg msg, Integer page,Integer limit) {
		try{
			if(page==null){
				page=1;
			}
			int start= (page-1)*limit;
			return systemDAO.find(UserMsg.class,binder.beanToMap(msg),start,limit,"find");
		}catch (Exception e) {
			log.error("查询邮件失败：" + e.getMessage());
			throw new BusinessException("查询邮件失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation="发送邮件",business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_INSERT,target_domain_mapper_class = UserMsgMapper.class,target_domain_position=0)
	public ServiceResponse insertUserMsg(UserMsg msg) {
		try {
			ServiceResponse response = new ServiceResponse(true, "发送邮件成功！");
			userMsgMapper.insert(msg) ;
			return response;
		} catch (Exception e) {
			log.error("发送邮件失败：" + e.getMessage());
			throw new BusinessException("发送邮件失败");
		}
	}
}

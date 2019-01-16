package com.ksource.liangfa.service.sms;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.ProcDiagram;
import com.ksource.liangfa.domain.ProcDiagramExample;
import com.ksource.liangfa.domain.SmsBind;
import com.ksource.liangfa.domain.SmsBindExample;
import com.ksource.liangfa.domain.SmsBindExample.Criteria;
import com.ksource.liangfa.mapper.ProcDiagramMapper;
import com.ksource.liangfa.mapper.SmsBindMapper;
import com.ksource.syscontext.Const;


/**
 * 此类为 TODO:类描述
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2011-12-15
 * time   下午2:56:20
 */
@Service
public class SmsServiceImpl implements SmsService {
	@Autowired
	private SmsBindMapper smsBindMapper;
	@Autowired
	private ProcDiagramMapper procDiagramMapper;
	@Autowired
	private SystemDAO systemDAO;
	//日志
		private static final Logger log = LogManager.getLogger(SmsServiceImpl.class);

	@Override
	@Transactional
	public ServiceResponse bindProcSms(String action, String groupDefId,
			String eleDefId) {
		ServiceResponse res = new ServiceResponse(true,"短信服务:代办任务短信提示绑定成功");
		SmsBindExample example = new SmsBindExample();
		Criteria cri = example.createCriteria();
		cri.andGroupDefIdEqualTo(groupDefId);
		if(eleDefId!=null){//如果有eleDefId则单个删除,否则全部删除
			cri.andEleDefIdEqualTo(eleDefId);
		}
		try{
		if("del".equals(action)){//删除动作
			smsBindMapper.deleteByExample(example);
		}else{//添加动作
			SmsBind sms = new SmsBind();
			sms.setState(Const.STATE_VALID);
			sms.setGroupDefId(groupDefId);
			if(eleDefId!=null){//如果有eleDefId则是单个添加,否则全部添加
				sms.setEleDefId(eleDefId);
				if(smsBindMapper.countByExample(example)==0){
					sms.setId(systemDAO.getSeqNextVal(Const.TABLE_SMS_BIND));
					smsBindMapper.insertSelective(sms);
				}else{
					smsBindMapper.updateByExampleSelective(sms, example);
				}
			}else{
				ProcDiagramExample diagramExample = new ProcDiagramExample();
				diagramExample.createCriteria().andProcDefIdEqualTo(
						groupDefId);
				List<ProcDiagram> procDiagramList = procDiagramMapper
						.selectByExample(diagramExample);
				//先全部删除 再添加
				smsBindMapper.deleteByExample(example);
				for(ProcDiagram procDiagram:procDiagramList){
					sms.setId(systemDAO.getSeqNextVal(Const.TABLE_SMS_BIND));
					sms.setEleDefId(procDiagram.getElementId());
					smsBindMapper.insertSelective(sms);
				}
			}
		}
		} catch (Exception e) {
			log.error("代办任务短信提示绑定失败" + e.getMessage());
			throw new BusinessException("代办任务短信提示绑定失败");
		}
		return res;
	}

	@Override
	@Transactional
	public ServiceResponse bindNoticeSms(String action, String eleDefId) {
	ServiceResponse res = new ServiceResponse(true,"短信服务:通知公告短信提示绑定成功");
	try{
		if("del".equals(action)){
			SmsBindExample example = new SmsBindExample();
			example.createCriteria().andGroupDefIdEqualTo(Const.SMS_BIND_GROUP_DEF_NOTICE)
			.andEleDefIdEqualTo(eleDefId);
			smsBindMapper.deleteByExample(example);
		}else{
			SmsBind sms = new SmsBind();
			sms.setGroupDefId(Const.SMS_BIND_GROUP_DEF_NOTICE);
			sms.setEleDefId(eleDefId);
			sms.setState(Const.STATE_VALID);
			sms.setId(systemDAO.getSeqNextVal(Const.TABLE_SMS_BIND));
			smsBindMapper.insertSelective(sms);
			
		}
	} catch (Exception e) {
		log.error("通知公告短信提示绑定失败" + e.getMessage());
		throw new BusinessException("通知公告短信提示绑定失败");
	}
		return res;
	}
}
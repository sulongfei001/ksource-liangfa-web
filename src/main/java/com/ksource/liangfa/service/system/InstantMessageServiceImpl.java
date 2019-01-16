package com.ksource.liangfa.service.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.util.GetuiUtil;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseConsultation;
import com.ksource.liangfa.domain.ClueInfo;
import com.ksource.liangfa.domain.CommunionReceive;
import com.ksource.liangfa.domain.CommunionSend;
import com.ksource.liangfa.domain.InstantMessage;
import com.ksource.liangfa.domain.InstructionReceive;
import com.ksource.liangfa.domain.Notice;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.WorkReportReceive;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.ClueInfoMapper;
import com.ksource.liangfa.mapper.InstantMessageMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Service
public class InstantMessageServiceImpl implements InstantMessageService {
    
    @Autowired
    private InstantMessageMapper instantMessageMapper;
    @Autowired
    private SystemDAO systemDAO;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ClueInfoMapper clueInfoMapper;
    @Autowired
    private CaseBasicMapper basicMapper;

    @Override
    public PaginationHelper<InstantMessage> query(InstantMessage instantMessage,String page) {
    	PaginationHelper<InstantMessage> msgs = systemDAO.find(instantMessage,null, page, "queryInstengMessageCount", "queryInstengMessageList");
        return msgs;
    }

    @Override
    public InstantMessage selectByPrimaryKey(Integer msgId) {
        return instantMessageMapper.selectByPrimaryKey(msgId);
    }

    @Override
    public void addNoticeMessage(Notice notice, String orgIds) {
        List<User> userList = new ArrayList<User>();
        if(notice.getIsPublic() == Notice.PUBLIC_YES){
            userList = userMapper.queryAll();
        }
        if(StringUtils.isNotBlank(orgIds)){
            String[] orgIdAry = orgIds.split(",");
            userList = userMapper.queryByOrgIds(orgIdAry);
        }
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
        for(User user:userList){
            InstantMessage instantMessage = new InstantMessage();
            instantMessage.setMsgId(systemDAO.getSeqNextVal(Const.TABLE_INSTANT_MESSAGE));
            instantMessage.setTitle("通知公告");
            instantMessage.setMsgType(InstantMessage.MSG_TYPE_NOTICE);
            instantMessage.setContent(notice.getNoticeTitle());
            instantMessage.setCreateTime(new Date());
            instantMessage.setReadStatus(Notice.READ_STATUS_NO);
            instantMessage.setCreateUser(notice.getNoticeCreater());
            instantMessage.setReceiveUser(user.getUserId());
            instantMessage.setBusinessKey(notice.getNoticeId().toString());
            instantMessageMapper.insertSelective(instantMessage);
            try {
                GetuiUtil.getInstance().PushToIosSingle(user.getUserId(), instantMessage.getTitle(), JSONObject.toJSONString(instantMessage,serializeConfig));
                } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteByBusinessKey(String businessKey) {
        instantMessageMapper.deleteByBusinessKey(businessKey);
    }

    @Override
    public void addCaseMessage(String caseId,String caseNo, String receiveUser, String createUser,Integer receiveOrg) {
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
        if(receiveOrg != null){
            List<User> userList = userMapper.findByOrgCodeForApp(receiveOrg);
            for(User user:userList){
                InstantMessage instantMessage = new InstantMessage();
                instantMessage.setMsgId(systemDAO.getSeqNextVal(Const.TABLE_INSTANT_MESSAGE));
                instantMessage.setTitle("待办案件");
                instantMessage.setMsgType(InstantMessage.MSG_TYPE_CASE);
                instantMessage.setContent("您有一个案件编号为：【"+caseNo+"】的案件需要办理，请登陆两法衔接平台（电脑版）进行办理。");
                instantMessage.setCreateTime(new Date());
                instantMessage.setReadStatus(Notice.READ_STATUS_NO);
                instantMessage.setCreateUser(createUser);
                instantMessage.setReceiveUser(user.getUserId());
                instantMessage.setBusinessKey(caseId);
                instantMessageMapper.insert(instantMessage);
                try {
                    GetuiUtil.getInstance().PushToIosSingle(instantMessage.getReceiveUser(), instantMessage.getTitle(),JSONObject.toJSONString(instantMessage,serializeConfig));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else{
            InstantMessage instantMessage = new InstantMessage();
            instantMessage.setMsgId(systemDAO.getSeqNextVal(Const.TABLE_INSTANT_MESSAGE));
            instantMessage.setTitle("待办案件");
            instantMessage.setMsgType(InstantMessage.MSG_TYPE_CASE);
            instantMessage.setContent("您有一个案件编号为：【"+caseNo+"】的案件需要办理，请登陆两法衔接平台（电脑版）进行办理。");
            instantMessage.setCreateTime(new Date());
            instantMessage.setReadStatus(Notice.READ_STATUS_NO);
            instantMessage.setCreateUser(createUser);
            instantMessage.setReceiveUser(receiveUser);
            instantMessage.setBusinessKey(caseId);
            instantMessageMapper.insert(instantMessage);
            try {
                GetuiUtil.getInstance().PushToIosSingle(instantMessage.getReceiveUser(), instantMessage.getTitle(),JSONObject.toJSONString(instantMessage,serializeConfig));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addCaseConsultationMessage(CaseConsultation caseConsultation,String users) {
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
        if(StringUtils.isNotBlank(users)){
            String[] userAry = users.split(",");
            for(String userId:userAry){
                InstantMessage instantMessage = new InstantMessage();
                instantMessage.setMsgId(systemDAO.getSeqNextVal(Const.TABLE_INSTANT_MESSAGE));
                instantMessage.setTitle("案件咨询");
                instantMessage.setMsgType(InstantMessage.MSG_TYPE_CONSULATION);
                instantMessage.setContent(caseConsultation.getTitle());
                instantMessage.setCreateTime(new Date());
                instantMessage.setReadStatus(Notice.READ_STATUS_NO);
                instantMessage.setCreateUser(caseConsultation.getInputer());
                instantMessage.setReceiveUser(userId);
                instantMessage.setBusinessKey(caseConsultation.getId().toString());
                instantMessageMapper.insert(instantMessage);
                try {
                    GetuiUtil.getInstance().PushToIosSingle(instantMessage.getReceiveUser(), instantMessage.getTitle(),JSONObject.toJSONString(instantMessage,serializeConfig));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void addCommuionMessage(CommunionReceive communionReceive,String createUser, String orgId) {
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));    
        List<User> userList = userMapper.findByOrgCodeForApp(Integer.valueOf(orgId));
            for(User user:userList){
                InstantMessage instantMessage = new InstantMessage();
                instantMessage.setMsgId(systemDAO.getSeqNextVal(Const.TABLE_INSTANT_MESSAGE));
                instantMessage.setTitle("横向交流");
                instantMessage.setMsgType(InstantMessage.MSG_TYPE_COMMUNION);
                instantMessage.setContent(communionReceive.getTitle());
                instantMessage.setCreateTime(new Date());
                instantMessage.setReadStatus(Notice.READ_STATUS_NO);
                instantMessage.setCreateUser(createUser);
                instantMessage.setReceiveUser(user.getUserId());
                instantMessage.setBusinessKey(communionReceive.getReceiveId().toString());
                instantMessageMapper.insert(instantMessage);
                try {
                    GetuiUtil.getInstance().PushToIosSingle(instantMessage.getReceiveUser(), instantMessage.getTitle(),JSONObject.toJSONString(instantMessage,serializeConfig));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }

    @Override
    public void addInstructionMessage(InstructionReceive instructionReceive,
            String createUser, String orgId) {
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
        List<User> userList = userMapper.findByOrgCodeForApp(Integer.valueOf(orgId));
        for(User user:userList){
            InstantMessage instantMessage = new InstantMessage();
            instantMessage.setMsgId(systemDAO.getSeqNextVal(Const.TABLE_INSTANT_MESSAGE));
            instantMessage.setTitle("工作指令");
            instantMessage.setMsgType(InstantMessage.MSG_TYPE_INSTRUCTION);
            instantMessage.setContent(instructionReceive.getTitle());
            instantMessage.setCreateTime(new Date());
            instantMessage.setReadStatus(Notice.READ_STATUS_NO);
            instantMessage.setCreateUser(createUser);
            instantMessage.setReceiveUser(user.getUserId());
            instantMessage.setBusinessKey(instructionReceive.getReceiveId().toString());
            instantMessageMapper.insert(instantMessage); 
            try {
                GetuiUtil.getInstance().PushToIosSingle(instantMessage.getReceiveUser(), instantMessage.getTitle(),JSONObject.toJSONString(instantMessage,serializeConfig));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }  
    }

    @Override
    public void addWorkReportMessage(WorkReportReceive reportReceive,
            String createUser, String orgId) {
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
        List<User> userList = userMapper.findByOrgCodeForApp(Integer.valueOf(orgId));
        for(User user:userList){
            InstantMessage instantMessage = new InstantMessage();
            instantMessage.setMsgId(systemDAO.getSeqNextVal(Const.TABLE_INSTANT_MESSAGE));
            instantMessage.setTitle("工作汇报");
            instantMessage.setMsgType(InstantMessage.MSG_TYPE_WORG_REPORT);
            instantMessage.setContent(reportReceive.getTitle());
            instantMessage.setCreateTime(new Date());
            instantMessage.setReadStatus(Notice.READ_STATUS_NO);
            instantMessage.setCreateUser(createUser);
            instantMessage.setReceiveUser(user.getUserId());
            instantMessage.setBusinessKey(reportReceive.getReceiveId().toString());
            instantMessageMapper.insert(instantMessage);
            try {
                GetuiUtil.getInstance().PushToIosSingle(instantMessage.getReceiveUser(), instantMessage.getTitle(),JSONObject.toJSONString(instantMessage,serializeConfig));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
    }

    @Override
    public void addClueDispacthMessage(ClueInfo clueInfo) {
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
        Integer createOrg = clueInfo.getCreateOrg();
        List<User> userList = userMapper.querySameLevelUser(createOrg,Const.ORG_TYPE_JIANCHAYUAN);
        for(User user:userList){
            InstantMessage instantMessage = new InstantMessage();
            instantMessage.setMsgId(systemDAO.getSeqNextVal(Const.TABLE_INSTANT_MESSAGE));
            instantMessage.setTitle("线索办理");
            instantMessage.setMsgType(InstantMessage.MSG_TYPE_CLUE_DISPATCH);
            instantMessage.setContent(clueInfo.getClueNo());
            instantMessage.setCreateTime(new Date());
            instantMessage.setReadStatus(Notice.READ_STATUS_NO);
            instantMessage.setCreateUser(clueInfo.getCreateUser());
            instantMessage.setReceiveUser(user.getUserId());
            instantMessage.setBusinessKey(clueInfo.getClueId().toString());
            instantMessageMapper.insert(instantMessage);
            try {
                GetuiUtil.getInstance().PushToIosSingle(instantMessage.getReceiveUser(), instantMessage.getTitle(),JSONObject.toJSONString(instantMessage,serializeConfig));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addClueHandleMessage(Integer clueId, String createUser,
            String orgId) {
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
        List<User> userList = userMapper.findByOrgCodeForApp(Integer.valueOf(orgId));
        ClueInfo clue = clueInfoMapper.selectByPrimaryKey(clueId);
        for(User user:userList){
            InstantMessage instantMessage = new InstantMessage();
            instantMessage.setMsgId(systemDAO.getSeqNextVal(Const.TABLE_INSTANT_MESSAGE));
            instantMessage.setTitle("线索办理");
            instantMessage.setMsgType(InstantMessage.MSG_TYPE_CLUE_HANDLE);
            instantMessage.setContent(clue.getClueNo());
            instantMessage.setCreateTime(new Date());
            instantMessage.setReadStatus(Notice.READ_STATUS_NO);
            instantMessage.setCreateUser(createUser);
            instantMessage.setReceiveUser(user.getUserId());
            instantMessage.setBusinessKey(clueId.toString());
            instantMessageMapper.insert(instantMessage);
            try {
                GetuiUtil.getInstance().PushToIosSingle(instantMessage.getReceiveUser(), instantMessage.getTitle(),JSONObject.toJSONString(instantMessage,serializeConfig));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void addCrimeCaseMessage(CaseBasic caseBasic,List<User> users) {
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
        if(users.size()>0){
            CaseBasic caseBasic2 = basicMapper.selectByCaseId(caseBasic.getCaseId());
            for(User user:users){
                InstantMessage instantMessage = new InstantMessage();
                instantMessage.setMsgId(systemDAO.getSeqNextVal(Const.TABLE_INSTANT_MESSAGE));
                instantMessage.setTitle("案件筛查");
                instantMessage.setMsgType(InstantMessage.MSG_TYPE_CRIME_CASE);
                instantMessage.setContent("系统自动筛选出一件疑似犯罪案件，案件编号为：【"+caseBasic2.getCaseNo()+"】。");
                instantMessage.setCreateTime(new Date());
                instantMessage.setReadStatus(Notice.READ_STATUS_NO);
                instantMessage.setCreateUser(caseBasic2.getInputer());
                instantMessage.setReceiveUser(user.getUserId());
                instantMessage.setBusinessKey(caseBasic2.getCaseId().toString());
                instantMessageMapper.insert(instantMessage);
                try {
                    GetuiUtil.getInstance().PushToIosSingle(instantMessage.getReceiveUser(), instantMessage.getTitle(),JSONObject.toJSONString(instantMessage,serializeConfig));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

	@Override
	public void updateReadStatus(InstantMessage instantMessage) {
		instantMessageMapper.updateReadStatus(instantMessage);
	}

	@Override
	public Map<String, Integer> selectMessageCount(InstantMessage instantMessage) {
		List<InstantMessage> ml = instantMessageMapper.selectMessageCount(instantMessage);
		//线索处理
		List<Integer> clue = Arrays.asList(InstantMessage.MSG_TYPE_CLUE_MNAGE);
		//互动连线77
		List<Integer> communion = Arrays.asList(InstantMessage.MSG_TYPE_COMMUNION_MANAGE);
		
		Map<String,Integer> count = new HashMap<String, Integer>();
		int clueNoReadCount = 0;
		int communionNoReadCount = 0;
		for(int a = 0 ; a < ml.size() ; a++ ) {
			InstantMessage m = ml.get(a);
			if(clue.contains(m.getMsgType())) {
				clueNoReadCount += m.getNoReadSize();
				continue;
			}
			if(communion.contains(m.getMsgType())) {
				communionNoReadCount += m.getNoReadSize();
				continue;
			}
			String msgType = getMsgType(m.getMsgType());
			count.put(msgType, count.get(msgType) == null?m.getNoReadSize():count.get(msgType)+m.getNoReadSize());
		}
		count.put("xscl", clueNoReadCount);
		count.put("hdlx", communionNoReadCount);
		
		return count;
	}
	
	public String getMsgType(Integer type) {
		String stype = "";
		if(type == InstantMessage.MSG_TYPE_CASE) {
			//待办案件
			stype = "dbaj";
		}
		if(type == InstantMessage.MSG_TYPE_NOTICE) {
			//通知公告
			stype = "tzgg";
		}
		if(type == InstantMessage.MSG_TYPE_CONSULATION) {
			//案件咨询
			stype = "ajzx";
		}
		if(type == InstantMessage.MSG_TYPE_CRIME_CASE) {
			//疑似犯罪
			stype = "ysfz";
		}
		return stype;
	}

	@Override
	public void deleteByMsgId(Integer msgId) {
		instantMessageMapper.deleteByPrimaryKey(msgId);
	}
	
	public boolean upReadStatus (String businessKey,User user){
    	JSONObject jsonObject = new JSONObject();
    	SerializeConfig serializeConfig = new SerializeConfig();
    	serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
    	try{
    		if(businessKey == null&&"".endsWith(businessKey)){
    			throw new Exception("业务主键为空!");
    		}
    		InstantMessage instantMessage = new InstantMessage();
    		instantMessage.setBusinessKey(businessKey);
    		instantMessage.setReceiveUser(user.getUserId());
    		instantMessage.setReadStatus(Const.INSTANT_MESSAGE_READ_YES);
    		//更新读取状态
    		updateReadStatus(instantMessage);
    		return true; 
    	}catch(Exception e){
    		e.printStackTrace();
    		return false; 
    	}
    }


    
}

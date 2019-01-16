package com.ksource.liangfa.service.notice;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.exception.BusinessException;
import com.ksource.liangfa.domain.Notice;
import com.ksource.liangfa.domain.NoticeOrg;
import com.ksource.liangfa.domain.NoticeOrgExample;
import com.ksource.liangfa.domain.SmsTemplate;
import com.ksource.liangfa.domain.SmsTemplateExample;
import com.ksource.liangfa.mapper.NoticeMapper;
import com.ksource.liangfa.mapper.NoticeOrgMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.PostMapper;
import com.ksource.liangfa.mapper.SmsBindMapper;
import com.ksource.liangfa.mapper.SmsTemplateMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.syscontext.Const;

@Service
public class NoticeOrgServiceImpl implements NoticeOrgService{
	
	@Autowired
	private NoticeOrgMapper noticeOrgMapper;
	
	@Autowired
	private OrganiseMapper organiseMapper;

	@Autowired
	private NoticeMapper noticeMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private SmsTemplateMapper smsTemplateMapper;
	@Autowired
	private SmsBindMapper smsBindMapper;
	
	private static final String INPUTER_ORG ="[inputerOrg]";
	private static final String INPUTER_POST="[inputerPost]";
	private static final String INPUTER="[inputer]";
	private static final String NOTICE_TITLE="[noticeTitle]";
	private static final String PHONE_OWNER="[phoneOwner]";
	private static final String smsInfoTemplete="尊敬的[phoneOwner]先生/女士,您好,[inputerOrg][inputer]录入了通知公告\"[noticeTitle]\",请查看!";
	// 日志
	private static final Logger log = LogManager.getLogger(NoticeServiceImpl.class);
	
	private String getProcSmsTemplate(){
			//查询数据库中有无对应模板,如果没有就使用默认模板.
			SmsTemplateExample example = new SmsTemplateExample();
			example.createCriteria().andTypeEqualTo(Const.SMS_TEMPLATE_NOTICE).andStateEqualTo(Const.STATE_VALID);
			List<SmsTemplate> smsTempList =smsTemplateMapper.selectByExample(example);
			if(smsTempList!=null&&!smsTempList.isEmpty()){
				SmsTemplate smsTemp = smsTempList.get(0);
				return smsTemp.getContent();
			}else{
				return smsInfoTemplete;
			}
	}
    private String getSmsInfo(String temp,String inputerName,String orgName,String postName,String userName,String noticeName){
			temp =StringUtils.replace(temp,INPUTER,inputerName);
			temp =StringUtils.replace(temp,INPUTER_ORG,orgName);
			temp =StringUtils.replace(temp,INPUTER_POST,postName);
			temp =StringUtils.replace(temp,NOTICE_TITLE,noticeName);
			temp =StringUtils.replace(temp,PHONE_OWNER,userName);
			return temp;
    }
	@Override
	@Transactional
	public void del(Integer noticeId)  {
		NoticeOrgExample example = new NoticeOrgExample();
		try {
			example.createCriteria().andNoticeIdEqualTo(noticeId);
			noticeOrgMapper.deleteByExample(example);
		} catch (Exception e) {
			log.error("删除部门失败：" + e.getMessage());
			throw new BusinessException("删除部门失败");
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public NoticeOrg find(Integer noticeId)  {
		NoticeOrg no = new NoticeOrg();
		NoticeOrgExample example = new NoticeOrgExample();
		List<NoticeOrg> list = null;
		String orgs = "";
		try {
			Notice notice = noticeMapper.selectByPrimaryKey(noticeId);
			if (notice != null) {
				no.setNoticeId(notice.getNoticeId());
			}
			example.createCriteria().andNoticeIdEqualTo(noticeId);
			list = noticeOrgMapper.selectByExample(example);
		} catch (Exception e) {
			
		}
		if (list!=null&&list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				NoticeOrg key = list.get(i);
				orgs += key.getOrgId() + ",";
			}
		}
		no.setOrgs(orgs);
		return no;
	}
	
	@Override
	@Transactional
	public void authorize(NoticeOrg no)  {
		try {
			Integer noticeId = no.getNoticeId();
			String orgs = no.getOrgs();
			del(noticeId);
			// 将orgs字符串转化成list
			ArrayList<Integer> list = new ArrayList<Integer>();
			if (!"".equals(orgs)) {
				String[] org = orgs.split(",");
				for (int i = 0; i < org.length; i++) {
					Integer str = Integer.parseInt(org[i]);
					list.add(str);
				}
			}
			for (int i = 0; i < list.size(); i++) {
				no.setOrgId(list.get(i));
				noticeOrgMapper.insert(no);
			}
			//发送短信
			//sendMessage(list,no.getNoticeId());
		} catch (Exception e) {
			log.error("关联部门失败：" + e.getMessage());
			throw new BusinessException("关联部门失败");
		}
	}
	@Override
	public List<NoticeOrg> selectByNoticeId(Integer noticeId) {
		return noticeOrgMapper.selectByNoticeId(noticeId);
	}
	
	@Override
	public int getNoticeOrgCount(Integer noticeId) {
		return noticeOrgMapper.getNoticeOrgCount(noticeId);
	}	
	
}
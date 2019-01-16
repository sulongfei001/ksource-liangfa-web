package com.ksource.liangfa.service.office;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.OfficeIdentity;
import com.ksource.liangfa.domain.OfficeIdentityExample;
import com.ksource.liangfa.mapper.OfficeIdentityMapper;
import com.ksource.syscontext.Const;

@Service
@Transactional
public class OfficeIdentityServiceImpl implements OfficeIdentityService{
	
	private static final Logger log = LogManager.getLogger(OfficeIdentityServiceImpl.class);

	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private OfficeIdentityMapper identityMapper;
	
	@Override
	public PaginationHelper<OfficeIdentity> find(OfficeIdentity identity,
			String page, Map<String, Object> params) {
		try {
			return systemDAO.find(identity, page,params);
		} catch (Exception e) {
			log.error("查询文书号失败：" + e.getMessage());
			throw new BusinessException("查询文书号失败");
		}
	}

	@Override
	public void add(OfficeIdentity identity) {
		Integer identityId = Integer.valueOf(systemDAO.getSeqNextVal(Const.TABLE_OFFICE_IDENTITY));
		identity.setIdentityId(identityId);
		identityMapper.insert(identity);
	}

	@Override
	public Integer checkAliasExisted(String alias,Integer identityId) {
		return identityMapper.countByAlias(alias,identityId);
	}

	@Override
	public int delete(Integer identityId) {
		return identityMapper.deleteByPrimaryKey(identityId);
	}

	@Override
	public OfficeIdentity selectById(Integer identityId) {
		OfficeIdentity officeIdentity = new OfficeIdentity();
		officeIdentity = identityMapper.selectByPrimaryKey(identityId);
		return officeIdentity;
	}

	@Override
	public void update(OfficeIdentity identity) {
		identityMapper.updateByPrimaryKeySelective(identity);
	}

	
	public String nextId(String alias) {
		OfficeIdentity identity = identityMapper.selectByAlias(alias);
		String rule = identity.getRegulation();
		int step = identity.getStep().shortValue();
		int genEveryDay = identity.getGentype().intValue();
		Long curValue = identity.getCurvalue();
		if (curValue == null){
			curValue = identity.getInitvalue();
		}
		if (genEveryDay == 1) {
			String curDate = getCurDate();
			String oldDate = identity.getCurdate();
			if (!curDate.equals(oldDate)) {
				identity.setCurdate(curDate);
				curValue = identity.getInitvalue();	
			} else {
				curValue = Long.valueOf(curValue.longValue() + step);
			}
		} else {
			curValue = Long.valueOf(curValue.longValue() + step);
		}
		identity.setNewCurvalue(curValue);
		identityMapper.updateVersion(identity);
		String nextNo = getByRule(rule, identity.getNolength(),curValue);
		return nextNo;
	}

	public static String getCurDate() {
		Date date = new Date();
		return date.getYear() + 1900 + (date.getMonth() + 1) + date.getDate()
				+ "";
	}

	private String getByRule(String rule, int length, Long curValue) {
		Date date = new Date();

		String year = date.getYear() + 1900 + "";
		int month = date.getMonth() + 1;
		int day = date.getDate();
		String shortMonth = "" + month;
		String longMonth = "" + month;

		String seqNo = getSeqNo(rule, curValue, length);

		String shortDay = "" + day;
		String longDay = "" + day;

		String rtn = rule.replace("{yyyy}", year).replace("{MM}", longMonth)
				.replace("{mm}", shortMonth).replace("{DD}", longDay)
				.replace("{dd}", shortDay).replace("{NO}", seqNo)
				.replace("{no}", seqNo);

		return rtn;
	}
	
	private static String getSeqNo(String rule, Long curValue, int length) {
		String tmp = curValue + "";
		int len = 0;
		if (rule.indexOf("no") > -1)
			len = length;
		else {
			len = length - tmp.length();
		}
		String rtn = "";
		switch (len) {
		case 1:
			rtn = "0";
			break;
		case 2:
			rtn = "00";
			break;
		case 3:
			rtn = "000";
			break;
		case 4:
			rtn = "0000";
			break;
		case 5:
			rtn = "00000";
			break;
		case 6:
			rtn = "000000";
			break;
		case 7:
			rtn = "0000000";
			break;
		case 8:
			rtn = "00000000";
			break;
		case 9:
			rtn = "000000000";
			break;
		case 10:
			rtn = "0000000000";
			break;
		case 11:
			rtn = "00000000000";
			break;
		case 12:
			rtn = "000000000000";
		}

		if (rule.indexOf("no") > -1) {
			return tmp + rtn;
		}
		return rtn + tmp;
	}	

}

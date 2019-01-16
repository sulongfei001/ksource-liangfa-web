package com.ksource.liangfa.service.casehandle;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseYisongJiwei;
import com.ksource.liangfa.domain.HotlineInfo;
import com.ksource.liangfa.mapper.HotlineInfoMapper;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.ThreadContext;

@Service
public class HotlineInfoServiceImpl implements HotlineInfoService{

	
	// 日志
    private static final Logger log = LogManager
            .getLogger(CaseTemplateServiceImpl.class);
	@Autowired
    private SystemDAO systemDAO;
	@Autowired
	private HotlineInfoMapper hotlineInfoMapper;
	
	@Override
	public void batckInsert(List<HotlineInfo> caseList) {
		try {
            if (caseList == null || caseList.size() == 0) return;
            Iterator<HotlineInfo> it = caseList.iterator();
            Date createTime = new Date();
            String currUser = ThreadContext.getCurUser().getUserId();
            while (it.hasNext()) {
                HotlineInfo temp = it.next();
                
                temp.setInfoId(systemDAO.getSeqNextVal(Const.HOTLINE_IMPORT));
                temp.setCreateUser(currUser);
                temp.setCreateTime(createTime);
                hotlineInfoMapper.insert(temp);
            }
        } catch (Exception e) {
            log.error("批量上传：" + e.getMessage());
            throw new BusinessException("批量上传");
        }
	}

	@Override
	public PaginationHelper<HotlineInfo> find(HotlineInfo hotlineInfo,String page) {
		return systemDAO.find(hotlineInfo,null,page,
				"com.ksource.liangfa.mapper.HotlineInfoMapper.getHotlineInfoCount",
				"com.ksource.liangfa.mapper.HotlineInfoMapper.getHotlineInfoList");
	}

	@Override
	public List<HotlineInfo> getDetailsByInfoId(Integer infoId) {
		return hotlineInfoMapper.getDetailsByInfoId(infoId);
	}

	@Override
	public Map<String, Object> getTypeNum(HotlineInfo hotlineInfo) {
		return hotlineInfoMapper.getTypeNum(hotlineInfo);
	}

	@Override
	public Map<String, Object> getTypePre(HotlineInfo hotlineInfo) {
		return hotlineInfoMapper.getTypePre(hotlineInfo);
	}

	@Override
	public HotlineInfo getHotlineByPk(Integer infoId) {
		return hotlineInfoMapper.getHotlineByPk(infoId);
	}

}

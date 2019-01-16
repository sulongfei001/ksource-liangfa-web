package com.ksource.liangfa.service.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.liangfa.domain.CaseAccuseKey;
import com.ksource.liangfa.domain.CaseXianyiren;
import com.ksource.liangfa.domain.CaseXianyirenExample;
import com.ksource.liangfa.domain.PeopleLib;
import com.ksource.liangfa.domain.XianyirenAccuse;
import com.ksource.liangfa.domain.XianyirenAccuseExample;
import com.ksource.liangfa.mapper.CaseAccuseMapper;
import com.ksource.liangfa.mapper.CaseXianyirenMapper;
import com.ksource.liangfa.mapper.PeopleLibMapper;
import com.ksource.liangfa.mapper.XianyirenAccuseMapper;
import com.ksource.syscontext.Const;

/**
 * 描述：<br>
 *
 * @author gengzi
 * @data 2012-3-30
 */
@Service
public class XianYiRenServiceImpl implements XianYiRenService {

    @Autowired
    CaseXianyirenMapper caseXianyirenMapper;
    @Autowired
    PeopleLibMapper peopleLibMapper;
    @Autowired
    XianyirenAccuseMapper xianyirenAccuseMapper;
    @Autowired
    CaseAccuseMapper caseAccuseMapper;

    @Transactional
    public void addXianyiren(CaseXianyiren caseXianyiren) {
        caseXianyirenMapper.insert(caseXianyiren);
        //罪名处理
        zmOpt(caseXianyiren);
        //保存到个人库
        PeopleLib peopleLib = PeopleLib.createFromCaseXianyiren(caseXianyiren);
        int count = peopleLibMapper.updateByPrimaryKeySelective(peopleLib);
        if (count == 0) {
            peopleLibMapper.insertSelective(peopleLib);
        }
    }

    @Transactional
    public void updateXianyiren(CaseXianyiren caseXianyiren, boolean updatePeopleLib, Integer zmForDelete) {
        caseXianyirenMapper.updateByPrimaryKeySelective(caseXianyiren);
        if (zmForDelete == null) {
            //罪名处理
            zmOpt(caseXianyiren);
        } else {//删除对应罪名
            deleteZm(null, caseXianyiren.getXianyirenId(), zmForDelete);
        }
        if (updatePeopleLib) {
            //保存到个人库
            PeopleLib peopleLib = PeopleLib.createFromCaseXianyiren(caseXianyiren);
            int count = peopleLibMapper.updateByPrimaryKeySelective(peopleLib);
            if (count == 0) {
                peopleLibMapper.insert(peopleLib);
            }
        }
    }

    //罪名处理
    private void zmOpt(CaseXianyiren caseXianyiren) {
        String tiqingdaibuZm = caseXianyiren.getTiqingdaibuZm();
        String pizhundaibuZm = caseXianyiren.getPizhundaibuZm();
        String tiqingqisuZm = caseXianyiren.getTiqingqisuZm();
        String tiqigongsuZm = caseXianyiren.getTiqigongsuZm();
        String panjue1Zm = caseXianyiren.getPanjue1Zm();
        String panjue2Zm = caseXianyiren.getPanjue2Zm();

        Long xianyirenId = caseXianyiren.getXianyirenId();

        XianyirenAccuse xianyirenAccuse = new XianyirenAccuse();
        xianyirenAccuse.setXianyirenId(xianyirenId);
        if (StringUtils.isNotBlank(caseXianyiren.getCaseId())) {
            xianyirenAccuse.setCaseId(caseXianyiren.getCaseId());
        } else {
            xianyirenAccuse.setCaseId(caseXianyirenMapper.selectByPrimaryKey(xianyirenId).getCaseId());
        }


        if (StringUtils.isNotBlank(tiqingdaibuZm)) {
            XianyirenAccuseExample example = new XianyirenAccuseExample();
            example.createCriteria().andXianyirenIdEqualTo(xianyirenId).andZmTypeEqualTo(Const.ZM_TYPE_tiqingdaibuZm);
            xianyirenAccuseMapper.deleteByExample(example);

            String[] tiqingdaibuZmArr = tiqingdaibuZm.split(",");
            xianyirenAccuse.setZmType(Const.ZM_TYPE_tiqingdaibuZm);
            for (String zm : tiqingdaibuZmArr) {
                xianyirenAccuse.setAccuseId(Integer.valueOf(zm));
                xianyirenAccuseMapper.insert(xianyirenAccuse);
            }
        }
        if (StringUtils.isNotBlank(pizhundaibuZm)) {
            XianyirenAccuseExample example = new XianyirenAccuseExample();
            example.createCriteria().andXianyirenIdEqualTo(xianyirenId).andZmTypeEqualTo(Const.ZM_TYPE_pizhundaibuZm);
            xianyirenAccuseMapper.deleteByExample(example);

            String[] pizhundaibuZmArr = pizhundaibuZm.split(",");
            xianyirenAccuse.setZmType(Const.ZM_TYPE_pizhundaibuZm);
            for (String zm : pizhundaibuZmArr) {
                xianyirenAccuse.setAccuseId(Integer.valueOf(zm));
                xianyirenAccuseMapper.insert(xianyirenAccuse);
            }
        }
        if (StringUtils.isNotBlank(tiqingqisuZm)) {
            XianyirenAccuseExample example = new XianyirenAccuseExample();
            example.createCriteria().andXianyirenIdEqualTo(xianyirenId).andZmTypeEqualTo(Const.ZM_TYPE_tiqingqisuZm);
            xianyirenAccuseMapper.deleteByExample(example);

            String[] tiqingqisuZmArr = tiqingqisuZm.split(",");
            xianyirenAccuse.setZmType(Const.ZM_TYPE_tiqingqisuZm);
            for (String zm : tiqingqisuZmArr) {
                xianyirenAccuse.setAccuseId(Integer.valueOf(zm));
                xianyirenAccuseMapper.insert(xianyirenAccuse);
            }
        }
        if (StringUtils.isNotBlank(tiqigongsuZm)) {
            XianyirenAccuseExample example = new XianyirenAccuseExample();
            example.createCriteria().andXianyirenIdEqualTo(xianyirenId).andZmTypeEqualTo(Const.ZM_TYPE_tiqigongsuZm);
            xianyirenAccuseMapper.deleteByExample(example);

            String[] tiqigongsuZmArr = tiqigongsuZm.split(",");
            xianyirenAccuse.setZmType(Const.ZM_TYPE_tiqigongsuZm);
            for (String zm : tiqigongsuZmArr) {
                xianyirenAccuse.setAccuseId(Integer.valueOf(zm));
                xianyirenAccuseMapper.insert(xianyirenAccuse);
            }
        }
        if (StringUtils.isNotBlank(panjue1Zm)) {
            XianyirenAccuseExample example = new XianyirenAccuseExample();
            example.createCriteria().andXianyirenIdEqualTo(xianyirenId).andZmTypeEqualTo(Const.ZM_TYPE_panjue1Zm);
            xianyirenAccuseMapper.deleteByExample(example);

            String[] panjue1ZmArr = panjue1Zm.split(",");
            xianyirenAccuse.setZmType(Const.ZM_TYPE_panjue1Zm);
            for (String zm : panjue1ZmArr) {
                xianyirenAccuse.setAccuseId(Integer.valueOf(zm));
                xianyirenAccuseMapper.insert(xianyirenAccuse);
            }
        }
        if (StringUtils.isNotBlank(panjue2Zm)) {
            XianyirenAccuseExample example = new XianyirenAccuseExample();
            example.createCriteria().andXianyirenIdEqualTo(xianyirenId).andZmTypeEqualTo(Const.ZM_TYPE_panjue2Zm);
            xianyirenAccuseMapper.deleteByExample(example);

            String[] panjue2ZmArr = panjue2Zm.split(",");
            xianyirenAccuse.setZmType(Const.ZM_TYPE_panjue2Zm);
            for (String zm : panjue2ZmArr) {
                xianyirenAccuse.setAccuseId(Integer.valueOf(zm));
                xianyirenAccuseMapper.insert(xianyirenAccuse);
            }
        }
    }

    @Override
    @Transactional
    public void deleteXianyiren(CaseXianyiren xianyirenForUpdate, int zmType) {
        caseXianyirenMapper.updateByPrimaryKeySelective(xianyirenForUpdate);
        XianyirenAccuseExample example = new XianyirenAccuseExample();
        example.createCriteria().andXianyirenIdEqualTo(xianyirenForUpdate.getXianyirenId()).andZmTypeEqualTo(zmType);
        xianyirenAccuseMapper.deleteByExample(example);
    }

    @Transactional
    @Override
    public void deleteZm(String caseId, Long xianyirenId, Integer zmType) {
        XianyirenAccuseExample example = new XianyirenAccuseExample();
        XianyirenAccuseExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(caseId)) {
            criteria.andCaseIdEqualTo(caseId);
        }
        if (xianyirenId != null) {
            criteria.andXianyirenIdEqualTo(xianyirenId);
        }
        if (zmType != null) {
            criteria.andZmTypeEqualTo(zmType);
        }
        xianyirenAccuseMapper.deleteByExample(example);
    }

    @Transactional
    @Override
    public void buqisuAl(String caseId) {
        CaseXianyirenExample example = new CaseXianyirenExample();
        example.createCriteria().andCaseIdEqualTo(caseId)
                .andTiqingqisuStateEqualTo(Const.XIANYIREN_TIQINGQISU_STATE_YES);

        CaseXianyiren record = new CaseXianyiren();
        record.setTiqigongsuState(Const.XIANYIREN_TIQIGONGSU_STATE_NO);
        caseXianyirenMapper.updateByExampleSelective(record, example);
        deleteZm(caseId, null, Const.ZM_TYPE_tiqigongsuZm);
    }

    @Transactional
    @Override
    public void addCaseZm(String caseId, String[] yisonggonganZmArr,
                          int caseZmType) {
        CaseAccuseKey caseAccuse = new CaseAccuseKey();
        caseAccuse.setCaseId(caseId);
        caseAccuse.setZmType(caseZmType);
        for (int i = 0; i < yisonggonganZmArr.length; i++) {
            String accuseId = yisonggonganZmArr[i];
            caseAccuse.setAccuseId(Integer.valueOf(accuseId));
            caseAccuseMapper.deleteByPrimaryKey(caseAccuse);
            caseAccuseMapper.insert(caseAccuse);
        }
    }

    @Override
    @Transactional
    public void updatePanjue(CaseXianyiren caseXianyiren, boolean updatePeopleLib, Integer zmForDelete) {
        caseXianyirenMapper.updatePanjueInfo(caseXianyiren);
        if (zmForDelete == null) {
            //罪名处理
            zmOpt(caseXianyiren);
        } else {//删除对应罪名
            deleteZm(null, caseXianyiren.getXianyirenId(), zmForDelete);
        }
        if (updatePeopleLib) {
            //保存到个人库
            PeopleLib peopleLib = PeopleLib.createFromCaseXianyiren(caseXianyiren);
            int count = peopleLibMapper.updateByPrimaryKeySelective(peopleLib);
            if (count == 0) {
                peopleLibMapper.insert(peopleLib);
            }
        }
    }

	@Override
	public CaseXianyiren selectById(Integer xianyirenId) {
		return caseXianyirenMapper.selectById(xianyirenId);
	}

	@Override
	public List<CaseXianyiren> findAll(CaseXianyiren caseXianyiren) {
		return caseXianyirenMapper.findAll(caseXianyiren);
	}

	/**
	 * 更新嫌疑人因未提交而已经是起诉状态的人员和案件关系，并删除相应的罪名
	 * 
	 * @createTime:2017年11月7日 下午7:02:16
	 */
	@Override
	@Transactional
	public void updateXianyirenStateByCaseId(String caseId, String caseIndex) {
		// 1.更新起诉状态为未起诉
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("caseId", caseId);// 案件id
		if ("tiqingqisu".equals(caseIndex)) {// 提请起诉
			paraMap.put("qisuStateSuccess", Const.XIANYIREN_TIQINGQISU_STATE_YES);// 已起诉
			paraMap.put("qisuStateFail", Const.XIANYIREN_TIQINGQISU_STATE_NOTYET);// 未起诉
			paraMap.put("zmType", Const.ZM_TYPE_tiqingqisuZm);// 罪名类型
		}
		if ("tiqingdaibu".equals(caseIndex)) {// 提请逮捕
			paraMap.put("daibuStateYes", Const.XIANYIREN_DAIBU_STATE_TIQING);// 已起诉
			paraMap.put("daibuStateNo", Const.XIANYIREN_DAIBU_STATE_NOTYET);// 未起诉
			paraMap.put("zmType", Const.ZM_TYPE_tiqingdaibuZm);// 罪名类型
		}
		if (paraMap.keySet().size() > 1) {
			caseXianyirenMapper.updateXianyirenStateByCaseId(paraMap);
			// 2.根据嫌疑人id删除，嫌疑人和罪名对应的关系
			xianyirenAccuseMapper.deleteAccuseByCaseId(paraMap);
		}
	}
}

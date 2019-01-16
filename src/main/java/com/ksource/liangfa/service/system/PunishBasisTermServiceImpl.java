package com.ksource.liangfa.service.system;

import java.util.List;

import com.ksource.exception.BusinessException;
import com.ksource.liangfa.domain.PunishBasisTerm;
import com.ksource.liangfa.mapper.DiscretionStandardMapper;
import com.ksource.liangfa.mapper.PunishBasisTermMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PunishBasisTermServiceImpl implements PunishBasisTermService {
	
	@Autowired
	private PunishBasisTermMapper punishBasisTermMapper;
	@Autowired
	private DiscretionStandardMapper discretionStandardMapper;
	// 日志
	private static final Logger log = LogManager
			.getLogger(PunishBasisTermServiceImpl.class);


	@Override
	public List<PunishBasisTerm> getTermByBasisId(PunishBasisTerm param) {
		return punishBasisTermMapper.getTermByBasisId(param);
	}


	@Override
	public int deleteByBasisId(Integer basisId) {
		try {
			return punishBasisTermMapper.deleteByBasisId(basisId);
		} catch (Exception e) {
			log.error("删除行政处罚依据项失败：" + e.getMessage());
			throw new BusinessException("删除行政处罚依据项失败");
		}
	}

	/**
	 * 删除处罚依据项
	 */
	@Override
	@Transactional
	public int deleteTerm(Integer termId) {
		try {
			//先删除项下面的行政处罚裁量标准信息,然后再删除行政处罚依据项信息
			discretionStandardMapper.deleteByTermId(termId);
			return punishBasisTermMapper.deleteByPrimaryKey(termId);
		} catch (Exception e) {
			log.error("删除行政处罚依据项失败：" + e.getMessage());
			throw new BusinessException("删除行政处罚依据项失败");
		}
		
	}
	
}

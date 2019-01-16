package com.ksource.liangfa.service.info;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.domain.ZhifaInfoExample;
import com.ksource.liangfa.domain.ZhifaInfoType;
import com.ksource.liangfa.domain.ZhifaInfoTypeExample;
import com.ksource.liangfa.mapper.ZhifaInfoMapper;
import com.ksource.liangfa.mapper.ZhifaInfoTypeMapper;

@Service
public class ZhifaTypeServiceImpl implements ZhifaTypeService{
	
	@Autowired
    private ZhifaInfoTypeMapper zhifaInfoTypeMapper;
    @Autowired
    private ZhifaInfoMapper zhifaInfoMapper;
    //日志
    private static final Logger log = LogManager.getLogger(ZhifaTypeServiceImpl.class);
	
	@Override
    @Transactional
    public boolean delete (Integer typeId) {
    	try{
    		ZhifaInfoExample example1 = new ZhifaInfoExample();
    		example1.createCriteria().andTypeIdEqualTo(typeId);
    		int zhifaCount = zhifaInfoMapper.countByExample(example1);
    		//判断执法动态类型下是否有执法动态信息
    		if(zhifaCount == 0){
    			ZhifaInfoTypeExample example2 = new ZhifaInfoTypeExample();
    			example2.createCriteria().andTypeIdEqualTo(typeId);
    			zhifaInfoTypeMapper.deleteByExample(example2);
    			return true;
    		}
    		else{
    			return false;
    		}
        } catch (Exception e) {
            log.error("删除执法动态失败：" + e.getMessage());
            throw new BusinessException("删除执法动态失败");
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ZhifaInfoType> queryZhifaInfoTypes(ZhifaInfoType zhifaInfoType){
    	if(zhifaInfoType!=null&&StringUtils.isNotBlank(zhifaInfoType.getTypeName())){
    		zhifaInfoType.setTypeName(zhifaInfoType.getTypeName().trim());
    	}
    	try {
			return zhifaInfoTypeMapper.find(zhifaInfoType);
		} catch (Exception e) {
			log.error("查询执法动态失败：" + e.getMessage());
            throw new BusinessException("查询执法动态失败");
		}
    }
    
}
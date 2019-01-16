package com.ksource.liangfa.service.info;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.DatabaseDialectBean;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.domain.InfoType;
import com.ksource.liangfa.domain.LayInfoExample;
import com.ksource.liangfa.mapper.InfoTypeMapper;
import com.ksource.liangfa.mapper.LayInfoMapper;

@Service
public class LayTypeServiceImpl implements LayTypeService{
	
	@Autowired
    private InfoTypeMapper infoTypeMapper;
    @Autowired
    private LayInfoMapper layInfoMapper;
    @Resource(name="databaseConfigBean")
    DatabaseDialectBean  databaseDialectBean;
    //日志
    private static final Logger log = LogManager.getLogger(LayTypeServiceImpl.class);
    
    @Override
    @Transactional
    public boolean delete (String typeId) {
    	try{
    		LayInfoExample example = new LayInfoExample();
    		example.createCriteria().andTypeIdEqualTo(typeId);
    		int layCount = layInfoMapper.countByExample(example);
    		//判断法律法规类型下是否拥有法律法规信息
    		if(layCount == 0){
    			infoTypeMapper.deleteByPrimaryKey(Integer.parseInt(typeId));
    			return true;
    		}
    		else{
    			return false;
    		}
        } catch (Exception e) {
            log.error("删除法律法规失败：" + e.getMessage());
            throw new BusinessException("删除法律法规失败");
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<InfoType> queryLayTypes(InfoType infoType){
    	if(infoType!=null&&StringUtils.isNotBlank(infoType.getTypeName())){
    		infoType.setTypeName(infoType.getTypeName().trim());
    	}
    	try {
			return infoTypeMapper.find(infoType);
		} catch (Exception e) {
			log.error("查询法律法规失败：" + e.getMessage());
            throw new BusinessException("查询法律法规失败");
		}
    }
}
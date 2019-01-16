package com.ksource.liangfa.service.cms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.cms.CmsChannel;
import com.ksource.liangfa.mapper.CmsChannelMapper;
import com.ksource.liangfa.mapper.CmsContentMapper;
import com.ksource.syscontext.Const;

@Service
public class CmsChannelServiceImpl implements CmsChannelService{
	
	@Autowired
	private CmsChannelMapper cmsChannelMapper;
	@Autowired
	private CmsContentMapper cmsContentMapper;
	@Autowired
	private SystemDAO systemDAO;
	 // 日志
    private static final Logger log = LogManager.getLogger(CmsChannelServiceImpl.class);

    @Override
    @Transactional
	public ServiceResponse insert(CmsChannel channel) {
    	ServiceResponse res = new ServiceResponse();
    	try{
        //添加默认值
    	int parentId = (channel.getParentId() == null) ? Const.TOP_CHANNEL_ID
                     : channel.getParentId(); 
    	channel.setParentId(parentId);
    	
        if (channel.getIsLeaf() == null) {
            channel.setIsLeaf(Const.LEAF_YES);
        }
        CmsChannel parentChannel = cmsChannelMapper.selectByPrimaryKey(channel.getParentId());
        int channelId = systemDAO.getSeqNextVal(Const.TABLE_CMS_CHANNEL);
        channel.setChannelId(channelId);
        channel.setPath(parentChannel.getPath()+channelId+".");
        cmsChannelMapper.insert(channel);

        //如果父节点 状态是 叶子节点 状态，修改之
        if (parentChannel.getIsLeaf() == Const.LEAF_YES) {
        	parentChannel.setIsLeaf(Const.LEAF_NO);
            cmsChannelMapper.updateByPrimaryKeySelective(parentChannel);
        }
    	} catch (Exception e) {
    		log.error("栏目添加失败：" + e.getMessage());
    		throw new BusinessException("栏目添加失败");
    	}
    	res.setingSucess("添加成功");
    	return res;
	}

    @Override
    @Transactional
    public Boolean del(Integer channelId,Integer parentId) {
        try {
        	//1.判断栏目是否可以删除
        	 int size =cmsContentMapper.getContentSizeByChannelId(channelId);
        	  if(size==0){
        		//2.删除菜单
        		  cmsChannelMapper.deleteById(channelId);
                //3.修改被删除菜单的父菜单的节点状态
                  if (isNoChildren(parentId)) {
                      CmsChannel parentChannel = new CmsChannel();
                      parentChannel.setChannelId(parentId);
                      parentChannel.setIsLeaf(Const.LEAF_YES);
                      cmsChannelMapper.updateByPrimaryKeySelective(parentChannel);
                  } 
        	  return true;
        	  }
        	  return false;
        } catch (Exception e) {
            log.error("删除栏目失败：" + e.getMessage());
            throw new BusinessException("删除栏目失败");
        }
    }
    

	@Override
	@Transactional
	public List<CmsChannel> selectByParentId(int parentId) {
		return cmsChannelMapper.selectByParentId(parentId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Boolean isNoChildren(Integer id) {
	        Boolean boo = false;
	        try {
	            int cheirdrenCount = cmsChannelMapper.childrenCounts(id);
	            if (cheirdrenCount == 0) {
	                boo = true;
	            }
	            return boo;
	        } catch (Exception e) {
	            log.error("查询栏目失败：" + e.getMessage());
	            throw new BusinessException("查询栏目失败");
	        }
	 }

	@Override
	@Transactional
	public Integer getChannelId(Integer channelFrom) {
		return cmsChannelMapper.getChannelId(channelFrom);
	}

	@Override
	@Transactional
	public Boolean fromIsExist(Integer channelFrom, Integer channelId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channelId", channelId);
		map.put("channelFrom", channelFrom);
		Integer count = cmsChannelMapper.fromIsExist(map);
		if (count != 0){
			return true;
		}else{
			return false;
		}
		
	}

}

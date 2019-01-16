package com.ksource.common.lucene;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksource.liangfa.domain.cms.CmsContent;
import com.ksource.liangfa.service.cms.CmsContentService;


/**
 *网站文章工具类
 */
@Service
public class CmsUtilService {
	
	@Autowired
	private CmsContentService cmsContentService;

    private  final Logger log = LogManager.getLogger(CmsUtilService.class);
    
    /**
     * 网站文章添加
     * @param content
     */
    public void addContent(CmsContent content){
		try{
			cmsContentService.add(content, null);
			log.debug("向网站添加文章成功！");
		}catch(Exception e){
			log.debug("向网站添加文章失败！"+e.getMessage());
		}
		
    }
    
    /**
     * 网站文章更新
     * @param content
     */
    public  void updateContent(CmsContent content){
		try{
			cmsContentService.updateContent(content, null); 
			log.debug("向网站更新文章成功！");
		}catch(Exception e){
			log.debug("向网站更新文章失败！"+e.getMessage());
		}
	 	
    }
    
    /**
     * 文章文章删除
     * @param outId
     * @param channelId
     */
    public  void deleteContent(Integer outId,Integer channelId){
    	int contentId = cmsContentService.getRealId(channelId, outId);
    	try{
			cmsContentService.realDel(contentId); 
			log.debug("删除网站文章成功！");
		}catch(Exception e){
			log.debug("删除网站文章成功！"+e.getMessage());
		}
    }
    
    /**
     * 批量删除
     * @param ids
     * @param channelId
     */
    public  void batchDeleteContent(Integer[] ids,Integer channelId){
    	try{
    	  	for(int id: ids){
        		int contentId = cmsContentService.getRealId(channelId, id);
        		cmsContentService.realDel(contentId); 
        	} 
			log.debug("批量删除网站文章成功！");
		}catch(Exception e){
			log.debug("批量删除网站文章成功！"+e.getMessage());
		}
    	
    }
}

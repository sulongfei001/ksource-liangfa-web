package com.ksource.common.lucene;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import com.ksource.liangfa.domain.LayInfo;
import com.ksource.liangfa.domain.Notice;
import com.ksource.liangfa.domain.ZhifaInfo;
import com.ksource.liangfa.domain.cms.CmsContent;
import com.ksource.liangfa.service.cms.CmsChannelService;
import com.ksource.liangfa.service.cms.CmsContentService;
import com.ksource.syscontext.Const;


/**
 * 系统切面类，用于切面控制与网站栏目有关联的菜单（法律法规、执法动态等）
 */
@Aspect
public class CmsAspect {
	/**
	 * 法律法规
	 */
    @Pointcut("execution(* com.ksource.liangfa.service.info.InfoService.insertLay (..))")
    public void addInfoPointcut(){}
    ;
    @Pointcut("execution(* com.ksource.liangfa.service.info.InfoService.updateLayByPrimaryKeySelective (..))")
    public void updateInfoPointcut(){}
    ;
    @Pointcut("execution(* com.ksource.liangfa.service.info.InfoService.delLay (..))")
    public void deleteInfoPointcut(){}
    ;
    @Pointcut("execution(* com.ksource.liangfa.service.info.InfoService.batchDeleteLay(..))")
    public void batchDeleteInfoPointcut(){}
    ;
    /**
	 * 执法动态
	 */
    @Pointcut("execution(* com.ksource.liangfa.service.info.InfoService.insertZhifaInfo (..))")
    public void addZhifaPointcut(){}
    ;
    @Pointcut("execution(* com.ksource.liangfa.service.info.InfoService.updateZhifaByPrimaryKeySelective (..))")
    public void updateZhifaPointcut(){}
    ;
    @Pointcut("execution(* com.ksource.liangfa.service.info.InfoService.delZhifa (..))")
    public void deleteZhifaPointcut(){}
    ;
    @Pointcut("execution(* com.ksource.liangfa.service.info.InfoService.batchDeleteZhifa(..))")
    public void batchDeleteZhifaPointcut(){}
    ;
    /**
	 * 通知公告
	 */
    @Pointcut("execution(* com.ksource.liangfa.service.notice.NoticeService.add (..))")
    public void addNoticePointcut(){}
    ;
    @Pointcut("execution(* com.ksource.liangfa.service.notice.NoticeService.updateByPrimaryKeySelective (..))")
    public void updateNoticePointcut(){}
    ;
    @Pointcut("execution(* com.ksource.liangfa.service.notice.NoticeService.del (..))")
    public void deleteNoticePointcut(){}
    ;
    @Pointcut("execution(* com.ksource.liangfa.service.notice.NoticeService.batchDeleteNotice(..))")
    public void batchDeleteNoticePointcut(){}
    ;
    
    @Autowired
	private CmsChannelService cmsChannelService;
    @Autowired
   	private CmsContentService cmsContentService;
	@Autowired
	private CmsUtilService cmsUtil;
	
	public static final String CONTENT_ID = "contentId";
	public static final String[] ANALYSE_ARRAY = new String[]{"text","title"};
		
    
    @AfterReturning("addInfoPointcut()")
    public void afterReturnAddInfoPointcut(JoinPoint joinPoint)
        throws ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {	
    	//查找与法律法规关联的栏目ID
    	Integer channelId = cmsChannelService.getChannelId(Const.CMS_FROM_TYPE_LAY);
    	if(channelId != null){
    		LayInfo info = (LayInfo) joinPoint.getArgs()[0] ;   	
        	
        	Integer outId = Integer.parseInt(info.getInfoId());
        	String title = info.getTitle();
        	String text = info.getContent();
        	String createUserId = info.getUserId();
        	Date createTime = info.getCreateTime();
        	
        	//构建用于添加的文章实体类
        	CmsContent content = createContentForAdd(outId, title, text, channelId, createUserId, createTime);
        	cmsUtil.addContent(content);
        	
        	//添加网站的索引
        	content.setText(content.getText()+content.getTitle());
            LuceneUtilForCms.addIndex(content,ANALYSE_ARRAY);
    	}
    }

    @AfterReturning("updateInfoPointcut()")
    public void afterReturnUpdateInfoPointcut(JoinPoint joinPoint)
        throws ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
    	Integer channelId = cmsChannelService.getChannelId(Const.CMS_FROM_TYPE_LAY);
    	if(channelId != null){
    		LayInfo info = (LayInfo) joinPoint.getArgs()[0];   	
        	
        	Integer outId = Integer.parseInt(info.getInfoId());
        	String title = info.getTitle();
        	String text = info.getContent();
        	
        	//构建用于更新的文章实体类
        	CmsContent content = createContentForUpdate(outId, title, text, channelId);
        	cmsUtil.updateContent(content);
        	
        	//更新文章的索引
        	content.setText(content.getText()+content.getTitle());
            LuceneUtilForCms.updateIndex(content,CONTENT_ID,ANALYSE_ARRAY);
    	}
    	
    }
    
    @AfterReturning("addZhifaPointcut()")
    public void afterReturnAddZhifaPointcut(JoinPoint joinPoint)
        throws ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {	
    	Integer channelId = cmsChannelService.getChannelId(Const.CMS_FROM_TYPE_ZHIFA);
    	if(channelId != null){
    		ZhifaInfo info = (ZhifaInfo) joinPoint.getArgs()[0] ;   	
        	
        	Integer outId = Integer.parseInt(info.getInfoId());
        	String title = info.getTitle();
        	String text = info.getContent();
        	String createUserId = info.getUserId();
        	Date createTime = info.getCreateTime();
        
        	CmsContent content = createContentForAdd(outId, title, text, channelId, createUserId, createTime);
        	cmsUtil.addContent(content);
        	
        	content.setText(content.getText()+content.getTitle());
            LuceneUtilForCms.addIndex(content,ANALYSE_ARRAY);
    	}
    }

    @AfterReturning("updateZhifaPointcut()")
    public void afterReturnUpdateZhifaPointcut(JoinPoint joinPoint)
        throws ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
    	Integer channelId = cmsChannelService.getChannelId(Const.CMS_FROM_TYPE_ZHIFA);
    	if(channelId != null){
    		ZhifaInfo info = (ZhifaInfo) joinPoint.getArgs()[0] ;   	
        	
        	Integer outId = Integer.parseInt(info.getInfoId());
        	String title = info.getTitle();
        	String text = info.getContent();
        	
        	CmsContent content = createContentForUpdate(outId, title, text, channelId);
        	cmsUtil.updateContent(content);
        	
        	content.setText(content.getText()+content.getTitle());
            LuceneUtilForCms.updateIndex(content,CONTENT_ID,ANALYSE_ARRAY);
    	}
    	
    }
    
    @AfterReturning("addNoticePointcut()")
    public void afterReturnAddNoticePointcut(JoinPoint joinPoint)
        throws ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {	
    	Integer channelId = cmsChannelService.getChannelId(Const.CMS_FROM_TYPE_NOTICE);
    	if(channelId != null){
    		Notice info = (Notice) joinPoint.getArgs()[0] ;   	
        	
        	Integer outId = info.getNoticeId();
        	String title = info.getNoticeTitle();
        	String text = info.getNoticeContent();
        	String createUserId = info.getNoticeCreater();
        	Date createTime = info.getNoticeTime();
        	
        	CmsContent content = createContentForAdd(outId, title, text, channelId, createUserId, createTime);
        	cmsUtil.addContent(content);
        	
        	content.setText(content.getText()+content.getTitle());
            LuceneUtilForCms.addIndex(content,ANALYSE_ARRAY);
    	}
    }

    @AfterReturning("updateNoticePointcut()")
    public void afterReturnUpdateNoticePointcut(JoinPoint joinPoint)
        throws ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
    	Integer channelId = cmsChannelService.getChannelId(Const.CMS_FROM_TYPE_NOTICE);
    	if(channelId != null){
    		Notice info = (Notice) joinPoint.getArgs()[0] ;   	
        	
    		Integer outId = info.getNoticeId();
        	String title = info.getNoticeTitle();
        	String text = info.getNoticeContent();
        	
        	CmsContent content = createContentForUpdate(outId, title, text, channelId);
        	cmsUtil.updateContent(content);
        	
        	content.setText(content.getText()+content.getTitle());
            LuceneUtilForCms.updateIndex(content,CONTENT_ID,ANALYSE_ARRAY);
    	}
    	
    }

    @AfterReturning("deleteInfoPointcut()")
    public void afterReturnDeleteInfoPointcut(JoinPoint joinPoint)
        throws ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {     
        Integer channelId = cmsChannelService.getChannelId(Const.CMS_FROM_TYPE_LAY);
    	if(channelId != null){
    		 String id = (String)joinPoint.getArgs()[0];
    		 //删除网站文章索引
    		 LuceneUtilForCms.deleteIndexById(id,CONTENT_ID);
    		 cmsUtil.deleteContent(Integer.parseInt(id), channelId);
    	}     
    }
    @AfterReturning("batchDeleteInfoPointcut()")
    public void afterReturnBatchDeleteInfoPointcut(JoinPoint joinPoint)
    	throws ClassNotFoundException, SecurityException, NoSuchMethodException,
        IllegalArgumentException, IllegalAccessException,
        InvocationTargetException{
    	Integer channelId = cmsChannelService.getChannelId(Const.CMS_FROM_TYPE_LAY);
    	if(channelId != null){
    		String infoId =null;
    		String[] ids =(String[]) joinPoint.getArgs()[0];
        	for(int i=0;i<ids.length;i++){
        		infoId =ids[i]; 		
        		LuceneUtil.deleteIndexById(infoId,CONTENT_ID);   
        		cmsUtil.deleteContent(Integer.parseInt(infoId), channelId);
        	}
    	}
    }
    
    @AfterReturning("deleteZhifaPointcut()")
    public void afterReturnDeleteZhifaPointcut(JoinPoint joinPoint)
        throws ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        Integer channelId = cmsChannelService.getChannelId(Const.CMS_FROM_TYPE_ZHIFA);
    	if(channelId != null){
    		 String id = (String)joinPoint.getArgs()[0];		 		 
    		 LuceneUtilForCms.deleteIndexById(id,CONTENT_ID);
    		 cmsUtil.deleteContent(Integer.parseInt(id), channelId);
    	} 
    }
    @AfterReturning("batchDeleteZhifaPointcut()")
    public void afterReturnBatchDeleteZhifaPointcut(JoinPoint joinPoint)
    	throws ClassNotFoundException, SecurityException, NoSuchMethodException,
        IllegalArgumentException, IllegalAccessException,
        InvocationTargetException{
    	Integer channelId = cmsChannelService.getChannelId(Const.CMS_FROM_TYPE_ZHIFA);
    	if(channelId != null){
    		String infoId =null;
    		String[] ids =(String[]) joinPoint.getArgs()[0];
        	for(int i=0;i<ids.length;i++){
        		infoId =ids[i]; 		
        		LuceneUtil.deleteIndexById(infoId,CONTENT_ID);   
        		cmsUtil.deleteContent(Integer.parseInt(infoId), channelId);
        	}
    	}
    }
    
    @AfterReturning("deleteNoticePointcut()")
    public void afterReturnDeleteNoticePointcut(JoinPoint joinPoint)
        throws ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        Integer channelId = cmsChannelService.getChannelId(Const.CMS_FROM_TYPE_NOTICE);
    	if(channelId != null){
    		 Integer id = (Integer)joinPoint.getArgs()[0];		 		 
    		 LuceneUtilForCms.deleteIndexById(String.valueOf(id),CONTENT_ID);
    		 cmsUtil.deleteContent(id, channelId);
    	}       
    }
    @AfterReturning("batchDeleteNoticePointcut()")
    public void afterReturnBatchDeleteNoticePointcut(JoinPoint joinPoint)
    	throws ClassNotFoundException, SecurityException, NoSuchMethodException,
        IllegalArgumentException, IllegalAccessException,
        InvocationTargetException{
    	Integer channelId = cmsChannelService.getChannelId(Const.CMS_FROM_TYPE_NOTICE);
    	if(channelId != null){
    		String infoId =null;
    		Integer[] ids =(Integer[]) joinPoint.getArgs()[0];
        	for(int i=0;i<ids.length;i++){
        		infoId =String.valueOf(ids[i]); 		
        		LuceneUtil.deleteIndexById(infoId,CONTENT_ID);   
        		cmsUtil.deleteContent(Integer.parseInt(infoId), channelId);
        	}
    	}
    }
    
    /**
     * 构造用于添加的文章实体类
     * @param outId
     * @param title
     * @param text
     * @param channelId
     * @param createUserId
     * @param createTime
     * @return
     */
    private CmsContent createContentForAdd(Integer outId,String title,String text,Integer channelId,String createUserId,Date createTime){
    	CmsContent content = new CmsContent();
    	content.setChannelId(channelId);
    	content.setOutId(outId);
		content.setCreateUserId(createUserId);
		content.setTitle(title);
		content.setText(text);
		content.setCreateTime(createTime);
		content.setStatus(Const.CMS_CONTENT_NORMAL);
		content.setTop(Const.CMS_CONTENT_NUTOP);
		content.setTitleColor("#000000");
		
		return content;
    	
    }
    
    /**
     * 构造用于更新的文章实体类
     * @param outId
     * @param title
     * @param text
     * @param channelId
     * @return
     */
  private CmsContent createContentForUpdate(Integer outId,String title,String text,Integer channelId){
	  	CmsContent content = new CmsContent();
	  	Integer contentId = cmsContentService.getRealId(channelId, outId);
  		content.setContentId(contentId);
		content.setTitle(title);
		content.setText(text);
		
		return content;
    }

}

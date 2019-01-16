package com.ksource.common.lucene;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.cms.CmsContent;


/**
 * 此类为  CmsContentController 类的切入点声明类.<br/>
 * 当调用CmsContentController的 add,update,delete方法后分别会调用
 * afterReturnAddPointcut,afterReturnUpdatePointcut,afterReturnDeletePointcut.用于更新
 * 网站的索引.
 */
@Aspect
public class CmsContentIndexAspect {
    @Pointcut("execution(* com.ksource.liangfa.service.cms.CmsContentService.add (..))")
    public void addPointcut(){}
    ;
    @Pointcut("execution(* com.ksource.liangfa.service.cms.CmsContentService.updateContent (..))")
    public void updatePointcut(){}
    ;
    @Pointcut("execution(* com.ksource.liangfa.service.cms.CmsContentService.del (..))")
    public void deletePointcut(){}
    ;

    @AfterReturning("addPointcut()")
    public void afterReturnAddPointcut(JoinPoint joinPoint)
        throws ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
    	//2.add index
    	CmsContent content = getBeanArg(joinPoint);
    	content.setText(content.getText()+content.getTitle());
        LuceneUtilForCms.addIndex(content,ANALYSE_ARRAY);
    }

    @AfterReturning("updatePointcut()")
    public void afterReturnUpdatePointcut(JoinPoint joinPoint)
        throws ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        // update index
    	CmsContent content = getBeanArg(joinPoint);
    	content.setText(content.getText()+content.getTitle());
        LuceneUtilForCms.updateIndex(content,CONTENT_ID,ANALYSE_ARRAY);
    }

    @AfterReturning("deletePointcut()")
    public void afterReturnDeletePointcut(JoinPoint joinPoint)
        throws ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        //1.bean的Id默认为方法的第一个参数
        String id =  joinPoint.getArgs()[0]+"";
        // update index
        LuceneUtilForCms.deleteIndexById(id,CONTENT_ID);
    }

    private CmsContent getBeanArg(JoinPoint joinPoint) {
        for (Object obj : joinPoint.getArgs()) {
            if (obj instanceof CmsContent) {
                return (CmsContent) obj;
            }
        }
        return null;
    }
    public static final String CONTENT_ID = "contentId";
    public static final String[] HIGHLIGH_ARRAY = new String[]{"text","title"};
    public static final String[] ANALYSE_ARRAY = new String[]{"text","title"};
	
	
	public PaginationHelper<CmsContent> queryIndex(CmsContent info,
			String page, String manageMark, HttpSession session) {
		return LuceneUtilForCms.queryIndex(info,page,HIGHLIGH_ARRAY,CONTENT_ID,manageMark,session);
	}


	public CmsContent detail(String infoId, String backType,
			HttpSession session) {
		return LuceneUtilForCms.detail(infoId,CONTENT_ID,CmsContent.class,HIGHLIGH_ARRAY,backType,session);
	}
}

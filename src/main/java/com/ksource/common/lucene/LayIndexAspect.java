package com.ksource.common.lucene;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.LayInfo;


/**
 * 此类为  LayInfoController 类的切入点声明类.<br/>
 * 当调用LayInfoController的 add,update,delete方法后分别会调用
 * afterReturnAddPointcut,afterReturnUpdatePointcut,afterReturnDeletePointcut.用于更新
 * 法律库的索引.
 *
 * @author zxl :)
 * @version 1.0
 * date   2011-9-8
 * time   上午09:37:15
 */
@Aspect
public class LayIndexAspect {
    @Pointcut("execution(* com.ksource.liangfa.web.info.LayInfoController.add (..))")
    public void addPointcut(){}
    ;
    @Pointcut("execution(* com.ksource.liangfa.web.info.LayInfoController.update (..))")
    public void updatePointcut(){}
    ;
    @Pointcut("execution(* com.ksource.liangfa.web.info.LayInfoController.delete (..))")
    public void deletePointcut(){}
    ;
    @Pointcut("execution(* com.ksource.liangfa.web.info.LayInfoController.batchDelete(..))")
    public void batchDeletePointcut(){}
    ;
    @AfterReturning("addPointcut()")
    public void afterReturnAddPointcut(JoinPoint joinPoint)
        throws ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
    	//2.add index
        LuceneUtil.addIndex(getBeanArg(joinPoint),HIGHLIGH_ARRAY1);
    }

    @AfterReturning("updatePointcut()")
    public void afterReturnUpdatePointcut(JoinPoint joinPoint)
        throws ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        LuceneUtil.updateIndex(getBeanArg(joinPoint),ID_FIELD,HIGHLIGH_ARRAY1);
    }

    @AfterReturning("deletePointcut()")
    public void afterReturnDeletePointcut(JoinPoint joinPoint)
        throws ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        //1.bean的Id默认为方法的第一个参数
        String id = (String) joinPoint.getArgs()[0];
        // update index
        LuceneUtil.deleteIndexById(id,ID_FIELD);
        
    }
    @AfterReturning("batchDeletePointcut()")
    public void afterReturnBatchDeletePointcut(JoinPoint joinPoint)
    	throws ClassNotFoundException, SecurityException, NoSuchMethodException,
        IllegalArgumentException, IllegalAccessException,
        InvocationTargetException{
    	String infoId;
    	String[] infoIds = (String[]) joinPoint.getArgs()[0];
    	for(int i=0;i<infoIds.length;i++){
    		infoId =infoIds[i];
    		LuceneUtil.deleteIndexById(infoId,ID_FIELD);    
    	}
    }

    private LayInfo getBeanArg(JoinPoint joinPoint) {
        for (Object obj : joinPoint.getArgs()) {
            if (obj instanceof LayInfo) {
                return (LayInfo) obj;
            }
        }
        return null;
    }
    public static final String ID_FIELD = "infoId";
    public static final String[] HIGHLIGH_ARRAY1 = new String[]{"content","title","typeId"};
	
	
	public PaginationHelper<LayInfo> queryIndex(LayInfo layInfo,
			String page, String manageMark, HttpSession session) {
		return LuceneUtil.queryIndex(layInfo,page,HIGHLIGH_ARRAY1,ID_FIELD,manageMark,session);
	}


	public LayInfo detail(String infoId, String backType,
			HttpSession session) {
		return LuceneUtil.detail(infoId,ID_FIELD,LayInfo.class,HIGHLIGH_ARRAY1,backType,session);
	}
}

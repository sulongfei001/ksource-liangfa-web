package com.ksource.common.lucene;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.HotlineInfo;
import com.ksource.liangfa.domain.LayInfo;
import com.ksource.liangfa.domain.cms.CmsContent;

@Aspect
public class HotlineIndexAspect {
    @Pointcut("execution(* com.ksource.liangfa.service.casehandle.HotlineInfoServiceImpl.batckInsert (..))")
    public void addPointcut(){};
    /*@Pointcut("execution(* com.ksource.liangfa.mapper.HotlineInfoMapper.insert (..))")
    public void addPointcut(){};*/
    
    
    @AfterReturning("addPointcut()")
    public void afterReturnAddPointcut(JoinPoint joinPoint)
        throws ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
    	//2.add index
    	List<HotlineInfo> asList = (List<HotlineInfo>) joinPoint.getArgs()[0];
    	for(HotlineInfo obj:asList){
    	 LuceneUtil.addHotlineIndex(obj,ANALYSE_ARRAY);
    	}
    }

	public static final String ID_FIELD = "infoId";
    public static final String[] HIGHLIGH_ARRAY = new String[]{"content","theme"};
    public static final String[] ANALYSE_ARRAY = new String[]{"content","theme"};
	
	
	public PaginationHelper<HotlineInfo> queryHotlineIndex(HotlineInfo hotlineInfo,
			String page, String manageMark, HttpSession session) {
		return LuceneUtil.queryHotlineIndex(hotlineInfo,page,HIGHLIGH_ARRAY,ID_FIELD,manageMark,session);
	}

	public HotlineInfo searchDetail(String infoId, String backType,
			HttpSession session) {
		return LuceneUtil.searchDetail(infoId,ID_FIELD,HotlineInfo.class,HIGHLIGH_ARRAY,backType,session);
	}
}

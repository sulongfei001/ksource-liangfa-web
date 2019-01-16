package com.ksource.common.lucene;

import java.lang.reflect.InvocationTargetException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.ksource.liangfa.domain.CaseBasic;


/**
 * 系统切面类，用于切面控制与网站栏目有关联的菜单（法律法规、执法动态等）
 */
@Aspect
public class CaseIndexAspect {
	/**
	 * 法律法规
	 */
    @Pointcut("execution(* com.ksource.liangfa.service.casehandle.CaseService.addPenaltyCase (..))")
    public void addPointcut(){}
    ;
    @Pointcut("execution(* com.ksource.liangfa.service.casehandle.CaseService.update (..)) " +
    		"|| execution(* com.ksource.liangfa.service.casehandle.CaseTodoService.lianAdd (..))" +
    		"|| execution(* com.ksource.liangfa.service.casehandle.CaseTodoService.addPenaltyCase (..))")
    public void updatePointcut(){}
    ;
    @Pointcut("execution(* com.ksource.liangfa.service.casehandle.CaseService.deleteCaseAndWorkflow (..))")
    public void deletePointcut(){}
    ;
	
    @AfterReturning("addPointcut()")
    public void afterReturnAddInfoPointcut(JoinPoint joinPoint)
        throws ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {	
    		CaseBasic caseBasic = getBeanArg(joinPoint);
            LuceneUtilForCase.addIndex(caseBasic, LuceneFactoryForCase.ANALYSE_ARRAY,LuceneFactoryForCase.INDEX_POSTION_ALL);
    }

    @AfterReturning("updatePointcut()")
    public void afterReturnUpdateInfoPointcut(JoinPoint joinPoint)
        throws ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
    		CaseBasic caseBasic = getBeanArg(joinPoint);
    		LuceneUtilForCase.updateIndex(caseBasic,LuceneFactoryForCase.ID_FIELD,LuceneFactoryForCase.ANALYSE_ARRAY,LuceneFactoryForCase.INDEX_POSTION_ALL);
    }

    @AfterReturning("deletePointcut()")
    public void afterReturnDeleteInfoPointcut(JoinPoint joinPoint)
        throws ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {     
    		 String caseId = (String)joinPoint.getArgs()[0];
    		 //删除网站文章索引
    		 LuceneUtilForCase.deleteIndexById(caseId,LuceneFactoryForCase.ID_FIELD,LuceneFactoryForCase.INDEX_POSTION_ALL);
    }
  
  private CaseBasic getBeanArg(JoinPoint joinPoint) {
      for (Object obj : joinPoint.getArgs()) {
          if (obj instanceof CaseBasic) {
              return (CaseBasic) obj;
          }
      }
      return null;
  }

}

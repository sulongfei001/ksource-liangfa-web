package com.ksource.liangfa.service.casehandle;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.lucene.LuceneFactoryForCase;
import com.ksource.common.lucene.LuceneUtilForCase;
import com.ksource.liangfa.domain.CaseForLucene;
import com.ksource.liangfa.mapper.CaseBasicMapper;

@Service
@Transactional
public class CaseIndexServiceImpl implements CaseIndexService{
	
	@Autowired
	private LuceneUtilForCase luceneUtilForCase;
	@Autowired
	private CaseBasicMapper caseBasicMapper;
	
	public ServiceResponse createCaseIndex(){
		try{
			// 查询6种筛选条件下的案件，分别创建索引
//			for(String fc:LuceneFactoryForCase.FILTER_CRITERIA){
//				Map<String,Object> params = new HashMap<String, Object>();
//				params.put(fc, "Y");
			//	List<CaseBasic> list = caseBasicMapper.getyisiFaCaseListForLucene(params);
			//	LuceneUtilForCase.createIndex(list, LuceneFactoryForCase.ANALYSE_ARRAY,fc);
		//}
			List<CaseForLucene> list = caseBasicMapper.findAllCaseForLuceneList();
			LuceneUtilForCase.createIndex(list, LuceneFactoryForCase.ANALYSE_ARRAY,"all");
			return new ServiceResponse(true, "创建索引成功");
		}catch(Exception e){
			e.printStackTrace();
			return new ServiceResponse(false, "创建索引失败");
		}
	}

	@Override
	public PaginationHelper<CaseForLucene> queryIndex(CaseForLucene caseBasic, String page,
			String manageMark, HttpSession session,String indexPositon,String keywords,boolean isAndCondition) {
		return LuceneUtilForCase.queryIndex(caseBasic, page, LuceneFactoryForCase.HIGHLIGH_ARRAY, LuceneFactoryForCase.ID_FIELD, manageMark, session,indexPositon,isAndCondition);
	}
}

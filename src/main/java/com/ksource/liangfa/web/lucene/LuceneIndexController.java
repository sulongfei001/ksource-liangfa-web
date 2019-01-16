package com.ksource.liangfa.web.lucene;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.lucene.LuceneUtil;
import com.ksource.common.lucene.LuceneUtilForCms;
import com.ksource.liangfa.domain.LayInfo;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseIndexService;
import com.ksource.liangfa.service.cms.CmsContentService;
import com.ksource.liangfa.service.info.InfoService;


/**
 * 此类为 TODO: 用于操作lucene 索引 控制器
 *
 * @author zxl :)
 * @version 1.0
 * date   2011-9-7
 * time   下午04:43:33
 */
@Controller
@RequestMapping("/lucene")
public class LuceneIndexController {
	@Autowired
	InfoService infoService;
	@Autowired
	CmsContentService cmsContentService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	CaseIndexService caseIndexService;	
    /**用于第一次生成索引*/
    @RequestMapping("createLay")
    @ResponseBody
    public ServiceResponse createLay(LayInfo layInfo) {
         return LuceneUtil.createIndex(infoService.queryLayInfos(layInfo),new String[]{"title","content","typeId"});
    }
    /**用于第一次生成索引*/
    @RequestMapping("createContent")
    @ResponseBody
    public ServiceResponse createContent() {
         return LuceneUtilForCms.createIndex(cmsContentService.queryContentInfos(),new String[]{"title","text"});
    }
    
    /**用于第一次生成索引*/
    @RequestMapping("createCase")
    @ResponseBody
    public ServiceResponse createCaseIndex(){
    	return caseIndexService.createCaseIndex();
    }
}

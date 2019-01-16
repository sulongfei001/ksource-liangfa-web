package com.ksource.liangfa.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ksource.liangfa.domain.CaseAttachment;
import com.ksource.liangfa.domain.FieldInstance;


@Component("DocConvertDAO")
public class DocConvertDAO extends SqlSessionDaoSupport {
    @Autowired
    public DocConvertDAO(SqlSessionFactory sqlSessionFactory) {
        this.setSqlSessionFactory(sqlSessionFactory);
    }

    @SuppressWarnings("unchecked")
	public List<CaseAttachment> getTopNCaseAttachmentNotConvert(int rows){
    	return this.getSqlSession().selectList("DocConvertDAO.getTopNCaseAttachmentNotConvert", rows);
    }
    @SuppressWarnings("unchecked")
    public List<FieldInstance> getTopNFieldInsFileNotConvert(int rows){
    	return this.getSqlSession().selectList("DocConvertDAO.getTopNFieldInsFileNotConvert", rows);
    }
}

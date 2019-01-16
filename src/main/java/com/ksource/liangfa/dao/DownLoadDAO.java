package com.ksource.liangfa.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ksource.liangfa.web.bean.MyFile;

@Component("downLoadDAO")
public class DownLoadDAO<V> extends SqlSessionDaoSupport{
	
	@Autowired
	public DownLoadDAO(SqlSessionFactory sqlSessionFactory) {
		this.setSqlSessionFactory(sqlSessionFactory);
	}
	
	//得到案件详情附件
	public MyFile getCaseDetail(String caseId){
		return (MyFile)this.getSqlSession().selectOne("downLoadDAO.getCaseDetail", caseId);
	}
	//得到案件详情附件
	public MyFile getWeijiCaseDetail(int caseId){
		return (MyFile)this.getSqlSession().selectOne("downLoadDAO.getWeijiCaseDetail", caseId);
	}
	//得到案件详情附件
	public MyFile getDutyCaseDetail(int caseId){
		return (MyFile)this.getSqlSession().selectOne("downLoadDAO.getDutyCaseDetail", caseId);
	}
	
	//得到案件处罚判决书附件
//	public MyFile getCaseChufapanjueshu(String caseId){
//		return (MyFile)this.getSqlSession().selectOne("downLoadDAO.getCaseChufapanjueshu", caseId);
//	}
	
	//得到表单字段字段文件（file类型的字段）
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MyFile getFieldFile(String taskId,int fieldId){
		Map params = new HashMap(2);
		params.put("taskId", taskId);
		params.put("fieldId", fieldId);
		return (MyFile)this.getSqlSession().selectOne("downLoadDAO.getFieldFile", params);
	}

	/**
	 * 得到案件咨询内容中的附件
	 * @param contentId
	 * @return
	 */
	public MyFile getCaseConsultationContentFile(Integer contentId) {
		return (MyFile)this.getSqlSession().selectOne("downLoadDAO.getCaseConsultationContentFile", contentId);
	}
	
	
	/**
	*功能：得到案件咨询问题中附件
	*@param contentId
	*@return 
	*/
	public MyFile getCaseConsultationFile(Integer consultationId) {
		return (MyFile)this.getSqlSession().selectOne("downLoadDAO.getCaseConsultationFile", consultationId);
	}
	
	public MyFile getResourceFile(Integer fileId){
		return (MyFile)this.getSqlSession().selectOne("downLoadDAO.getResourceFile", fileId);
	}

	public MyFile getThemeReplyFile(Integer replyId) {
		return (MyFile)this.getSqlSession().selectOne("downLoadDAO.getThemeReplyFile", replyId);
	}

	public MyFile getForumThemeFile(Integer themeId) {
		return (MyFile)this.getSqlSession().selectOne("downLoadDAO.getForumThemeFile", themeId);
	}
	
	public MyFile getNoticeFile(Integer fileId) {
		return (MyFile)this.getSqlSession().selectOne("downLoadDAO.getNoticeFile", fileId);
	}

	public MyFile getCaseReplyFile(Integer id) {
		return (MyFile)this.getSqlSession().selectOne("downLoadDAO.getCaseReplyFile", id);
	}

	public MyFile getCaseAtta(String id) {
		return (MyFile)this.getSqlSession().selectOne("downLoadDAO.getCaseAtta", id);
	}
	
	public MyFile getLicenseApplyForm(Integer id){
		return (MyFile) this.getSqlSession().selectOne("downLoadDAO.getLicenseApplyForm", id);
	}

    public MyFile getMailFile(Integer id) {
        return (MyFile) this.getSqlSession().selectOne("downLoadDAO.getMailFile", id);
    }
    
    public MyFile getPublishInfoFile(Integer fileId) {
    	return (MyFile) this.getSqlSession().selectOne("downLoadDAO.getPublishInfoFile", fileId);
    }

	public MyFile getActivityFile(Integer id) {
		return (MyFile) this.getSqlSession().selectOne("downLoadDAO.getActivityFile", id);
	}

	public MyFile getContentFile(Integer id) {
		return (MyFile) this.getSqlSession().selectOne("downLoadDAO.getContentFile", id);
	}

	public MyFile getModifiedImpFile(Integer id) {
		return (MyFile) this.getSqlSession().selectOne("downLoadDAO.getModifiedImpFile", id);
	}

	public MyFile getCaseExportFile(String logId) {
		return (MyFile) this.getSqlSession().selectOne("downLoadDAO.getCaseExportFile", logId);
	}

	public MyFile getclue(Integer clueId, String tableClueInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("clueId", clueId);
		map.put("tableClueInfo", tableClueInfo);
		return (MyFile) this.getSqlSession().selectOne("downLoadDAO.getclueFile", map);
	}

    public MyFile getNewAppFile(Integer fileId, Double versionNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fileId", fileId);
        map.put("versionNo", versionNo);
        return (MyFile) this.getSqlSession().selectOne("downLoadDAO.getNewAppFile",map);
    }
}

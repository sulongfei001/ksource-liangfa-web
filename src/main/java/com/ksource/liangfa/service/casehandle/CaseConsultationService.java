package com.ksource.liangfa.service.casehandle;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.CaseConsultation;
import com.ksource.liangfa.domain.CaseConsultationContent;
import com.ksource.liangfa.domain.Participants;
import com.ksource.liangfa.domain.ParticipantsKey;

/**
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2011-8-29
 * time   上午11:39:58
 */
public interface CaseConsultationService {
	
	/**
	 * 通过 参与人参与的未读的案件咨询.
	 * 可查出
	 *  <ul>
	 *  <li>参与人名字</li>
	 *  <li>参与人所在组织机构名称</li>
	 *  </ul>
	 * @param userId  参与人ID
	 * @param readState  状态
	 * @return
	 */
	public List<CaseConsultation> getToDoList(String userId, int readState);
	/**
	 * 查找 指定案件咨询ID 的参与内容
	 * @param caseConsultationId 案件咨询ID
	 * @return
	 */
	List<CaseConsultationContent> selectContext(Integer caseConsultationId);
	/**
	 *  查找 指定案件咨询ID的信息.
	 *  与selectByPrimaryKey()方法不同的是 selectByPK()可以查出
	 *  <ul>
	 *  <li>参与人名字</li>
	 *  <li>参与人所在组织机构名称</li>
	 *  </ul>
	 * @param caseConsultationId
	 * @return
	 */
	public CaseConsultation selectByPK(Integer caseConsultationId);
	/**
	 * 添加案件咨询内容.
	 * @param content
	 * @param attachmentFile 附件
	 * @return
	 */
	public CaseConsultationContent addContext(CaseConsultationContent content,
			MultipartFile attachmentFile);
	
	/**
	 * 删除指定案件咨询ID的所有参与人
	 * @param caseConsultationId
	 */
	void del(int caseConsultationId);

	/**
	 * 通过查询条件查询 案件咨询.<br/>
	 * 1.如果是系统管理员查询所有案件咨询,其它用户只能查询自己录入的案件咨询.<br/>
	 * 2.置顶类案件咨询首先被查询出来,非置顶类案件咨询后被查询出来,最终的集合<br/>
	 *   为这两类咨询集合的总集合.
	 * <br/>
	 * 此查询为数据库分页查询.
	 * @param caseConsultation
	 * @param page
	 * @return 案件咨询集合
	 */
	PaginationHelper<CaseConsultation> search(CaseConsultation caseConsultation, String page);

	/**
	 * 添加案件咨询信息.
	 * @param caseConsultation
	 * @param participantsKey
	 * @param attachmentFile
	 * @return 
	 */
	ServiceResponse add(CaseConsultation caseConsultation, ParticipantsKey participantsKey,MultipartFile attachmentFile);
	
	void updateParticipants(Participants par);
	public void updateConsultation(CaseConsultation con);
	public void updateContent(CaseConsultationContent content);
	
	/**
	 * 删除案件咨询，包括已经咨询的内容、附件和指定案件咨询ID的所有参与人
	 * @param id
	 */
	public void deleteCaseConsultation(Integer id);
	
	/**
	 * 查询我接收的咨询
	 * @param caseConsultation
	 * @param page
	 * @return
	 */
	PaginationHelper<CaseConsultation> receiveList(CaseConsultation caseConsultation, String page);

	/**
	 * 查询回复列表
	 * @param caseConsultationId
	 * @return
	 */
	PaginationHelper<CaseConsultationContent> findReplyList(Map<String,Object> map,String page);
}
/**
 * 
 */
package com.ksource.liangfa.service.casehandle;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.liangfa.domain.AdmdivLicenseApprove;
import com.ksource.liangfa.domain.AdmdivLicenseInfo;
import com.ksource.liangfa.domain.AdmdivLicenseReply;

/**
 * @author XT
 * 2013-1-7
 */
public interface AdmdivLicenseService {
	/**
	 * 添加
	 * @param approve  申批信息
	 * @param info 
	 * @param attachmentFiles
	 * @author XT
	 */
	public void add(AdmdivLicenseApprove approve,AdmdivLicenseInfo info,MultipartHttpServletRequest attachmentFiles);

	/**
	 * 获得行政许可ID
	 * <br>自增长
	 * @return
	 * @author XT
	 */
	Integer getLicenseSeq();


	/**
	 * 获得备案ID
	 * @return
	 * @author XT
	 */
	Integer getFiledSeq();


	/**
	 * 获得备案ID
	 * @return
	 * @author XT
	 */
	Integer getOptionSeq();

	/**
	 * 详细
	 * @param licenseId
	 * @author XT
	 */
	public AdmdivLicenseInfo detail(Integer licenseId);

	/**
	 * 批复功能
	 * @param reply
	 * @return
	 * @author XT
	 */
	public Boolean reply(AdmdivLicenseReply reply);

	/**
	 * 获得批复ID
	 * @return
	 * @author XT
	 */
	Integer getReplySeq();

	/**
	 * 查询批复
	 * @param reply
	 * @author XT
	 */
	public AdmdivLicenseReply queryReply(AdmdivLicenseReply reply);
	
}

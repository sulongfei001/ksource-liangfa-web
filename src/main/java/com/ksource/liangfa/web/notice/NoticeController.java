package com.ksource.liangfa.web.notice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.Notice;
import com.ksource.liangfa.domain.NoticeExample;
import com.ksource.liangfa.domain.NoticeOrg;
import com.ksource.liangfa.domain.NoticeRead;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.notice.NoticeOrgService;
import com.ksource.liangfa.service.notice.NoticeReadService;
import com.ksource.liangfa.service.notice.NoticeService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	public static final String NOTICEADD_VIEW = "notice/noticeAdd";
	public static final String NOTICEMAIN_VIEW = "notice/noticeMain";
	public static final String MAIN_VIEW = "redirect:/notice/back";
	public static final String NOTICEUPDATE_VIEW = "notice/noticeUpdate";

	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private NoticeOrgService noticeOrgService;
	@Autowired
	private NoticeReadService noticeReadService;

	/**
	 * 进入通知公告添加页面 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "addUI")
	public ModelAndView addUI(Map<String, Object> model) {
		ModelAndView view = new ModelAndView(NOTICEADD_VIEW);
		return view;
	}

	/**通知公告保存操作
	 * 
	 * @param request
	 * @param notice
	 * @param orgIds
	 * @param attachmentFile
	 * @return
	 */
	@RequestMapping(value = "add")
	public ModelAndView add(HttpServletRequest request, Notice notice,String orgIds,
			MultipartHttpServletRequest attachmentFile) {
		User loginUser = SystemContext.getCurrentUser(request);
		notice.setNoticeCreater(loginUser.getUserId());

		Calendar c = Calendar.getInstance();
		notice.setNoticeTime(c.getTime());
		ServiceResponse response = noticeService.add(notice,orgIds, attachmentFile);
		ModelAndView view = new ModelAndView(NOTICEADD_VIEW);
		view.addObject("info", response.getResult());
		return view;
	}

	/**
	 * 已发通知查询
	 * @param session
	 * @param notice
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "main")
	public ModelAndView main(HttpSession session, Notice notice, String page) {
		ModelAndView view = new ModelAndView(NOTICEMAIN_VIEW);
		User loginUser = SystemContext.getCurrentUser(session);
		// 普通用户只能查到自己添加的通知公告
		if (loginUser.getUserType() == Const.USER_TYPE_PLAIN) {
			notice.setNoticeCreater(loginUser.getUserId());
		}
		PaginationHelper<Notice> noticeList = noticeService.find(notice, page,null);
		//设置前台页面通知状态
		for (Notice n : noticeList.getList()) {
			Date effectDate=n.getValidBeginTime();
			Date failureDate=n.getValidEndTime();
			Date sysDate=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String currDate=sdf.format(sysDate);
			try {
				sysDate=sdf.parse(currDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(n.getIsPublished() == 1){
				if(effectDate!=null){
					if(failureDate!=null){
						if(sysDate.before(effectDate)||failureDate.before(sysDate)){
							n.setIsPublished(3);
						}
					}else{
						if(sysDate.before(effectDate)){
							n.setIsPublished(3);
						}
					}
				}
				if(effectDate==null){
					if(failureDate!=null){
						if(failureDate.before(sysDate)){
							n.setIsPublished(3);
						}
					}
				}
				
			}
		}
		view.addObject("noticeList", noticeList);
		view.addObject("page", page);
		return view;
	}

	/**
	 * 通知查阅查询
	 * @param session
	 * @param notice
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "search")
	public ModelAndView search(HttpSession session, Notice notice, String page) {
		User loginUser = SystemContext.getCurrentUser(session);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("orgId",loginUser.getOrgId());
        map.put("userId",loginUser.getUserId());
		PaginationHelper<Notice> noticeList = noticeService.findMyNoticeList(notice,map,page);
		ModelAndView view = new ModelAndView("notice/noticeSearch");
		view.addObject("noticeList", noticeList);
		view.addObject("page", page);
		return view;
	}

	/**
	 * 批量删除通知公告
	 * @param check
	 * @return
	 */
	@RequestMapping(value ="batch_delete")
	public String batchDelete(Integer[] check){
		if(check != null){
			noticeService.batchDeleteNotice(check);
		}
		return MAIN_VIEW;
	}
	

	/**
	 * 查询通知公告详情
	 * @param request
	 * @param model
	 * @param noticeId
	 * @param backType
	 * @return
	 */
	@RequestMapping(value = "searchDisplay")
	public ModelAndView searchDisplay(HttpServletRequest request,Map<String, Object> model, Integer noticeId,
			String backType) {
		ModelAndView view = new ModelAndView("notice/noticeDetail");
		Notice notice = noticeService.find(noticeId);
		view.addObject("notice", notice);
		//通知公告详情页面返回列表时使用，backType判断返回到哪个列表页面，1为通知查阅，2为已发通知
		view.addObject("backType", backType);
		view.addObject("noticeId", noticeId);
		findNoticeFileID(model, noticeId);
		
		User user = SystemContext.getCurrentUser(request);
        if(!Const.SYSTEM_ADMIN_ID.equals(user.getAccount())){
            NoticeRead record=new NoticeRead();
            record.setNoticeId(noticeId);
            record.setOrgId(user.getOrganise().getOrgCode());
            record.setUserId(user.getUserId());
            int count=noticeReadService.getNoticeReadCount(record);
            if(count==0){
                NoticeRead noticeRead = new NoticeRead();
                noticeRead.setId(systemDAO.getSeqNextVal("notice_read"));
                noticeRead.setNoticeId(noticeId);
                noticeRead.setUserId(user.getUserId());
                noticeRead.setOrgId(user.getOrganise().getOrgCode());
                Calendar calendar = Calendar.getInstance();
                noticeRead.setReadTime(calendar.getTime());
                noticeReadService.insert(noticeRead);
            }
        }
        int noticeOrgNumber=noticeOrgService.getNoticeOrgCount(noticeId);
		view.addObject("noticeOrgNumber", noticeOrgNumber);
		return view;
	}

	/**
	 * 进入通知公告修改页面
	 * @param model
	 * @param example
	 * @param noticeId
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "updateUI")
	public ModelAndView updateUI( NoticeExample example,
			Integer noticeId,String info) {
		ModelAndView view = new ModelAndView(NOTICEUPDATE_VIEW);
		Notice notice = noticeService.selectByPrimaryKey(noticeId);
		PublishInfoFile publishInfoFile=new PublishInfoFile();
		publishInfoFile.setFileType(Const.TABLE_NOTICE);
		publishInfoFile.setInfoId(noticeId);
		//查询通知公告附件信息
		List<PublishInfoFile> publishInfoFiles =noticeService.getFilesByInfoId(publishInfoFile);
		view.addObject("publishInfoFiles", publishInfoFiles);
		view.addObject("notice", notice);
		List<NoticeOrg> noticeOrgs = noticeOrgService.selectByNoticeId(noticeId);
		if(noticeOrgs.size() > 0){
			String orgIds = "";
			String orgNames = "";
			for(NoticeOrg no:noticeOrgs){
				orgIds += no.getOrgId()+",";
				orgNames += no.getOrgName()+",";
			}
			orgIds = StringUtils.trimSufffix(orgIds, ",");
			orgNames = StringUtils.trimSufffix(orgNames, ",");
			view.addObject("orgIds", orgIds);
			view.addObject("orgNames", orgNames);
		}
		view.addObject("info", info);
		return view;
	}

	/**
	 * 通知更新操作 
	 * @param notice
	 * @param orgIds
	 * @param attachmentFile
	 * @return
	 */
	@RequestMapping(value = "update")
	public ModelAndView update(Notice notice, String orgIds,MultipartHttpServletRequest attachmentFile) {
		ServiceResponse res = noticeService.updateByPrimaryKeySelective(notice,orgIds, attachmentFile);
		ModelAndView view = new ModelAndView("redirect:/notice/updateUI?noticeId="+notice.getNoticeId());
		view.addObject("info", res.getResult());
		return view;
	}

	/**
	 * 通知公告详情返回操作
	 * @param session
	 * @param backType
	 * @return
	 */
	@RequestMapping(value = "back")
	public ModelAndView back(HttpSession session,String backType) {
		Notice notice= new Notice();
		String page="1";
		//根据参数判断返回页面
		if (StringUtils.isNotBlank(backType) && backType.equals("1")) {//返回到通知查阅页面
			return this.search(session, notice, page);
		}else {//返回到已发通知页面
			return this.main(session, notice, page);
		}
	}

	/**
	 * 查询通知公告附件信息
	 * @param model
	 * @param noticeId
	 */
	public void findNoticeFileID(Map<String, Object> model, Integer noticeId) {
		PublishInfoFile publishInfoFile=new PublishInfoFile();
		publishInfoFile.setFileType(Const.TABLE_NOTICE);
		publishInfoFile.setInfoId(noticeId);
		List<PublishInfoFile> publishInfoFiles =noticeService.getFilesByInfoId(publishInfoFile);
		model.put("iDList",publishInfoFiles);
	}

	/**
	 * 通知公告终止
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("noticeStop")
	public boolean noticeStop(HttpServletRequest request,String noticeId,HttpServletResponse response){
		try {
			Notice notice = new Notice();
			notice.setNoticeId(Integer.parseInt(noticeId));
			notice.setIsPublished(Const.NOTICE_ISNOT_PUBLISHED);
			noticeService.updateByPrimaryKeySelective(notice);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 通知公告生效
	 * @param request
	 * @param noticeId
	 * @param response
	 * @return
	 */
	@RequestMapping("noticeStart")
	@ResponseBody
	public boolean noticeStart(HttpServletRequest request,String noticeId,HttpServletResponse response){
		try {
			Notice notice = new Notice();
			notice.setNoticeId(Integer.parseInt(noticeId));
			notice.setIsPublished(Const.NOTICE_IS_PUBLISHED);
			noticeService.updateByPrimaryKeySelective(notice);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@InitBinder
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
	
}

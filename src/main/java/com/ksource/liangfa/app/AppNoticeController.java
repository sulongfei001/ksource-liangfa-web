package com.ksource.liangfa.app;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.Notice;
import com.ksource.liangfa.domain.NoticeRead;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.notice.NoticeReadService;
import com.ksource.liangfa.service.notice.NoticeService;
import com.ksource.liangfa.service.system.InstantMessageService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 通知公告
 * @author lijiajia
 * @date 2017
 */

@Controller
@RequestMapping("/app/notice")
public class AppNoticeController {
	
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private NoticeReadService noticeReadService;
	@Autowired
	private PublishInfoFileMapper publishInfoFileMapper;
	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private InstantMessageService instantMessageService;

	/**
	 * 查询发送给我的通知公告
	 * @param title
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("query")
	@ResponseBody
	public String query (String title,String page,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
    	JSONObject jsonObject = new JSONObject();
    	User currUser = SystemContext.getCurrentUser(request);
    	try {
    		map.put("orgId",currUser.getOrgId());
            map.put("userId",currUser.getUserId());
            Notice notice=new Notice();
            notice.setNoticeTitle(title);
    		PaginationHelper<Notice> noticeList = noticeService.findMyNoticeList(notice,map,page);
            jsonObject.put("success", true);
            jsonObject.put("msg", "查询成功");
            jsonObject.put("list", noticeList.getList());
            jsonObject.put("totalPageNum", noticeList.getTotalPageNum());
    	    SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
    	    return result;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success",false);
			jsonObject.put("msg","获取我的通知公告信息失败！");
			return jsonObject.toJSONString();
		}
	}
	
	/**
	 * 获取通知公告详情
	 * @param noticeId
	 * @return
	 */
    @RequestMapping("detail")
    @ResponseBody
    public String detail(Integer noticeId,HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
    	try {
            if(noticeId == null){
                throw new Exception("noticeId为空！");
            }
    		Notice notice = noticeService.find(noticeId);
    		notice.setNoticeContent(StringUtils.Html2Text(notice.getNoticeContent()));
        	NoticeRead record=new NoticeRead();
        	record.setNoticeId(noticeId);
        	int readCount=noticeReadService.getNoticeReadCount(record);
        	int unreadCount=noticeReadService.getNotReadNoticeCount(noticeId);
        	//为该条通知的已读人数和未读人数赋值
        	notice.setReadCount(readCount);
        	notice.setUnreadCount(unreadCount);
            PublishInfoFile publishInfoFile=new PublishInfoFile();
            publishInfoFile.setFileType(Const.TABLE_NOTICE);
            publishInfoFile.setInfoId(noticeId);
            List<PublishInfoFile> publishInfoFiles =noticeService.getFilesByInfoId(publishInfoFile);
            if(publishInfoFiles.size() > 0){
                jsonObject.put("fileList", publishInfoFiles);
            }
            //通知公告增加已读信息
            User user = SystemContext.getCurrentUser(request);
            if(!Const.SYSTEM_ADMIN_ID.equals(user.getAccount())){
                NoticeRead record1=new NoticeRead();
                record1.setNoticeId(noticeId);
                record1.setOrgId(user.getOrganise().getOrgCode());
                record1.setUserId(user.getUserId());
                int count=noticeReadService.getNoticeReadCount(record1);
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
            
            User currUser = SystemContext.getCurrentUser(request);
			//更新消息详情
			instantMessageService.upReadStatus(noticeId.toString(), currUser);
            
        	jsonObject.put("success",true);
        	jsonObject.put("msg","通知公告详情查询成功！");
        	jsonObject.put("notice",notice);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success",false);
			jsonObject.put("msg","通知公告详情查询失败:"+e.getMessage());
		}
        String result = JSONObject.toJSONString(jsonObject,serializeConfig);
        return result; 
    }
	
    /**
     * 获取通知公告已读组织信息
     * @param noticeId
     * @param page
     * @return
     */
    @RequestMapping("readNotices")
    @ResponseBody
    public String readNotices(Integer noticeId,String page){
    	Map<String,Object> map = new HashMap<String,Object>();
    	JSONObject jsonObject = new JSONObject();
    	try {
    		map.put("noticeId",noticeId);
    		PaginationHelper<NoticeRead> readList = noticeReadService.readNotices(map,page);
            jsonObject.put("success", true);
            jsonObject.put("msg", "查询成功");
            jsonObject.put("list", readList.getList());
            jsonObject.put("totalPageNum", readList.getTotalPageNum());
    	    SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
    	    return result;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success",false);
			jsonObject.put("msg","获取通知公告已读组织信息错误！");
			return jsonObject.toJSONString();
		}
    }
    
    /**
     * 获取通知公告未读组织信息
     * @param noticeId
     * @param page
     * @return
     */
    @RequestMapping("unreadNotices")
    @ResponseBody
    public String unreadNotices(Integer noticeId,String page){
    	Map<String,Object> map = new HashMap<String,Object>();
    	JSONObject jsonObject = new JSONObject();
    	try {
    		map.put("noticeId",noticeId);
    	    PaginationHelper<NoticeRead> unreadList = noticeReadService.notReadNotices(map,page);
            jsonObject.put("success", true);
            jsonObject.put("msg", "查询成功");
            jsonObject.put("list", unreadList.getList());
            jsonObject.put("totalPageNum", unreadList.getTotalPageNum());
    	    return jsonObject.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success",false);
			jsonObject.put("msg","获取通知公告未读组织信息错误！");
			return jsonObject.toJSONString();
		}
    }
    
    
     
}

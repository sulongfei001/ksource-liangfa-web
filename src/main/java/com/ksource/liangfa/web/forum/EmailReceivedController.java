package com.ksource.liangfa.web.forum;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.common.util.FileUtil;
import com.ksource.liangfa.domain.*;
import com.ksource.liangfa.mapper.MailFileMapper;
import com.ksource.liangfa.mapper.MailReceivedInfoMapper;
import com.ksource.liangfa.mapper.MailSendInfoMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.forum.EmailReceivedService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PromptType;
import com.ksource.syscontext.SystemContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;

/**
 * User: zxl
 * Date: 13-1-25
 * Time: 上午11:27
 * 电子邮件 与接收相关功能 控制器
 */
@Controller
@RequestMapping("email/received")
public class EmailReceivedController {
    /**
     * 用于保存查询条件
     */
    private static final String SEARCH_CONDITION = EmailSendController.class
            .getName() + "conditionObj";
    /**
     * 用于保存分页的标志
     */
    private static final String PAGE_MARK = EmailSendController.class.getName()
            + "page";
    /**
     * 重定向到 发件箱列表界面 视图
     */
    private static final String REDI_BACK_VIEW = "redirect:/email/received/back";
    @Autowired
    EmailReceivedService emailRecivedService;
    @Autowired
    MybatisAutoMapperService mybatisAutoMapperService;

    @RequestMapping("main")
    public String main(MailReceivedInfo receivedInfo, String page, ModelMap map, HttpSession session) {
        User user = SystemContext.getCurrentUser(session);
        //判断为空时，重新创建一个对象，不然会报错
        if (receivedInfo==null) {
			receivedInfo=new MailReceivedInfo();
		}
        receivedInfo.setReceiveUser(user.getUserId());
        // TODO:保存查询条件,用于返回使用
        session.setAttribute(SEARCH_CONDITION, receivedInfo);
        session.setAttribute(PAGE_MARK, page);
        PaginationHelper<MailReceivedInfo> receivedList = emailRecivedService.findByReciveder(receivedInfo, page);
        map.put("receivedList", receivedList);
        map.put("page", page);
        //同步未读邮件数量
        MailReceivedInfoExample receivedInfoExample = new MailReceivedInfoExample();
        receivedInfoExample.createCriteria().andReadStateEqualTo(Const.STATE_INVALID)
                .andFlagEqualTo(Const.STATE_VALID)
                .andReceiveUserEqualTo(user.getUserId());
        map.put("notReadNum", mybatisAutoMapperService.countByExample(MailReceivedInfoMapper.class, receivedInfoExample));
        return "forum/email/emailReceivedList";
    }

    @RequestMapping("invalid")
    public String invalid(Integer[] check) {
        MailReceivedInfo receivedInfo = new MailReceivedInfo();
        receivedInfo.setFlag(Const.EMAIL_STATE_INVALD);
        if (check != null) {
            for (Integer id : check) {
                receivedInfo.setReceivedId(id);
                mybatisAutoMapperService.updateByPrimaryKeySelective(MailReceivedInfoMapper.class, receivedInfo);
            }
        }
        return ResponseMessage.addPromptTypeForPath(REDI_BACK_VIEW,
                PromptType.del);
    }

    @RequestMapping("del")
    public String del(Integer[] check) {
            emailRecivedService.del(check);
        return ResponseMessage.addPromptTypeForPath(REDI_BACK_VIEW,
                PromptType.del);
    }

    @RequestMapping("updateReadState/{state}")
    public String updateReadState(@PathVariable Integer state, Integer[] check) {
        MailReceivedInfo receivedInfo = new MailReceivedInfo();
        receivedInfo.setReadState(state);
        if (check != null) {
            for (Integer id : check) {
                receivedInfo.setReceivedId(id);
                mybatisAutoMapperService.updateByPrimaryKeySelective(MailReceivedInfoMapper.class, receivedInfo);
            }
        }
        return ResponseMessage.addPromptTypeForPath(REDI_BACK_VIEW,
                PromptType.update);
    }

    @RequestMapping(value = "back")
    public String back(HttpSession session, ModelMap map) {
        // 有查询条件,按查询条件返回查询功能
        String page;
        MailReceivedInfo receivedInfoInfo = (MailReceivedInfo) session.getAttribute(SEARCH_CONDITION);
        if (session.getAttribute(PAGE_MARK) != null) {
            page = (String) session.getAttribute(PAGE_MARK);
        } else {
            page = "1";
        }
        return this.main(receivedInfoInfo, page, map, session);
    }

    /**
     * 未读信息详情信息：如果信息是未读邮件修改为已读
     *
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "detail/{id}")
    public String detail(@PathVariable Integer id, ModelMap map) {
        MailReceivedInfoExample example = new MailReceivedInfoExample();
        example.createCriteria().andReadStateEqualTo(Const.STATE_INVALID).andReceivedIdEqualTo(id);
        if (mybatisAutoMapperService.countByExample(MailReceivedInfoMapper.class, example) != 0) {
            MailReceivedInfo info = new MailReceivedInfo();
            info.setReadState(Const.STATE_VALID);
            info.setReceivedId(id);
            mybatisAutoMapperService.updateByPrimaryKeySelective(MailReceivedInfoMapper.class, info);
            map.put("updateNum", "1");  //"1"是无意义的，传输updateNum这个参数的目的是减少未读信息数。
        }
        //有查询条件,按查询条件返回
        map.put("emailInfo", emailRecivedService.findById(id));
        return "forum/email/emailReceviedDetail";
    }
}

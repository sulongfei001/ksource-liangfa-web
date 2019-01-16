package com.ksource.liangfa.web.cms;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksource.common.bean.ResponseMessage;
import com.ksource.common.util.JsTreeUtils;
import com.ksource.liangfa.domain.cms.CmsChannel;
import com.ksource.liangfa.mapper.CmsChannelMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.cms.CmsChannelService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PromptType;

@Controller
@RequestMapping(value = "cms/channel/")
public class CmsChannelController {

	@Autowired
	private CmsChannelService cmsChannelService;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;


	/** 点击时进入所选节点详细信息页面 */
	@RequestMapping(value = "detail/{id}")
	public String detail(@PathVariable Integer id,
			ResponseMessage res, String isDel,ModelMap map) {
		id = (id == null) ? Const.TOP_MODULE_ID : id;
		
		CmsChannel channel = mybatisAutoMapperService.selectByPrimaryKey(
				CmsChannelMapper.class, id, CmsChannel.class);
		map.addAttribute("channel", channel);
		map.addAttribute("isLoadTree", res.getIsLoadTree());
		map.addAttribute("isDel", isDel);
		map.addAttribute("message", ResponseMessage.parseMsg(res));
		return "cms/cmsChannelInfo";
	}

	/** 新增一个栏目 */
	@RequestMapping(value = "insert")
	public String insert(CmsChannel channel) {
		cmsChannelService.insert(channel);
		return ResponseMessage.addIsLoadTreeForPath("redirect:/cms/channel/detail/"
				+ channel.getParentId(), true);
	}

	/** 修改更新栏目信息 */
	@RequestMapping(value = "update")
	public String update(CmsChannel channel) {
		if (channel.getIsShow() == null) {
			channel.setIsShow(Const.SHOW_NO);
		}
		mybatisAutoMapperService.updateByPrimaryKeySelective(
				CmsChannelMapper.class, channel);
		return ResponseMessage.addPromptTypeForPath("redirect:/cms/channel/detail/"
				+ channel.getChannelId(), PromptType.update);
	}

	/** 删除一个栏目 */
	@RequestMapping(value = "del/{channelId}")
	public String del(@PathVariable Integer channelId) {
		String path = "redirect:/cms/channel/detail/";
		CmsChannel channel = null;
		if (!channelId.equals(Const.TOP_CHANNEL_ID)) {
			channel = mybatisAutoMapperService.selectByPrimaryKey(
					CmsChannelMapper.class, channelId, CmsChannel.class);
			if (channel.getIsLeaf() == Const.LEAF_YES) {
				channel = mybatisAutoMapperService.selectByPrimaryKey(
						CmsChannelMapper.class, channelId, CmsChannel.class);
				path += channel.getParentId();
				Boolean del =cmsChannelService.del(channelId, channel.getParentId());
				if(del){
					path += "?isDel=true";// 删除标志，根据这个标志，前台zTree会执行刷新树操作。
					path = ResponseMessage.addIsLoadTreeForPath(path, true);
				}else{
					path = ResponseMessage.addPromptTypeForPath("redirect:/cms/channel/detail/"
							+ channel.getChannelId(), PromptType.cmsChannelDelFail);
				}
			} else {
				path = ResponseMessage.addPromptTypeForPath("redirect:/cms/channel/detail/"
						+ channel.getChannelId(),PromptType.moduleDelFail);
			}
		}
		return path;
	}

	/**
	 * 获取下级栏目
	 * */
	@RequestMapping(value = "loadChildChannel")
	public void loadChildModule(HttpServletResponse response,
			HttpServletRequest request, Integer id) {
		id = id == null ? Const.DUMMY_SUPER_TOP_MODULE_ID : id;

		List<CmsChannel> channels = cmsChannelService.selectByParentId(id);
		String trees = JsTreeUtils.channelJsonztree(channels);
		response.setContentType("application/json");
		PrintWriter out = null;

		try {
			out = response.getWriter();
			out.print(trees);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	/** 判断栏目来源是否已关联 */
	@RequestMapping(value = "fromIsExist")
	@ResponseBody
	public boolean fromIsExist(int channelFrom, Integer channelId) {
		boolean isExist = true;
		if(channelFrom !=Const.CMS_FROM_TYPE_AUTO){
			if(channelId == null){
				Integer id = cmsChannelService.getChannelId(channelFrom);
				if(id != null){
					isExist =true;
				}else{
					isExist =false;
				}
			}else{
				isExist = cmsChannelService.fromIsExist(channelFrom, channelId);
			}
		}else{
			isExist = false;
		}		
		return isExist;
	}
}

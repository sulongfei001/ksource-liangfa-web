package com.ksource.syscontext;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ksource.common.docconvert.OpenOfficePDFConverter;
import com.ksource.common.util.DictionaryManager;
import com.ksource.liangfa.domain.SystemInfo;
import com.ksource.liangfa.domain.SystemInfoExample;
import com.ksource.liangfa.mapper.DictionaryMapper;
import com.ksource.liangfa.mapper.DtGroupMapper;
import com.ksource.liangfa.mapper.SystemInfoMapper;
import com.ksource.license.LicenseInfo;

/**
 * 系统初始化，服务器启动时运行
 * 
 * @author gengzi
 */
public class SystemInit extends HttpServlet {

	private static final long serialVersionUID = 7690685052350540376L;
	static final Logger logger = LogManager.getLogger(SystemInit.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			SpringContext.setApplicationContext(WebApplicationContextUtils
					.getRequiredWebApplicationContext(config
							.getServletContext()));
			SystemContext.setServletContext(config.getServletContext());
			//初始化字典表
			logger.info("---初始化字典表---");
			DictionaryMapper dictionaryDAO = (DictionaryMapper)SpringContext.getApplicationContext().getBean(DictionaryMapper.class);
			DtGroupMapper dtGroupDAO = (DtGroupMapper)SpringContext.getApplicationContext().getBean(DtGroupMapper.class);
			DictionaryManager.init(dictionaryDAO,dtGroupDAO);
			//初始化系统基础信息
			logger.info("---初始化系统基础信息---");
			SystemInfoMapper systemInfoMapper = SpringContext.getApplicationContext().getBean(SystemInfoMapper.class);
			List<SystemInfo> systemInfos = systemInfoMapper.selectByExample(new SystemInfoExample());
			if(CollectionUtils.isEmpty(systemInfos)){
				throw new Exception("系统基础信息（SYSTEM_INFO表）未初始化！");
			}else if(systemInfos.size()>1){
				throw new Exception("系统基础信息（SYSTEM_INFO表）数据错误，只允许一条记录存在！");
			}
			SystemContext.setSystemInfo(systemInfos.get(0));
			//读取系统授权信息
			logger.info("读取系统授权信息："+com.ksource.license.Const.lrcPath.toURI().getPath());
			LicenseInfo.getLicenseInfo();
			//开始openOffice文档转换服务
			//OpenOfficePDFConverter.stopService();
			//OpenOfficePDFConverter.startService();
			//开启短信服务
			//logger.info("---开启短信服务---");
			//SmsServiceFactory.startService();
		} catch (Exception e) {
			SpringContext.closeContext();
			logger.error("启动失败："+e.getMessage());
			throw new ServletException(e);
		}
		logger.info("业务系统启动成功！");
	}
}
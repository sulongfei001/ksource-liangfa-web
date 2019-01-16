/**
 * 
 */
package com.ksource.liangfa.web.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksource.exception.BusinessException;
import com.ksource.liangfa.service.system.AttachmentService;

/**
 * @author XT
 * 2012-12-20
 */
@Controller
@RequestMapping(value="system/attachment")
public class AttachmentController {
	@Autowired
	AttachmentService attachmentService;
	
	/**
	 * 备份附件
	 * @param backupPath
	 * @param request
	 * @return
	 * @author XT
	 */
	@RequestMapping(value="backup")
	@ResponseBody
	public Boolean backup(String backupPath,HttpServletRequest request){
		return attachmentService.backupAttachment(backupPath);
	}
	
	@RequestMapping(value="main")
	public String main(Map<String, Object> model){
		Properties properties=new Properties();
		String configFilePath = this.getClass().getResource("/system_config.properties").getPath();
		try {
			properties.load(new FileInputStream(configFilePath));
		} catch (Exception e) {
			throw new BusinessException("查询备份路径时出错！");
		}
		String path=properties.getProperty("filesBackup.path");
		model.put("bakupPath", path);
		return "system/attachment/backup";
	}
	
	
	/**
	 * 检查路径是否存在
	 * @param backupPth
	 * @return
	 * @author XT
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@RequestMapping(value="check_folder")
	@ResponseBody
	public Boolean checkFolder(String backupPath) throws IOException{
		String drive= StringUtils.substringBefore(backupPath, ":");
		File driveFile=new File(drive+":");
		File file=new File(backupPath);
		Boolean exists = driveFile.exists();
		if (exists) {
			if (!file.exists()) {
				//如果不存在，创建文件夹
				file.mkdir();
			}
			//写入system_config.properties文件中
			String configFilePath = this.getClass().getResource("/system_config.properties").getPath();
			Properties properties=new Properties();
			try {
				properties.load(new FileInputStream(configFilePath));
				properties.setProperty("filesBackup.path", backupPath);
				FileOutputStream fos=new FileOutputStream(configFilePath);
				properties.store(fos, "attachment backup path");
			} catch (Exception e) {
				throw new BusinessException("缺少系统配置文件system_config，请联系管理员！");
			}
			return true;
		}
		return exists;
	}
}

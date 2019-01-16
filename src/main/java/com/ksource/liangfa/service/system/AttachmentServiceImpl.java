/**
 * 
 */
package com.ksource.liangfa.service.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ksource.common.upload.UpLoadUtil;
import com.ksource.common.upload.bean.UploadBean;
import com.ksource.common.util.BackupFiles;
import com.ksource.common.util.ZipUtil;
import com.ksource.syscontext.SystemContext;

/**
 * @author XT
 * 2012-12-21
 */
@Component("attachmentService")
public class AttachmentServiceImpl implements AttachmentService{
	
	static final Logger logger=LogManager.getLogger(AttachmentServiceImpl.class);
	
	public void backupAttachment_scrap(){
		String contextPath=SystemContext.getRealPath();
		String defaultBackupPath=contextPath+"\\uploadbackup";
		String defaultAttachMentPath=contextPath+"\\resources\\uploadfile";
		BackupFiles backupFiles=new BackupFiles(defaultAttachMentPath, defaultBackupPath);
		logger.info("开始自动备份"+defaultAttachMentPath);
		boolean success=backupFiles.backupFiles();
		if (!success) {
			logger.info("自动备份失败");
		}
		logger.info("成功自动备份到"+defaultBackupPath);
	}
	
	public Boolean backupAttachment(String backupPath){
		//补后面的斜扛
		backupPath=StringUtils.endsWith(backupPath, "\\")?backupPath:backupPath+"\\";
		//如果不存在备份文件夹，就创建一个
		File file=new File(backupPath);
		if (!file.exists()) {
			file.mkdir();
		}
		boolean backupResult=false;
		//获取附件存储路径
	    UploadBean uploadBean = UpLoadUtil.getUpLoadBeanFromFile("upload_resource.properties");
	    String defaultAttachMentPath=uploadBean.getUploadDir();
		//BackupFiles backupFiles=new BackupFiles(defaultAttachMentPath, defaultBackupPath);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		String zipFullName = backupPath+sdf.format(new Date())+".zip";
		logger.info("开始自动备份"+defaultAttachMentPath);
		try {
			ZipUtil.zip(defaultAttachMentPath, zipFullName);
			backupResult=true;
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("备份失败！");
		}
		logger.info("成功自动备份到"+zipFullName);
		try{
			//删除历史附件（只保留最近的7个的附件）
			logger.info("开始删除历史备份附件");
			File deleteFolder = new File(backupPath);
			if(deleteFolder.isDirectory()){
				File[] fileAry = deleteFolder.listFiles();
				if(fileAry.length > 7){
					//按最后修改时间排序
					Arrays.sort(fileAry, new Comparator<File>() {
						@Override
						public int compare(File f1, File f2) {
							long diff = f1.lastModified() - f2.lastModified();
							if (diff > 0)
								return 1;
							else if (diff == 0)
								return 0;
							else
								return -1;
						}
						public boolean equals(Object obj) {
							return true;
						}
					});
					for(int i = 0 ;i<fileAry.length-7 ;i++){
						fileAry[i].delete();
					}
				}
			}
			logger.info("删除历史备份附件");
		}catch(Exception e){
			logger.info("删除备份历史附件失败");
		}		
		return backupResult;
	}
	
	public void autoBackup(){
		Properties properties=new Properties();
		String configFilePath = this.getClass().getResource("/system_config.properties").getPath();
		try {
			properties.load(new FileInputStream(configFilePath));
		} catch (Exception e) {
			logger.error("加载备份路径失败！"+e.getMessage());
		}
		String path=properties.getProperty("filesBackup.path");
		backupAttachment(path);
	}
}

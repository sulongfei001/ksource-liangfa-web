package com.ksource.common.upload;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
  * 此类为 上传文件类.
  * 所有上传的文件都可以用这个类,他们会保存在
  * upload_resource.properties文件配置的文件夹内.(不支持绝对路径)
  * 如果需要一些特殊的初始化步骤和验证,可以像此类一样继承UploadFile,
  * 并重写initParam()和validate()方法.
  * @author zxl :)
  * @version 1.0   
  * date   2011-12-30
  * time   下午1:48:14
  */ 
public class UploadImp extends UpLoadFile{

	private String fileExt;
	@Override
	protected void initParam() {
		
		// 加载配置文件中的参数
		initUploadBean("upload_imp.properties");
		
		//截取扩展名
        fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        
        //修改文件名
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        fileName = df.format(new Date()) + "_" + new Random().nextInt(1000) +
            "." + fileExt;
	}

	@Override
	public MesObject validate() {
		return mesObject;
	}

}

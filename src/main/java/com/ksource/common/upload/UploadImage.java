package com.ksource.common.upload;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;


/**
 * 此类为 上传图片类
 *
 * @author zxl :)
 * @version 1.0
 * date   2011-8-6
 * time   上午11:27:23
 */
public class UploadImage extends UpLoadFile {
    private String[] fileTypes;
    private String fileExt;

    @Override
    protected void initParam() {
    	//加载配置文件中的参数
    	initUploadBean("upload_image.properties");
        
        //检查扩展名
        fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        //修改文件名
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        fileName = df.format(new Date()) + "_" + new Random().nextInt(1000) +
            "." + fileExt;
        //文件格式
        fileTypes = uploadBean.getFileTypes();
    }

    @Override
    public MesObject validate() {
        if (!multipartFile.getContentType().toLowerCase().startsWith("image")) {
            mesObject.settingError("请选择图片文件。");

            return mesObject;
        }

        if (!Arrays.<String>asList(fileTypes).contains(fileExt)) {
            mesObject.settingError(
                "不支持的图片格式，图片格式必须是\"gif\", \"jpg\", \"jpeg\", \"png\", \"bmp\"中的一种。");

            return mesObject;
        }

        return mesObject;
    }
}

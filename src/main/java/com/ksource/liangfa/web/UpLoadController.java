package com.ksource.liangfa.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadImage;
import com.ksource.common.upload.UploadResource;


/**
 * 此类为   上传文件类
 *
 * @author zxl :)
 * @version 1.0
 * date   2011-8-3
 * time   下午03:24:54
 */
@Controller
@RequestMapping("/upload")
public class UpLoadController {
    @RequestMapping(value = "/image")
    public void image(
        @RequestParam(value = "imgFile", required = false)
    MultipartFile imgFile,
        HttpServletResponse response) throws Exception {
    	UpLoadContext upLoad = new UpLoadContext(new UploadImage());
    	upLoad.uploadFileApp(imgFile,response);
    }
    //上传共享资源文件
    @RequestMapping(value="/resource")
    public void resource(
    		@RequestParam(value="resourceFile",required=false)MultipartFile file,
    		HttpServletResponse response)throws Exception{
    	UpLoadContext upLoad=new UpLoadContext(new UploadResource());
    	upLoad.uploadFile(file, response);
    }
}

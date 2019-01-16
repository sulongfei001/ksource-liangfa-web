package com.ksource.common.upload;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 此类为 论坛图标上传类。与UploadImage不同的是，此类验证了上传图片的尺寸。
 * 尺寸要求在400*400;
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2012-2-8
 * time   下午4:46:15
 */
public class UploadForumIcon extends UploadImage {
	private static final int MAX_WIDTH = 400;
	private static final int MAX_HEIGHT = 400;
	@Override
	public MesObject validate() {
		MesObject mesObject =super.validate();
		if(mesObject.getError().equals(MesObject.ERROR))return mesObject;
		BufferedImage image;
		try {
			image = ImageIO.read(multipartFile.getInputStream());
			if(image.getWidth()>MAX_WIDTH||image.getHeight()>MAX_HEIGHT){
				mesObject.settingError("图片的尺寸过大，请上传宽:400px,高:400px范围内的图片。");
			}
			return mesObject;
		} catch (IOException e) {
			throw new UploadException();
		}
	}
}

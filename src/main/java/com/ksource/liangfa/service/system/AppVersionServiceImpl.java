package com.ksource.liangfa.service.system;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.AppVersion;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.mapper.AppVersionMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.syscontext.Const;

@Service
public class AppVersionServiceImpl implements AppVersionService {
    
    private static final Logger logger = LoggerFactory.getLogger(AppVersionServiceImpl.class);
    
    @Autowired
    private AppVersionMapper appVersionMapper;
    @Autowired
    private SystemDAO systemDAO;
    @Autowired
    private PublishInfoFileMapper publishInfoFileMapper;
    
    @Override
    public List<AppVersion> selectAll(AppVersion appVersion) {
        return appVersionMapper.selectAll(appVersion);
    }

    @Override
    public void selectByPrimaryKey(Integer versionId,ModelAndView mv) {
        AppVersion appVersion = appVersionMapper.selectByPrimaryKey(versionId);
        PublishInfoFile publishInfoFile=new PublishInfoFile();
        publishInfoFile.setFileType(Const.TABLE_APP_VERSION);
        publishInfoFile.setInfoId(versionId);
        List<PublishInfoFile> publishInfoFiles =publishInfoFileMapper.getFileByInfoId(publishInfoFile);
        mv.addObject("apkFile",publishInfoFiles);
        mv.addObject("appVersion", appVersion);
    }

    @Override
    public ServiceResponse add(AppVersion appVersion,MultipartHttpServletRequest attachmentFile) {
        ServiceResponse serviceResponse = new ServiceResponse(true, "添加成功！");
        Integer versionId = systemDAO.getSeqNextVal(Const.TABLE_APP_VERSION);
        appVersion.setVersionId(versionId);
        try{
            appVersionMapper.insert(appVersion);
            List<MultipartFile> files = attachmentFile.getFiles("file");
            PublishInfoFile publishInfoFile = null;
            for (int i = 0; i < files.size(); i++) {
                MultipartFile mFile = files.get(i);
                if (mFile != null && !mFile.isEmpty()) {
                    UpLoadContext upLoad = new UpLoadContext(new UploadResource());
                    String url = upLoad.uploadFile(mFile, null);
                    String fileName = mFile.getOriginalFilename();
                    publishInfoFile = new PublishInfoFile();
                    publishInfoFile.setFileId(systemDAO.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE));
                    publishInfoFile.setInfoId(versionId);
                    publishInfoFile.setFileName(fileName);
                    publishInfoFile.setFilePath(url);
                    publishInfoFile.setFileType(Const.TABLE_APP_VERSION);
                    publishInfoFileMapper.insert(publishInfoFile);
                }
            }
        }catch(Exception e){
            logger.error("添加失败", e.getMessage());
            serviceResponse.setingError("添加失败，请联系管理员");
        }
        return serviceResponse;
    }

    @Override
    public ServiceResponse update(AppVersion appVersion) {
        ServiceResponse serviceResponse = new ServiceResponse(true, "修改成功！");
        try{
            appVersionMapper.updateByPrimaryKeySelective(appVersion);
        }catch(Exception e){
            logger.error("修改失败", e.getMessage());
            serviceResponse.setingError("修改失败，请联系管理员");
        }
        return serviceResponse;
    }

    @Override
    public boolean delete(Integer versionId) {
        int deleteCount = appVersionMapper.deleteByPrimaryKey(versionId);
        return deleteCount > 0;
    }

    @Override
    public AppVersion selectNewVersion() {
        return appVersionMapper.selectNewVersion();
    }
}

package com.ksource.liangfa.service.system;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.AppVersion;

public interface AppVersionService {

    List<AppVersion> selectAll(AppVersion appVersion);

    void selectByPrimaryKey(Integer versionId, ModelAndView mv);

    ServiceResponse add(AppVersion appVersion, MultipartHttpServletRequest attachmentFile);

    ServiceResponse update(AppVersion appVersion);

    boolean delete(Integer versionId);

    AppVersion selectNewVersion();
    
}

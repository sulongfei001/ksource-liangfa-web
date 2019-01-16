package com.ksource.liangfa.web.system;

import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.DistrictExample;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.OrganiseExample;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.DistrictService;
import com.ksource.license.LicenseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;


/**
 * 系统授权
 *
 * @author gengzi
 *         date   2012-5-11
 */
@Controller
@RequestMapping("/system/license")
public class LicenseController {

    @Autowired
    private DistrictService districtService;
    @Autowired
    private OrganiseMapper organiseMapper;
    @Autowired
    private MybatisAutoMapperService mybatisAutoMapperService;

    @RequestMapping
    public ModelAndView main() {
        ModelAndView view = new ModelAndView("system/license/main");
        //系统授权验证
        LicenseInfo licenseInfo = null;
        try {
            licenseInfo = LicenseInfo.getLicenseInfo();
            if (licenseInfo == null) {
                view.setViewName("system/license/licError");
                view.addObject("errorMsg", "未授权！");
                return view;
            }
            DistrictExample example = new DistrictExample();
            example.createCriteria().andUpDistrictCodeIsNull();
            List<District> districtList = mybatisAutoMapperService.selectByExample(DistrictMapper.class, example, District.class);
            if (districtList.size() != 1) {
                view.setViewName("system/license/licError");
                view.addObject("errorMsg", "未授权！");
                return view;
            }
            //String error = licenseInfo.check(districtList.get(0).getDistrictCode(), districtList.get(0).getDistrictName());
            LicenseInfo.LicError licError = licenseInfo.check();
            if (licError == null || licError == LicenseInfo.LicError.LicError) {
            //if(error.length()>0){
                view.setViewName("system/license/licError");
                view.addObject("errorMsg", "未授权！");
                return view;
            }
        } catch (Exception e) {
            view.setViewName("system/license/licError");
            view.addObject("errorMsg", "未授权！");
            return view;
        }
        //已接入的行政区划
        List<District> districtList = districtService.getUsedXingzheng();
        //已接入的行政执法机关
        OrganiseExample example = new OrganiseExample();
        example.createCriteria().andIsDeptEqualTo(com.ksource.syscontext.Const.STATE_INVALID).andOrgTypeEqualTo(com.ksource.syscontext.Const.ORG_TYPE_XINGZHENG);
        List<Organise> orgList = organiseMapper.selectByExample(example);
        view.addObject("districtList", districtList);
        view.addObject("orgList", orgList);
        view.addObject("licenseInfo", licenseInfo);
        return view;
    }

    //导入授权文件
    @RequestMapping("/importLic")
    public ModelAndView importLic(@RequestParam MultipartFile lic) {
        InputStream inFile;
        FileOutputStream file;
        byte[] input = new byte[10 * 2048];
        File uploadedFile = null;
        String tempFile = com.ksource.license.Const.lrcName + new Date().getTime();
        try {
            uploadedFile = new File(com.ksource.license.Const.lrcPath.toURI().getPath(), tempFile);
            file = new FileOutputStream(uploadedFile);
            inFile = lic.getInputStream();

            int length = inFile.read(input);

            while (length != -1) {
                file.write(input, 0, length);
                length = inFile.read(input);
            }

            file.flush();
            file.close();
            inFile.close();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ModelAndView view = new ModelAndView("system/license/licImportResult");
        //系统授权验证
        LicenseInfo licenseInfo = null;
        File fileForDel = null;//便于删除
        try {
            uploadedFile = null;
            fileForDel = new File(com.ksource.license.Const.lrcPath.toURI().getPath(), tempFile);
            LicenseInfo.deleteLic();
            licenseInfo = LicenseInfo.getLicenseInfo(com.ksource.license.Const.lrcPath.toURI().getPath(), tempFile);
            if (licenseInfo == null) {
                view.addObject("implortResult", 1);//导入失败
                view.addObject("msg", "导入失败！");
                fileForDel.delete();
                licenseInfo = LicenseInfo.getLicenseInfo();
                return view;
            }
            DistrictExample example = new DistrictExample();
           example.createCriteria().andUpDistrictCodeIsNull();
           List<District> districtList = mybatisAutoMapperService.selectByExample(DistrictMapper.class, example, District.class);
           if (districtList.size() != 1) {
               view.addObject("implortResult", 2);//导入失败
               view.addObject("msg", "授权文件校验失败！");
               return view;
           }
           //String error = licenseInfo.check(districtList.get(0).getDistrictCode(), districtList.get(0).getDistrictName());
            LicenseInfo.LicError licError = licenseInfo.check();
            if (licError != LicenseInfo.LicError.Success) {
          // if(error.length()>0){
                view.addObject("implortResult", 2);//授权文件校验失败
                view.addObject("msg", "授权文件校验失败！");
                LicenseInfo.deleteLic();
                fileForDel.delete();
                licenseInfo = LicenseInfo.getLicenseInfo();
            } else {
                //更新授权文件
                File oldLic = new File(com.ksource.license.Const.lrcPath.toURI().getPath(), com.ksource.license.Const.lrcName);
                oldLic.delete();
                fileForDel.renameTo(oldLic);
                view.addObject("implortResult", 3);//授权成功
            }
        } catch (Exception e) {
            LicenseInfo.deleteLic();
            fileForDel.delete();
            licenseInfo = LicenseInfo.getLicenseInfo();
            view.addObject("implortResult", 1);//导入失败
            view.addObject("msg", "导入失败！");
            return view;
        }
        return view;
    }
}

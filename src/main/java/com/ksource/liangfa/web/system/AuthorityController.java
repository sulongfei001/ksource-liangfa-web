package com.ksource.liangfa.web.system;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.jms.MessageProducer;
import com.ksource.common.util.PasswordUtil;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.DistrictExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.AuthorityService;
import com.ksource.liangfa.service.system.DistrictService;
import com.ksource.liangfa.service.system.UserService;
import com.ksource.license.LicenseInfo;
import com.ksource.syscontext.SystemContext;


/**
 * @author zxl :)
 * @version 1.0
 *          date   2011-5-10
 *          time   下午02:18:39
 */
@Controller
@RequestMapping("/system/authority")
public class AuthorityController {
    private static final String OPEN_HOME = "/openhome";
    @Autowired
    AuthorityService authorityService;
    @Autowired
    DistrictService districtService;
    @Autowired
    MybatisAutoMapperService mybatisAutoMapperService;
    @Autowired
    private UserService userService;
    
	@Autowired
	private MessageProducer messageProducer;

    // 非ikey用户登录
    @RequestMapping(value = "login-noikey")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("/login-noikey");
        //系统授权验证
//        LicenseInfo licenseInfo = LicenseInfo.getLicenseInfo();
//        if (licenseInfo == null) {
//            view.setViewName("/licError");
//            view.addObject("errorMsg", "未授权！");
//            return view;
//        } 

//        LicenseInfo.LicError licError = licenseInfo.check();
//        if (licError == null || licError == LicenseInfo.LicError.LicError) {
//        //if(error.length()>0){
//            view.setViewName("/licError");
//            view.addObject("errorMsg", "未授权！");
//            //view.addObject("errorMsg", error);
//            return view;
//        }
//        if (licError.equals(LicenseInfo.LicError.Overdue)) {
//          view.setViewName("/licError");
//          view.addObject("errorMsg", "授权已过期！");
//          return view;
//        }
        return view;
    }
    
    //TODO:校验用户的登录
    @RequestMapping("checkPwdAndUserName")
    public ModelAndView checkPwdAndUserName(HttpServletRequest request){
    	ModelAndView view = new ModelAndView("/login-noikey");
    	String userId = request.getParameter("userId");
        String pwd = request.getParameter("pwd");
        String securityCode = request.getParameter("securityCode");
        String fromView = "checkPwdAndUserName";
        view.addObject("fromView", fromView);
        if (userId == null || pwd == null || securityCode == null
                || userId.isEmpty() || pwd.isEmpty() || securityCode.isEmpty()) {
            view.addObject("error", "用户或密码和校验码不能空,请重试!");
            view.addObject("userId", userId);
            return view;
        }
        //session保存的校验码
        String securityCodeT = "";
        if (request.getSession().getAttribute("securityCode") != null) {
            securityCodeT = request.getSession().getAttribute("securityCode").toString();
        } else {
            return view;
        }
        //执行校验
        User user = userService.loginByAccount(userId);
        if (user == null || user.getUserId().equals("")) {
            view.addObject("error", "用户不存在！");
            view.addObject("userId", userId);
            return view;
        }
        
        //验证是否允许用户登录
        if (user != null && (user.getIsLogin()==null || user.getIsLogin()==0)) {
            view.addObject("error", "系统禁止此用户登录！");
            view.addObject("userId", userId);
            return view;
        }

        ServiceResponse response = authorityService.login(user, securityCodeT, pwd, securityCode);
        if (!response.getResult()) {
            view.addObject("error", response.getMsg());
            view.addObject("userId", userId);
            return view;
        }
        SystemContext.setCurrentUser(request, user);
        view.setViewName(OPEN_HOME);
    	return view;
    }
    //
    // 退出
    @RequestMapping(value = "logout")
    public String logout(HttpSession session) {
        User user = SystemContext.getCurrentUser(session);
        if (user != null) {   //防止重复退出时业务日志拦截失败
            authorityService.logout(user);
        }
        SystemContext.clearCurrentUser(session);
        return "/login-main";
    }

    // 灵购ukey用户登录
    @RequestMapping(value = "login-ukey")
    public ModelAndView login(HttpSession session, String IID_SecureWeb_I_SerialNumber, String IID_SecureWeb_I_UserName,
                              String IID_SecureWeb_I_Random, String IID_SecureWeb_I_MD5Result, String securityCode) {
        ModelAndView view = new ModelAndView("/login-ukey");

        //系统授权验证
        LicenseInfo licenseInfo = LicenseInfo.getLicenseInfo();
        if (licenseInfo == null) {
            view.setViewName("/licError");
            view.addObject("errorMsg", "未授权！");
            return view;
        }
        //String error = licenseInfo.check(districtList.get(0).getDistrictCode(), districtList.get(0).getDistrictName());
        LicenseInfo.LicError licError = licenseInfo.check();
        if (licError == null || licError == LicenseInfo.LicError.LicError) {
        //if(error.length()>0){
            view.setViewName("/licError");
            view.addObject("errorMsg", "未授权！");
            //view.addObject("errorMsg", error);
            return view;
        }

        if (licError.equals(LicenseInfo.LicError.Overdue)) {
            view.setViewName("/licError");
            view.addObject("errorMsg", "授权已过期！");
            return view;
          }
        
        if (StringUtils.isBlank(IID_SecureWeb_I_UserName) || StringUtils.isBlank(IID_SecureWeb_I_MD5Result)
                || StringUtils.isBlank(securityCode)) {
            view.addObject("error", "未读取到u-key设备的数据，请重试！");
            return view;
        }
        //session保存的校验码
        String securityCodeSession = "";
        if (session.getAttribute("securityCode") != null) {
            securityCodeSession = session.getAttribute("securityCode").toString();
        } else {
            return view;
        }
        //执行校验
        User user = userService.loginByAccount(IID_SecureWeb_I_UserName);
        if (user == null || user.getAccount().equals("")) {
            view.addObject("error", "用户不存在,u-key设备未授权！");
            return view;
        }
        //参照例子代码这样写的，no why
        IID_SecureWeb_I_MD5Result = IID_SecureWeb_I_MD5Result.toUpperCase().substring(0, 32);
        ServiceResponse response = authorityService.loginWithUkey(user, IID_SecureWeb_I_SerialNumber, IID_SecureWeb_I_Random, IID_SecureWeb_I_MD5Result, securityCodeSession, securityCode);
        if (!response.getResult()) {
            view.addObject("error", response.getMsg());
            return view;
        }
        SystemContext.setCurrentUser(session, user);
        view.setViewName(OPEN_HOME);
        return view;
    }

    // 修改密码
    @RequestMapping(value = "changePwd")
    public String changePwd(HttpServletRequest request) {
        String path = "redirect:authority.do?method=changePwdUI";
        User user = SystemContext.getCurrentUser(request);
        String oldPwd = user.getPassword();
        String pwd = request.getParameter("oldPwd");
        if (PasswordUtil.verify(pwd, oldPwd)) {
            String newPwd = request.getParameter("password");
            String userId = request.getParameter("userId");
            ServiceResponse info = userService.resetPwd(userId, newPwd);
            if (info.getResult()) {
                path += "&isSuccess=true";
            } else {
                path += "&isSuccess=false";
            }
            // 更新session内的当前用户信息
            user.setPassword(PasswordUtil.encrypt(newPwd));
        } else {
            path += "&isSuccess=wrong";
        }
        return path;
    }

    private Color getRandColor(int fc, int bc) {//给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 生成校验码图片
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/securityCode")
    public void securityCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 在内存中创建图象
        int width = 99, height = 33;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 获取图形上下文
        Graphics2D g = (Graphics2D)image.getGraphics();
        //消除线段的锯齿状边缘
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        //生成随机类
        Random random = new Random();

        // 设定背景色
        g.setColor(new Color(255, 255,255));
        g.fillRect(0, 0, width, height);

        //设定字体
        g.setFont(new Font("Times New Roman", Font.BOLD, 28));
        
        //画边框
        //g.setColor(new Color());
        //g.drawRect(0,0,width-1,height-1);

        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
       g.setColor(new Color(0,0,0));
       for (int i = 0; i < 10; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(30);
            int yl = random.nextInt(20);
            g.drawLine(x, y, x + xl, y + yl);
        }
        // 取随机产生的认证码(4位数字)
        String sRand = "";
        for (int i = 0; i < 4; i++) {
        	int angle=random.nextInt(90)-45;
        	double radian = angle*Math.PI/180;
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            // 将认证码显示到图象中
            g.setColor(new Color(31, 144,223));
            //g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            //调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.rotate(radian, 20 * i + 10, height/2);
            g.drawString(rand, 20 * i + 10, 27);
            //将画笔角度恢复正常
            g.rotate(-radian, 20 * i + 10, height/2);
        }

        HttpSession session = request.getSession();
        // 将认证码存入SESSION
        session.setAttribute("securityCode", sRand);

        // 图象生效
        g.dispose();

        // 输出图象到页面
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    //导入授权文件
    @RequestMapping("/importLic")
    public ModelAndView importLic(@RequestParam MultipartFile lic) {
        InputStream inFile;
        FileOutputStream file;
        byte[] input = new byte[10 * 2048];
        File uploadedFile;
        try {
            uploadedFile = new File(com.ksource.license.Const.lrcPath.toURI().getPath(), com.ksource.license.Const.lrcName);
            file = new FileOutputStream(uploadedFile);
            inFile = lic.getInputStream();

            int length = inFile.read(input);

            while (length != -1) {
                file.write(input, 0, length);
                length = inFile.read(input);
            }

            file.flush();
            file.close();
            LicenseInfo.deleteLic();//删除授权信息
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ModelAndView view = new ModelAndView("/licImportResult");
        //系统授权验证
        LicenseInfo licenseInfo = LicenseInfo.getLicenseInfo();
        if (licenseInfo == null) {
            view.addObject("implortResult", 1);//导入失败
            view.addObject("msg", "导入失败！");
            return view;
        }
        LicenseInfo.LicError licError = licenseInfo.check();
        if (licError != LicenseInfo.LicError.Success) {
        //if(error.length()>0){
            view.addObject("implortResult", 2);//授权文件校验失败
            view.addObject("msg", "授权文件校验失败");
        } else {
            view.addObject("implortResult", 3);//授权成功
        }
        return view;
    }
    
}

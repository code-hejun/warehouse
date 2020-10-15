package com.hejun.sys.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.hejun.sys.common.ActiveUser;
import com.hejun.sys.common.ResultObj;
import com.hejun.sys.common.WebUtils;
import com.hejun.sys.entity.Loginfo;
import com.hejun.sys.service.ILoginfoService;
import com.hejun.sys.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/login")
public class LoginController {


    @Autowired
    private ILoginfoService loginfoService;

    @RequestMapping("login")
    public ResultObj login(UserVo userVo,HttpSession httpSession){
        String code = userVo.getCode();
        String sessionCode = (String) httpSession.getAttribute("code");
        if (null!=code && sessionCode.equals(code)){
            Subject subject = SecurityUtils.getSubject();
            AuthenticationToken authenticationToken = new UsernamePasswordToken(userVo.getLoginname(),userVo.getPwd());
            try{
                //对用户进行认证登陆
                subject.login(authenticationToken);
                //通过subject获取以认证活动的user
                ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
                //将user存储到session中
                WebUtils.getSession().setAttribute("user",activeUser.getUser());
                //记录登陆日志
                Loginfo loginfo = new Loginfo();
                loginfo.setLoginname(activeUser.getUser().getName()+"-"+activeUser.getUser().getLoginname());
                loginfo.setLoginip(WebUtils.getRequest().getRemoteAddr());
                loginfo.setLogintime(new Date());
                loginfoService.save(loginfo);

                return ResultObj.LOGIN_SUCCESS;
            }catch (AuthenticationException e){
                e.printStackTrace();
                return ResultObj.LOGIN_ERROR_PASS;
            }

        }else {
            return  ResultObj.LOGIN_ERROR_CODE;
        }

    }



    /**
     * 得到登陆验证码
     * @param response
     * @param session
     * @throws IOException
     */
    @RequestMapping("getCode")
    public void getCode(HttpServletResponse response, HttpSession session) throws IOException {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(116, 36,4,5);
        session.setAttribute("code",lineCaptcha.getCode());
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            lineCaptcha.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

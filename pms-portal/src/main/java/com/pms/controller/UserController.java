package com.pms.controller;

import com.pms.pojo.PmsUser;
import com.pms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.PmsResult;
import utils.ExceptionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *     
 *   * @ProjectName:    pms
 *   * @Package:        com.pms.controller
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2018/3/10 10:21
 *   *
 **/
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    @ResponseBody
    public PmsResult registerUser(PmsUser pmsUser) {
        try {
            PmsResult pmsResult = userService.registerUser(pmsUser);
            return pmsResult;
        }catch (Exception e){
            return PmsResult.build(400, ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public PmsResult login(String username, String password, HttpServletRequest request, HttpServletResponse response){
        try{
            PmsResult pmsResult=userService.login(username,password,request,response);
            return pmsResult;
        }catch (Exception e){
            return PmsResult.build(400,ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/user/showLoginPage")
    public String showLoginPage(){
        return "";
    }

}

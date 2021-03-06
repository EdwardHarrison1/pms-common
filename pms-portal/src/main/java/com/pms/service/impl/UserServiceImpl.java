package com.pms.service.impl;

import com.pms.mapper.PmsUserMapper;
import com.pms.pojo.PmsUser;
import com.pms.pojo.PmsUserExample;
import com.pms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import pojo.PmsResult;
import utils.CookieUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *     
 *   * @ProjectName:    pms
 *   * @Package:        com.pms.service.impl
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2018/3/10 10:29
 *   *
 **/
@Service
public class UserServiceImpl implements UserService {
    @Value("${USER_COOKIE}")
    private String USER_COOKIE;


    @Autowired
    private PmsUserMapper pmsUserMapper;

    @Override
    public PmsResult registerUser(PmsUser pmsUser) {
        Date date=new Date();
        pmsUser.setCreated(date);
        pmsUser.setUpdated(date);
        //md5加密
        pmsUser.setPassword(DigestUtils.md5DigestAsHex(pmsUser.getPassword().getBytes()));
        pmsUserMapper.insert(pmsUser);
        return PmsResult.ok();
    }

    @Override
    public PmsResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        PmsUserExample example=new PmsUserExample();
        PmsUserExample.Criteria criteria=example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<PmsUser> list = pmsUserMapper.selectByExample(example);
        if (list.size()==0||list==null){
            return PmsResult.build(400,"用户名或密码错误");
        }
        PmsUser pmsUser = list.get(0);
        if (!pmsUser.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
            return PmsResult.build(400,"用户名或密码错误");
        }
        CookieUtils.setCookie(request,response,USER_COOKIE,username);
        return PmsResult.ok(list.get(0));
    }
}

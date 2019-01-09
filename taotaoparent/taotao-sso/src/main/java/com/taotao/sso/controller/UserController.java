package com.taotao.sso.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import com.taotao.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public Object checkData(@PathVariable String param,@PathVariable Integer type, String callback){

        TaotaoResult taotaoResult = null;

        if (TextUtils.isBlank(param)){
            taotaoResult = TaotaoResult.build(400,"检验内容不能为空");
        }

        if (type == null){
            taotaoResult = TaotaoResult.build(400,"检验内容不能为空");
        }

        if (type != 1 && type != 2 && type != 3){
            taotaoResult = TaotaoResult.build(400,"检验内容不能为空");
        }

        if (null != taotaoResult){
            if (null != callback){
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(taotaoResult);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            }else{
                return taotaoResult;
            }
        }

        try{
            taotaoResult = userService.checkData(param, type);
        }catch (Exception e){
            e.printStackTrace();
            taotaoResult =  TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));

        }

        if (null != callback){
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(taotaoResult);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }else{
            return taotaoResult;
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult regist(String username, String password, String phone,String email){
        if (TextUtils.isBlank(username) || TextUtils.isBlank(password)){
            return TaotaoResult.ok("用户名或密码不能为空");
        }
        TbUser tbUser = new TbUser();
        tbUser.setUsername(username);
        tbUser.setPassword(password);
        tbUser.setPhone(phone);
        tbUser.setEmail(email);
        try{
            TaotaoResult result = userService.createUser(tbUser);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response){
        if (TextUtils.isBlank(username) || TextUtils.isBlank(password)){
            return TaotaoResult.ok("用户名或密码不能为空");
        }
        try{
            TaotaoResult result = userService.login(username,password,request,response);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token,String callback){
        TaotaoResult result = null;
        try {
            result = userService.getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

        //判断是否为jsonp调用
        if (StringUtils.isBlank(callback)) {
            return result;
        } else {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }

    }


}

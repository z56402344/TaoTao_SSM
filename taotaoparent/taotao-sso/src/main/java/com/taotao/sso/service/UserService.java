package com.taotao.sso.service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    TaotaoResult checkData(String content,int type);

    TaotaoResult createUser(TbUser tbUser);

    TaotaoResult login(String username, String pwd, HttpServletRequest request, HttpServletResponse response);

    TaotaoResult getUserByToken(String token);

}

package com.taotao.portal.service.impl;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.portal.Common;
import com.taotao.portal.service.UserService;
import com.taotao.utils.HttpClientUtil;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService ,Common {

    @Override
    public TbUser getUserByToken(String token) {
        String json = HttpClientUtil.doGet(M_SSO_USER_TOKEN+token);
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbUser.class);
        if (taotaoResult.getStatus() ==200){
            TbUser tbUser = (TbUser) taotaoResult.getData();
            return tbUser;
        }
        return null;
    }
}

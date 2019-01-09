package com.taotao.sso.service.impl;

import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.core.Common;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.UserService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService ,Common {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public TaotaoResult checkData(String content, int type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //type为类型，可选参数1、2、3分别代表username、phone、email
        if (type == 1){
            criteria.andUsernameEqualTo(content);
        }else if (type == 2){
            criteria.andPhoneEqualTo(content);
        }else if (type == 3){
            criteria.andEmailEqualTo(content);
        }else if (type == 4){

        }
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.size() == 0){
            return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }

    @Override
    public TaotaoResult createUser(TbUser tbUser) {
        tbUser.setUpdated(new Date());
        tbUser.setCreated(new Date());
        String md5pwd = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
        tbUser.setPassword(md5pwd);
        userMapper.insert(tbUser);
        return TaotaoResult.build(200,"注册成功");
    }

    @Override
    public TaotaoResult login(String username, String pwd, HttpServletRequest request, HttpServletResponse response) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.size() == 0){
            return TaotaoResult.build(400,"用户名不存在");
        }
        TbUser tbUser = list.get(0);
        String equalpwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
        if (!tbUser.getPassword().equals(equalpwd)){
            return TaotaoResult.build(400,"密码不正确");
        }
        tbUser.setPassword(null);
        String token = UUID.randomUUID().toString();
        jedisClient.set(REDIS_USER_SESSION_KEY+":"+token,JsonUtils.objectToJson(tbUser));
        //TODO 设置session的过期时间 打开后报错 说redis poll 未开启，这个问题是因为redis没有正确关闭造成的
        jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, REDIS_SESSION_EXPIRE);

        CookieUtils.deleteCookie(request,response,TT_TOKEN);
        //添加cookies
        CookieUtils.setCookie(request,response,TT_TOKEN,token);
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
        if (TextUtils.isBlank(json)){
            return TaotaoResult.build(400,"请重新登录");
        }
        //更新过期时间
        jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, REDIS_SESSION_EXPIRE);
        return TaotaoResult.ok(json);
    }


}

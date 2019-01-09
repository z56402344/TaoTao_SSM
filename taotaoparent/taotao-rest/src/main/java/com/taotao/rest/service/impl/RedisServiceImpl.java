package com.taotao.rest.service.impl;

import com.taotao.pojo.TaotaoResult;
import com.taotao.rest.Ikey;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService,Ikey {

    @Autowired
    private JedisClient jedisClient;

    @Override
    public TaotaoResult syncContent(long contentCid) {
        try{
            jedisClient.hdel(INDEX_CONTENT_REDIS_KEY,contentCid+"");
        }catch (Exception e){
           e.printStackTrace();
        }
        return TaotaoResult.ok();
    }
}

package com.taotao.rest.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.Ikey;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService ,Ikey {

    @Autowired
    TbContentMapper tbContentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public List<TbContent> getContentList(long contentId) {
        //从缓存中取内容
        String result = null;
        try{
            result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY,contentId+"");
            if (!StringUtils.isEmpty(result)){
                List<TbContent> tbContents = JsonUtils.jsonToList(result, TbContent.class);
                return tbContents ;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //根据内容分类id查询内容列表
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(contentId);
        //执行查询
        List<TbContent> list = tbContentMapper.selectByExample(example);

        //向缓存中添加数据
        try{
            //把list转换成字符串
            String jsonStr = JsonUtils.objectToJson(list);
            jedisClient.hset(INDEX_CONTENT_REDIS_KEY,contentId+"",jsonStr);
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }
}

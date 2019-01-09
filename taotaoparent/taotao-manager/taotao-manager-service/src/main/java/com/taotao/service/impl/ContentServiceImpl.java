package com.taotao.service.impl;

import com.taotao.Common;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContenService;
import com.taotao.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ContentServiceImpl implements ContenService ,Common {

    @Autowired
    private TbContentMapper tbContentMapper;

    @Override
    public TaotaoResult insertContent(TbContent tbContent) {
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        tbContentMapper.insert(tbContent);
        //添加缓存同步逻辑
        try{
            HttpClientUtil.doGet(BASE_URL + CONTENT_URL + tbContent.getCategoryId());
        }catch (Exception e){
            e.printStackTrace();
        }
        return TaotaoResult.ok();
    }
}

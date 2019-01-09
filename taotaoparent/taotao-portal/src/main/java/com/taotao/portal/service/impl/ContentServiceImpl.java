package com.taotao.portal.service.impl;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContentServiceImpl implements ContentService {

//    @Value("${REST_BASE_URL}")
    public String REST_BASE_URL = "http://localhost:8081/rest";

//    @Value("${REST_INDEX_AD_URL}")
    public String REST_INDEX_AD_URL = "/content/list/89";


    @Override
    public String getContentList() {
        //调用服务层服务
        String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
        try{
            //把字符串转换成TaoTaoResult
            TaotaoResult taotaoResult = TaotaoResult.formatToList(result, TbContent.class);
            List<TbContent> list = (List<TbContent>) taotaoResult.getData();
            List<Map> resultMap = new ArrayList<>();
            TbContent tbContent = new TbContent();
            tbContent.setPic("http://img.zcool.cn/community/01f9ea56e282836ac72531cbe0233b.jpg@2o.jpg");
            tbContent.setPic2("http://pic33.photophoto.cn/20141022/0019032438899352_b.jpg");
            list.add(tbContent);
            for (int i = 0; i < list.size(); i++) {
                tbContent = list.get(i);
                Map<String,Object> map = new HashMap<>();
                map.put("src",tbContent.getPic());
                map.put("height",240);
                map.put("width",670);
                map.put("srcB",tbContent.getPic2());
                map.put("heightB",550);
                map.put("widthB",670);
                map.put("href",tbContent.getUrl());
                map.put("alt",tbContent.getSubTitle());
                resultMap.add(map);
            }
            return JsonUtils.objectToJson(resultMap);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}

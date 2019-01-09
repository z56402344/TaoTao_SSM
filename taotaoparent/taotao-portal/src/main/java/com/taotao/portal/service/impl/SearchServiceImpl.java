package com.taotao.portal.service.impl;

import com.taotao.pojo.TaotaoResult;
import com.taotao.portal.Common;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import com.taotao.utils.HttpClientUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SearchServiceImpl implements SearchService ,Common {


    @Override
    public SearchResult search(String queryString, int page) {
        HashMap<String,String> map = new HashMap<>();
        map.put("q",queryString);
        map.put("page",page+"");
        String json = HttpClientUtil.doGet(BASE_SEARCH_URL, map);
        try{
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, SearchResult.class);
            if ( taotaoResult.getStatus() == 200){
                SearchResult result = (SearchResult) taotaoResult.getData();
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}

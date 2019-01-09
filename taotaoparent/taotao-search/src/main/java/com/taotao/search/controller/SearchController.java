package com.taotao.search.controller;

import com.alibaba.druid.util.StringUtils;
import com.taotao.pojo.TaotaoResult;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import com.taotao.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;


    @RequestMapping(value="/query",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult search(@RequestParam("q")String queryString,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "60") Integer rows){
        if (StringUtils.isEmpty(queryString)){
            return TaotaoResult.build(400,"查询条件不能为空");
        }

        SearchResult result =   null;
        try{
            queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
            result  = searchService.search(queryString, page, rows);
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }

        return TaotaoResult.ok(result);
    }


}

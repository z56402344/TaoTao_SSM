package com.taotao.rest.controller;

import com.sun.tools.javac.main.Main;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.rest.service.ContentService;
import com.taotao.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    ContentService contentService;

    @RequestMapping("/list/{contentCategoryId}")
    @ResponseBody
    public TaotaoResult getContentList(@PathVariable long contentCategoryId){
        try{
            List<TbContent> contentList = contentService.getContentList(contentCategoryId);
            return TaotaoResult.ok(contentList);
        }catch (Throwable t){
            t.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(t));
        }
    }
}

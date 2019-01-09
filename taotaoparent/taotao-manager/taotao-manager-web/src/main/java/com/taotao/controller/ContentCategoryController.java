package com.taotao.controller;

import com.taotao.pojo.EUTreeNode;
import com.taotao.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    ContentCategoryService contentCategoryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EUTreeNode> getContentCatList(@RequestParam(value="id", defaultValue="0")long parentId){
        return contentCategoryService.getCategoryList(parentId);
    }

    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult insertCatInfo(long parentId, String name){
        return contentCategoryService.insertCategoryNode(parentId,name);
    }

    @RequestMapping("/delete/")
    @ResponseBody
    public TaotaoResult deleteCategory(long parentId,long id){
        return contentCategoryService.deleteCategoryNode(parentId,id);
    }


    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateCategory(long id, String name){
        return contentCategoryService.updateCategoryNode(id,name);
    }
}

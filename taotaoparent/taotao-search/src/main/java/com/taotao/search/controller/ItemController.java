package com.taotao.search.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.search.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manager")
public class ItemController {

    @Autowired
    private ItemService itemService;


    @RequestMapping("/importall")
    @ResponseBody
    public TaotaoResult importAllItem(){
        return itemService.importAllItem();
    }
}

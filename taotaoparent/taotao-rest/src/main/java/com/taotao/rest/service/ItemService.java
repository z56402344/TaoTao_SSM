package com.taotao.rest.service;

import com.taotao.pojo.TaotaoResult;

public interface ItemService {

    TaotaoResult getItemBaseInfo(long itemId);

    TaotaoResult getItemDescInfo(long itemId);

    TaotaoResult getItemParam(long itemId);
}

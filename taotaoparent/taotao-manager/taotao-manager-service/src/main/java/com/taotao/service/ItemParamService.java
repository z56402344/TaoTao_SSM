package com.taotao.service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

public interface ItemParamService {
    TaotaoResult getItemParamByCid(long id);
    TaotaoResult insertItemParam(TbItemParam tbItemParam);
}

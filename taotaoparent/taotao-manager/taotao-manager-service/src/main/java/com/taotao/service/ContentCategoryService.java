package com.taotao.service;

import com.taotao.pojo.EUTreeNode;
import com.taotao.pojo.TaotaoResult;

import java.util.List;

public interface ContentCategoryService {

    List<EUTreeNode> getCategoryList(long parentId);

    TaotaoResult insertCategoryNode(long parentId, String name);

    TaotaoResult deleteCategoryNode(long parentId, long id);

    TaotaoResult updateCategoryNode(long id,String name);
}

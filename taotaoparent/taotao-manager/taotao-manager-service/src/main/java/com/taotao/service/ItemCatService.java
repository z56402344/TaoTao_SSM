package com.taotao.service;

import com.taotao.pojo.TreeNode;

import java.util.List;

public interface ItemCatService {

    List<TreeNode> getItemCatList(long parentId);
}

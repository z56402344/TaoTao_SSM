package com.taotao.service.impl;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TreeNode;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public List<TreeNode> getItemCatList(long parentId) {
        //创建条件
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);

        //根据条件查询
        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(tbItemCatExample);

        //把列表转成treeNode list
        ArrayList<TreeNode> treeNodes = new ArrayList<>();
        for (TbItemCat itemcat: tbItemCats) {
            TreeNode treeNode = new TreeNode(itemcat.getId(), itemcat.getName(), itemcat.getIsParent() ? "closed" : "open");
            treeNodes.add(treeNode);
        }
        return treeNodes;
    }
}

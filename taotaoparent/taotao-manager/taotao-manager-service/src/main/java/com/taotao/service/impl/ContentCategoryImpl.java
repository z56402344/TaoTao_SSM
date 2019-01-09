package com.taotao.service.impl;

import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.EUTreeNode;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryImpl implements ContentCategoryService {

    @Autowired
    public TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<EUTreeNode> getCategoryList(long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        List<EUTreeNode> resultList = new ArrayList<>();
        for (TbContentCategory c: list) {
            EUTreeNode node = new EUTreeNode();
            node.id = c.getId();
            node.text = c.getName();
            node.state = c.getIsParent()?"closed":"open";
            resultList.add(node);
        }
        return resultList;
    }

    @Override
    public TaotaoResult insertCategoryNode(long parentId, String name) {
        TbContentCategory category = new TbContentCategory();
        category.setParentId(parentId);
        category.setName(name);
        category.setIsParent(false);
        //1==正常，2==删除
        category.setStatus(1);
        category.setSortOrder(1);
        category.setCreated(new Date());
        category.setUpdated(new Date());
        //添加数据到数据库中
        tbContentCategoryMapper.insert(category);

        //修改父节点isParentId 为true

        TbContentCategory parentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentCategory.getIsParent()){
            parentCategory.setIsParent(true);
            //更新父节点属性
            tbContentCategoryMapper.updateByPrimaryKey(parentCategory);
        }
        return TaotaoResult.ok(category);
    }

    @Override
    public TaotaoResult deleteCategoryNode(long parentId, long id) {

        tbContentCategoryMapper.deleteByPrimaryKey(id);
//        TbContentCategory parentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentId);
//
//        TbContentCategoryExample example = new TbContentCategoryExample();
//        TbContentCategoryExample.Criteria criteria = example.createCriteria();
//        criteria.andParentIdEqualTo(parentId);
//        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
//        if (list.size() == 0){
//            parentCategory.setIsParent(false);
//            //更新父节点属性
//            tbContentCategoryMapper.updateByPrimaryKey(parentCategory);
//        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateCategoryNode(long id, String name) {
        TbContentCategory category = tbContentCategoryMapper.selectByPrimaryKey(id);
        category.setName(name);
        category.setUpdated(new Date());
        tbContentCategoryMapper.updateByPrimaryKey(category);
        return TaotaoResult.ok();
    }
}

package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemService;
import com.taotao.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

//商品管理service
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper mItemMapper;

    @Autowired
    private TbItemDescMapper mDescMapper;

    @Autowired
    private TbItemParamItemMapper mItemParamItemMapper;

    @Override
    public TbItem getItemById(long itemId) {
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemId);
        List<TbItem> tbItems = mItemMapper.selectByExample(example);
        if (tbItems != null && tbItems.size() > 0){
            return  tbItems.get(0);
        }
        return null;
    }

    @Override
    public EUDataGridResult getItemList(int page, int rows) {
        //查询商品列表
        TbItemExample example = new TbItemExample();
        //分页查询
        PageHelper.startPage(page,rows);
        List<TbItem> list = mItemMapper.selectByExample(example);
        //创建一个返回对象
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        //查询page 总 total
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public TaotaoResult createItem(TbItem item, String desc, String itemParams) throws Exception {
        long id = IDUtils.genItemId();
        //商品状态 1-正常，2-下架，3-删除；
        item.setStatus((byte)1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        item.setId(id);
        mItemMapper.insert(item);

        TaotaoResult taotaoResult = insertItemDesc(id, desc);
        if (taotaoResult.getStatus() != 200){
            throw new Exception();
        }

        taotaoResult = insertItemParamItem(id,itemParams );
        if (taotaoResult.getStatus() != 200){
            throw new Exception();
        }

        return TaotaoResult.ok();
    }

    public TaotaoResult insertItemDesc(long id, String desc){
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(id);
        itemDesc.setItemDesc(desc);
        Date date = new Date();
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        mDescMapper.insert(itemDesc);
        return TaotaoResult.ok();
    }

    public TaotaoResult insertItemParamItem(long id, String paramItems){
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setItemId(id);
        tbItemParamItem.setParamData(paramItems);
        Date date = new Date();
        tbItemParamItem.setCreated(date);
        tbItemParamItem.setUpdated(date);
        mItemParamItemMapper.insert(tbItemParamItem);
        return TaotaoResult.ok();
    }
}

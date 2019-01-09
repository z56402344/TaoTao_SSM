package com.taotao.rest.service.impl;

import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.rest.Ikey;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;
import com.taotao.utils.JsonUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService ,Ikey {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    //取商品基础信息
    @Override
    public TaotaoResult getItemBaseInfo(long itemId) {
        TbItem tbItem = null;
        try{
            //从redis缓存中取数据
            String jsonStr = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":base");
            if (!TextUtils.isBlank(jsonStr)){
                tbItem = JsonUtils.jsonToPojo(jsonStr,TbItem.class);
                return TaotaoResult.ok(tbItem);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //从数据库中取数据
        tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        try{
            //把查询到的数据存储到redis缓存中 存储格式按照 key=key+itemid:base,vlue=jsonStr
            jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":base", JsonUtils.objectToJson(tbItem));
            jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":base", REDIS_ITEM_EXPIRE);
        }catch (Exception e){
            e.printStackTrace();
        }

        return TaotaoResult.ok(tbItem);
    }

    //取商品详情
    @Override
    public TaotaoResult getItemDescInfo(long itemId) {
        TbItemDesc tbItemDesc = null;
        try{
            String jsonStr = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
            if (!TextUtils.isBlank(jsonStr)){
                tbItemDesc = JsonUtils.jsonToPojo(jsonStr, TbItemDesc.class);
                return TaotaoResult.ok(tbItemDesc);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        tbItemDesc = itemDescMapper.selectByPrimaryKey(itemId);

        try{
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc", JsonUtils.objectToJson(tbItemDesc));
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc", REDIS_ITEM_EXPIRE);
        }catch (Exception e){
            e.printStackTrace();
        }

        return TaotaoResult.ok(tbItemDesc);
    }

    @Override
    public TaotaoResult getItemParam(long itemId) {
        TbItemParamItem tbItemParamItem = null;
        try{
            String jsonStr = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
            if (!TextUtils.isBlank(jsonStr)){
                tbItemParamItem = JsonUtils.jsonToPojo(jsonStr, TbItemParamItem.class);
                return TaotaoResult.ok(tbItemParamItem);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);

        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);

        if (list != null && list.size() > 0){
            tbItemParamItem = list.get(0);
            try{
                jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(tbItemParamItem));
                jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
            }catch (Exception e){
                e.printStackTrace();
            }
            return TaotaoResult.ok(tbItemParamItem);
        }

        return TaotaoResult.build(400,"无此商品信息");
    }


}

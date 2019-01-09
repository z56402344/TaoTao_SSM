package com.taotao.order.service.impl;

import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.core.IKey;
import com.taotao.order.dao.JedisClient;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService, IKey {

    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Autowired
    private TbOrderItemMapper orderItemMapper;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public TaotaoResult createOrder(TbOrder order,  List<TbOrderItem> tbOrderItems, TbOrderShipping tbOrderShipping) {
        //a.生成订单号
        String genStr = jedisClient.get(ORDER_GEN_KEY);
        if (TextUtils.isBlank(genStr)) {
            jedisClient.set(ORDER_GEN_KEY, ORDER_INIT_ID);
        }
        long orderId = jedisClient.incr(ORDER_GEN_KEY);
        order.setOrderId(orderId + "");
        //状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        order.setStatus(1);
        Date date = new Date();
        order.setCreateTime(date);
        order.setUpdateTime(date);
        //0：未评价 1：已评价
        order.setBuyerRate(0);
        //1.向订单表插入数据
        orderMapper.insert(order);


        for (TbOrderItem tbOrderItem : tbOrderItems) {
            //生成订单明细ID
            long orderDetailId = jedisClient.incr(ORDER_GEN_KEY);
            tbOrderItem.setId(orderDetailId + "");
            tbOrderItem.setOrderId(orderId+"");
            //2.向订单明细表插入订单记录
            orderItemMapper.insert(tbOrderItem);
        }

        //3.向订单地址表插入订单地址信息
        //插入物流表
        //补全物流表的属性
        tbOrderShipping.setOrderId(orderId + "");
        tbOrderShipping.setCreated(date);
        tbOrderShipping.setUpdated(date);
        orderShippingMapper.insert(tbOrderShipping);

        return TaotaoResult.ok(orderId);
    }
}

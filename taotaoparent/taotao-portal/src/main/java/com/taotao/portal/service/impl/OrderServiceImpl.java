package com.taotao.portal.service.impl;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.taotao.pojo.TaotaoResult;
import com.taotao.portal.Common;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.OrderService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService,Common {


    @Override
    public String createOrder(Order order) {
        //调用taotao-order的服务提交订单。
        String json = HttpClientUtil.doPostJson(M_ORDER_CREATE, JsonUtils.objectToJson(order));
        TaotaoResult result = TaotaoResult.format(json);
        if (result.getStatus() == 200){
            Object orderId =  result.getData();
            return orderId.toString();
        }
        return "";
    }
}

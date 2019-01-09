package com.taotao.portal.controller;

import com.taotao.pojo.TbUser;
import com.taotao.portal.IKey;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.CarService;
import com.taotao.portal.service.OrderService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController implements IKey  {

    @Autowired
    private CarService cartService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/order-cart")
    public String showOrderCart(HttpServletRequest request, HttpServletResponse response, Model model) {
        //取购物车商品列表
        List<CartItem> list = cartService.getCartItemList(request, response);
        //传递给页面
        model.addAttribute("cartList", list);
        return "order-cart";
    }

    @RequestMapping("/create")
    public String createOrder(Order order,Model model,HttpServletRequest request){
        try{
            TbUser tbUser = (TbUser) request.getAttribute(KEY_USER);
            //在Order对象中，通过tbUser补全对应的用户信息
            String orderId = orderService.createOrder(order);
            order.setUserId(tbUser.getId());
            order.setBuyerNick(tbUser.getUsername());
            model.addAttribute("orderId",orderId);
            model.addAttribute("payment",order.getPayment());
            model.addAttribute("date",new DateTime().plus(3).toString("yyyy-MM-dd"));
            return "success";
        }catch (Exception e){
            e.printStackTrace();;
            model.addAttribute("message","创建订单出错，请重新尝试");
            //TODO 如果出错需要通过电话短信，邮件等通知开发人员
            return "error/exception";
        }

    }


}

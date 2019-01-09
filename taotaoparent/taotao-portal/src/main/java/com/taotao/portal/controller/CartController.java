package com.taotao.portal.controller;

import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CarService carService;

    @RequestMapping("/add/{itemId}")
    public String addCartItem(@PathVariable Long itemId, @RequestParam(defaultValue="1")Integer num, HttpServletRequest request, HttpServletResponse response){

        carService.addCarItem(itemId,num,request,response);
        return "redirect:/cart/success.html";
    }

    @RequestMapping("/success")
    public String showSuccess(){
        return "cartSuccess";
    }

    @RequestMapping("/cart")
    public String showCart(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<CartItem> list = carService.getCartItemList(request, response);
        model.addAttribute("cartList", list);
        return "cart";
    }

    @RequestMapping("/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response){
        carService.deleteCartItem(itemId,request,response);
        return "redirect:/cart/cart.html";
    }
}

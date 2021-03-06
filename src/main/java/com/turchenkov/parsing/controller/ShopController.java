package com.turchenkov.parsing.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.turchenkov.parsing.service.ShopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ShopController {

    @Autowired
    private ShopServiceImpl service;

    @GetMapping("/shops")
    public String allReportsGet(Model model) {
        model.addAttribute("shops", service.getListOfShop());
        return "shops";
    }

    @PostMapping("/shops")
    public String allReportsPost() {
        return "redirect:/shops";
    }

    @PostMapping("/update")
    public String updateShopsPost() throws UnirestException {
        service.update();
        return "redirect:/shops";
    }

    @GetMapping("/shops/orderByDiscount")
    public String orderByDiscountGet(Model model) {
        model.addAttribute("shops", service.orderByDiscount());
        return "shops";
    }

//    @PostMapping("/shops/orderByDiscount")
//    public String orderByDiscountPost(){
//        return "redirect:";
//    }

    @GetMapping("/shops/orderByDiscountDesc")
    public String orderByDiscountDescGet(Model model) {
        model.addAttribute("shops", service.orderByDiscountDesc());
        return "shops";
    }

//    @PostMapping("/shops/orderByDiscountDesc")
//    public String orderByDiscountDescPost(){
//        return "redirect:";
//    }

}

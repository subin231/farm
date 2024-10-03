package com.farmstory.controller.admin;

import com.farmstory.dto.OrderDTO;
import com.farmstory.dto.ProductDTO;
import com.farmstory.dto.UserDTO;
import com.farmstory.service.OrderService;
import com.farmstory.service.ProductService;
import com.farmstory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
public class AdminIndexController {
    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/admin/AdminIndex")
    public String AdminIndex(Model model) {
        List<ProductDTO> productDTO = productService.selectProducts();
        List<UserDTO> userDto = userService.selectUsers();
        List<OrderDTO> orderDTO = orderService.selectOrders();
        Collections.reverse(productDTO);
        Collections.reverse(userDto);
        Collections.reverse(orderDTO);
        model.addAttribute("productDTOs", productDTO);
        model.addAttribute("orderDTOs",orderDTO);
        model.addAttribute("userDtos", userDto);
        log.info("productDTO"+ productDTO );
        log.info("userDto"+ userDto );
        log.info("orderDTO"+ orderDTO );
        return "admin/AdminIndex";
    }
}

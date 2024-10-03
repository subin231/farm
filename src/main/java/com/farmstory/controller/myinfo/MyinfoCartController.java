package com.farmstory.controller.myinfo;

import com.farmstory.dto.CartDTO;
import com.farmstory.dto.OrderDTO;
import com.farmstory.dto.ProductDTO;
import com.farmstory.dto.pageDTO.PageRequestDTO;
import com.farmstory.dto.pageDTO.PageResponseDTO;
import com.farmstory.service.CartService;
import com.farmstory.service.OrderService;
import com.farmstory.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
public class MyinfoCartController {

    private final CartService cartService;
    private final ProductService productService;
    private final OrderService orderService;

    @GetMapping("/userInfo/UserMyinfoCart")
    public String MarketCart12(@RequestParam(required = false) String userId, Model model) {


        List<CartDTO> carts = cartService.selectCartAll(userId);

        ProductDTO product = null;

        for(CartDTO cartDTO : carts) {

            cartDTO.setProdDTO(productService.selectProduct(cartDTO.getProdNo()));

            product = cartDTO.getProdDTO();
            product.setCartProdCount(cartDTO.getCartProdCount());


        }
        log.info("222222222222222"+carts);

        model.addAttribute("carts", carts);
        return "/user/UserMyinfoCart";
    }

    @GetMapping("userInfo/UserMyinfoOrder")
    public String UserMyinfoOrder(PageRequestDTO pageRequestDTO, Model model) {

        PageResponseDTO<OrderDTO> OrderPageResponseDTO = orderService.selectorderAll(pageRequestDTO);
        log.info("orderorder"+OrderPageResponseDTO);

        model.addAttribute("OrderPageResponseDTO", OrderPageResponseDTO);

        return "user/UserMyinfoOrder";
    }

    @GetMapping("/userInfo/UserMyinfoOrder/{orderNo}")
    @ResponseBody
    public ResponseEntity<OrderDTO> getOrderDetail(@PathVariable String orderNo) {
        OrderDTO orderDetail = orderService.selectOrderbyId(orderNo);

        if (orderDetail != null) {
            return ResponseEntity.ok(orderDetail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

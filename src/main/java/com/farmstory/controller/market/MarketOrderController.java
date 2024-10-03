package com.farmstory.controller.market;

import com.farmstory.dto.*;
import com.farmstory.entity.Cart;
import com.farmstory.service.CartService;
import com.farmstory.service.OrderService;
import com.farmstory.service.ProductService;
import com.farmstory.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Controller
public class MarketOrderController {

    private final ProductService productService;
    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;


    @GetMapping("/market/MarketCart")
    public String MarketCart(@RequestParam(required = false) String userId, Model model) {


        List<CartDTO> carts = cartService.selectCartAll(userId);

        ProductDTO product = null;

        for(CartDTO cartDTO : carts) {

            cartDTO.setProdDTO(productService.selectProduct(cartDTO.getProdNo()));

            product = cartDTO.getProdDTO();
            product.setCartProdCount(cartDTO.getCartProdCount());

        }
        log.info("222222222222222"+carts);

        model.addAttribute("carts", carts);
        return "/market/MarketCart";
    }

    @PostMapping("/market/MarketCart")
    public ResponseEntity<Map<String, Object>> MarketCart(HttpServletRequest req, CartDTO cartDTO){

        String action = req.getParameter("action");

        if("delete".equals(action)) {

            String[] carts = req.getParameterValues("cart");

            for(String cart : carts ) {
                cartService.deleteCart(Integer.parseInt(cart));
            }
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return ResponseEntity.ok(response);

        }else {

            log.info(cartDTO);

            cartService.insertCart(cartDTO);
            cartDTO.setProdDTO(productService.selectProduct(cartDTO.getProdNo()));

            ProductDTO product = cartDTO.getProdDTO();
            product.setCartProdCount(cartDTO.getCartProdCount());

            log.info("ordercontroller. proddto : " + product);

            Map<String, Object> response = new HashMap<>();
            response.put("cartItems", product);
            response.put("success", true);
            return ResponseEntity.ok(response);

        }


    }

    @GetMapping("/market/MarketOrder12")
    public String MarketOrder12(@RequestParam(required = false) int[] cartId, Model model) {

        List<CartDTO> carts = new ArrayList<>();
        log.info("123123123123"+cartId.toString());

        String uid = null;

        for(int cart : cartId) {
            log.info(cart);
            CartDTO cartDTO = cartService.selectCart(cart);
            cartDTO.setProdDTO(productService.selectProduct(cartDTO.getProdNo()));
            ProductDTO product = cartDTO.getProdDTO();
            product.setCartProdCount(cartDTO.getCartProdCount());
            carts.add(cartDTO);
            uid = cartDTO.getUserId();
        }
        UserDTO user = userService.selectUserById(uid);


        model.addAttribute("carts", carts);
        model.addAttribute("user", user);
        return "/market/MarketOrder";
    }

    @GetMapping("/market/MarketOrder")
    public String MarketOrder(@RequestParam(required = false) int prodNo, @RequestParam(required = false) int cartProdCount,@RequestParam(required = false) String userId , Model model){

        log.info(userId);

        List<CartDTO> carts = new ArrayList<>();

        CartDTO cart = new CartDTO();

        ProductDTO product = productService.selectProduct(prodNo);

        product.setCartProdCount(cartProdCount);

        cart.setProdDTO(product);

        carts.add(cart);
        log.info(userId);

        UserDTO user = userService.selectUserById(userId);
        log.info(user);

        model.addAttribute("user", user);
        model.addAttribute("carts", carts);

        return "/market/MarketOrder";
    }

    @PostMapping("/market/MarketOrder")
    public ResponseEntity<Map<String, Object>> MarketOrder(@ModelAttribute OrderDTO orderDTO, HttpServletRequest req, Model model){
        log.info(orderDTO);
        String[] prodNos = req.getParameterValues("No");
        String[] prodCounts = req.getParameterValues("Count");

        int a = Integer.parseInt(prodNos[0]);

        orderDTO.setProdNo(a);

        log.info(orderDTO);
        int result = orderService.insertOrder(orderDTO);

        if(result > 0){



            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return ResponseEntity.ok(response);
        }
        else {
            Map<String, Object> response = new HashMap<>();
            response.put("failed", false);
            return ResponseEntity.ok(response);
        }

    }

}

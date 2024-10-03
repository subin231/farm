package com.farmstory.controller.admin;

import com.farmstory.dto.OrderDTO;
import com.farmstory.dto.pageDTO.PageRequestDTO;
import com.farmstory.dto.pageDTO.OrderPageResponseDTO;
import com.farmstory.dto.pageDTO.PageResponseDTO;
import com.farmstory.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
public class AdminOrderListController {

    private final OrderService orderService;

    @GetMapping("/admin/OrderList")
    public String AdminOrder(Model model, PageRequestDTO pageRequestDTO) {
        PageResponseDTO<OrderDTO> OrderPageResponseDTO = orderService.selectorderAll(pageRequestDTO);
        log.info("orderorder"+OrderPageResponseDTO);
        model.addAttribute("OrderPageResponseDTO", OrderPageResponseDTO);
        return "/admin/order/OrderList";
    }

    @GetMapping("/admin/OrderList/{orderNo}")
    @ResponseBody
    public ResponseEntity<OrderDTO> getOrderDetail(@PathVariable String orderNo) {
        OrderDTO orderDetail = orderService.selectOrderbyId(orderNo);

        if (orderDetail != null) {
            return ResponseEntity.ok(orderDetail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/admin/OrderList/Delete")
    public ResponseEntity<?> AdminProdListDelete(@RequestBody List<String> orderIds) {
        try{
            log.info(orderIds);
            orderService.DeleteOrders(orderIds);
            return ResponseEntity.ok().body(Collections.singletonMap("success", true));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("success",false));
        }
    }
}

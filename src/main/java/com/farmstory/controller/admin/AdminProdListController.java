package com.farmstory.controller.admin;

import com.farmstory.dto.ProductDTO;
import com.farmstory.dto.pageDTO.PageRequestDTO;
import com.farmstory.dto.pageDTO.PageResponseDTO;
import com.farmstory.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
public class AdminProdListController {

    private final ProductService productService;
    @GetMapping("/admin/ProductList")
    public String AdminProd(Model model, PageRequestDTO pageRequestDTO) {
        PageResponseDTO<ProductDTO> productDTO = productService.selectProductAll(pageRequestDTO,0);
        log.info("productDTOproductDTO"+productDTO);
        model.addAttribute("productDTOs",productDTO);
        return "/admin/product/ProductList";
    }

    @DeleteMapping("/admin/ProductList/Delete")
    public ResponseEntity<?> AdminProdListDelete(@RequestBody  List<String> productIds) {
        try{
            log.info(productIds);
            productService.DeleteProduct(productIds);
            return ResponseEntity.ok().body(Collections.singletonMap("success", true));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("success",false));
        }
    }

}

package com.farmstory.controller.admin;

import com.farmstory.dto.ProductDTO;
import com.farmstory.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Log4j2
@RequiredArgsConstructor
@Controller
public class AdminProdRegisterController {

    private final ProductService productService;
    @GetMapping("/admin/ProductRegister")
    public String AdminProdRegister() {
        return "/admin/product/ProductRegister";
    }

    @PostMapping("/admin/ProductRegister")
    public String AdminProdRegister(ProductDTO productDTO, RedirectAttributes redirectAttributes) {
        log.info("AdminProdRegister" + productDTO);
        boolean isSuccess = productService.insertProduct(productDTO);
        if (isSuccess) {
            redirectAttributes.addFlashAttribute("message", "등록되었습니다.");
            return "redirect:/admin/ProductRegister?success=true";
        } else {
            redirectAttributes.addFlashAttribute("message", "등록에 실패하였습니다.");
            return "redirect:/admin/ProductRegister?success=false";
        }

    }

}

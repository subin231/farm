package com.farmstory.controller.market;

import com.farmstory.dto.pageDTO.PageRequestDTO;
import com.farmstory.dto.pageDTO.MarketPageResponseDTO;
import com.farmstory.dto.pageDTO.PageResponseDTO;
import com.farmstory.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@RequiredArgsConstructor
@Controller
public class MarketListController {

    private final ProductService productService;

    @GetMapping("/market/MarketList")
    public String MarketList(Model model, PageRequestDTO pageRequestDTO, @RequestParam(value = "catetype", required=false) Integer catetype) {
        if(catetype == null) {
            catetype = 0;
        }
        PageResponseDTO marketPageResponseDTO = productService.selectProductAll(pageRequestDTO, catetype);
        model.addAttribute("marketPageResponseDTO", marketPageResponseDTO);
        return "/market/MarketList";
    }


}

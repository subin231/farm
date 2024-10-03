package com.farmstory.controller.mainIndex;

import com.farmstory.dto.ProductDTO;
import com.farmstory.entity.Article;
import com.farmstory.service.ArticleService;
import com.farmstory.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
public class IndexController {
    private final ProductService productService;
    private final ArticleService articleService;
    @GetMapping(value = {"/", "/index"})
    public String index(Authentication authentication, Model model) {
        if(authentication !=null && authentication.isAuthenticated()){
            // 로그인 했을 경우

            String auth = authentication.getAuthorities().toString();

            String role = auth.replace("[", "")        // 대괄호 제거
                        .replace("]", "")       // 대괄호 제거
                        .replace("ROLE_", "");  // ROLE_ 접두사 제거

            model.addAttribute("isAuthenticated", true);
             model.addAttribute("role",role);

        }else {
            // 로그인 안했을 경우
            model.addAttribute("isAuthenticated", false);
        }
        List<ProductDTO> productDTO = productService.selectProducts();
        List<Article> CropGarden = articleService.selectArticles("CropGarden");
        List<Article> CropReturnfarm = articleService.selectArticles("CropReturnfarm");
        List<Article> CropStory = articleService.selectArticles("CropStory");
        List<Article> CommunityNotice = articleService.selectArticles("CommunityNotice");

        Collections.reverse(productDTO);
        Collections.reverse(CropGarden);
        Collections.reverse(CropReturnfarm);
        Collections.reverse(CropStory);
        Collections.reverse(CommunityNotice);

        model.addAttribute("productDTOs", productDTO);
        model.addAttribute("CropGarden", CropGarden);
        model.addAttribute("CropReturnfarm", CropReturnfarm);
        model.addAttribute("CropStory", CropStory);
        model.addAttribute("CommunityNotice", CommunityNotice);

        return "index";
    }

}

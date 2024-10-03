package com.farmstory.controller.article.community;

import com.farmstory.dto.ArticleDTO;
import com.farmstory.dto.FileDTO;
import com.farmstory.dto.UserDTO;
import com.farmstory.dto.pageDTO.ArticlePageRequestDTO;
import com.farmstory.dto.pageDTO.ArticlePageResponseDTO;
import com.farmstory.entity.Article;
import com.farmstory.entity.Comment;
import com.farmstory.repository.article.ArticleRepository;
import com.farmstory.service.ArticleService;
import com.farmstory.service.CommentService;
import com.farmstory.service.FileService;
import com.farmstory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class CommunityController {
    private final ArticleService articleService;
    private final ArticleRepository articleRepository;
    private final CommentService commentService;
    private final UserService userService;
    private final FileService fileService;
//    public CommunityController(ArticleService articleService) {
//        this.articleService = articleService;
//    }

    @GetMapping("/community/{cate}")
    public String CommunityView(@PathVariable String cate, @RequestParam(defaultValue = "1") int pg, Model model, ArticlePageRequestDTO articlePageRequestDTO) {
        String str1 = "";
        if (cate.equals("CommunityNotice")) {
            str1 = "b101";
        } else if (cate.equals("CommunityDiet")) {
            str1 = "b102";
        } else if (cate.equals("CommunityChef")) {
            str1 = "b103";
        } else if (cate.equals("CommunityCs")) {
            str1 = "b104";
        } else if (cate.equals("CommunityFaq")) {
            str1 = "b105";
        }

        model.addAttribute("str1", str1);

        articlePageRequestDTO.setPg(pg);

        ArticlePageResponseDTO articlePageResponseDTO = articleService.selectArticleAll(articlePageRequestDTO, cate);


        List<ArticleDTO> articles = articlePageResponseDTO.getDtoList();
        for (ArticleDTO articleDTO : articles) {
            int commentCount = articleService.countCommentsForArticle(articleDTO.getArtNo());
            articleDTO.setArtComment(commentCount);
        }

        model.addAttribute("pageResponseDTO", articlePageResponseDTO);

        log.info("articles_controller " + articlePageResponseDTO.getDtoList());
        log.info("articles_controller " + articlePageResponseDTO);

        return "/community/" + cate;
    }

    //글쓰기
    @GetMapping("/community/{cate}/CommunityWrite")
    public String CommunityWrite(Model model, @PathVariable("cate") String cate) {

        String str1 = "";
        if (cate.equals("CommunityNotice")) {
            str1 = "b101";
        } else if (cate.equals("CommunityDiet")) {
            str1 = "b102";
        } else if (cate.equals("CommunityChef")) {
            str1 = "b103";
        } else if (cate.equals("CommunityCs")) {
            str1 = "b104";
        } else if (cate.equals("CommunityFaq")) {
            str1 = "b105";
        }

        model.addAttribute("str1", str1);
        model.addAttribute("artCate", cate);
        System.out.println("Model Map: " + model.asMap());
        System.out.println(str1);
        return "/community/talk/CommunityWrite";
    }

    //글쓰기
    @PostMapping("/community/CommunityWrite")

    public String CommunityWrite(ArticleDTO articleDTO, String artCate, Principal principal) {

        log.info("article info : " + articleDTO);

        // 사용자 정보 설정
        if (principal != null) {
            String username = principal.getName();
            UserDTO userDTO = userService.selectUserById(username);
            if (userDTO != null) {
                String usernick = userDTO.getUserNick();
                articleDTO.setArtWriter(username);
                articleDTO.setArtNick(usernick);
            }
        }

        String str1 = "";
        if (artCate.equals("CommunityNotice")) {
            str1 = "b101";
        } else if (artCate.equals("CommunityDiet")) {
            str1 = "b102";
        } else if (artCate.equals("CommunityChef")) {
            str1 = "b103";
        } else if (artCate.equals("CommunityCs")) {
            str1 = "b104";
        } else if (artCate.equals("CommunityFaq")) {
            str1 = "b105";
        }

        // 먼저 글을 저장하여 artNo 생성
        int artNo = articleService.insertArticle(articleDTO);
        log.info("Generated artNo : " + artNo);

        // 생성된 artNo를 파일 DTO에 설정
        List<FileDTO> uploadedFiles = fileService.uploadFile(articleDTO);
        if (!uploadedFiles.isEmpty()) {
            for (FileDTO fileDTO : uploadedFiles) {
                fileDTO.setArtNo(artNo);  // 파일에 artNo 할당
                log.info("File with artNo: " + fileDTO); // 로그로 파일 정보 출력
                fileService.insertFile(fileDTO);  // 파일 정보 저장
            }
        }

        return "redirect:/community/" + artCate;
    }

    //글보기
    @GetMapping("/community/{cate}/CommunityView/{artNo}")
    public String CommunityView(Model model, @PathVariable("cate") String cate, @PathVariable("artNo") int artNo) {
        String str1 = "";
        if (cate.equals("CommunityNotice")) {
            str1 = "b101";
        } else if (cate.equals("CommunityDiet")) {
            str1 = "b102";
        } else if (cate.equals("CommunityChef")) {
            str1 = "b103";
        } else if (cate.equals("CommunityCs")) {
            str1 = "b104";
        } else if (cate.equals("CommunityFaq")) {
            str1 = "b105";
        }

        ArticleDTO articleDTO = articleService.selectArticle(artNo);
        System.out.println("article_DTO"+articleDTO.getFileList());
        List<Comment> comments = commentService.selectCommentByArtNo(articleDTO.getArtNo());
        model.addAttribute(articleDTO);

        model.addAttribute("str1", str1);
        model.addAttribute("comments", comments);

        System.out.println("comments :" + comments);
        System.out.println(model);
        System.out.println(str1);
        System.out.println(cate);
        return "/community/talk/CommunityView";
    }

    // 글수정
    @GetMapping("/community/{cate}/CommunityModify/{artNo}")
    public String CommunityViewModify(Model model, @PathVariable String cate, @PathVariable("artNo") int artNo) {
        String str1 = "";
        System.out.println(cate);
        if (cate.equals("CommunityNotice")) {
            str1 = "b101";
        } else if (cate.equals("CommunityDiet")) {
            str1 = "b102";
        } else if (cate.equals("CommunityChef")) {
            str1 = "b103";
        } else if (cate.equals("CommunityCs")) {
            str1 = "b104";
        } else if (cate.equals("CommunityFaq")) {
            str1 = "b105";
        }

        ArticleDTO articleDTO = articleService.selectArticle(artNo);
        model.addAttribute("articleDTO", articleDTO);
        log.info("articleDTO_modify_Conrtroller :" + articleDTO);

        model.addAttribute("str1", str1);


        // 수정 폼으로 이동
        return "community/talk/CommunityModify";
    }

    // 게시물 수정을 처리하는 POST 요청
    @PostMapping("/community/{cate}/CommunityModify/{artNo}")
    public String updateArticle(@ModelAttribute ArticleDTO articleDTO, @RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2, @PathVariable String cate, @PathVariable("artNo") int artNo) {
        String str1 = "";
        if (cate.equals("CommunityNotice")) {
            str1 = "b101";
        } else if (cate.equals("CommunityDiet")) {
            str1 = "b102";
        } else if (cate.equals("CommunityChef")) {
            str1 = "b103";
        } else if (cate.equals("CommunityCs")) {
            str1 = "b104";
        } else if (cate.equals("CommunityFaq")) {
            str1 = "b105";
        }

        log.info("Modified article info : " + articleDTO);

        // 기존 글 정보 불러오기
        ArticleDTO existingArticle = articleService.selectArticle(artNo);

        if (existingArticle == null) {
            return "redirect:/community/" + cate;  // 글이 없으면 다시 목록으로
        }

        // 업데이트할 내용 반영
        existingArticle.setArtTitle(articleDTO.getArtTitle());
        existingArticle.setArtContent(articleDTO.getArtContent());

        // 파일 처리 (새 파일이 있을 경우 처리)
        if (!file1.isEmpty() || !file2.isEmpty()) {
            List<MultipartFile> files = new ArrayList<>();
            if (!file1.isEmpty()) files.add(file1);
            if (!file2.isEmpty()) files.add(file2);

            articleDTO.setFiles(files);

            // 파일 업로드 처리
            List<FileDTO> uploadedFiles = fileService.uploadFile(articleDTO);
            for (FileDTO fileDTO : uploadedFiles) {
                fileDTO.setArtNo(artNo);  // 파일에 artNo 할당
                log.info("File with artNo: " + fileDTO);
                fileService.insertFile(fileDTO);  // 파일 정보 저장
            }
        }

        // 수정된 글 정보 저장
        articleService.updateArticle(artNo, existingArticle);
        log.info("Article updated: " + existingArticle);
        // 수정 완료 후 게시글 상세 페이지로 리다이렉트

        return "redirect:/community/" + articleDTO.getArtCate() + "/CommunityView/" + articleDTO.getArtNo();
    }

    @DeleteMapping("/community/{cate}/CommunityDelete/{no}")
    public String CommunityDelete(@PathVariable String cate, @PathVariable("no") int no) {
        articleService.deleteArticle(no);
        return "redirect:/community/" + cate;
    }

    @PostMapping("/community/increaseHit")
    @ResponseBody
    public ResponseEntity<Void> increaseHit(@RequestParam("artNo") int artNo) {
        articleService.increaseHit(artNo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/community/file/delete/{fileNo}")
    @ResponseBody
    public ResponseEntity<String> deleteCommunityFile(@PathVariable("fileNo") int fileNo) {
        try {
            // 파일 삭제 로직 수행
            fileService.deleteFile(fileNo);

            // 파일 삭제 성공 시
            return ResponseEntity.ok("파일이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            // 오류 발생 시
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 삭제 중 오류가 발생했습니다.");
        }
    }


}


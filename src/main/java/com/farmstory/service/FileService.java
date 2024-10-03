package com.farmstory.service;

import com.farmstory.dto.ArticleDTO;
import com.farmstory.dto.FileDTO;
import com.farmstory.entity.Article;
import com.farmstory.entity.FileEntity;
import com.farmstory.repository.FileRepository;
import com.farmstory.repository.article.ArticleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
@Transactional
public class FileService {

    private final FileRepository fileRepository;
    private final ModelMapper modelMapper;
    private final ArticleRepository articleRepository;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;


    public void saveFiles(List<FileDTO> fileDTOs, int artNo) {
        Article article = articleRepository.findById(artNo)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        for (FileDTO fileDTO : fileDTOs) {
            FileEntity fileEntity = fileDTO.toEntity();
            fileEntity.setArticle(article);  // Article 객체 설정
            fileRepository.save(fileEntity);
        }
    }

    public List<FileDTO> uploadFile(ArticleDTO articleDTO) {

        // 파일 업로드 경로 파일 객체 생성
        File fileUploadPath = new File(uploadPath);

        // 파일 업로드 디렉터리가 존재하지 않으면 디렉터리 생성
        if(!fileUploadPath.exists()){
            fileUploadPath.mkdirs();
        }

        // 파일 업로드 시스템 경로 구하기
        String path = fileUploadPath.getAbsolutePath();

        // 파일 정보 객체 가져오기
        List<MultipartFile> files = articleDTO.getFiles();

        // 업로드 파일 정보 객체 리스트 생성
        List<FileDTO> uploadedFiles = new ArrayList<>();

        for(MultipartFile file : files) {

            if(!file.isEmpty()){
                String oName = file.getOriginalFilename();
                String ext = oName.substring(oName.lastIndexOf("."));
                String sName = UUID.randomUUID().toString() + ext;

                // 파일 저장
                try {
                    file.transferTo(new File(path, sName));
                } catch (IOException e) {
                    log.error(e);
                }

                FileDTO fileDTO = FileDTO.builder()
                        .fileoName(oName)
                        .filesName(sName)
                        .build();

                uploadedFiles.add(fileDTO);
            }
        }
        return uploadedFiles;
    }


    public ResponseEntity<Resource> downloadFile(int fno) {

        Optional<FileEntity> optFile = fileRepository.findById(fno);

        FileEntity fileEntity = null;

        if(optFile.isPresent()){
            fileEntity = optFile.get();

            // 파일 다운로드 카운트 +1
            int count = fileEntity.getFileDownload();
            fileEntity.setFileDownload(count+1);

            fileRepository.save(fileEntity);
        }

        try {
            Path path = Paths.get(uploadPath + fileEntity.getFilesName());
            String contentType = Files.probeContentType(path);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(
                    ContentDisposition.builder("attachment")
                            .filename(fileEntity.getFileoName(), StandardCharsets.UTF_8).build());

            headers.add(HttpHeaders.CONTENT_TYPE, contentType);
            Resource resource = new InputStreamResource(Files.newInputStream(path));

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(resource);

        } catch (IOException e) {

            return ResponseEntity
                    .notFound().build();
        }
    }

    public void insertFile(FileDTO fileDTO) {
        FileEntity fileEntity = modelMapper.map(fileDTO, FileEntity.class);

        Article article = new Article();
        article.setArtNo(fileDTO.getArtNo());

        fileEntity.setArticle(article);
        log.info("FileEntity with artNo: " + fileEntity.getArticle().getArtNo());
        fileRepository.save(fileEntity);
    }

    public List<FileDTO> getFilesByArtNo(int artNo) {

        Article article = articleRepository.findById(artNo)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));


        return fileRepository.findByArticle(article).stream()
                .map(FileEntity::toDTO)
                .collect(Collectors.toList());
    }

    public FileDTO selectFile(int fno){
        return null;
    }
    public List<FileDTO> selectFileAll(){
        return null;
    }
    public void updateFile(FileDTO fileDTO){}
    public void deleteFile(int fno){
        fileRepository.deleteById(fno);
    }

}

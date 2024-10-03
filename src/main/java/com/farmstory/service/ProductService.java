package com.farmstory.service;

import com.farmstory.dto.OrderDTO;
import com.farmstory.dto.pageDTO.PageRequestDTO;
import com.farmstory.dto.pageDTO.MarketPageResponseDTO;
import com.farmstory.dto.ProductDTO;
import com.farmstory.dto.pageDTO.PageResponseDTO;
import com.farmstory.entity.Product;
import com.farmstory.repository.product.ProductRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductService {


    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    public ProductDTO selectProduct(int pNo) {
        Optional<Product> opt = productRepository.findById(pNo);

        Product product = null;

        if (opt.isPresent()) {
            product = opt.get();
        }

        return modelMapper.map(product, ProductDTO.class);

    }
    public List<ProductDTO> selectProducts(){

        List<Product> products = productRepository.selectProducts();

        List<ProductDTO> productDTOS = products
                .stream()
                .map(entity -> entity.toDTO())
                .collect(Collectors.toList());
        for(ProductDTO product : productDTOS) {
            LocalDateTime productDateTime = product.getProdRegDate();
            if(productDateTime != null) {
                String fullDateTime = productDateTime.toString();
                String[] split = fullDateTime.split("T");
                if(split.length == 2) {
                    product.setDate(split[0]);
                    product.setTimeDate(split[1]);
                }
            }
        }
        return productDTOS;
    }

    public PageResponseDTO<ProductDTO> selectProductAll(PageRequestDTO pageRequestDTO, int catetype){
        Pageable pageable = pageRequestDTO.getPageable("prodNo",false);

        pageRequestDTO.setCateType(catetype);

        Page<Tuple> pageProduct = productRepository.selectProductAllForList(pageRequestDTO, pageable, catetype);

        List<ProductDTO> productList = pageProduct.getContent().stream().map(tuple -> {

            Product product = tuple.get(0, Product.class);

            return modelMapper.map(product, ProductDTO.class);

        }).toList();
        for(ProductDTO product : productList) {
            LocalDateTime productDateTime = product.getProdRegDate();
            if(productDateTime != null) {
                String fullDateTime = productDateTime.toString();
                String[] split = fullDateTime.split("T");
                if(split.length == 2) {
                    product.setDate(split[0]);
                    product.setTimeDate(split[1]);
                }
            }
        }
        int total = (int) pageProduct.getTotalElements();

        return PageResponseDTO.<ProductDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(productList)
                .total(total)
                .build();
    }

    public boolean insertProduct(ProductDTO productDTO) {

        // 파일 업로드 경로 파일 객체 생성
        File fileUploadPath = new File(uploadPath);

        //파일 업로드 디렉터리가 존재하지 않으면 디렉터리 생성
        if(!fileUploadPath.exists()){
            fileUploadPath.mkdirs();
        }

        //파일 업로드 시스템 경로 구하기
        String path = fileUploadPath.getAbsolutePath();

        log.info("pathpathpathpathpathpath :: "+path);

        //파일 정보 객체 가져오기
        List<MultipartFile> files = new ArrayList<>();  // ArrayList로 초기화
        files.add(productDTO.getProdImageName1());
        files.add(productDTO.getProdImageName2());
        files.add(productDTO.getProdImageName3());

        int i = 1;  // 이미지 번호를 매기기 위한 인덱스
        boolean isUploadSuccessful = true;
        for(MultipartFile file : files){
            if(!file.isEmpty()){
                // 원본 파일명 가져오기
                String oName = file.getOriginalFilename();
                // 파일 확장자 추출
                String ext = oName.substring(oName.lastIndexOf("."));
                // UUID를 사용하여 새로운 파일명 생성
                String sName = UUID.randomUUID().toString() + ext;

                // 파일 저장
                try {
                    file.transferTo(new File(path, sName));
                    switch (i){
                        case 1:
                            productDTO.setProdImage1(sName);
                            break;
                        case 2:
                            productDTO.setProdImage2(sName);
                            break;
                        case 3:
                            productDTO.setProdImage3(sName);
                            break;
                    }
                } catch (IOException e) {
                    log.error(e);
                    isUploadSuccessful = false;
                }
            }
            i++;
        }
        if (isUploadSuccessful) {
            Product product = modelMapper.map(productDTO, Product.class);
            productRepository.save(product);
            return true;
        } else {
            return false;
        }
    }
    public void DeleteProduct(List<String> productIds) {
        for (String id : productIds) {
            try {
                int productId = Integer.parseInt(id);
                productRepository.deleteById(productId);
            } catch (NumberFormatException e) {
                // 문자열이 숫자로 변환되지 않을 경우 예외 처리
                System.out.println("Invalid product ID format: " + id);
                throw new IllegalArgumentException("Invalid product ID format: " + id);
            }
        }
    }
}
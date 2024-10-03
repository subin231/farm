package com.farmstory.dto.pageDTO;

import com.farmstory.dto.ArticleDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePageResponseDTO {

    private List<ArticleDTO> dtoList;

    private int pg;
    private int size;
    private int total;
    private int startNo;
    private int start, end;
    private boolean prev, next;

    private int cateType;

    private String cate;

    @Builder
    public ArticlePageResponseDTO(ArticlePageRequestDTO articlePageRequestDTO, List<ArticleDTO> dtoList, int total) {
        this.pg = articlePageRequestDTO.getPg();
        this.size = articlePageRequestDTO.getSize();
        this.total = total;
        this.dtoList = dtoList;

        this.cate = articlePageRequestDTO.getCate();

        this.startNo = total - ((pg -1) * size);
        this.end = (int) (Math.ceil(this.pg / 5.0)) * 5;
        this.start = this.end -4;

        int last = (int) (Math.ceil(total / (double)size));
        this.end = end > last ? last : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }



}

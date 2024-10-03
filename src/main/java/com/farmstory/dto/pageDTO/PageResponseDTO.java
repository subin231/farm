package com.farmstory.dto.pageDTO;

import com.farmstory.dto.ArticleDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDTO<T> {

    private List<T> dtoList;
    private int pg;
    private int size;
    private int artsize;
    private int total;
    private int startNo;
    private int start, end;
    private boolean prev, next;

    private int cateType;
    private String artcateType;
    @Builder
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<T> dtoList, int total) {
        this.pg = pageRequestDTO.getPg();
        if (!dtoList.isEmpty() && dtoList.get(0) instanceof ArticleDTO) {
            // articleDTO라면 artsize 설정
            this.size  = pageRequestDTO.getArtsize();
        } else {
            // 그렇지 않다면 일반적인 size 설정
            this.size = pageRequestDTO.getSize();
        }
        this.total = total;
        this.dtoList = dtoList;

        this.startNo = total - ((pg -1) * size);
        this.end = (int) (Math.ceil(this.pg / 5.0)) * 5;
        this.start = this.end - 4;

        int last = (int) (Math.ceil(total / (double)size));
        this.end = end > last ? last : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }
}

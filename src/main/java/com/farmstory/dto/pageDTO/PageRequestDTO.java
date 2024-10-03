package com.farmstory.dto.pageDTO;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequestDTO {

    @Builder.Default
    private int no = 1;

    @Builder.Default
    private int pg = 1;

    @Builder.Default
    private int size = 5;

    private int artsize = 10;

    private int cateType;
    private String artcateType;

    public Pageable getPageable(String sort, boolean isOrderDTO) {
        int pageSize = isOrderDTO ? this.artsize : this.size;
        return PageRequest.of(this.pg - 1, pageSize, Sort.by(sort).descending());
    }

}

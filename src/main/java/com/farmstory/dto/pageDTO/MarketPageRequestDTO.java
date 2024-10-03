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
public class MarketPageRequestDTO {

    @Builder.Default
    private int no = 1;

    @Builder.Default
    private int pg = 1;

    @Builder.Default
    private int size = 5;

    private int cateType;


    public Pageable getPageable(String sort) {
        return PageRequest.of(this.pg -1, this.size, Sort.by(sort).descending());
    }

}

package com.farmstory.repository.order;

import com.farmstory.dto.pageDTO.PageRequestDTO;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {
    Page<Tuple> selectOrderAllForList(PageRequestDTO pageRequestDTO, Pageable pageable);

}

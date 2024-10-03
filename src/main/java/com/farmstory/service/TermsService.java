package com.farmstory.service;

import com.farmstory.dto.TermsDTO;
import com.farmstory.entity.Terms;
import com.farmstory.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TermsService {
    private final TermsRepository termsRepository;

    public TermsDTO selectTerms(){
        Terms terms = termsRepository.findterms();
        TermsDTO termsDTO = terms.toDTO();
        return termsDTO;
    }
}

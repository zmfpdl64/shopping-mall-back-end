package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;
}

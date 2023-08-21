package com.supercoding.shoppingmallbackend.controller.aop;

import com.supercoding.shoppingmallbackend.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScrapController {

    private final ScrapService scrapService;
}

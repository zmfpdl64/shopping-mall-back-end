package com.supercoding.shoppingmallbackend.service;


import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.CommonErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.ProductErrorCode;
import com.supercoding.shoppingmallbackend.dto.response.ProductDetailResponse;
import com.supercoding.shoppingmallbackend.dto.response.ProductImageResponse;
import com.supercoding.shoppingmallbackend.entity.Product;
import com.supercoding.shoppingmallbackend.repository.ProductRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDetailResponse getProductByProductId(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new CustomException(ProductErrorCode.NOTFOUND_PRODUCT.getErrorCode()));

        List<ProductImageResponse> productImageResponseList = new ArrayList<>();
        ProductImageResponse productImageResponse = new ProductImageResponse();
        productImageResponse.setImgIdx(1L);
        productImageResponse.setImgUrl("https://chat.openai.com/");
        productImageResponseList.add(productImageResponse);

        try{
            return ProductDetailResponse.builder()
                    .build()
                    .toResponse(product, productImageResponseList);
        } catch (ParseException e){
            throw new CustomException(CommonErrorCode.INTERNAL_SERVER_ERROR.getErrorCode());
        }
    }

}

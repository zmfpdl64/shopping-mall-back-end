package com.supercoding.shoppingmallbackend.service;


import com.supercoding.shoppingmallbackend.dto.request.ShoppingCartItemRequest;
import com.supercoding.shoppingmallbackend.entity.Consumer;
import com.supercoding.shoppingmallbackend.entity.Product;
import com.supercoding.shoppingmallbackend.repository.ConsumerRepository;
import com.supercoding.shoppingmallbackend.repository.KoeyProductRepository;
import com.supercoding.shoppingmallbackend.repository.ShoppingCartRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class ShoppingCartServiceTest {
    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ConsumerRepository consumerRepository;

    @Mock
    private KoeyProductRepository productRepository;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    @Test
    @DisplayName("장바구니에 상품 추가하기 테스트")
    public void testAddProductToShoppingCart(){
        final Long consumerId = 1L;
        final Long productId = 1L;
        final Long selectedAmount = 2L;

//        ShoppingCartItemRequest request = ShoppingCartItemRequest.builder()
//                .productId(productId)
//                .amount(selectedAmount)
//                .build();
//
//        when(consumerRepository.findById(consumerId)).thenReturn(Optional.of(Consumer.builder()
//                .id(consumerId)
//                .profileIdx(1L)
//                .build()
//        ));
//        when(productRepository.findById(productId)).thenReturn(Optional.of(Product.builder()
//                .id(1L)
//                .title("쉐입 퍼즐: 콜로세움 600 PCS")
//                .price(17600L)
//                .genreIdx(1L)
//                .amount(99L)
//                .mainImageUrl("https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1688696198_a.jpg")
//                .sellerIdx(1L)
//                .build()
//        ));
//        when(shoppingCartRepository.findByConsumerIdAndProductId(consumerId, productId)).thenReturn(Optional.empty());
    }
}

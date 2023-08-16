package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.ConsumerErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.ProductErrorCode;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.common.util.JpaUtils;
import com.supercoding.shoppingmallbackend.dto.request.ShoppingCartItemRequest;
import com.supercoding.shoppingmallbackend.dto.response.KoeyConsumerResponse;
import com.supercoding.shoppingmallbackend.dto.response.KoeyProductResponse;
import com.supercoding.shoppingmallbackend.dto.response.ShoppingCartItemResponse;
import com.supercoding.shoppingmallbackend.entity.Consumer;
import com.supercoding.shoppingmallbackend.entity.Product;
import com.supercoding.shoppingmallbackend.entity.ShoppingCart;
import com.supercoding.shoppingmallbackend.repository.ConsumerRepository;
import com.supercoding.shoppingmallbackend.repository.KoeyProductRepository;
import com.supercoding.shoppingmallbackend.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ConsumerRepository consumerRepository;
//    private final ProductRepository productRepository;
    private final KoeyProductRepository productRepository;

    @Transactional
    public CommonResponse<ShoppingCartItemResponse> setProduct(ShoppingCartItemRequest shoppingCartItemRequest) {
//        String consumerEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long consumerId = 1L;

        Consumer consumer = consumerRepository.findById(consumerId).orElseThrow(
                ()->new CustomException(ConsumerErrorCode.NOT_FOUND_BY_ID)
        );
        Product product = productRepository.findById(shoppingCartItemRequest.getProductId()).orElseThrow(
                ()->new CustomException(ProductErrorCode.NOTFOUND_PRODUCT)
        );

        ShoppingCart shoppingCartItem = shoppingCartRepository.findByConsumerIdAndProductId(consumerId, shoppingCartItemRequest.getProductId()).orElse(null);
        if (shoppingCartItem == null) {
            ShoppingCart newData = ShoppingCart.builder()
                    .consumer(consumer)
                    .product(product)
                    .amount(shoppingCartItemRequest.getAmount())
                    .build();

            JpaUtils.managedSave(shoppingCartRepository, newData);

            ShoppingCartItemResponse createdData = ShoppingCartItemResponse.builder()
                    .consumer(KoeyConsumerResponse.builder()
                            .id(consumer.getId())
                            .profileId(consumer.getProfileIdx())
                            .build()
                    )
                    .product(KoeyProductResponse.builder()
                            .id(product.getId())
                            .title(product.getTitle())
                            .mainImageUrl(product.getMainImageUrl())
                            .amount(product.getAmount())
                            .price(product.getPrice())
                            .genre("product.getGenre()")
                            .sellerId(product.getSellerIdx())
                            .build()
                    )
                    .amount(shoppingCartItemRequest.getAmount())
                    .build();

            return ApiUtils.success("장바구니에 상품을 성공적으로 추가했습니다.", createdData);
        }

        shoppingCartItem.setAmount(shoppingCartItemRequest.getAmount());

        ShoppingCartItemResponse modifiedData = ShoppingCartItemResponse.builder()
                .consumer(KoeyConsumerResponse.builder()
                        .id(consumer.getId())
                        .profileId(consumer.getProfileIdx())
                        .build()
                )
                .product(KoeyProductResponse.builder()
                        .id(product.getId())
                        .title(product.getTitle())
                        .mainImageUrl(product.getMainImageUrl())
                        .amount(product.getAmount())
                        .price(product.getPrice())
                        .genre("product.getGenre()")
                        .sellerId(product.getSellerIdx())
                        .build()
                )
                .amount(shoppingCartItem.getAmount())
                .build();

        return ApiUtils.success("장바구니에 담긴 상품의 수량을 성공적으로 변경하였습니다.", modifiedData);
    }

    public CommonResponse<List<ShoppingCartItemResponse>> getShoppingCart() {
        // 토큰에서 구매자 id 혹은 email 파싱
        Long consumerId = 1L;

        // 구매자 id 혹은 email로 장바구니아이템들 리스트로 불러오기
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAllByConsumerId(1L);

        // 엔티티를 dto로 번환
        List<ShoppingCartItemResponse> data = shoppingCartList.stream()
                .map(ShoppingCartItemResponse::from)
                .collect(Collectors.toList());

        // 불러온 리스트 반환하기
        return ApiUtils.success("장바구니를 성공적으로 조회했습니다.", data);
    }
}

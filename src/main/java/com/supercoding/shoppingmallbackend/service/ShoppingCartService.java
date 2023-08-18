package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.ConsumerErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.GenreErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.ProductErrorCode;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.common.util.JpaUtils;
import com.supercoding.shoppingmallbackend.dto.ProfileDetail;
import com.supercoding.shoppingmallbackend.dto.request.ShoppingCartItemRequest;
import com.supercoding.shoppingmallbackend.dto.response.ProductSimpleResponse;
import com.supercoding.shoppingmallbackend.dto.response.ShoppingCartItemResponse;
import com.supercoding.shoppingmallbackend.entity.Consumer;
import com.supercoding.shoppingmallbackend.entity.Genre;
import com.supercoding.shoppingmallbackend.entity.Product;
import com.supercoding.shoppingmallbackend.entity.ShoppingCart;
import com.supercoding.shoppingmallbackend.repository.*;
import com.supercoding.shoppingmallbackend.security.AuthHolder;
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
    private final ProductRepository productRepository;

    @Transactional
    public CommonResponse<ShoppingCartItemResponse> setProduct(ShoppingCartItemRequest shoppingCartItemRequest) {
        Long profileId = AuthHolder.getUserIdx();
        Long productId = shoppingCartItemRequest.getProductId();
        Long addedQuantity = shoppingCartItemRequest.getAmount();

        Consumer consumer = consumerRepository.findByProfileId(profileId).orElseThrow(
                ()->new CustomException(ConsumerErrorCode.NOT_FOUND_BY_ID)
        );
        Product product = productRepository.findProductById(productId).orElseThrow(
                ()->new CustomException(ProductErrorCode.NOTFOUND_PRODUCT)
        );

        // consuemrId, productId로 장바구니 조회
        ShoppingCart shoppingCartItem = shoppingCartRepository.findByConsumerIdProductId(consumer.getId(), productId).orElse(null);

        if (shoppingCartItem == null) {
            // 장바구니에 존재하지 않다면
            ShoppingCart newData = ShoppingCart.builder()
                    .consumer(consumer)
                    .product(product)
                    .amount(addedQuantity)
                    .build();

            JpaUtils.managedSave(shoppingCartRepository, newData);

            ShoppingCartItemResponse createdData = ShoppingCartItemResponse.from(newData);
            return ApiUtils.success("장바구니에 상품을 성공적으로 추가했습니다.", createdData);
        }

        // 장바구니에 이미 존재한다면
        shoppingCartItem.setAmount(addedQuantity);

        ShoppingCartItemResponse modifiedData = ShoppingCartItemResponse.from(shoppingCartItem);
        return ApiUtils.success("장바구니에 담긴 상품의 수량을 성공적으로 변경하였습니다.", modifiedData);
    }

    public CommonResponse<List<ShoppingCartItemResponse>> getShoppingCart() {
        Long profileId = AuthHolder.getUserIdx();

        Consumer consumer = consumerRepository.findByProfileId(profileId).orElseThrow(
                ()->new CustomException(ConsumerErrorCode.NOT_FOUND_BY_ID)
        );

        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAllByConsumerId(consumer.getId());

        List<ShoppingCartItemResponse> shoppingCartItemResponses = shoppingCartList.stream()
                .map(ShoppingCartItemResponse::from)
                .collect(Collectors.toList());

        return ApiUtils.success("장바구니를 성공적으로 조회했습니다.", shoppingCartItemResponses);
    }
}

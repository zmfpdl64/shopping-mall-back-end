package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.ConsumerErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.ProductErrorCode;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.common.util.JpaUtils;
import com.supercoding.shoppingmallbackend.dto.request.OrderRequest;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ConsumerRepository consumerRepository;
//    private final ProductRepository productRepository;
    private final KoeyProductRepository productRepository;

    @Transactional(transactionManager = "tmJpa")
    public CommonResponse<ShoppingCartItemResponse> setProduct(OrderRequest orderRequest) {

        Consumer consumer = consumerRepository.findById(orderRequest.getConsumerId()).orElseThrow(
                ()->new CustomException(ConsumerErrorCode.NOT_FOUND_BY_ID)
        );
        Product product = productRepository.findById(orderRequest.getProductId()).orElseThrow(
                ()->new CustomException(ProductErrorCode.NOT_FOUND_BY_ID)
        );

        ShoppingCart shoppingCartItem = shoppingCartRepository.findByConsumerIdAndProductId(orderRequest.getConsumerId(), orderRequest.getProductId()).orElse(null);
        if (shoppingCartItem == null) {
            ShoppingCart newData = ShoppingCart.builder()
                    .consumer(consumer)
                    .product(product)
                    .amount(orderRequest.getAmount())
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
                    .amount(orderRequest.getAmount())
                    .build();

            return ApiUtils.success("장바구니에 상품을 성공적으로 추가했습니다.", createdData);
        }

        shoppingCartItem.setAmount(orderRequest.getAmount());


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
}

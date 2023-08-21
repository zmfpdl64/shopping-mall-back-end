package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.*;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.common.util.JpaUtils;
import com.supercoding.shoppingmallbackend.common.util.PaginationBuilder;
import com.supercoding.shoppingmallbackend.dto.request.ShoppingCartItemRequest;
import com.supercoding.shoppingmallbackend.dto.response.PaginationResponse;
import com.supercoding.shoppingmallbackend.dto.response.ShoppingCartItemResponse;
import com.supercoding.shoppingmallbackend.entity.Consumer;
import com.supercoding.shoppingmallbackend.entity.Product;
import com.supercoding.shoppingmallbackend.entity.ShoppingCart;
import com.supercoding.shoppingmallbackend.repository.*;
import com.supercoding.shoppingmallbackend.security.AuthHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ConsumerRepository consumerRepository;
    private final ProductRepository productRepository;

    @Cacheable(value = "shoppingcart", key = "'getAll'")
    public CommonResponse<List<ShoppingCartItemResponse>> getShoppingCart() {
        Consumer consumer = getConsumer();

        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAllByConsumerAndIsDeletedIsFalse(consumer);

        List<ShoppingCartItemResponse> shoppingCartItemResponses = shoppingCartList.stream()
                .map(ShoppingCartItemResponse::from)
                .collect(Collectors.toList());

        return ApiUtils.success("장바구니를 성공적으로 조회했습니다.", shoppingCartItemResponses);
    }

    @Cacheable(value = "shoppingcart", key = "'getPage('+#page+','+#size+')'")
    public CommonResponse<PaginationResponse<ShoppingCartItemResponse>> getShoppingCartWithPagination(int page, int size) {
        Consumer consumer = getConsumer();

        Page<ShoppingCart> dataPage = shoppingCartRepository.findAllByConsumerAndIsDeletedIsFalse(consumer, PageRequest.of(page, size));
        List<ShoppingCartItemResponse> contents = dataPage.getContent().stream().map(ShoppingCartItemResponse::from).collect(Collectors.toList());

        PaginationResponse<ShoppingCartItemResponse> response = new PaginationBuilder<ShoppingCartItemResponse>()
                .contents(contents)
                .hasPrivious(dataPage.hasPrevious())
                .hasNext(dataPage.hasNext())
                .totalPages(dataPage.getTotalPages())
                .build();

        return ApiUtils.success("장바구니를 성공적으로 조회했습니다.", response);
    }

    @Transactional
    @CacheEvict(value = "shoppingcart", allEntries = true)
    public CommonResponse<ShoppingCartItemResponse> setProduct(ShoppingCartItemRequest shoppingCartItemRequest) {
        Consumer consumer = getConsumer();

        ShoppingCart proccessedData = processSetProduct(consumer, shoppingCartItemRequest);
        ShoppingCartItemResponse response = ShoppingCartItemResponse.from(proccessedData);

        return ApiUtils.success("장바구니에 상품이 성공적으로 담겼습니다.", response);
    }

    @Transactional
    @CacheEvict(value = "shoppingcart", allEntries = true)
    public CommonResponse<List<ShoppingCartItemResponse>> setProductList(List<ShoppingCartItemRequest> shoppingCartItemRequestList) {
        Consumer consumer = getConsumer();

        List<ShoppingCartItemResponse> responses = shoppingCartItemRequestList.stream()
                .map(shoppingCartItemRequest -> processSetProduct(consumer,shoppingCartItemRequest))
                .map(ShoppingCartItemResponse::from)
                .collect(Collectors.toList());

        return ApiUtils.success("장바구니에 상품이 성공적으로 담겼습니다.", responses);
    }

    @Transactional
    @CacheEvict(value = "shoppingcart", allEntries = true)
    public CommonResponse<List<ShoppingCartItemResponse>> softDeleteShoppingCart() {
        Consumer consumer = getConsumer();

        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAllByConsumerAndIsDeletedIsFalse(consumer);
        shoppingCartList.forEach(shoppingCart -> {
            shoppingCart.setIsDeleted(true);
        });

        return ApiUtils.success("장바구니의 모든 상품을 성공적으로 삭제했습니다.", new ArrayList<>());
    }

    @Transactional
    @CacheEvict(value = "shoppingcart", allEntries = true)
    public CommonResponse<List<ShoppingCartItemResponse>> softDeleteShoppingCartByIds(Set<Long> shoppingCartIdSet) {
        Consumer consumer = getConsumer();

        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAllByConsumerAndIsDeletedIsFalse(consumer);
        shoppingCartList.forEach(shoppingCart -> {
            if (shoppingCartIdSet.contains(shoppingCart.getId())) shoppingCart.setIsDeleted(true);
        });

        return ApiUtils.success("장바구니에서 선택한 상품들을 성공적으로 삭제했습니다.", new ArrayList<>());
    }

    public void hardDeleteShoppingCart(){
        shoppingCartRepository.hardDelete();
    }

    private Consumer getConsumer(){
        Long profileId = AuthHolder.getUserIdx();
        return consumerRepository.findByProfileId(profileId).orElseThrow(()->new CustomException(ConsumerErrorCode.NOT_FOUND_BY_ID));
    }

    private ShoppingCart processSetProduct(Consumer consumer, ShoppingCartItemRequest request) {
        Long productId = request.getProductId();
        Long addedQuantity = request.getAmount();

        Product product = productRepository.findProductById(productId).orElseThrow(
                ()->new CustomException(ProductErrorCode.NOTFOUND_PRODUCT)
        );

        // consuemrId, productId로 장바구니 조회
        ShoppingCart shoppingCartItem = shoppingCartRepository.findByConsumerAndProductAndIsDeletedIsFalse(consumer, product).orElse(null);

        if (shoppingCartItem == null) {
            ShoppingCart newData = ShoppingCart.builder()
                    .consumer(consumer)
                    .product(product)
                    .amount(addedQuantity)
                    .build();
            newData.setIsDeleted(false);
            JpaUtils.managedSave(shoppingCartRepository, newData);
            return newData;
        }

        shoppingCartItem.setAmount(addedQuantity);
        return shoppingCartItem;
    }
}
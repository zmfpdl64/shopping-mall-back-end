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
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ConsumerRepository consumerRepository;
    private final ProductRepository productRepository;

    @Cacheable(value = "shoppingcart", key = "#profileId")
    public CommonResponse<List<ShoppingCartItemResponse>> getShoppingCart(Long profileId) {
        Consumer consumer = getConsumer(profileId);

        List<ShoppingCart> datas = shoppingCartRepository.findAllByConsumerAndIsDeletedIsFalseOrderByCreatedAtDesc(consumer);
        List<ShoppingCartItemResponse> responses = datas.stream()
                .map(ShoppingCartItemResponse::from)
                .collect(Collectors.toList());

        return ApiUtils.success("장바구니를 성공적으로 조회했습니다.", responses);
    }

    @Cacheable(value = "shoppingCartPage", key = "#profileId+'-'+#page+'-'+#size")
    public CommonResponse<PaginationResponse<ShoppingCartItemResponse>> getShoppingCartWithPagination(Long profileId, int page, int size) {
        Consumer consumer = getConsumer(profileId);

        Page<ShoppingCart> dataPage = shoppingCartRepository.findAllByConsumerAndIsDeletedIsFalseOrderByCreatedAtDesc(consumer, PageRequest.of(page, size));
        List<ShoppingCartItemResponse> contents = dataPage.getContent().stream().map(ShoppingCartItemResponse::from).collect(Collectors.toList());

        PaginationResponse<ShoppingCartItemResponse> response = new PaginationBuilder<ShoppingCartItemResponse>()
                .contents(contents)
                .hasPrivious(dataPage.hasPrevious())
                .hasNext(dataPage.hasNext())
                .totalPages(dataPage.getTotalPages())
                .totalElements(dataPage.getTotalElements())
                .build();

        return ApiUtils.success("장바구니를 성공적으로 조회했습니다.", response);
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "shoppingcart", key = "#profileId"),
        @CacheEvict(value = "shoppingCartPage", allEntries = true)
    })
    public CommonResponse<ShoppingCartItemResponse> setProduct(Long profileId, ShoppingCartItemRequest shoppingCartItemRequest) {
        Consumer consumer = getConsumer(profileId);

        ShoppingCart proccessedData = processSetProduct(consumer, shoppingCartItemRequest);
        ShoppingCartItemResponse response = ShoppingCartItemResponse.from(proccessedData);

        return ApiUtils.success("장바구니에 상품이 성공적으로 담겼습니다.", response);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "shoppingcart", key = "#profileId"),
            @CacheEvict(value = "shoppingCartPage", allEntries = true)
    })
    public CommonResponse<List<ShoppingCartItemResponse>> setProductList(Long profileId, List<ShoppingCartItemRequest> shoppingCartItemRequestList) {
        Consumer consumer = getConsumer(profileId);

        List<ShoppingCartItemResponse> responses = shoppingCartItemRequestList.stream()
                .map(shoppingCartItemRequest -> processSetProduct(consumer,shoppingCartItemRequest))
                .map(ShoppingCartItemResponse::from)
                .collect(Collectors.toList());

        return ApiUtils.success("장바구니에 상품이 성공적으로 담겼습니다.", responses);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "shoppingcart", key = "#profileId"),
            @CacheEvict(value = "shoppingCartPage", allEntries = true)
    })
    public CommonResponse<List<ShoppingCartItemResponse>> softDeleteShoppingCart(Long profileId) {
        Consumer consumer = getConsumer(profileId);

        List<ShoppingCart> datas = shoppingCartRepository.findAllByConsumerAndIsDeletedIsFalse(consumer);
        List<ShoppingCartItemResponse> responses = datas.stream()
                .map(data->{
                    data.setIsDeleted(true);
                    return ShoppingCartItemResponse.from(data);
                })
                .collect(Collectors.toList());

        return ApiUtils.success("장바구니의 모든 상품을 성공적으로 삭제했습니다.", responses);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "shoppingcart", key = "#profileId"),
            @CacheEvict(value = "shoppingCartPage", allEntries = true)
    })
    public CommonResponse<List<ShoppingCartItemResponse>> softDeleteShoppingCartByIds(Long profileId, Set<Long> shoppingCartIdSet) {
        Consumer consumer = getConsumer(profileId);

        List<ShoppingCart> datas = shoppingCartRepository.findAllByConsumerAndIsDeletedIsFalse(consumer);
        List<ShoppingCartItemResponse> responses = datas.stream()
                .filter(data->shoppingCartIdSet.contains(data.getId()))
                .map(data->{
                    data.setIsDeleted(true);
                    return ShoppingCartItemResponse.from(data);
                })
                .collect(Collectors.toList());

        return ApiUtils.success("장바구니에서 선택한 상품들을 성공적으로 삭제했습니다.", responses);
    }

    public void hardDeleteShoppingCart(){
        shoppingCartRepository.deleteAllByIsDeletedIsTrue();
    }

    private Consumer getConsumer(Long profileId){
        return consumerRepository.findByProfileIdAndIsDeletedIsFalse(profileId).orElseThrow(()->new CustomException(ConsumerErrorCode.INVALID_PROFILE_ID));
    }

    private ShoppingCart processSetProduct(Consumer consumer, ShoppingCartItemRequest request) {
        Long productId = request.getProductId();
        Long addedQuantity = request.getAmount();
        Product product = productRepository.findByIdAndIsDeletedIsFalse(productId).orElseThrow(()->new CustomException(ShoppingCartErrorCode.INVALID_PRODUCT));
        ShoppingCart data = shoppingCartRepository.findByConsumerAndProductAndIsDeletedIsFalse(consumer, product).orElse(null);

        if (data == null) {
            ShoppingCart newData = ShoppingCart.builder()
                    .consumer(consumer)
                    .product(product)
                    .amount(addedQuantity)
                    .build();
            newData.setIsDeleted(false);
            JpaUtils.managedSave(shoppingCartRepository, newData);
            return newData;
        }

        data.setAmount(addedQuantity);
        return data;
    }
}
package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.ConsumerErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.ProductErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.ProfileErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.ScrapErrorCode;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.common.util.JpaUtils;
import com.supercoding.shoppingmallbackend.dto.response.ConsumerResponse;
import com.supercoding.shoppingmallbackend.dto.response.PaginationPageResponse;
import com.supercoding.shoppingmallbackend.dto.response.ScrapResponse;
import com.supercoding.shoppingmallbackend.entity.Consumer;
import com.supercoding.shoppingmallbackend.entity.Product;
import com.supercoding.shoppingmallbackend.entity.Scrap;
import com.supercoding.shoppingmallbackend.repository.ConsumerRepository;
import com.supercoding.shoppingmallbackend.repository.ProductRepository;
import com.supercoding.shoppingmallbackend.repository.ScrapRepository;
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
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final ConsumerRepository consumerRepository;
    private final ProductRepository productRepository;

    @Cacheable(value = "scrap-list", key = "#profileId")
    public CommonResponse<List<ScrapResponse>> getAllScrap(Long profileId) {
        Consumer consumer = getConsumer(profileId);
        List<Scrap> datas = scrapRepository.findAllByConsumerAndIsDeletedIsFalse(consumer);
        List<ScrapResponse> responses = datas.stream().map(ScrapResponse::from).collect(Collectors.toList());
        return ApiUtils.success("찜 목록을 성공적으로 조회했습니다.", responses);
    }

    @Cacheable(value = "scrap-page", key = "#profileId+'-'+#page+'-'+#size")
    public CommonResponse<PaginationPageResponse<ScrapResponse>> getScrapPage(Long profileId, int page, int size) {
        Consumer consumer = getConsumer(profileId);
        Page<Scrap> dataPage = scrapRepository.findAllByConsumerAndIsDeletedIsFalse(consumer, PageRequest.of(page, size));
        List<ScrapResponse> contents = dataPage.getContent().stream().map(ScrapResponse::from).collect(Collectors.toList());
        PaginationPageResponse<ScrapResponse> response = new PaginationPageResponse<>(dataPage.getTotalPages(), contents);
        return ApiUtils.success("찜 목록을 성공적으로 조회했습니다.", response);
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "scrap-list", key = "#profileId"),
        @CacheEvict(value = "scrap-page", allEntries = true)
    })
    public CommonResponse<List<ScrapResponse>> addScrap(Long profileId, Set<Long> productIdSet) {
        Consumer consumer = getConsumer(profileId);

        List<ScrapResponse> responses = productIdSet.stream()
                .filter(productId->!scrapRepository.existsByConsumerAndProductIdAndIsDeletedIsFalse(consumer, productId))
                .map(productId->productRepository.findProductById(productId)
                        .orElseThrow(()->new CustomException(ScrapErrorCode.INVALID_PRODUCT))
                )
                .map(product -> {
                    Scrap newData = Scrap.builder().consumer(consumer).product(product).build();
                    newData.setIsDeleted(false);
                    return newData;
                })
                .map(scrap -> JpaUtils.managedSave(scrapRepository, scrap))
                .map(ScrapResponse::from)
                .collect(Collectors.toList());

        return ApiUtils.success("상품을 성공적으로 찜했습니다.", responses);
    }

    private Consumer getConsumer(Long profileId) {
        return consumerRepository.findByProfileId(profileId).orElseThrow(()->new CustomException(ConsumerErrorCode.INVALID_PROFILE_ID));
    }



}

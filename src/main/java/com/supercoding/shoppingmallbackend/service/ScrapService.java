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

    @Cacheable(value = "scrap", key = "'getAll('+#profileId+')'")
    public CommonResponse<List<ScrapResponse>> getAllScrap(Long profileId) {
        Consumer consumer = getConsumer(profileId);
        List<Scrap> datas = scrapRepository.findAllByConsumerAndIsDeletedIsFalse(consumer);
        List<ScrapResponse> responses = datas.stream().map(ScrapResponse::from).collect(Collectors.toList());
        return ApiUtils.success("찜 목록을 성공적으로 조회했습니다.", responses);
    }

    @Transactional
    @CacheEvict(value = "scrap", key = "'getAll('+#profileId+')'")
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

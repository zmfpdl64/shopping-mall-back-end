package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.*;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.common.util.JpaUtils;
import com.supercoding.shoppingmallbackend.common.util.PaginationBuilder;
import com.supercoding.shoppingmallbackend.dto.request.PaymentRequest;
import com.supercoding.shoppingmallbackend.dto.response.*;
import com.supercoding.shoppingmallbackend.entity.*;
import com.supercoding.shoppingmallbackend.repository.*;
import com.supercoding.shoppingmallbackend.security.AuthHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ConsumerRepository consumerRepository;
    private final SellerRepository sellerRepository;

    @Cacheable(value = "payment", key = "'getPurchaseAll'")
    public CommonResponse<List<PurchaseResponse>> getPurchaseHistory() {
        Long profileId = AuthHolder.getUserIdx();
        Consumer consumer = consumerRepository.findByProfileId(profileId).orElseThrow(()->new CustomException(ProfileErrorCode.NOT_FOUND));

        // 구매내역 조회하기
        List<Payment> payments = paymentRepository.findAllByConsumerAndIsDeletedIsFalse(consumer);
        List<PurchaseResponse> purchaseResponses = payments.stream()
                .map(PurchaseResponse::from)
                .collect(Collectors.toList());

        return ApiUtils.success("구매내역을 성공적으로 조회했습니다.", purchaseResponses);
    }

    @Cacheable(value = "payment", key = "'getSaleAll'")
    public CommonResponse<List<SaleResponse>> getSaleHistory() {
        Long profileId = AuthHolder.getUserIdx();
        Seller seller = sellerRepository.findByProfileId(profileId).orElseThrow(()->new CustomException(ProfileErrorCode.NOT_FOUND));

        // 판매내역 조회하기
        List<Payment> payments = paymentRepository.findAllBySellerId(seller.getId());
        List<SaleResponse> saleResponses = payments.stream()
                .map(SaleResponse::from)
                .collect(Collectors.toList());

        return ApiUtils.success("판매내역을 성공적으로 조회했습니다.", saleResponses);
    }

    @Cacheable(value = "payment", key = "'getPurchasePage('+#page+','+#size+')'")
    public CommonResponse<PaginationResponse<PurchaseResponse>> getPurchaseHistoryWithPagination(int page, int size) {
        Long profileId = AuthHolder.getUserIdx();
        Consumer consumer = consumerRepository.findByProfileId(profileId).orElseThrow(()->new CustomException(ProfileErrorCode.NOT_FOUND));

        Page<Payment> dataPage = paymentRepository.findAllByConsumerAndIsDeletedIsFalse(consumer, PageRequest.of(page, size));

        List<PurchaseResponse> contents = dataPage.getContent().stream()
                .map(PurchaseResponse::from)
                .collect(Collectors.toList());

        PaginationResponse<PurchaseResponse> response = new PaginationBuilder<PurchaseResponse>()
                .contents(contents)
                .hasPrivious(dataPage.hasPrevious())
                .hasNext(dataPage.hasNext())
                .totalPages(dataPage.getTotalPages())
                .build();

        return ApiUtils.success("구매내역을 성공적으로 조회했습니다.", response);
    }

    @Cacheable(value = "payment", key = "'getSalePage('+#page+','+#size+')'")
    public CommonResponse<PaginationResponse<SaleResponse>> getSaleHistoryWithPagination(int page, int size) {
        Long profileId = AuthHolder.getUserIdx();
        Seller seller = sellerRepository.findByProfileId(profileId).orElseThrow(()->new CustomException(ProfileErrorCode.NOT_FOUND));

        Page<Payment> dataPage = paymentRepository.findAllBySellerId(seller.getId(), PageRequest.of(page, size));
        List<SaleResponse> contents = dataPage.getContent().stream()
                .map(SaleResponse::from)
                .collect(Collectors.toList());

        PaginationResponse<SaleResponse> response = new PaginationBuilder<SaleResponse>()
                .totalPages(dataPage.getTotalPages())
                .hasNext(dataPage.hasNext())
                .hasPrivious(dataPage.hasPrevious())
                .contents(contents)
                .build();

        return ApiUtils.success("판매내역을 성공적으로 조회했습니다.", response);
    }

    @CacheEvict(value = "payment", allEntries = true)
    @Transactional
    public CommonResponse<List<PaymentResponse>> buyWhole(PaymentRequest paymentRequest) {
        Long profileId = AuthHolder.getUserIdx();
        Consumer consumer = consumerRepository.findByProfileId(profileId).orElseThrow(()->new CustomException(ProfileErrorCode.NOT_FOUND));
        List<ShoppingCart> purchaseList =  shoppingCartRepository.findAllByConsumerAndIsDeletedIsFalse(consumer);

        return processPayment(consumer, purchaseList, paymentRequest);
    }

    @CacheEvict(value = "payment", allEntries = true)
    @Transactional
    public CommonResponse<List<PaymentResponse>> buySelected(PaymentRequest paymentRequest, Set<Long> shoppingCartIdSet) {
        Long profileId = AuthHolder.getUserIdx();
        Consumer consumer = consumerRepository.findByProfileId(profileId).orElseThrow(()->new CustomException(ProfileErrorCode.NOT_FOUND));
        List<ShoppingCart> purchaseList =  shoppingCartRepository.findAllByConsumerAndIsDeletedIsFalse(consumer).stream()
                .filter(shoppingCart -> shoppingCartIdSet.contains(shoppingCart.getId()))
                .collect(Collectors.toList());

        return processPayment(consumer, purchaseList, paymentRequest);
    }

    private CommonResponse<List<PaymentResponse>> processPayment(Consumer consumer, List<ShoppingCart> purchaseList, PaymentRequest paymentRequest) {

        if (!validateProductQuatity(purchaseList)) throw new CustomException(PaymentErrorCode.OVER_AMOUNT);

        // 페이머니가 충분한지 확인
        Profile profile = consumer.getProfile();
        Long totalPrice = calculateTotalPrice(purchaseList);
        if (profile.getPaymoney() < totalPrice) throw new CustomException(PaymentErrorCode.NOT_ENOUGH_PAYMONEY);

        //주문번호 생성
        String orderNumber = createOrderNumber();
        if (orderNumber == null) throw new CustomException(PaymentErrorCode.FAIL_TO_CREATE);

        //결제일자 생성
        Timestamp paidAt = createPaidAt();

        // 상품 재고 차감, 장바구니 소프트 딜리트, 결제내역 추가
        purchaseList.forEach(cart-> {
            Product product = cart.getProduct();
            product.setAmount(product.getAmount()-cart.getAmount());

            cart.setIsDeleted(true);

            Payment newData = Payment.from(orderNumber, cart, paymentRequest, paidAt);
            newData.setIsDeleted(false);
            JpaUtils.managedSave(paymentRepository, newData);
        });

        // 페이머니 차감
        profile.setPaymoney(profile.getPaymoney() - totalPrice);

        // 결제내역 응답
        List<Payment> payments = paymentRepository.findAllByOrderNumberAndIsDeletedIsFalse(orderNumber);
        List<PaymentResponse> paymentResponses = payments.stream()
                .map(PaymentResponse::from)
                .collect(Collectors.toList());

        return ApiUtils.success("결제를 성공적으로 완료했습니다.", paymentResponses) ;
    }

    private boolean validateProductQuatity(@NotNull List<ShoppingCart> purchaseList) {
        if (purchaseList.isEmpty()) throw new CustomException(PaymentErrorCode.NO_PRODUCT);

        return purchaseList.stream().allMatch(cart -> cart.getAmount() <= cart.getProduct().getAmount());
    }

    private Long calculateTotalPrice(@NotNull List<ShoppingCart> purchaseList) {
        return purchaseList.stream().mapToLong(el->el.getAmount() * el.getProduct().getPrice()).sum();
    }

    private Timestamp createPaidAt() {
        return new Timestamp(new Date().getTime());
    }

    private String createOrderNumber(){
        String dateString = new SimpleDateFormat("yyMMddHH").format(new Date());

        for (int i = 1; i <= 10; i++) {
            int randomInt = new Random().nextInt(0xfff + 1);
            String orderNumber = dateString + Integer.toHexString(randomInt);

            if (!paymentRepository.existsByOrderNumberAndIsDeletedIsFalse(orderNumber))
                return orderNumber;

            log.info("중복되는 주문번호 생성: '{}', {}회 시도", orderNumber, i);
        }

        return null;
    }
}

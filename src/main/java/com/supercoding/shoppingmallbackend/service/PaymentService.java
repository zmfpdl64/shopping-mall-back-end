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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Cacheable(value = "purchaseList", key = "#profileId")
    public CommonResponse<List<PurchaseResponse>> getPurchaseHistory(Long profileId) {
        Consumer consumer = getConsumer(profileId);

        // 구매내역 조회하기
        List<Payment> payments = paymentRepository.findAllByConsumerAndIsDeletedIsFalseOrderByPaidAtDesc(consumer);
        List<PurchaseResponse> purchaseResponses = payments.stream()
                .map(PurchaseResponse::from)
                .collect(Collectors.toList());

        return ApiUtils.success("구매내역을 성공적으로 조회했습니다.", purchaseResponses);
    }

    @Cacheable(value = "purchaseListOrderNumber", key = "#profileId+'-'+#orderNumber.toString()")
    public CommonResponse<List<PurchaseResponse>> getPurchaseHistory(Long profileId, Set<String> orderNumber) {
        Consumer consumer = getConsumer(profileId);

        List<Payment> payments = paymentRepository.findAllByConsumerAndOrderNumberIsInAndIsDeletedIsFalseOrderByPaidAtDesc(consumer, orderNumber);
        List<PurchaseResponse> purchaseResponses = payments.stream()
                .map(PurchaseResponse::from)
                .collect(Collectors.toList());

        return ApiUtils.success("구매내역을 성공적으로 조회했습니다.", purchaseResponses);
    }

    @Cacheable(value = "saleList", key = "#profileId")
    public CommonResponse<List<SaleResponse>> getSaleHistory(Long profileId) {
        Seller seller = getSeller(profileId);

        // 판매내역 조회하기
        List<Payment> payments = paymentRepository.findAllByProductSellerAndIsDeletedIsFalseOrderByPaidAtDesc(seller);
        List<SaleResponse> saleResponses = payments.stream()
                .map(SaleResponse::from)
                .collect(Collectors.toList());

        return ApiUtils.success("판매내역을 성공적으로 조회했습니다.", saleResponses);
    }

    @Cacheable(value = "saleListOrderNumber", key = "#profileId+'-'+#orderNumber.toString()")
    public CommonResponse<List<SaleResponse>> getSaleHistory(Long profileId, Set<String> orderNumber) {
        Seller seller = getSeller(profileId);

        // 판매내역 조회하기
        List<Payment> payments = paymentRepository.findAllByProductSellerAndOrderNumberIsInAndIsDeletedIsFalseOrderByPaidAtDesc(seller, orderNumber);
        List<SaleResponse> saleResponses = payments.stream()
                .map(SaleResponse::from)
                .collect(Collectors.toList());

        return ApiUtils.success("판매내역을 성공적으로 조회했습니다.", saleResponses);
    }

    @Cacheable(value = "purchaseListPage", key = "#profileId+'-'+#page+'-'+#size")
    public CommonResponse<PaginationResponse<PurchaseResponse>> getPurchaseHistoryWithPagination(Long profileId, int page, int size) {
        Consumer consumer = getConsumer(profileId);

        Page<Payment> dataPage = paymentRepository.findAllByConsumerAndIsDeletedIsFalseOrderByPaidAtDesc(consumer, PageRequest.of(page, size));

        List<PurchaseResponse> contents = dataPage.getContent().stream()
                .map(PurchaseResponse::from)
                .collect(Collectors.toList());

        PaginationResponse<PurchaseResponse> response = new PaginationBuilder<PurchaseResponse>()
                .contents(contents)
                .hasPrivious(dataPage.hasPrevious())
                .hasNext(dataPage.hasNext())
                .totalPages(dataPage.getTotalPages())
                .totalElements(dataPage.getTotalElements())
                .build();

        return ApiUtils.success("구매내역을 성공적으로 조회했습니다.", response);
    }

    @Cacheable(value = "purchaseListPage", key = "#profileId+'-'+#page+'-'+#size+'-'+#orderNumberSet.toString()")
    public CommonResponse<PaginationResponse<PurchaseResponse>> getPurchaseHistoryWithPagination(Long profileId, int page, int size, Set<String> orderNumberSet) {
        Consumer consumer = getConsumer(profileId);

        Page<Payment> dataPage = paymentRepository.findAllByConsumerAndOrderNumberIsInAndIsDeletedIsFalseOrderByPaidAtDesc(consumer, orderNumberSet, PageRequest.of(page, size));

        List<PurchaseResponse> contents = dataPage.getContent().stream()
                .map(PurchaseResponse::from)
                .collect(Collectors.toList());

        PaginationResponse<PurchaseResponse> response = new PaginationBuilder<PurchaseResponse>()
                .contents(contents)
                .hasPrivious(dataPage.hasPrevious())
                .hasNext(dataPage.hasNext())
                .totalPages(dataPage.getTotalPages())
                .totalElements(dataPage.getTotalElements())
                .build();

        return ApiUtils.success("구매내역을 성공적으로 조회했습니다.", response);
    }

    @Cacheable(value = "saleListPage", key = "#profileId+'-'+#page+'-'+#size")
    public CommonResponse<PaginationResponse<SaleResponse>> getSaleHistoryWithPagination(Long profileId, int page, int size) {
        Seller seller = getSeller(profileId);

        Page<Payment> dataPage = paymentRepository.findAllByProductSellerAndIsDeletedIsFalseOrderByPaidAtDesc(seller, PageRequest.of(page, size));
        List<SaleResponse> contents = dataPage.getContent().stream()
                .map(SaleResponse::from)
                .collect(Collectors.toList());

        PaginationResponse<SaleResponse> response = new PaginationBuilder<SaleResponse>()
                .totalPages(dataPage.getTotalPages())
                .hasNext(dataPage.hasNext())
                .hasPrivious(dataPage.hasPrevious())
                .contents(contents)
                .totalElements(dataPage.getTotalElements())
                .build();

        return ApiUtils.success("판매내역을 성공적으로 조회했습니다.", response);
    }

    @Cacheable(value = "saleListPage", key = "#profileId+'-'+#page+'-'+#size+'-'+#orderNumberSet.toString()")
    public CommonResponse<PaginationResponse<SaleResponse>> getSaleHistoryWithPagination(Long profileId, int page, int size, Set<String> orderNumberSet) {
        Seller seller = getSeller(profileId);

        Page<Payment> dataPage = paymentRepository.findAllByProductSellerAndOrderNumberIsInAndIsDeletedIsFalseOrderByPaidAtDesc(seller, orderNumberSet, PageRequest.of(page, size));
        List<SaleResponse> contents = dataPage.getContent().stream()
                .map(SaleResponse::from)
                .collect(Collectors.toList());

        PaginationResponse<SaleResponse> response = new PaginationBuilder<SaleResponse>()
                .totalPages(dataPage.getTotalPages())
                .hasNext(dataPage.hasNext())
                .hasPrivious(dataPage.hasPrevious())
                .contents(contents)
                .totalElements(dataPage.getTotalElements())
                .build();

        return ApiUtils.success("판매내역을 성공적으로 조회했습니다.", response);
    }

    @Caching(evict = {
            @CacheEvict(value = "purchaseList", key = "#profileId"),
            @CacheEvict(value = "purchaseListOrderNumber", allEntries = true),
            @CacheEvict(value = "saleList", key = "#profileId"),
            @CacheEvict(value = "saleListOrderNumber", allEntries = true),
            @CacheEvict(value = "purchaseListPage", allEntries = true),
            @CacheEvict(value = "saleListPage", allEntries = true),
            @CacheEvict(value = "shoppingcart", key = "#profileId"),
            @CacheEvict(value = "shoppingCartPage", allEntries = true)
    })
    @Transactional
    public CommonResponse<List<PaymentResponse>> buyWhole(Long profileId, PaymentRequest paymentRequest) {
        Consumer consumer = getConsumer(profileId);
        List<ShoppingCart> purchaseList =  shoppingCartRepository.findAllByConsumerAndIsDeletedIsFalse(consumer);
        return processPayment(consumer, purchaseList, paymentRequest);
    }

    @Caching(evict = {
            @CacheEvict(value = "purchaseList", key = "#profileId"),
            @CacheEvict(value = "purchaseListOrderNumber", allEntries = true),
            @CacheEvict(value = "saleList", key = "#profileId"),
            @CacheEvict(value = "saleListOrderNumber", allEntries = true),
            @CacheEvict(value = "purchaseListPage", allEntries = true),
            @CacheEvict(value = "saleListPage", allEntries = true),
            @CacheEvict(value = "shoppingcart", key = "#profileId"),
            @CacheEvict(value = "shoppingCartPage", allEntries = true)
    })
    @Transactional
    public CommonResponse<List<PaymentResponse>> buySelected(Long profileId, PaymentRequest paymentRequest, Set<Long> shoppingCartIdSet) {
        Consumer consumer = getConsumer(profileId);
        List<ShoppingCart> purchaseList =  shoppingCartRepository.findAllByConsumerAndIsDeletedIsFalseAndIdIsIn(consumer, shoppingCartIdSet);
        return processPayment(consumer, purchaseList, paymentRequest);
    }

    private CommonResponse<List<PaymentResponse>> processPayment(Consumer consumer, List<ShoppingCart> purchaseList, PaymentRequest paymentRequest) {

        // 상품의 재고가 충분한지 확인
        if (!validateProductQuatity(purchaseList)) throw new CustomException(PaymentErrorCode.OVER_AMOUNT);

        // 페이머니가 충분한지 확인
        Profile profile = consumer.getProfile();
        Long totalPrice = calculateTotalPrice(purchaseList);
        if (profile.getPaymoney() < totalPrice) throw new CustomException(PaymentErrorCode.NOT_ENOUGH_PAYMONEY);

        //주문번호 생성
        String orderNumber = createOrderNumber();
        if (orderNumber == null) throw new CustomException(PaymentErrorCode.FAIL_TO_CREATE_ORDERNUMBER);

        //결제일자 생성
        Timestamp paidAt = createPaidAt();

        // 페이머니 차감
        profile.setPaymoney(profile.getPaymoney() - totalPrice);

        // 상품 재고 차감, 장바구니 소프트 딜리트, 결제내역 추가
        List<PaymentResponse> responses = purchaseList.stream()
                .map(cart->{
                    Product product = cart.getProduct();
                    product.setAmount(product.getAmount() - cart.getAmount());

                    cart.setIsDeleted(true);

                    Payment newData = Payment.from(orderNumber, cart, paymentRequest, paidAt);
                    newData.setIsDeleted(false);

                    Payment savedData = JpaUtils.managedSave(paymentRepository, newData);
                    return PaymentResponse.from(savedData);
                })
                .collect(Collectors.toList());

        return ApiUtils.success("결제를 성공적으로 완료했습니다.", responses) ;
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

    private Consumer getConsumer(Long profileId) {
        return consumerRepository.findByProfileIdAndIsDeletedIsFalse(profileId).orElseThrow(()->new CustomException(ConsumerErrorCode.INVALID_PROFILE_ID));
    }

    private Seller getSeller(Long profileId) {
        return sellerRepository.findByProfileIdAndIsDeletedIsFalse(profileId).orElseThrow(()->new CustomException(SellerErrorCode.INVALID_PROFILE_ID));
    }
}

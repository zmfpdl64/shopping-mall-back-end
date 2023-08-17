package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.*;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.common.util.JpaUtils;
import com.supercoding.shoppingmallbackend.dto.request.PaymentRequest;
import com.supercoding.shoppingmallbackend.dto.response.PaymentResponse;
import com.supercoding.shoppingmallbackend.dto.response.SimplePurchaseInfoResponse;
import com.supercoding.shoppingmallbackend.entity.*;
import com.supercoding.shoppingmallbackend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProfileRepository profileRepository;
    private final ConsumerRepository consumerRepository;
    private final GenreRepository genreRepository;


    @Transactional
    public CommonResponse<PaymentResponse> processPayment(PaymentRequest paymentRequest) {
        // 토큰에서 consumerId 혹은 email 파싱
        Long consumerId = 1L;

        // 재고가 충분한지 확인
        List<ShoppingCart> shoppingCart =  shoppingCartRepository.findAllByConsumerIdAndIsDeletedIsFalse(consumerId);
        if (shoppingCart.isEmpty()) throw new CustomException(ShoppingCartErrorCode.EMPTY);
        if (shoppingCart.stream().anyMatch(cart->cart.getAmount() > cart.getProduct().getAmount())) throw new CustomException(PaymentErrorCode.OVER_AMOUNT);

        // 페이머니가 충분한지 확인
        Consumer consumer = consumerRepository.findByIdAndIsDeletedIsFalse(consumerId).orElseThrow(()->new CustomException(ConsumerErrorCode.NOT_FOUND_BY_ID));
        Long profileId = consumer.getProfileIdx();
        Profile profile = profileRepository.findByProfileId(profileId);
        if (profile == null) throw new CustomException(ProfileErrorCode.NOT_FOUND);
        Long totalPrice = shoppingCart.stream().mapToLong(el->el.getAmount() * el.getProduct().getPrice()).sum();
        if (profile.getPaymoney() < totalPrice) throw new CustomException(PaymentErrorCode.NOT_ENOUGH_PAYMONEY);

        //주문번호 생성
        String orderNumber = createOrderNumber();
        if (orderNumber == null) throw new CustomException(PaymentErrorCode.FAIL_TO_CREATE);

        //결제 일시 생성
        Timestamp paymentAt = new Timestamp(new Date().getTime());

        // 상품 재고 차감, 장바구니 소프트 딜리트, 결제내역 추가
        shoppingCart.forEach(cart-> {
            Product product = cart.getProduct();
            product.setAmount(product.getAmount()-cart.getAmount());

            cart.setIsDeleted(true);

            Payment newData = Payment.from(orderNumber, cart, paymentRequest, paymentAt);
            JpaUtils.managedSave(paymentRepository, newData);
        });

        // 페이머니 차감
        profile.setPaymoney(profile.getPaymoney() - totalPrice);

        // 추가된 결제내역 응답
        List<Payment> purchaseHistories = paymentRepository.findByOrderNumberAndIsDeletedIsFalse(orderNumber);
        if (purchaseHistories.isEmpty()) throw new CustomException(PaymentErrorCode.NO_CREATED_PAYMENT);
        List<SimplePurchaseInfoResponse> purchaseInfos = purchaseHistories.stream()
                .map(purchaseHistory -> {
                    Long genreId = purchaseHistory.getProduct().getGenreIdx();
                    Genre genre = genreRepository.findById(genreId).orElseThrow(()->new CustomException(GenreErrorCode.NOT_FOUND_BY_ID));
                    return SimplePurchaseInfoResponse.from(purchaseHistory, genre);
                }).collect(Collectors.toList());

        return ApiUtils.success("결제를 성공적으로 완료했습니다.", PaymentResponse.from(orderNumber, paymentRequest, purchaseInfos, paymentAt)) ;
    }

    private String createOrderNumber(){
        String dateString = new SimpleDateFormat("yyMMddHH").format(new Date());

        for (int i = 1; i <= 10; i++) {
            int randomInt = new Random().nextInt(0xfff + 1);
            String orderNumber = dateString + Integer.toHexString(randomInt);

            if (!paymentRepository.existsByOrderNumber(orderNumber))
                return orderNumber;

            log.info("중복되는 주문번호 생성: '{}', {}회 시도", orderNumber, i);
        }

        return null;
    }

    public CommonResponse<List<PaymentResponse>> getPurchasedInfo() {
        // 토큰에서 consumerId 혹은 email 파싱하기
        Long consumerId = 1L;

        // 구매내역 조회하기
        List<Payment> purchaseHistories = paymentRepository.findAllByConsumerIdAndIsDeletedIsFalse(consumerId);


        // 반환하기
        return null;
    }
}

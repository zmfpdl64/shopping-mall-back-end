package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;

public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("setProduct 메서드 테스트")
    public void testSetProduct(){
        CommonResponse<Object> res = orderController.setProduct(any(), any());
        System.out.println(res.getData().toString());
    }
}

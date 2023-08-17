package com.supercoding.shoppingmallbackend.service;


import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.CommonErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.GenreErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.ProductErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.UserErrorCode;
import com.supercoding.shoppingmallbackend.common.util.FilePath;
import com.supercoding.shoppingmallbackend.dto.request.ProductCreateRequest;
import com.supercoding.shoppingmallbackend.dto.response.ProductDetailResponse;
import com.supercoding.shoppingmallbackend.dto.response.ProductImageResponse;
import com.supercoding.shoppingmallbackend.entity.Genre;
import com.supercoding.shoppingmallbackend.entity.Product;
import com.supercoding.shoppingmallbackend.entity.Seller;
import com.supercoding.shoppingmallbackend.repository.GenreRepository;
import com.supercoding.shoppingmallbackend.repository.ProductRepository;
import com.supercoding.shoppingmallbackend.repository.SellerRepository;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final AwsS3Service awsS3Service;
    private final GenreRepository genreRepository;
    private final SellerRepository sellerRepository;

    public ProductDetailResponse getProductByProductId(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new CustomException(ProductErrorCode.NOTFOUND_PRODUCT.getErrorCode()));

        List<ProductImageResponse> productImageResponseList = new ArrayList<>();
        ProductImageResponse productImageResponse = new ProductImageResponse();
        productImageResponse.setImgIdx(1L);
        productImageResponse.setImgUrl("https://chat.openai.com/");
        productImageResponseList.add(productImageResponse);

        try{
            return ProductDetailResponse.builder()
                    .build()
                    .toResponse(product, productImageResponseList);
        } catch (ParseException e){
            throw new CustomException(CommonErrorCode.INTERNAL_SERVER_ERROR.getErrorCode());
        }
    }

    @Transactional
    public void createProductItem(ProductCreateRequest productCreateRequest) {

        Seller seller = sellerRepository.findById(1L).orElseThrow(() -> new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode()));
        Genre genre = genreRepository.findById(productCreateRequest.getGenre()).orElseThrow(() -> new CustomException(GenreErrorCode.NOT_FOUND));



        try {
            Product product = Product.from(productCreateRequest, seller, genre);
            productRepository.save(product);
            if (product.getId() != null) {
                uploadThumbNailImage(productCreateRequest.getMainImage(), product);
            }
        }catch (ParseException e) {
            throw new CustomException(CommonErrorCode.INTERNAL_SERVER_ERROR.getErrorCode());
        }
    }

    private void uploadThumbNailImage(MultipartFile thumbNailFile, Product product) {
        try {
            if (thumbNailFile != null) {
                String url = awsS3Service.upload(thumbNailFile, FilePath.PRODUCT_THUMB_NAIL.getPath() + product.getId());
                product.setMainImageUrl(url);
            }
        } catch (IOException e) {
            throw new CustomException(CommonErrorCode.FAIL_TO_SAVE.getErrorCode());
        }
    }

}

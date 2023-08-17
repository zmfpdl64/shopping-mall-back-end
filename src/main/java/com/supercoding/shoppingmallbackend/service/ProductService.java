package com.supercoding.shoppingmallbackend.service;


import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.*;
import com.supercoding.shoppingmallbackend.common.util.FilePath;
import com.supercoding.shoppingmallbackend.dto.request.ProductRequestBase;
import com.supercoding.shoppingmallbackend.dto.response.ProductDetailResponse;
import com.supercoding.shoppingmallbackend.dto.response.ProductImageResponse;
import com.supercoding.shoppingmallbackend.entity.*;
import com.supercoding.shoppingmallbackend.repository.*;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final AwsS3Service awsS3Service;
    private final GenreRepository genreRepository;
    private final SellerRepository sellerRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductContentImageRepository productContentImageRepository;

    public ProductDetailResponse getProductByProductId(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new CustomException(ProductErrorCode.NOTFOUND_PRODUCT.getErrorCode()));
        List<ProductContentImage> productContentImageList = productContentImageRepository.findAllByProduct_Id(product.getId());
        List<ProductImageResponse> productImageResponseList = productContentImageList.stream().map(ProductImageResponse::from).collect(Collectors.toList());
        List<Category> categories = categoryRepository.findCategoriesByProductId(product.getId());
        /*
        TODO 리뷰 로직
        List<Long> 타입으로 findRatingByProductID 을 리뷰 엔티티에서 가져옴
        stream으로 rating 다 더함
        size만큼 나눠서 소수점 1~2자리
         */

        try {
            return ProductDetailResponse.from(product, productImageResponseList, categories);
        } catch (ParseException e) {
            throw new CustomException(CommonErrorCode.INTERNAL_SERVER_ERROR.getErrorCode());
        }
    }

    @Transactional
    public void createProductItem(ProductRequestBase productRequestBase, MultipartFile thumbFile, List<MultipartFile> imgFiles) {

        Seller seller = sellerRepository.findById(1L).orElseThrow(() -> new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode()));
        Genre genre = genreRepository.findById(productRequestBase.getGenre()).orElseThrow(() -> new CustomException(GenreErrorCode.NOT_FOUND));
        Category playerCount = categoryRepository.findByName(productRequestBase.getPlayerCount()).orElseThrow(() -> new CustomException(CategoryErrorCode.NOT_FOUND_BY_ID));
        Category playTime = categoryRepository.findByName(productRequestBase.getPlayTime()).orElseThrow(() -> new CustomException(CategoryErrorCode.NOT_FOUND_BY_ID));
        Category difficultyLevel = categoryRepository.findByName(productRequestBase.getDifficultyLevel()).orElseThrow(() -> new CustomException(CategoryErrorCode.NOT_FOUND_BY_ID));
        if (imgFiles.size() > 5) throw new CustomException(ProductErrorCode.TOO_MANY_FILES);

        try {
            Product product = Product.from(productRequestBase, seller, genre);
            productRepository.save(product);
            if (product.getId() != null) {
                uploadThumbNailImage(thumbFile, product);

                List<ProductCategory> productCategoryList = new ArrayList<>(Arrays.asList(
                        ProductCategory.from(product, playerCount),
                        ProductCategory.from(product, playTime),
                        ProductCategory.from(product, difficultyLevel)
                ));
                productCategoryRepository.saveAll(productCategoryList);
                productContentImageRepository.saveAll(uploadImageFileList(imgFiles, product));
            }
        } catch (ParseException e) {
            throw new CustomException(CommonErrorCode.INTERNAL_SERVER_ERROR.getErrorCode());
        }
    }

    private void uploadThumbNailImage(MultipartFile thumbNailFile, Product product) {
        try {
            if (thumbNailFile != null) {
                String url = awsS3Service.upload(thumbNailFile, FilePath.PRODUCT_THUMB_NAIL_DIR.getPath() + product.getId());
                product.setMainImageUrl(url);
            }
        } catch (IOException e) {
            throw new CustomException(CommonErrorCode.FAIL_TO_SAVE.getErrorCode());
        }
    }

    private List<ProductContentImage> uploadImageFileList(List<MultipartFile> imgList, Product product) {
        return imgList.stream().map(multipartFile -> {
            String uniqueIdentifier = UUID.randomUUID().toString();
            try {
                String url = awsS3Service.upload(multipartFile, FilePath.PRODUCT_CONTENT_DIR.getPath() + product.getId() + uniqueIdentifier);
                return ProductContentImage.from(product, url);
            } catch (IOException e) {
                throw new CustomException(CommonErrorCode.FAIL_TO_SAVE.getErrorCode());
            }
        }).collect(Collectors.toList());
    }

}

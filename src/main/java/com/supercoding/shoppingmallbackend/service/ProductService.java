package com.supercoding.shoppingmallbackend.service;


import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.*;
import com.supercoding.shoppingmallbackend.common.util.FilePath;
import com.supercoding.shoppingmallbackend.dto.request.ProductFileRequest;
import com.supercoding.shoppingmallbackend.dto.request.ProductListRequest;
import com.supercoding.shoppingmallbackend.dto.request.ProductRequestBase;
import com.supercoding.shoppingmallbackend.dto.response.ProductDetailResponse;
import com.supercoding.shoppingmallbackend.dto.response.ProductImageResponse;
import com.supercoding.shoppingmallbackend.dto.response.ProductListResponse;
import com.supercoding.shoppingmallbackend.entity.*;
import com.supercoding.shoppingmallbackend.repository.*;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
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
    private final ProductContentImageRepository productContentImageRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Transactional
    public ProductDetailResponse getProductByProductId(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ProductErrorCode.NOTFOUND_PRODUCT.getErrorCode()));
        List<ProductContentImage> productContentImageList = product.getProductContentImages();
        List<ProductImageResponse> productImageResponseList = productContentImageList
                .stream()
                .map(ProductImageResponse::from)
                .collect(Collectors.toList());
        List<Category> categories = categoryRepository.findCategoriesByProductId(product.getId());

        try {
            return ProductDetailResponse.from(product, productImageResponseList, categories);
        } catch (ParseException e) {
            throw new CustomException(CommonErrorCode.INTERNAL_SERVER_ERROR.getErrorCode());
        }
    }

    @Transactional
    public void createProductItem(ProductRequestBase productRequestBase,
                                  MultipartFile thumbFile,
                                  List<MultipartFile> imgFiles,
                                  Long profileIdx) {
        Long validProfileIdx = Optional.ofNullable(profileIdx)
                .orElseThrow(() -> new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode()));

        Seller seller = getSellerByProfileIdx(validProfileIdx);
        Genre genre = getGenreById(productRequestBase.getGenre());
        Category playerCount = getCategoryByName(productRequestBase.getPlayerCount());
        Category playTime = getCategoryByName(productRequestBase.getPlayTime());
        Category difficultyLevel = getCategoryByName(productRequestBase.getDifficultyLevel());

        boolean imageExists = Optional.ofNullable(imgFiles).isPresent();

        if (imageExists && imgFiles.size() > 5) throw new CustomException(ProductErrorCode.TOO_MANY_FILES);

        try {
            Product product = Product.from(productRequestBase, seller, genre);
            productRepository.save(product);
            if (product.getId() != null) {
                uploadThumbNailImage(thumbFile, product);
                product.addProductCategory(playerCount);
                product.addProductCategory(playTime);
                product.addProductCategory(difficultyLevel);
                if (imageExists) {
                    productContentImageRepository.saveAll(uploadImageFileList(imgFiles, product));
                }

            }
        } catch (ParseException e) {
            throw new CustomException(CommonErrorCode.INTERNAL_SERVER_ERROR.getErrorCode());
        }
    }


    @Transactional
    public void deleteProductByProductId(Long productId, Long profileIdx) {
        Product validProduct = validProfileAndProduct(productId, profileIdx);
        productRepository.deleteById(validProduct.getId());
    }

    @Transactional
    public List<ProductListResponse> getProductList(ProductListRequest productListRequest, Pageable pageable) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Page<ProductListResponse> productList =
                productRepository.findAvailableProductsBySearchCriteria(currentTimestamp, productListRequest, pageable);

        return productList.getContent();
    }


    @Transactional
    public void updateProductByProductId(Long productId, Long profileIdx, ProductFileRequest productFileRequest, MultipartFile thumbNailFile, List<MultipartFile> imageFiles) {
        Product originProduct = validProfileAndProduct(productId, profileIdx);

        Seller seller = getSellerByProfileIdx(profileIdx);
        Genre genre = getGenreById(productFileRequest.getGenre());

        List<String> originCategoriesNames = originProduct.getProductCategories().stream().map(productCategory -> productCategory.getCategory().getName()).collect(Collectors.toList());
        //order by type 으로 난이도, 시간, 인원수 순으로 list 순서 고정
        List<Category> oldCategories = categoryRepository.findCategoriesByName(originCategoriesNames);

        Category playerCount = getCategoryByName(productFileRequest.getPlayerCount());
        Category playTime = getCategoryByName(productFileRequest.getPlayTime());
        Category difficultyLevel = getCategoryByName(productFileRequest.getDifficultyLevel());

        boolean imageExists = Optional.ofNullable(imageFiles).isPresent();

        if (imageExists && imageFiles.size() > 5) throw new CustomException(ProductErrorCode.TOO_MANY_FILES);

        try {
            Product updateProduct = Product.from(originProduct, productFileRequest, genre);
            if (updateProduct.getId() != null) {
                updateProduct.updateProductCategory(oldCategories.get(2), playerCount);
                updateProduct.updateProductCategory(oldCategories.get(1), playTime);
                updateProduct.updateProductCategory(oldCategories.get(0), difficultyLevel);
                if (updateProduct.getMainImageUrl() != null) {
//                    deleteS3ServiceImage(updateProduct.getMainImageUrl());
//                    updateThumbNailImage(thumbNailFile, updateProduct);
                }

                if (imageExists) {
//                    productContentImageRepository.saveAll(uploadImageFileList(imageFiles, validProduct));
                }
            }
            productRepository.save(updateProduct);
        } catch (ParseException e) {
            throw new CustomException(CommonErrorCode.INTERNAL_SERVER_ERROR.getErrorCode());
        }

    }

    public Product validProfileAndProduct(Long productId, Long profileIdx) {
        Long validProfileIdx = Optional.ofNullable(profileIdx)
                .orElseThrow(() -> new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode()));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ProductErrorCode.NOTFOUND_PRODUCT));

        if (!Objects.equals(product.getSeller().getProfile().getId(), validProfileIdx)) {
            throw new CustomException(UserErrorCode.NOT_AUTHORIZED.getErrorCode());
        }
        return product;
    }

    public Seller getSellerByProfileIdx(Long profileIdx) {
        return sellerRepository.findByProfile_Id(profileIdx)
                .orElseThrow(() -> new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode()));
    }

    public Genre getGenreById(Long genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new CustomException(GenreErrorCode.NOT_FOUND));
    }

    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new CustomException(CategoryErrorCode.NOT_FOUND_BY_ID));
    }

    private void updateThumbNailImage(MultipartFile thumbNailFile, Product product) {
        try {
            if (thumbNailFile != null) {
                String url = awsS3Service.upload(thumbNailFile,
                        product.getMainImageUrl());
                product.setMainImageUrl(url);
            }
        } catch (IOException e) {
            throw new CustomException(CommonErrorCode.FAIL_TO_SAVE.getErrorCode());
        }
    }

    private void uploadThumbNailImage(MultipartFile thumbNailFile, Product product) {
        try {
            if (thumbNailFile != null) {
                String url = awsS3Service.upload(thumbNailFile,
                        FilePath.PRODUCT_THUMB_NAIL_DIR.getPath() + product.getId());
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
                String url = awsS3Service.upload(multipartFile,
                        FilePath.PRODUCT_CONTENT_DIR.getPath() + product.getId() + uniqueIdentifier);
                return ProductContentImage.from(product, url);
            } catch (IOException e) {
                throw new CustomException(CommonErrorCode.FAIL_TO_SAVE.getErrorCode());
            }
        }).collect(Collectors.toList());
    }

}

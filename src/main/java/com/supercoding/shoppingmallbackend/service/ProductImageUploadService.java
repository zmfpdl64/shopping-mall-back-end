package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.CommonErrorCode;
import com.supercoding.shoppingmallbackend.common.util.FilePath;
import com.supercoding.shoppingmallbackend.entity.Product;
import com.supercoding.shoppingmallbackend.entity.ProductContentImage;
import com.supercoding.shoppingmallbackend.repository.ProductContentImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImageUploadService {

    private final AwsS3Service awsS3Service;
    private final ProductContentImageRepository productContentImageRepository;

    @Async
    public CompletableFuture<Void> uploadThumbNailImage(MultipartFile thumbNailFile, Product product) {
        try {
            if (thumbNailFile != null) {
                String url = awsS3Service.memoryUpload(thumbNailFile,
                        FilePath.PRODUCT_THUMB_NAIL_DIR.getPath() + product.getId() + "/" + thumbNailFile.getOriginalFilename());
                product.setMainImageUrl(url);
            }
        } catch (IOException e) {
            throw new CustomException(CommonErrorCode.FAIL_TO_SAVE.getErrorCode());
        }
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Void> uploadImageFileList(List<MultipartFile> imgList, Product product) {
        List<ProductContentImage> productContentImageList = imgList.stream().map(multipartFile -> {
            String uniqueIdentifier = UUID.randomUUID().toString();
            try {
                String url = awsS3Service.memoryUpload(multipartFile,
                        FilePath.PRODUCT_CONTENT_DIR.getPath() + product.getId() + uniqueIdentifier);
                return ProductContentImage.from(product, url);
            } catch (IOException e) {
                throw new CustomException(CommonErrorCode.FAIL_TO_SAVE.getErrorCode());
            }
        }).collect(Collectors.toList());
        productContentImageRepository.saveAll(productContentImageList);

        return CompletableFuture.completedFuture(null);
    }

}

package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.ConsumerErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.ProductErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.UtilErrorCode;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.common.util.FilePath;
import com.supercoding.shoppingmallbackend.common.util.JpaUtils;
import com.supercoding.shoppingmallbackend.dto.request.ReviewRequest;
import com.supercoding.shoppingmallbackend.dto.response.ReviewResponse;
import com.supercoding.shoppingmallbackend.entity.Consumer;
import com.supercoding.shoppingmallbackend.entity.Product;
import com.supercoding.shoppingmallbackend.entity.Review;
import com.supercoding.shoppingmallbackend.repository.ConsumerRepository;
import com.supercoding.shoppingmallbackend.repository.ProductRepository;
import com.supercoding.shoppingmallbackend.repository.ReviewRepository;
import com.supercoding.shoppingmallbackend.security.AuthHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ConsumerRepository consumerRepository;
    private final ProductRepository productRepository;
    private final AwsS3Service awsS3Service;

    @Transactional
    public CommonResponse<ReviewResponse> createReview(ReviewRequest request, MultipartFile imageFile) {

        Consumer consumer = getConsumer();
        Product product = productRepository.findProductById(request.getProductId()).orElseThrow(()->new CustomException(ProductErrorCode.NOTFOUND_PRODUCT));

        Review newData = Review.from(consumer, product, request);
        newData.setIsDeleted(false);
        Review savedData = JpaUtils.managedSave(reviewRepository, newData);

        if (imageFile != null) {
            String imageUrl = uploadImageFile(imageFile, String.valueOf(savedData.getId()));
            savedData.setReviewImageUrl(imageUrl);
        }

        ReviewResponse response = ReviewResponse.from(savedData);
        return ApiUtils.success("리뷰를 성공적으로 작성했습니다.", response);
    }

    private String uploadImageFile(MultipartFile imageFile, String imageId) {
        if (imageFile == null) return null;

        try {
            return awsS3Service.upload(imageFile, FilePath.REVIEW_IMAGE_DIR.getPath()+imageId);
        } catch (IOException e) {
            throw new CustomException(UtilErrorCode.IOE_ERROR);
        }
    }

    private Consumer getConsumer() {
        return consumerRepository.findByProfileId(AuthHolder.getUserIdx()).orElseThrow(()->new CustomException(ConsumerErrorCode.NOT_FOUND_BY_ID));
    }
}

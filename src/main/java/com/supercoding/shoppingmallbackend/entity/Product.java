package com.supercoding.shoppingmallbackend.entity;

import com.supercoding.shoppingmallbackend.common.util.DateUtils;
import com.supercoding.shoppingmallbackend.dto.request.ProductFileRequest;
import com.supercoding.shoppingmallbackend.dto.request.ProductRequestBase;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "product")
@SQLDelete(sql = "UPDATE product as p SET p.is_deleted = true WHERE idx = ?")
@Where(clause = "is_deleted = false")
public class Product extends CommonField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "seller_idx", nullable = false)
    private Seller seller;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "genre_idx", nullable = false)
    private Genre genre;

    @Size(max = 50)
    @NotNull
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Lob
    @Column(name = "main_image_url")
    private String mainImageUrl;

    @NotNull
    @Column(name = "price", nullable = false)
    private Long price;

    @NotNull
    @Column(name = "closing_at", nullable = false)
    private Timestamp closingAt;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Long amount;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductCategory> productCategories = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductContentImage> productContentImages = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public void addProductCategory(Category category) {
        ProductCategory productCategory = ProductCategory.from(this, category);
        productCategories.add(productCategory);
        productCategory.setProduct(this);
    }

    public void updateProductCategory(Category oldCategory, Category newCategory) {
        ProductCategory existingProductCategory = findProductCategoryByCategory(oldCategory);

        if (existingProductCategory != null) {
            existingProductCategory.setCategory(newCategory);
        }
    }

    public void removeProductCategory(Category category) {
        ProductCategory productCategory = findProductCategoryByCategory(category);
        if (productCategory != null) {
            productCategories.remove(productCategory);
            productCategory.setProduct(null);
        }
    }

    private ProductCategory findProductCategoryByCategory(Category category) {
        return productCategories.stream()
                .filter(pc -> pc.getCategory().equals(category))
                .findFirst()
                .orElse(null);
    }

    public void addProductContentImage(ProductContentImage image) {
        productContentImages.add(image);
        image.setProduct(this);
    }

    public void removeProductContentImage(ProductContentImage image) {
        productContentImages.remove(image);
        image.setProduct(null);
    }

    public ProductContentImage findProductContentImageByImage(ProductContentImage image) {
        return productContentImages.stream()
                .filter(contentImage -> contentImage.equals(image))
                .findFirst()
                .orElse(null);
    }

    public String calculateFormattedAverageRating() {
        if (reviews.isEmpty()) {
            return "0.00"; // 리뷰가 없을 때 0.00으로 표시
        }

        double totalRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .sum();

        double avgRating = totalRating / reviews.size();
        return String.format("%.2f", avgRating); // 소수점 두 자리까지 포맷팅
    }


    public static Product from(ProductRequestBase productRequestBase, Seller seller, Genre genre) throws ParseException {
        return Product.builder()
                .seller(seller)
                .genre(genre)
                .title(productRequestBase.getTitle())
                .price(productRequestBase.getPrice())
                .closingAt(DateUtils.convertToTimestamp(productRequestBase.getClosingAt()))
                .amount(productRequestBase.getAmount())
                .productCategories(new ArrayList<>())
                .build();
    }

    public static Product from(Product originProduct, ProductFileRequest productFileRequest, Genre genre) throws ParseException {
        return Product.builder()
                .id(originProduct.getId())
                .seller(originProduct.getSeller())
                .genre(genre)
                .title(productFileRequest.getTitle())
                .price(productFileRequest.getPrice())
                .closingAt(DateUtils.convertToTimestamp(productFileRequest.getClosingAt()))
                .amount(productFileRequest.getAmount())
                .productCategories(originProduct.getProductCategories())
                .productContentImages(originProduct.getProductContentImages())
                .mainImageUrl(originProduct.getMainImageUrl())
                .reviews(originProduct.getReviews())
                .build();
    }

}
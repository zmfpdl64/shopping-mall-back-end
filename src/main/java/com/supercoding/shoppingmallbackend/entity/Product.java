package com.supercoding.shoppingmallbackend.entity;

import com.supercoding.shoppingmallbackend.common.util.DateUtils;
import com.supercoding.shoppingmallbackend.dto.request.ProductRequestBase;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.text.ParseException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "product")
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


    public static Product from(ProductRequestBase ProductRequestBase, Seller seller, Genre genre) throws ParseException {
        return Product.builder()
                .seller(seller)
                .genre(genre)
                .title(ProductRequestBase.getTitle())
                .price(ProductRequestBase.getPrice())
                .closingAt(DateUtils.convertToTimestamp(ProductRequestBase.getClosingAt()))
                .amount(ProductRequestBase.getAmount())
                .build();
    }

}
package com.supercoding.shoppingmallbackend.entity.orderBy;

public enum ReviewOrderBy {
    CREATION_DATE("creation-date", "createdAt"),
    POPULARITY("popularity", "rating");
    private final String value;
    private final String key;

    ReviewOrderBy(String key, String value) {
        this.key = key;
        this.value = value;
    }
}

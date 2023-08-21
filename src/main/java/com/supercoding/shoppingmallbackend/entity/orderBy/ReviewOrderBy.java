package com.supercoding.shoppingmallbackend.entity.orderBy;

import java.util.Arrays;

public enum ReviewOrderBy {
    CREATION_DATE("creation-date", "createdAt"),
    POPULARITY("popularity", "rating");
    private final String value;
    private final String key;

    public String getKey() { return key; }
    public String getValue() { return value; }

    ReviewOrderBy(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static ReviewOrderBy from(String key) {
        for (ReviewOrderBy e : ReviewOrderBy.values()) {
            if (e.key.equals(key)) return e;
        }

        return null;
    }
}

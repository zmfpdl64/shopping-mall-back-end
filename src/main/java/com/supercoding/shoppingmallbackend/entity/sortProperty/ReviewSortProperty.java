package com.supercoding.shoppingmallbackend.entity.sortProperty;

public enum ReviewSortProperty {
    CREATION_DATE("creation-date", "createdAt"),
    POPULARITY("popularity", "rating");
    private final String value;
    private final String key;

    public String getKey() { return key; }
    public String getValue() { return value; }

    ReviewSortProperty(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static ReviewSortProperty from(String key) {
        for (ReviewSortProperty e : ReviewSortProperty.values()) {
            if (e.key.equals(key)) return e;
        }
        return null;
    }

    public static String get(String key) {
        for (ReviewSortProperty e : ReviewSortProperty.values()) {
            if (e.key.equals(key)) return e.getValue();
        }
        return null;
    }
}

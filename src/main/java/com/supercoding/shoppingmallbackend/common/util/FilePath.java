package com.supercoding.shoppingmallbackend.common.util;

public enum FilePath {
    MEMBER_PROFILE_DIR("user/image/"),
    PRODUCT_THUMB_NAIL("product/thumbnails/");

    FilePath(String path) {
        this.path = path;
    }

    private final String path;

    public String getPath() {
        return path;
    }
}

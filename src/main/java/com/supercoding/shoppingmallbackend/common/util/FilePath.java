package com.supercoding.shoppingmallbackend.common.util;

public enum FilePath {
    MEMBER_PROFILE_DIR("user/image/"),
    PRODUCT_THUMB_NAIL_DIR("product/thumbnails/"),
    PRODUCT_CONTENT_DIR("product/content/"),
    REVIEW_IMAGE_DIR("review/image/");

    FilePath(String path) {
        this.path = path;
    }

    private final String path;

    public String getPath() {
        return path;
    }
}

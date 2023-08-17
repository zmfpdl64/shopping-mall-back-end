package com.supercoding.shoppingmallbackend.common.util;

public enum Regex {
    PASSWORD_PATTERN("(?=.*[0-9])(?=.*[a-zA-Z]).{8,20}"),
    PHONE_PATTERN("[0-9]{2,3}-[0-9]{3,4}-[0-9]{3,4}"),
    EMAIL_PATTERN("^[A-Za-z0-9+_-]+@+[A-Za-z0-9+_.-]+");

    private final String pattern;

    Regex(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}

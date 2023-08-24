package com.supercoding.shoppingmallbackend.entity;

public enum ProfileRole {
    SELLER("SELLER"),
    EMPTY("EMPTY"),
    CONSUMER("CONSUMER");

    private final String position;

    ProfileRole(String position) {
        this.position = position;
    }
    public static ProfileRole getProfileRole(String position) {
        for(ProfileRole role: ProfileRole.values()){
            if(role.name().equals(position)) return role;
        }
        return ProfileRole.EMPTY;
    }

    public String getPosition() {
        return position;
    }
}

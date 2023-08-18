package com.supercoding.shoppingmallbackend.dto;

import com.supercoding.shoppingmallbackend.entity.Profile;
import com.supercoding.shoppingmallbackend.entity.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Getter
public class ProfileDetail implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private String imageUrl;
    private String name;
    private String phone;
    private Long phonemoney;
    private ProfileRole role;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Boolean isDeleted;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getRole().name()));
    }

    @Override
    public String getUsername() {
        return this.getName();
    }
    @Override
    public boolean isAccountNonExpired() {
        return this.isDeleted == null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isDeleted == null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isDeleted == null;
    }

    @Override
    public boolean isEnabled() {
        return this.isDeleted == null;
    }
    public static ProfileDetail from(Profile profile) {
        return new ProfileDetail(
                profile.getId(),
                profile.getEmail(),
                profile.getPassword(),
                profile.getImageUrl(),
                profile.getName(),
                profile.getPhone(),
                profile.getPaymoney(),
                profile.getRole(),
                profile.getCreatedAt(),
                profile.getUpdatedAt(),
                profile.getIsDeleted()
        );
    }
}

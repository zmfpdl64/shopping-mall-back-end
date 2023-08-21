package com.supercoding.shoppingmallbackend.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE Profiles as p SET p.is_deleted = true WHERE idx = ?")
@Where(clause = "is_deleted = false")
@Builder
@Getter
@Setter
@Entity
@Table(name = "profiles")
public class Profile extends CommonField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long id;

    @Size(max = 63)
    @NotNull
    @Column(name = "email", nullable = false, length = 63)
    private String email;

    @Size(max = 63)
    @NotNull
    @Column(name = "password", nullable = false, length = 63)
    private String password;

    @Lob
    @Column(name = "image_url")
    private String imageUrl;

    @Size(max = 63)
    @NotNull
    @Column(name = "name", nullable = false, length = 63)
    private String name;

    @Size(max = 15)
    @NotNull
    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @NotNull
    @Column(name = "paymoney", nullable = false)
    private Long paymoney;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses =  new ArrayList<>();

    public void addProfileAddress(Address address) {
        addresses.add(address);
        address.setProfile(this);
    }

    public void removeProfileAddress(Address address) {
        addresses.remove(address);
        address.setProfile(null);
    }


    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProfileRole role;
}
package com.supercoding.shoppingmallbackend.dto.request;

import lombok.*;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ShoppingCartIdSetRepuest {
    Set<Long> shoppingCartIdSet;
}

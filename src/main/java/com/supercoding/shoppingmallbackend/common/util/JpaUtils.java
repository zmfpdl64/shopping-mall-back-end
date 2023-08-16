package com.supercoding.shoppingmallbackend.common.util;

import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;

@Slf4j
public class JpaUtils {
    public static <T, ID> T managedSave(@NotNull JpaRepository<T, ID> repository, @NotNull T entity) {
        for (int i = 1; i <= 3; i++) {
            try {
                return repository.save(entity);
            } catch (OptimisticLockingFailureException e) {
                log.warn("데이터 저장 중 충돌이 발생하여 저장에 실패했습니다. (시도횟수: {})", i);
            }
        }

        throw new CustomException(CommonErrorCode.FAIL_TO_SAVE);
    }
}

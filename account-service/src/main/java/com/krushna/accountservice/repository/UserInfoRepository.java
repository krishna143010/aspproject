package com.krushna.accountservice.repository;
import com.krushna.accountservice.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByUsername(String username);
    Optional<UserInfo> findByEmail(String email);
}

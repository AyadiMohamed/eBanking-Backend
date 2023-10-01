package com.ayadi.ebankingbackend.repositories;

import com.ayadi.ebankingbackend.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo , Long> {
    Optional<UserInfo> findByName(String username);
}

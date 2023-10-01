package com.ayadi.ebankingbackend.services;

import com.ayadi.ebankingbackend.config.PasswordConfig;
import com.ayadi.ebankingbackend.entities.UserInfo;
import com.ayadi.ebankingbackend.repositories.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;
   @Autowired
    private PasswordConfig passwordConfig;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> userDetail = userInfoRepository.findByName(username);
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
    public UserInfo addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordConfig.passwordEncoder().encode(userInfo.getPassword()));
        return userInfoRepository.save(userInfo);

    }
}

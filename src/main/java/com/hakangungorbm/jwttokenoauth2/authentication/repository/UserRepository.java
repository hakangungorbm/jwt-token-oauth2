package com.hakangungorbm.jwttokenoauth2.authentication.repository;

import com.hakangungorbm.jwttokenoauth2.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * @author HakanGungorBm
 * @date 5.08.2022
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findUserByUsername(String username);
}

package com.hakangungorbm.jwttokenoauth2.authorization;

import com.hakangungorbm.jwttokenoauth2.authentication.dto.PrincipalDetails;
import com.hakangungorbm.jwttokenoauth2.authentication.entity.User;
import com.hakangungorbm.jwttokenoauth2.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author HakanGungorBm
 * @date 1.08.2022
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isPresent()) {
            return new PrincipalDetails(user.get());
        } else {
            //todo burası düzenlenecek
            return null;
        }

    }
}

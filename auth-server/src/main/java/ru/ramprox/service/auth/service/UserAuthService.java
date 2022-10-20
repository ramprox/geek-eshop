package ru.ramprox.service.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ramprox.service.auth.database.entity.Role;
import ru.ramprox.service.auth.database.repository.UserRepository;
import ru.ramprox.service.auth.model.AuthUser;

import java.util.stream.Collectors;

@Service
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new AuthUser(user.getId(), user.getUsername(), user.getPassword(),
                        user.getRoles().stream()
                                .map(Role::getName)
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList())))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь с именем '%s' не найден", username)));
    }
}

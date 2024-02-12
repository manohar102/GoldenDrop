package com.techbuddy.goldendrop.service;

import com.techbuddy.goldendrop.model.User;
import com.techbuddy.goldendrop.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;

@Service(value = "userService")
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public User loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUserName(userName);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("No User found with User Name : %s", userName));
        }
        return user.get();
    }

    public Page<User> findAll(Specification<User> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    public User findById(Long id) throws Exception {
        return repository
                .findById(id)
                .orElseThrow(() -> new Exception(String.format("User not found with id : %d", id)));
    }

    public User findByUserName(String email) {
        return repository
                .findByUserName(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("User not found with User Name : %d", email)));
    }

    public User save(User user) {
        return repository.save(user);
    }

    public Optional<User> fetchUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() != null) {
            return Optional.of((User) auth.getPrincipal());
        }
        return Optional.empty();
    }
}

package com.techbuddy.goldendrop.service;

import com.techbuddy.goldendrop.model.User;
import com.techbuddy.goldendrop.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userService")
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = repository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("No User found with email : %s", email));
        }
        return user.get();
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) throws Exception {
        return repository
                .findById(id)
                .orElseThrow(() -> new Exception(String.format("User not found with id : %d", id)));
    }

    public User findByEmail(String email) {
        return repository
                .findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("User not found with Email : %d", email)));
    }

    public User save(User user) {
        return repository.save(user);
    }
}

package com.techbuddy.goldendrop.repository;


import com.techbuddy.goldendrop.model.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends BaseRepository<User, Long>{
    Optional<User> findByEmail(String email);
}

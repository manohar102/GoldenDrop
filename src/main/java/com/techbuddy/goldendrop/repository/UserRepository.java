package com.techbuddy.goldendrop.repository;

import com.techbuddy.goldendrop.model.User;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findByUserName(String userName);
}

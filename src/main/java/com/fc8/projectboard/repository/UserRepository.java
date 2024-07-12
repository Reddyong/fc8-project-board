package com.fc8.projectboard.repository;

import com.fc8.projectboard.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

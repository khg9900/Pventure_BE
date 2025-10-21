package com.example.pventure.domain.user.repository;

import com.example.pventure.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

package com.antrepbo.antrepbo2.repository;

import com.antrepbo.antrepbo2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Tambahkan ini supaya UserService bisa memanggilnya
    User findByUsername(String username);
}
package com.antrepbo.antrepbo2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

// Import Model dan Repository kamu
import com.antrepbo.antrepbo2.model.User;
import com.antrepbo.antrepbo2.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // simpan user
    public User saveUser(@NonNull User user) {
        return userRepository.save(user);
    }

    // ambil semua user
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // cari user berdasarkan id
    public User getUserById(@NonNull Long id) {
        // Menggunakan .orElse(null) atau .orElseThrow() agar tidak warning Optional
        return userRepository.findById(id).orElse(null);
    }

    // hapus user
    public void deleteUser(@NonNull Long id) {
        userRepository.deleteById(id);
    }

    // login username
    public User login(String username) {
        return userRepository.findByUsername(username);
    }
}
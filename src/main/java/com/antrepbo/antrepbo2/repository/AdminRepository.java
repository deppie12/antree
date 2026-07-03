package com.antrepbo.antrepbo2.repository;

import com.antrepbo.antrepbo2.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    
    // Baris ini wajib ada agar AdminService bisa mencari admin berdasarkan email
    Admin findByEmailAndPassword(String email, String password);
}
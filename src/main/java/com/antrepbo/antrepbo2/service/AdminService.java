package com.antrepbo.antrepbo2.service;

import com.antrepbo.antrepbo2.model.Admin;
import com.antrepbo.antrepbo2.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // simpan admin
    public Admin saveAdmin(@NonNull Admin admin){
        return adminRepository.save(admin);
    }

    // tampil semua admin
    public List<Admin> getAllAdmin(){
        return adminRepository.findAll();
    }

    // cari admin berdasarkan id
    public Admin getAdminById(int id){
        return adminRepository.findById(id).orElse(null);
    }

    // hapus admin
    public void deleteAdmin(int id){
        adminRepository.deleteById(id);
    }

    // ================= LOGIN ADMIN =================
    // Tambahkan method ini agar Controller tidak error
    public Admin loginAdmin(String email, String password) {
        // Pastikan di AdminRepository kamu sudah membuat method findByEmailAndPassword
        return adminRepository.findByEmailAndPassword(email, password);
    }
}
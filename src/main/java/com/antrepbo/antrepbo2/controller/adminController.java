package com.antrepbo.antrepbo2.controller;

import com.antrepbo.antrepbo2.model.Admin;
import com.antrepbo.antrepbo2.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class adminController {

    @Autowired
    private AdminService adminService;

    // READ: Ambil semua data
    @GetMapping
    public List<Admin> getAllAdmin() {
        return adminService.getAllAdmin(); // Diperbaiki: sesuaikan dengan Service
    }

    // CREATE: Tambah admin baru
    @PostMapping
    public Admin createAdmin(@RequestBody @NonNull Admin admin) {
        return adminService.saveAdmin(admin); // Diperbaiki: sesuaikan dengan Service
    }

    // UPDATE: Ubah data admin
    @PutMapping("/{id}")
    public Admin updateAdmin(@PathVariable int id, @RequestBody Admin adminDetails) {
        // Diperbaiki: getAdminById mengembalikan Admin (bukan Optional), jadi tidak perlu .orElseThrow()
        Admin admin = adminService.getAdminById(id); 
        
        if (admin != null) {
            admin.setNama(adminDetails.getNama());
            admin.setEmail(adminDetails.getEmail());
            admin.setPassword(adminDetails.getPassword());
            return adminService.saveAdmin(admin); // Diperbaiki
        }
        return null; // Atau kamu bisa melempar error di sini jika admin tidak ditemukan
    }

    // DELETE: Hapus admin
    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable int id) {
        adminService.deleteAdmin(id); // Diperbaiki: sesuaikan dengan Service
    }

   // ================= LOGIN ADMIN =================
    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody Admin loginData) {
        Admin admin = adminService.loginAdmin(loginData.getEmail(), loginData.getPassword());
        
        if (admin != null) {
            return ResponseEntity.ok(admin); // Berhasil
        } else {
            // Mengirim status 401 agar masuk ke blok .catch() di JavaScript
            return ResponseEntity.status(401).body("Email atau Password salah"); 
        }
    }
}
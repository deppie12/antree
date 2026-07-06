package com.antrepbo.antrepbo2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.antrepbo.antrepbo2.model.User;
import com.antrepbo.antrepbo2.service.UserService;
import java.util.Map;

@Controller
@CrossOrigin(origins = "*") // Tambahan agar bisa diakses oleh file HTML Anda
public class UserController {

    @Autowired
    private UserService userService;

    // Ini fungsi register lama Anda, biarkan tetap ada
    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password
    ) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.saveUser(user);

        return "redirect:/login.html";
    }

    // TAMBAHKAN FUNGSI BARU INI DI BAWAHNYA
    @PostMapping("/api/user/login")
    @ResponseBody
    public ResponseEntity<?> loginUserAPI(@RequestBody Map<String, String> dataLogin) {
        String email = dataLogin.get("email");
        String password = dataLogin.get("password");

        // Cari di tabel USERS berdasarkan email (yang disimpan di kolom username)
        User user = userService.login(email);
        
        if (user != null && user.getPassword().equals(password)) {
            return ResponseEntity.ok(user); // Login Sukses
        } else {
            return ResponseEntity.status(401).body("Email atau Password salah"); // Login Gagal
        }
    }
}
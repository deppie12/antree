package com.antrepbo.antrepbo2.controller;
import com.antrepbo.antrepbo2.model.pendaftaran;
import com.antrepbo.antrepbo2.service.PendaftaranService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/daftar")
@CrossOrigin(origins = "*") 
public class PendaftaranController {

    @Autowired
    private PendaftaranService pendaftaranService;

    // 1. TARO METHOD POST DI SINI (Untuk Simpan Data dari Form Pendaftaran)
    @PostMapping
    public pendaftaran simpanPendaftar(@RequestBody @NonNull pendaftaran dataBaru) {
    // Simpan ke database H2
        pendaftaranService.simpanPendaftar(dataBaru);
        return dataBaru;
    }

    // 2. Method GET (Untuk Ambil Data ke Halaman Admin)
    @GetMapping
    public List<pendaftaran> getAllPendaftar() {
        return pendaftaranService.getAllPendaftar();
    }
    // 3. Method DELETE (Untuk Hapus Data di Halaman Admin)
    @DeleteMapping("/{id}")
    public void deletePendaftar(@PathVariable int id) {
        pendaftaranService.deletePendaftar(id);
    }
    // Method untuk Mengupdate Data Pendaftar
    @PutMapping("/{id}")
    public pendaftaran updatePendaftar(@PathVariable int id, @RequestBody pendaftaran dataBaru) {
        return pendaftaranService.updatePendaftar(id, dataBaru);
            
    }
}

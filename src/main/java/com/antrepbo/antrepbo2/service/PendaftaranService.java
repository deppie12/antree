package com.antrepbo.antrepbo2.service;

import com.antrepbo.antrepbo2.model.pendaftaran;
import com.antrepbo.antrepbo2.model.User; // 1. TAMBAHKAN IMPORT INI
import com.antrepbo.antrepbo2.repository.PendaftaranRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PendaftaranService {

    @Autowired
    private PendaftaranRepository pendaftaranRepository;

    @Autowired
    private UserService userService; // 2. TAMBAHKAN INI AGAR BISA MENGAKSES TABEL USERS

    public pendaftaran simpanPendaftar(@NonNull pendaftaran dataBaru) {
        // Aksi 1: Tetap simpan data lengkap ke tabel pendaftaran asli Anda
        pendaftaran pendaftarTersimpan = pendaftaranRepository.save(dataBaru);

        // Aksi 2: Otomatis daftarkan juga email & sandi ke tabel USERS
        User userBaru = new User();
        userBaru.setUsername(dataBaru.getEmail()); // Email pendaftar jadi username
        userBaru.setPassword(dataBaru.getSandi()); // Sandi pendaftar jadi password
        userService.saveUser(userBaru); // Simpan ke tabel USERS

        return pendaftarTersimpan;
    }

    public List<pendaftaran> getAllPendaftar() {
        return pendaftaranRepository.findAll();
    }

    public void deletePendaftar(int id) {
        pendaftaranRepository.deleteById(id);
    }

    public pendaftaran updatePendaftar(int id, pendaftaran dataBaru) {
        return pendaftaranRepository.findById(id)
            .map(pendaftar -> {
                pendaftar.setNama(dataBaru.getNama());
                pendaftar.setNik(dataBaru.getNik());
                pendaftar.setWhatsapp(dataBaru.getWhatsapp());
                pendaftar.setEmail(dataBaru.getEmail());
                return pendaftaranRepository.save(pendaftar);
            })
            .orElseGet(() -> {
                dataBaru.setId(id);
                return pendaftaranRepository.save(dataBaru);
            });
    }
}
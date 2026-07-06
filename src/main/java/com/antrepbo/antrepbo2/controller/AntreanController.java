package com.antrepbo.antrepbo2.controller;

import com.antrepbo.antrepbo2.model.Antrean;
import com.antrepbo.antrepbo2.service.AntreanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List; // 💡 TAMBAHKAN IMPORT INI

@RestController
@RequestMapping("/api/antrean")
@CrossOrigin(origins = "*") // 💡 SANGAT DIREKOMENDASIKAN agar tidak terkena Error CORS saat dipanggil HTML
public class AntreanController {

    @Autowired
    private AntreanService antreanService;

    // 1. Jalur POST untuk mendaftar antrean (Sudah ada & sudah berhasil)
    @PostMapping("/daftar")
    public Antrean simpanAntrean(@RequestBody @NonNull Antrean antrean) {
        return antreanService.saveAntrean(antrean);
    }

    // 2. Jalur GET BARU untuk mengambil semua data (Riwayat Kunjungan)
    @GetMapping
    public List<Antrean> ambilSemuaAntrean() {
        return antreanService.getAllAntrean();
    }
}
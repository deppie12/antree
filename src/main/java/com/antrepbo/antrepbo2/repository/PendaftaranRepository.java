package com.antrepbo.antrepbo2.repository;

import com.antrepbo.antrepbo2.model.pendaftaran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendaftaranRepository extends JpaRepository<pendaftaran, Integer> {
    // Spring Data JPA otomatis menangani fungsi simpan ke database
}
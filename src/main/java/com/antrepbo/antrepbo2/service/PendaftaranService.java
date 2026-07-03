package com.antrepbo.antrepbo2.service;

import com.antrepbo.antrepbo2.model.pendaftaran;
import com.antrepbo.antrepbo2.repository.PendaftaranRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PendaftaranService {

    @Autowired
    private PendaftaranRepository pendaftaranRepository;

    public pendaftaran simpanPendaftar(@NonNull pendaftaran dataBaru) {
        return pendaftaranRepository.save(dataBaru);
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
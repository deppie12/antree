package com.antrepbo.antrepbo2.service;

import com.antrepbo.antrepbo2.model.Antrean;
import com.antrepbo.antrepbo2.repository.AntreanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AntreanService {

    @Autowired
    private AntreanRepository antreanRepository;

    // simpan antrean
    public Antrean saveAntrean(@NonNull Antrean antrean){
        return antreanRepository.save(antrean);
    }

    // tampil semua antrean
    public List<Antrean> getAllAntrean(){
        return antreanRepository.findAll();
    }

    // cari antrean by id
    public Antrean getAntreanById(int id){
        return antreanRepository.findById(id).orElse(null);
    }

    // hapus antrean
    public void deleteAntrean(int id){
        antreanRepository.deleteById(id);
    }

}
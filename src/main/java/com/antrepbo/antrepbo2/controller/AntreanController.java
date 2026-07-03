package com.antrepbo.antrepbo2.controller;
import com.antrepbo.antrepbo2.model.Antrean;
import com.antrepbo.antrepbo2.service.AntreanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/antrean")
public class AntreanController {

    @Autowired
    private AntreanService antreanService;

    @PostMapping("/daftar")
    public Antrean simpanAntrean(@RequestBody @NonNull Antrean antrean) {
        return antreanService.saveAntrean(antrean);
    }
}
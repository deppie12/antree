package com.antrepbo.antrepbo2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "antrean")
public class Antrean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String namaPasien;
    private String poli;
    private String dokter;
    private String tanggal;

    public Antrean() {
    }

    public Antrean(int id, String namaPasien, String poli, String dokter, String tanggal) {
        this.id = id;
        this.namaPasien = namaPasien;
        this.poli = poli;
        this.dokter = dokter;
        this.tanggal = tanggal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaPasien() {
        return namaPasien;
    }

    public void setNamaPasien(String namaPasien) {
        this.namaPasien = namaPasien;
    }

    public String getPoli() {
        return poli;
    }

    public void setPoli(String poli) {
        this.poli = poli;
    }

    public String getDokter() {
        return dokter;
    }

    public void setDokter(String dokter) {
        this.dokter = dokter;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
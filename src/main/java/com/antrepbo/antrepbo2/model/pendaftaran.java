package com.antrepbo.antrepbo2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pendaftaran")
public class pendaftaran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nama;
    private String nik;
    private String whatsapp;
    private String email;
    private String sandi;

    public pendaftaran() {}

    // Getter dan Setter untuk semua field (nama, nik, whatsapp, email, sandi)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }
    public String getWhatsapp() { return whatsapp; }
    public void setWhatsapp(String whatsapp) { this.whatsapp = whatsapp; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSandi() { return sandi; }
    public void setSandi(String sandi) { this.sandi = sandi; }
}
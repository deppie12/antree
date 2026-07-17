# ANTREE — Sistem Antrean Rumah Sakit

Aplikasi web untuk mengelola pendaftaran akun pasien, login, pendaftaran antrean poli/dokter, riwayat kunjungan, serta panel admin untuk mengelola data pendaftar. Dibangun dengan Java Spring Boot di sisi backend dan HTML + Tailwind (CDN) + vanilla JavaScript di sisi frontend.

> Proyek ini merupakan tugas mata kuliah Pemrograman Berorientasi Objek (PBO). Folder utama yang aktif dikembangkan adalah `antrepbo/antrepbo2`

## Deskripsi Project

ANTREE adalah sistem antrean rumah sakit sederhana yang memungkinkan:

- **Pasien** mendaftar akun, login, memilih poli/dokter/tanggal untuk mendapatkan nomor antrean, dan melihat riwayat kunjungan mereka.
- **Admin** login melalui halaman yang sama dengan pasien (sistem mendeteksi otomatis role login), lalu mengelola (lihat, ubah, hapus) data pendaftar melalui panel admin.

Backend menyediakan REST API sederhana (tanpa autentikasi token/session — pengecekan password dilakukan langsung terhadap data di database), sedangkan frontend adalah halaman statis yang memanggil API tersebut lewat `fetch`.

## Preview

Aplikasi terdiri dari 4 halaman utama:

| Halaman | File | Fungsi |
|---|---|---|
| Login | `login.html` | Form masuk untuk admin & pasien dalam satu pintu |
| Pendaftaran Akun | `pendaftaran.html` | Form pendaftaran akun pasien baru (nama, NIK, WhatsApp, email, sandi) |
| Dashboard Pasien | `dashboard.html` | Daftar antrean baru (poli, dokter, tanggal) & lihat riwayat kunjungan |
| Panel Admin | `admin.html` | Kelola (lihat/ubah/hapus) data pendaftar |

Tampilan menggunakan Tailwind CSS (via CDN) dengan tema warna biru (`primary #004482`) dan hijau (`secondary #006d37`), font Plus Jakarta Sans/Inter, serta ikon Material Symbols. Untuk melihat tampilan langsung, jalankan aplikasi secara lokal (lihat bagian [Menjalankan Secara Lokal](#menjalankan-secara-lokal)) lalu buka halaman-halaman di atas melalui browser.

## Cara Kerja

**1. Pendaftaran akun baru** (`pendaftaran.html`)
```
Pasien isi form (nama, nik, whatsapp, email, sandi)
  → validasi sandi vs konfirmasi sandi (di JS)
  → POST /api/daftar
  → PendaftaranService.simpanPendaftar()
       ├─ simpan record lengkap ke tabel `pendaftaran`
       └─ otomatis buat akun baru di tabel `USERS`
             (email pendaftar → username, sandi pendaftar → password)
  → redirect ke login.html / dashboard.html
```

**2. Login** (`login.html`)
```
User isi email & password
  → POST /api/admin/login
       ├─ berhasil → role = admin → redirect admin.html
       └─ gagal    → coba POST /api/user/login
                        ├─ berhasil → role = user → redirect dashboard.html
                        └─ gagal juga → tampilkan "Email atau Password salah!"
```
Sistem mencoba login sebagai **admin** terlebih dahulu, baru fallback ke akun **pasien** biasa.

**3. Dashboard pasien** (`dashboard.html`)
```
Pilih poli, dokter, tanggal
  → POST /api/antrean/daftar
  → simpan ke tabel `antrean`
  → tampilkan nomor antrean

Riwayat kunjungan:
  → GET /api/antrean
  → filter di sisi client berdasarkan nama pasien aktif
  → tampilkan sebagai tabel riwayat
```

**4. Panel admin** (`admin.html`)
```
GET    /api/daftar       → tampilkan semua data pendaftar
PUT    /api/daftar/{id}  → ubah data pendaftar
DELETE /api/daftar/{id}  → hapus data pendaftar
```

### REST API

| Method | Endpoint | Controller | Fungsi |
|---|---|---|---|
| POST | `/register` | `UserController` | Registrasi user manual (form-encoded) |
| POST | `/api/user/login` | `UserController` | Login pasien (cek `username` & `password`) |
| GET | `/api/admin` | `adminController` | Ambil semua admin |
| POST | `/api/admin` | `adminController` | Tambah admin baru |
| PUT | `/api/admin/{id}` | `adminController` | Ubah data admin |
| DELETE | `/api/admin/{id}` | `adminController` | Hapus admin |
| POST | `/api/admin/login` | `adminController` | Login admin (cek `email` & `password`) |
| POST | `/api/daftar` | `PendaftaranController` | Simpan pendaftar baru + auto-buat akun `USERS` |
| GET | `/api/daftar` | `PendaftaranController` | Ambil semua data pendaftar |
| PUT | `/api/daftar/{id}` | `PendaftaranController` | Ubah data pendaftar |
| DELETE | `/api/daftar/{id}` | `PendaftaranController` | Hapus data pendaftar |
| POST | `/api/antrean/daftar` | `AntreanController` | Simpan antrean baru |
| GET | `/api/antrean` | `AntreanController` | Ambil semua data antrean (riwayat kunjungan) |

## Tech Stack

**Backend**
- Java 17
- Spring Boot 3.3.5 (Spring Web, Spring Data JPA)
- Thymeleaf
- Lombok
- Maven (dengan Maven Wrapper `mvnw`)

**Database**
- PostgreSQL (Supabase, untuk production) via env var `DB_PASSWORD`
- H2 (dependency runtime, tersedia untuk pengujian/lokal)

**Frontend**
- HTML statis
- Tailwind CSS (via CDN)
- Vanilla JavaScript (`fetch` API)
- Google Fonts (Plus Jakarta Sans, Inter) & Material Symbols

**Deployment**
- Railway (`https://antree-production.up.railway.app`)

## Struktur Project

```
antrepbo2/
├── src/
│   ├── main/
│   │   ├── java/com/antrepbo/antrepbo2/
│   │   │   ├── Antrepbo2Application.java     # Entry point Spring Boot
│   │   │   ├── controller/
│   │   │   │   ├── AntreanController.java
│   │   │   │   ├── PendaftaranController.java
│   │   │   │   ├── UserController.java
│   │   │   │   └── adminController.java
│   │   │   ├── model/
│   │   │   │   ├── Admin.java
│   │   │   │   ├── Antrean.java
│   │   │   │   ├── User.java
│   │   │   │   └── pendaftaran.java
│   │   │   ├── repository/
│   │   │   │   ├── AdminRepository.java
│   │   │   │   ├── AntreanRepository.java
│   │   │   │   ├── PendaftaranRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   └── service/
│   │   │       ├── AdminService.java
│   │   │       ├── AntreanService.java
│   │   │       ├── PendaftaranService.java
│   │   │       └── UserService.java
│   │   └── resources/
│   │       ├── application.properties        # Konfigurasi DB & port
│   │       └── static/
│   │           ├── login.html
│   │           ├── pendaftaran.html
│   │           ├── dashboard.html
│   │           ├── admin.html
│   │           └── js/antrean.js
│   └── test/
│       └── java/com/antrepbo/antrepbo2/
│           └── Antrepbo2ApplicationTests.java
├── data/                # File database H2 lokal (auto-generated, aman dihapus)
├── pom.xml
├── mvnw / mvnw.cmd       # Maven Wrapper
└── README.md
```

## Prerequisites

Sebelum menjalankan project ini, pastikan sudah terpasang:

- **JDK 17** atau lebih baru
- **Maven** (opsional — project sudah menyertakan Maven Wrapper `mvnw`/`mvnw.cmd`)
- **Git** (untuk clone repository)
- Akses ke database **PostgreSQL** (mis. Supabase) jika ingin menjalankan dengan konfigurasi production, **atau** cukup andalkan **H2** (in-memory/file) untuk uji coba lokal tanpa setup database eksternal
- Browser modern (Chrome/Edge/Firefox) untuk membuka halaman frontend statis

## Menjalankan Secara Lokal

1. **Clone repository dan masuk ke folder project utama**
   ```bash
   git clone <url-repo-ini>
   cd antree-pbo/antrepbo/antrepbo2
   ```

2. **Atur environment variable** (jika ingin memakai database PostgreSQL/Supabase seperti pada `application.properties` saat ini)
   ```bash
   export DB_PASSWORD=password_database_anda
   ```
   Jika tidak diset, aplikasi akan gagal terhubung ke datasource karena `application.properties` saat ini mengarah ke PostgreSQL Supabase. Untuk menjalankan sepenuhnya lokal tanpa database eksternal, ganti sementara konfigurasi datasource ke H2 (lihat bagian [Konfigurasi](#konfigurasi)).

3. **Jalankan aplikasi dengan Maven Wrapper**
   ```bash
   ./mvnw spring-boot:run        # Linux/Mac
   mvnw.cmd spring-boot:run      # Windows
   ```

4. **Akses aplikasi**
   Aplikasi berjalan di `http://localhost:9999` (atau sesuai env var `PORT`). Buka halaman berikut di browser:
   - `http://localhost:9999/login.html`
   - `http://localhost:9999/pendaftaran.html`
   - `http://localhost:9999/dashboard.html`
   - `http://localhost:9999/admin.html`

   > ⚠️ **Catatan penting:** file-file statis di `static/*.html` saat ini melakukan `fetch` ke URL production yang sudah di-hardcode (`https://antree-production.up.railway.app/...`), bukan ke origin lokal. Agar aplikasi benar-benar terhubung ke backend lokal saat dijalankan di komputer sendiri, ubah base URL tersebut menjadi `http://localhost:9999` (atau port lokal yang dipakai) di setiap file HTML/JS terkait sebelum menguji end-to-end secara lokal.

## Konfigurasi

Konfigurasi utama ada di `src/main/resources/application.properties`:

```properties
spring.application.name=antrepbo2
server.port=${PORT:9999}

# Database PostgreSQL (Supabase)
spring.datasource.url=jdbc:postgresql://<host-pooler-supabase>:6543/postgres?prepareThreshold=0
spring.datasource.username=postgres.<project-ref>
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
spring.jpa.open-in-view=false

spring.datasource.hikari.maximum-pool-size=5
spring.datasource.hikari.idle-timeout=30000
spring.datasource.hikari.connection-timeout=20000
```

**Environment variable yang dibutuhkan:**

| Variabel | Wajib | Keterangan |
|---|---|---|
| `DB_PASSWORD` | Ya (untuk mode PostgreSQL) | Password akun database PostgreSQL/Supabase |
| `PORT` | Tidak | Port server, default `9999` |

**Menjalankan dengan H2 (opsional, tanpa database eksternal):**
Ganti bagian datasource di `application.properties` menjadi konfigurasi H2, misalnya:
```properties
spring.datasource.url=jdbc:h2:file:./data/antrepbodb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```
File database H2 akan otomatis dibuat di folder `data/` saat aplikasi pertama kali dijalankan.

## Deploy ke Production

Aplikasi ini saat ini di-deploy ke **Railway** dan dapat diakses di `https://antree-production.up.railway.app`.

Langkah umum deploy ke Railway (atau platform serupa yang mendukung Java/Maven):

1. Buat project baru di Railway dan hubungkan ke repository ini (arahkan root directory ke `antrepbo/antrepbo2` jika platform mendukung monorepo).
2. Set environment variable di dashboard Railway:
   - `DB_PASSWORD` — password database PostgreSQL/Supabase
   - `PORT` — biasanya otomatis di-inject oleh Railway, tidak perlu diset manual
3. Railway akan mendeteksi `pom.xml` dan menjalankan build Maven (`./mvnw clean package`) secara otomatis, lalu menjalankan artifact hasil build (`spring-boot-maven-plugin` sudah dikonfigurasi di `pom.xml`).
4. Setelah deploy berhasil, pastikan database Supabase (atau PostgreSQL lain yang dipakai) sudah dapat diakses dari luar (connection pooling/whitelist IP sesuai kebutuhan).
5. Karena file statis (`login.html`, `dashboard.html`, dll.) memanggil API dengan base URL yang di-hardcode, pastikan URL tersebut sesuai dengan domain hasil deploy sebelum build/push ke production.

## Menjalankan Test

Project menyertakan test dasar Spring Boot (`Antrepbo2ApplicationTests`) yang memverifikasi Spring context dapat dimuat dengan benar.

```bash
./mvnw test        # Linux/Mac
mvnw.cmd test       # Windows
```

> Saat ini hanya tersedia satu test (`contextLoads`) yang mengecek aplikasi dapat start tanpa error. Belum ada unit test untuk service/repository maupun test untuk endpoint REST API secara spesifik.

## Batasan Desain

Beberapa keterbatasan yang perlu diketahui pengguna/pengembang selanjutnya:

- **Password disimpan dalam plain text** — belum ada hashing (mis. BCrypt) pada `Admin`, `User`, maupun `pendaftaran`, dan proses login membandingkan password secara langsung (`equals`).
- **Tidak ada session/token autentikasi** — status login (admin/pasien) hanya disimpan di `localStorage` browser, tanpa mekanisme JWT/session di server. Tidak ada proteksi endpoint berbasis role di backend.
- **Base URL API di-hardcode ke domain production** di seluruh file statis, sehingga pengujian lokal end-to-end membutuhkan penyesuaian manual URL terlebih dahulu.
- **CORS dibuka penuh** (`@CrossOrigin(origins = "*")`) di semua controller — cocok untuk kebutuhan development/tugas kuliah, tapi tidak disarankan untuk production sesungguhnya.
- **Validasi input minim** — validasi (mis. format email, kekuatan password, duplikasi akun) sebagian besar hanya dilakukan di sisi client (JavaScript), bukan di backend.
- **Riwayat kunjungan difilter di sisi client** — endpoint `GET /api/antrean` mengembalikan seluruh data antrean, lalu difilter berdasarkan nama pasien di JavaScript, bukan melalui query khusus di backend.
- **Belum ada penanganan konflik jadwal** — sistem tidak mengecek apakah poli/dokter/tanggal yang sama sudah penuh sebelum menyimpan antrean baru.

## Lisensi

Project ini dibuat untuk keperluan tugas akademik (mata kuliah Pemrograman Berorientasi Objek) dan belum menyertakan lisensi open-source formal. Jika ingin menggunakan, memodifikasi, atau mendistribusikan ulang kode ini, silakan hubungi pemilik repository terlebih dahulu, atau tambahkan berkas `LICENSE` (mis. MIT License) sesuai kebutuhan Anda.
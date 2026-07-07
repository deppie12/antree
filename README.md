# ANTREE-PBO

Repository tugas mata kuliah **Pemrograman Berorientasi Objek (PBO)** — Sistem Antrean Rumah Sakit berbasis Java Spring Boot. Repo ini berisi beberapa folder project yang merupakan iterasi/versi dari sistem yang sama, dari scaffold paling awal sampai versi paling lengkap.

## Struktur Folder (sesuai isi ZIP, tidak diubah)

```
antree-pbo/
├── antree/                        # Iterasi awal
│   ├── src/
│   │   ├── App.java               # Program "Hello, World!" (scaffold Java biasa)
│   │   └── user.java              # Kelas kosong (placeholder)
│   ├── bin/                       # Hasil kompilasi .class dari src/
│   └── antre/antre/                # Sub-project Spring Boot versi awal
│       ├── src/main/java/com/antre/antre/
│       │   ├── AntreApplication.java
│       │   ├── TestController.java
│       │   ├── controller/AntreanController.java
│       │   ├── model/              # Admin, Antrean, Dokter, Laporan, Pembayaran, Pendaftaran, Poli, User
│       │   └── repository/AntreanRepository.java
│       ├── src/main/resources/application.properties
│       ├── pom.xml, mvnw, mvnw.cmd
│       └── target/                 # Hasil build Maven
│
└── antrepbo/
    └── antrepbo2/                  # Project utama (versi paling lengkap)
        ├── src/main/java/com/antrepbo/antrepbo2/
        │   ├── Antrepbo2Application.java
        │   ├── model/               # Admin, Antrean, User, pendaftaran
        │   ├── repository/          # AdminRepository, AntreanRepository, PendaftaranRepository, UserRepository
        │   ├── service/             # AdminService, AntreanService, PendaftaranService, UserService
        │   └── controller/          # AntreanController, PendaftaranController, UserController, adminController
        ├── src/main/resources/
        │   ├── application.properties
        │   └── static/              # login.html, pendaftaran.html, dashboard.html, admin.html, js/antrean.js
        ├── data/                    # File database H2 (.mv.db, .trace.db)
        ├── database.types.ts        # (kosong)
        ├── pom.xml, mvnw, mvnw.cmd
        └── target/                  # Hasil build Maven
```

> Catatan: struktur di atas hanya mendeskripsikan isi ZIP apa adanya — tidak ada folder/file yang ditambahkan atau diubah.

---

## Project Utama: `antrepbo/antrepbo2`

Aplikasi **Sistem Antrean Rumah Sakit** dengan nama tampilan **ANTREE**.

### Tech Stack
- Java 17, Spring Boot 3.3.5
- Spring Web, Spring Data JPA
- Thymeleaf
- Database: H2 (runtime/lokal) & PostgreSQL Supabase (produksi, lewat `DB_PASSWORD`)
- Lombok, Maven (`mvnw`)
- Frontend: HTML statis (Tailwind via CDN) + vanilla JS, memanggil API lewat `fetch`

### Entity / Model
| Model | Tabel | Field |
|---|---|---|
| `Admin` | `admin` | id, nama, email, password |
| `User` | `USERS` | id, username, password |
| `pendaftaran` | `pendaftaran` | id, nama, nik, whatsapp, email, sandi |
| `Antrean` | `antrean` | id, namaPasien, poli, dokter, tanggal |

### REST API Endpoint
| Method | Endpoint | Controller → Service | Fungsi |
|---|---|---|---|
| POST | `/register` | `UserController` | Registrasi user manual (form-encoded), redirect ke `login.html` |
| POST | `/api/user/login` | `UserController` | Login user: cari `User` by `username`, cek password manual |
| GET | `/api/admin` | `adminController` | Ambil semua admin |
| POST | `/api/admin` | `adminController` | Tambah admin baru |
| PUT | `/api/admin/{id}` | `adminController` | Update data admin (nama, email, password) |
| DELETE | `/api/admin/{id}` | `adminController` | Hapus admin |
| POST | `/api/admin/login` | `adminController` | Login admin: cari `Admin` by email & password |
| POST | `/api/daftar` | `PendaftaranController` → `PendaftaranService` | Simpan data pendaftaran **dan** otomatis buat akun di tabel `USERS` |
| GET | `/api/daftar` | `PendaftaranController` | Ambil semua data pendaftaran |
| PUT | `/api/daftar/{id}` | `PendaftaranController` | Update data pendaftaran |
| DELETE | `/api/daftar/{id}` | `PendaftaranController` | Hapus data pendaftaran |
| POST | `/api/antrean/daftar` | `AntreanController` | Simpan antrean baru |
| GET | `/api/antrean` | `AntreanController` | Ambil semua data antrean (untuk riwayat kunjungan) |

### Alur Aplikasi (End-to-End)

**1. Pendaftaran akun baru** (`pendaftaran.html`)
```
User isi form (nama, nik, whatsapp, email, sandi)
   → validasi sandi vs konfirmasi sandi (di JS)
   → POST /api/daftar
   → PendaftaranService.simpanPendaftar()
        ├─ simpan record lengkap ke tabel `pendaftaran`
        └─ otomatis buat akun baru di tabel `USERS`
              (email pendaftar → username, sandi pendaftar → password)
   → data hasil disimpan ke localStorage['user_aktif']
   → redirect ke dashboard.html
```

**2. Login** (`login.html`)
```
User isi email & password
   → POST /api/admin/login
        ├─ jika berhasil (200)  → role = admin → simpan localStorage['admin_aktif'] → redirect admin.html
        └─ jika gagal           → coba POST /api/user/login
                                     ├─ jika berhasil (200) → role = user → simpan localStorage['user_aktif'] → redirect dashboard.html
                                     └─ jika gagal juga     → alert "Email atau Password salah!"
```
> Jadi sistem login mencoba akun Admin dulu, baru fallback ke akun User biasa.

**3. Dashboard pasien** (`dashboard.html`)
```
Ambil data pasien aktif dari localStorage['user_aktif']
   → jika tidak ada → redirect ke login.html

Daftar antrean baru:
   pilih poli, dokter, tanggal
   → POST /api/antrean/daftar { namaPasien, poli, dokter, tanggal }
   → AntreanService.saveAntrean() → simpan ke tabel `antrean`
   → tampilkan nomor antrean (format "A-00{id}")

Lihat riwayat kunjungan:
   → GET /api/antrean
   → filter hasil di sisi client berdasarkan namaPasien == pasien aktif
   → tampilkan sebagai tabel riwayat (tanggal, poli, nomor antrean)
```

**4. Panel admin** (`admin.html`)
```
Cek localStorage['admin_aktif'] → jika tidak ada, redirect ke login.html
   → GET /api/daftar  → tampilkan semua data pendaftar
   → PUT /api/daftar/{id}    → edit data pendaftar
   → DELETE /api/daftar/{id} → hapus data pendaftar
```

> Semua panggilan `fetch` di frontend statis mengarah ke base URL `https://antree-production.up.railway.app` (hasil deploy), bukan ke `localhost`, meskipun aplikasi juga bisa dijalankan lokal di port sesuai `application.properties`.

### Query Khusus di Repository
- `AdminRepository.findByEmailAndPassword(email, password)` — dipakai login admin.
- `UserRepository.findByUsername(username)` — dipakai login user.
- `AntreanRepository` & `PendaftaranRepository` — standar, hanya mewarisi `JpaRepository` tanpa query kustom.

### Halaman Frontend (`src/main/resources/static`)
| File | Fungsi |
|---|---|
| `login.html` | Form login (coba sebagai admin dulu, fallback ke user) |
| `pendaftaran.html` | Form pendaftaran akun pasien baru |
| `dashboard.html` | Daftar antrean baru & lihat riwayat kunjungan pasien |
| `admin.html` | Kelola (lihat/edit/hapus) data pendaftar |
| `js/antrean.js` | Script JS pendukung fitur antrean |

### Konfigurasi Database
Terhubung ke PostgreSQL (Supabase) lewat environment variable `DB_PASSWORD`, dengan H2 tersedia sebagai dependency runtime untuk pengujian lokal. Port server memakai environment variable `PORT` (fallback `9999`).

### Cara Menjalankan
```bash
cd antrepbo/antrepbo2

# Set password database (jika terhubung ke PostgreSQL/Supabase)
export DB_PASSWORD=your_password

# Jalankan aplikasi
./mvnw spring-boot:run
```
Aplikasi dapat diakses melalui `http://localhost:9999` (atau port sesuai `PORT`). Namun perlu dicatat: file HTML statis di `static/` saat ini masih hardcode memanggil API ke `https://antree-production.up.railway.app`, bukan ke origin lokal — sesuaikan URL tersebut bila ingin menjalankan penuh secara lokal.

---

## Project Pendukung

### `antree/antre/antre`
Sub-project Spring Boot versi lebih awal:
- Spring Boot 3.2.5 (lihat `.mvn/wrapper/maven-wrapper.properties` & `pom.xml`), terhubung ke MySQL lokal (`jdbc:mysql://localhost:3306/antree_pbo`), port server `8081`.
- Model lebih lengkap secara konsep OOP — ada pewarisan `Admin extends User` — namun sebagian besar model (`Dokter`, `Poli`, `Laporan`, `Pembayaran`, `Pendaftaran`) **belum** jadi `@Entity` (tidak terhubung ke database), method-nya masih dummy (contoh: `System.out.println("Dokter ditambahkan")`).
- Endpoint yang tersedia:
  - `GET /` (`TestController`) → mengembalikan teks "Mari Antree!!!"
  - `GET /antrean` (`AntreanController`) → mengambil semua data antrean lewat `AntreanRepository`
- ⚠️ Ada bug penulisan di `AntreanController`: anotasi salah ketik (bukan `@GetMapping`), sehingga endpoint kedua di controller tersebut **tidak akan bisa dikompilasi** sampai typo ini diperbaiki.

### `antree/` (root)
Folder scaffold Java biasa (bukan Maven/Spring):
- `App.java` — program "Hello, World!"
- `user.java` — kelas kosong
- `bin/` — hasil kompilasi `.class`

Tampaknya ini adalah project rintisan paling awal sebelum tim pindah ke Spring Boot.

---

## Catatan Tambahan
- File `database.types.ts` pada `antrepbo2` masih kosong.
- Folder `data/` berisi file database H2 (`antredb.mv.db`, `antredb.trace.db`, `antrepbodb.mv.db`) hasil eksekusi aplikasi — bisa dihapus dan akan ter-generate ulang otomatis saat aplikasi dijalankan.
- Folder `target/` pada masing-masing sub-project adalah hasil build Maven, bisa di-generate ulang dengan `./mvnw clean install`.
- Folder `antrepbo/antrepbo2/.git/` menunjukkan sub-project ini pernah menjadi repository Git sendiri sebelum digabung ke repo `antree-pbo` ini.


// ===============================
// DATA TIKET
// ===============================

const ticketData = {
    queue: "A-042",
    patient: "Nia Rahmadani",
    rm: "123-456-789",
    poli: "Penyakit Dalam",
    doctor: "Dr. Siti Aminah",
    date: "24 Okt 2023",
    estimate: "09:30 WIB"
};

// ===============================
// SET DATA KE HTML
// ===============================

document.querySelector(".queue-number").innerText = ticketData.queue;

document.querySelector("#patient-name").innerText = ticketData.patient;
document.querySelector("#rm-number").innerText = ticketData.rm;
document.querySelector("#poli-name").innerText = ticketData.poli;
document.querySelector("#doctor-name").innerText = ticketData.doctor;
document.querySelector("#visit-date").innerText = ticketData.date;
document.querySelector("#estimate-time").innerText = ticketData.estimate;

// ===============================
// BUTTON UNDUH PDF
// ===============================

const downloadBtn = document.querySelector(".btn-primary");

downloadBtn.addEventListener("click", () => {

    alert("Tiket berhasil diunduh!");

    // Simulasi download PDF
    const content = `
=========================
      TIKET ANTREAN
=========================

Nomor Antrean : ${ticketData.queue}

Nama Pasien   : ${ticketData.patient}
No RM         : ${ticketData.rm}
Poli          : ${ticketData.poli}
Dokter        : ${ticketData.doctor}
Tanggal       : ${ticketData.date}
Estimasi      : ${ticketData.estimate}

=========================
`;

    // Buat file txt sementara
    const blob = new Blob([content], { type: "text/plain" });

    const link = document.createElement("a");

    link.href = URL.createObjectURL(blob);

    link.download = `Tiket-${ticketData.queue}.txt`;

    link.click();

});

// ===============================
// BUTTON SHARE
// ===============================

const shareBtn = document.querySelector(".btn-secondary");

shareBtn.addEventListener("click", async () => {

    const shareText = `
Nomor Antrean : ${ticketData.queue}
Pasien : ${ticketData.patient}
Poli : ${ticketData.poli}
Dokter : ${ticketData.doctor}
Estimasi : ${ticketData.estimate}
`;

    // Jika browser support Web Share API
    if (navigator.share) {

        try {

            await navigator.share({
                title: "Tiket Antrean RS",
                text: shareText
            });

            alert("Tiket berhasil dibagikan!");

        } catch (error) {

            console.log(error);

        }

    } else {

        // Fallback copy clipboard
        navigator.clipboard.writeText(shareText);

        alert("Data tiket disalin ke clipboard!");

    }

});

// ===============================
// STATUS ANIMASI
// ===============================

const status = document.querySelector(".status");

setInterval(() => {

    status.style.opacity = "0.5";

    setTimeout(() => {
        status.style.opacity = "1";
    }, 500);

}, 1000);

// ===============================
// INTERAKSI QR CODE
// ===============================

const qrImage = document.querySelector(".qr-section img");

qrImage.addEventListener("click", () => {

    qrImage.style.transform = "scale(1.1)";
    qrImage.style.transition = "0.3s";

    setTimeout(() => {
        qrImage.style.transform = "scale(1)";
    }, 300);

    alert("QR Code diperbesar!");

});

// ===============================
// NOTIFIKASI CHECK-IN
// ===============================

setTimeout(() => {

    alert("Nomor antrean Anda telah terkonfirmasi!");

}, 1500);

// ===============================
// login form
// ===============================
loginForm.addEventListener("submit", function(e){

    e.preventDefault();

    alert("Login berhasil!");

    window.location.href = "/pendaftaran.html";

});

// ===============================
// button submit
// ===============================
const daftarBtn = document.querySelector(".btn-submit");

daftarBtn.addEventListener("click", () => {

    window.location.href = "/noantrean.html";

});
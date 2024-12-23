import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pendaftaran {
    private Siswa siswa;
    private LocalDateTime waktuPendaftaran;

    public Pendaftaran(Siswa siswa) {
        this.siswa = siswa;
        this.waktuPendaftaran = LocalDateTime.now();
    }

    public void cetakBuktiPendaftaran() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm");
        String formattedDate = waktuPendaftaran.format(formatter);
        // Mencetak bukti pendaftaran dengan format yang lebih rapi
        System.out.println("===========================================");
        System.out.println("            BUKTI PENDAFTARAN              ");
        System.out.println("     CALON SISWA CENDEKIA HIGH SCHOOL      ");
        System.out.println("===========================================");
        System.out.printf("Tanggal Pendaftaran: %s%n", formattedDate);
        System.out.printf("Nama Siswa: %s%n", siswa.getNama());
        System.out.printf("Alamat: %s%n", siswa.getAlamat());
        System.out.printf("Tanggal Lahir: %s%n", siswa.getTanggalLahir());
        System.out.printf("Asal Sekolah: %s%n", siswa.getAsalSekolah());
        System.out.printf("Nilai Rata-Rata: %.2f%n", siswa.getNilaiRataRata());
        System.out.printf("Umur: %d tahun%n", siswa.hitungUmur()); //perhitungan matematika untuk umur
        System.out.println("===========================================");
        System.out.println(" Terima kasih sudah melakukan pendaftaran! ");
        System.out.println("===========================================");
    }
}
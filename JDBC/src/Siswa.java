import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;

public class Siswa{
    private String nama;
    private String alamat;
    private LocalDate tanggalLahir;
    private String asalSekolah;
    private double nilaiRataRata;

    public Siswa(String nama, String alamat, LocalDate tanggalLahir, String asalSekolah, double nilaiRataRata) {
        this.nama = formatNama(nama); // Memformat nama menjadi huruf kapital
        this.alamat = alamat;
        this.tanggalLahir = tanggalLahir;
        this.asalSekolah = asalSekolah;
        this.nilaiRataRata = nilaiRataRata;
    }

    private String formatNama(String nama) { //String
        String[] parts = nama.split(" ");
        StringBuilder formattedName = new StringBuilder();
        for (String part : parts) {
            formattedName.append(part.substring(0, 1).toUpperCase())
                         .append(part.substring(1).toLowerCase())
                         .append(" ");
        }
        return formattedName.toString().trim();
    }

    public int hitungUmur() {
        return Period.between(tanggalLahir, LocalDate.now()).getYears();
    }

    public String tampilkanData() {
        return "Nama: " + nama + "\nAlamat: " + alamat + "\nTanggal Lahir: " + tanggalLahir + 
               "\nAsal Sekolah: " + asalSekolah + "\nNilai Rata-Rata: " + nilaiRataRata + 
               "\nUmur: " + hitungUmur() + " tahun";
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public String getAsalSekolah() {
        return asalSekolah;
    }

    public double getNilaiRataRata() {
        return nilaiRataRata;
    }

        public static void create(Connection conn, Siswa siswa) throws SQLException {
        String query = "INSERT INTO siswa (nama, alamat, tanggal_lahir, asal_sekolah, nilai_rata_rata) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, siswa.nama);
            pstmt.setString(2, siswa.alamat);
            pstmt.setDate(3, Date.valueOf(siswa.tanggalLahir));
            pstmt.setString(4, siswa.asalSekolah);
            pstmt.setDouble(5, siswa.nilaiRataRata);
            pstmt.executeUpdate();
            System.out.println("Siswa berhasil ditambahkan ke database.");
        }
    }

    public static void read(Connection conn) throws SQLException {
        String query = "SELECT * FROM siswa";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nama: " + rs.getString("nama"));
                System.out.println("Alamat: " + rs.getString("alamat"));
                System.out.println("Tanggal Lahir: " + rs.getDate("tanggal_lahir"));
                System.out.println("Asal Sekolah: " + rs.getString("asal_sekolah"));
                System.out.println("Nilai Rata-Rata: " + rs.getDouble("nilai_rata_rata"));
                System.out.println("============================");
            }
        }
    }

    public static void update(Connection conn, int id, String field, String newValue) throws SQLException {
        String query = "UPDATE siswa SET " + field + " = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newValue);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Data siswa berhasil diperbarui.");
        }
    }

    public static void delete(Connection conn, int id) throws SQLException {
        String query = "DELETE FROM siswa WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Data siswa berhasil dihapus.");
        }
    }
}
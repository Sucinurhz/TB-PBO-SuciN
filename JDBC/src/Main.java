import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin("adminchs", "sucicantik");
        boolean loggedIn = false;

        // Koneksi database
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tb_pbo", "root", "");
            System.out.println("Database connected.");
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            return;
        }

        // Loop untuk login menggunakan perulangan
        while (!loggedIn) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (admin.login(username, password)) {
                loggedIn = true; // Set loggedIn ke true jika login berhasil
                System.out.println("Login successful!");

                // Menu utama
                boolean running = true;
                while (running) {
                    System.out.println("\n=== MENU UTAMA ===");
                    System.out.println("1. Tambahkan Siswa");
                    System.out.println("2. Lihat Data Siswa");
                    System.out.println("3. Update Data Siswa");
                    System.out.println("4. Hapus Data Siswa");
                    System.out.println("5. Logout");
                    System.out.print("Pilih opsi: ");

                    int pilihan = scanner.nextInt();
                    scanner.nextLine(); // Clear buffer

                    switch (pilihan) {
                        case 1: // Tambahkan Siswa
                            tambahSiswa(conn, scanner);
                            break;
                        case 2: // Lihat Data Siswa
                            lihatDataSiswa(conn);
                            break;
                        case 3: // Update Data Siswa
                            updateDataSiswa(conn, scanner);
                            break;
                        case 4: // Hapus Data Siswa
                            hapusDataSiswa(conn, scanner);
                            break;
                        case 5: // Logout
                            running = false;
                            System.out.println("Logout berhasil. Terima kasih!");
                            break;
                        default:
                            System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                    }
                }
            } else {
                System.out.println("Login failed! Invalid username or password.");
            }
        }

        try {
            if (conn != null) conn.close(); // Menutup koneksi database
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }

        scanner.close();
    }

    // Method untuk menambahkan siswa
    private static void tambahSiswa(Connection conn, Scanner scanner) {
        System.out.print("Masukkan Nama Siswa: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan Alamat Siswa: ");
        String alamat = scanner.nextLine();

        LocalDate tanggalLahir = null;
        while (tanggalLahir == null) {
            System.out.print("Masukkan Tanggal Lahir (DD-MM-YYYY): ");
            String tanggalLahirInput = scanner.nextLine();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                tanggalLahir = LocalDate.parse(tanggalLahirInput, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Format tanggal tidak valid! Harap masukkan tanggal dalam format DD-MM-YYYY.");
            }
        }

        System.out.print("Masukkan Asal Sekolah: ");
        String asalSekolah = scanner.nextLine();
        double nilaiRataRata = 0;
        boolean validNilai = false;

        // Penanganan exception untuk input nilai rata-rata
        while (!validNilai) {
            System.out.print("Masukkan Nilai Rata-Rata: ");
            try {
                nilaiRataRata = scanner.nextDouble();
                validNilai = true; // Jika input valid, keluar dari loop
            } catch (InputMismatchException e) {
                System.out.println("Nilai rata-rata harus berupa angka! Harap masukkan nilai yang valid.");
                scanner.nextLine(); // Clear the invalid input
            }
        }

        Siswa siswa = new Siswa(nama, alamat, tanggalLahir, asalSekolah, nilaiRataRata);

        try {
            Siswa.create(conn, siswa);
            // Menggunakan notifikasi
        Notifikasi notifikasi = new EmailNotifikasi(); // Atau SmsNotifikasi sebagai implementasi interface notifikasi
        notifikasi.kirimNotifikasi("Siswa " + nama + " berhasil ditambahkan ke database.");
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
        }
    }

    // Method untuk melihat data siswa
    private static void lihatDataSiswa(Connection conn) {
        try {
            Siswa.read(conn);
        } catch (SQLException e) {
            System.err.println("Error reading student data: " + e.getMessage());
        }
    }

    // Method untuk memperbarui data siswa
    private static void updateDataSiswa(Connection conn, Scanner scanner) {
        System.out.print("Masukkan ID Siswa yang ingin diupdate: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        System.out.print("Masukkan nama kolom yang ingin diupdate (nama, alamat, tanggal_lahir, asal_sekolah, nilai_rata_rata): ");
        String field = scanner.nextLine();
        System.out.print("Masukkan data baru: ");
        String newValue = scanner.nextLine();

        try {
            Siswa.update(conn, id, field, newValue);
        } catch (SQLException e) {
            System.err.println("Error updating student data: " + e.getMessage());
        }
    }

    // Method untuk menghapus data siswa
    private static void hapusDataSiswa(Connection conn, Scanner scanner) {
        System.out.print("Masukkan ID Siswa yang ingin dihapus: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        try {
            Siswa.delete(conn, id);
        } catch (SQLException e) {
            System.err.println("Error deleting student data: " + e.getMessage());
        }
    }
}

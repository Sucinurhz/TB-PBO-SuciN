public class SmsNotifikasi implements Notifikasi {
    @Override
    public void kirimNotifikasi(String pesan) {
        System.out.println("Mengirim SMS: " + pesan);
        // Di sini, kamu bisa menambahkan logika pengiriman SMS jika diperlukan.
    }
}
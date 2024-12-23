public class EmailNotifikasi implements Notifikasi {
    @Override
    public void kirimNotifikasi(String pesan) {
        System.out.println("Mengirim Email: " + pesan);
    }
}



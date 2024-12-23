import java.util.ArrayDeque;
import java.util.Deque;

public class AdmissionDeque {
    private Deque<Siswa> queue; //collection framework menggunakan deque

    public AdmissionDeque() {
        queue = new ArrayDeque<>();
    }

    // Menambahkan siswa ke antrian
    public void enqueue(Siswa siswa) {
        queue.addLast(siswa);
    }

    // Mengeluarkan siswa dari antrian
    public Siswa dequeue() {
        return queue.pollFirst();
    }

    // Memeriksa apakah antrian kosong
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // Mendapatkan ukuran antrian
    public int size() {
        return queue.size();
    }
}
// Kelas Node untuk merepresentasikan elemen Linked List
class HashNode {
    int key;
    String value;
    HashNode next;

    public HashNode(int key, String value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}

// Kelas HashTable utama
class HashTable {
    private HashNode[] table;
    private int capacity;

    // 1. Struktur Data dengan ukuran awal array sebesar 10
    public HashTable() {
        this.capacity = 10;
        this.table = new HashNode[capacity];
    }

    // 2. Fungsi Hash: Metode Pembagian (Modulo)
    private int hashFunction(int key) {
        return key % capacity;
    }

    // 4.a. Operasi Insert
    public void insert(int key, String value) {
        int index = hashFunction(key);
        HashNode newNode = new HashNode(key, value);

        if (table[index] == null) {
            table[index] = newNode;
        } else {
            HashNode current = table[index];
            // Jika kunci sudah ada, update nilainya (opsional, mencegah duplikasi key)
            if (current.key == key) {
                current.value = value;
                return;
            }
            while (current.next != null) {
                if (current.next.key == key) {
                    current.next.value = value;
                    return;
                }
                current = current.next;
            }
            // 3. Separate Chaining: Menambahkan di akhir list
            current.next = newNode;
        }
    }

    // 4.b. Operasi Search
    public String search(int key) {
        int index = hashFunction(key);
        HashNode current = table[index];

        while (current != null) {
            if (current.key == key) {
                return current.value;
            }
            current = current.next;
        }
        return "Tidak ditemukan";
    }

    // 4.c. Operasi Remove
    public void remove(int key) {
        int index = hashFunction(key);
        HashNode current = table[index];
        HashNode prev = null;

        while (current != null) {
            if (current.key == key) {
                if (prev == null) {
                    // Node yang dihapus berada di kepala list
                    table[index] = current.next;
                } else {
                    // Node yang dihapus berada di tengah atau akhir list
                    prev.next = current.next;
                }
                System.out.println("Kunci " + key + " berhasil dihapus.");
                return;
            }
            prev = current;
            current = current.next;
        }
        System.out.println("Gagal menghapus: Kunci " + key + " tidak ditemukan.");
    }

    // 4.d. Operasi Display
    public void display() {
        System.out.println("\n=== ISI HASH TABLE ===");
        for (int i = 0; i < capacity; i++) {
            System.out.print("Indeks " + i + ": ");
            HashNode current = table[i];
            if (current == null) {
                System.out.print("kosong");
            } else {
                while (current != null) {
                    System.out.print("[" + current.key + ": " + current.value + "]");
                    if (current.next != null) {
                        System.out.print(" -> ");
                    }
                    current = current.next;
                }
            }
            System.out.println();
        }
        System.out.println("======================");
    }
}

// Main class untuk testing
public class HashTableApp {
    public static void main(String[] args) {
        HashTable ht = new HashTable();

        // Uji Coba: Memasukkan data (insert)
        // Kita masukkan beberapa data yang menghasilkan collision (tabrakan index)
        // Karena kapasitas = 10, kunci 5, 15, dan 25 akan masuk ke Indeks 5.
        ht.insert(5, "Beras A");
        ht.insert(15, "Beras B");
        ht.insert(25, "Beras C");
        ht.insert(3, "Beras D");
        ht.insert(13, "Beras E");
        ht.insert(8, "Beras F");

        // Tampilkan kondisi awal table
        ht.display();

        // Uji Coba: Pencarian (search)
        System.out.println("\nMencari kunci 15: " + ht.search(15)); // Output: Andi
        System.out.println("Mencari kunci 99: " + ht.search(99));  // Output: Tidak ditemukan

        // Uji Coba: Penghapusan (remove)
        System.out.println();
        ht.remove(15); // Menghapus node di tengah chain (indeks 5)
        ht.remove(5);  // Menghapus node di kepala chain (indeks 5)
        ht.remove(99); // Mencoba menghapus key yang tidak ada

        // Tampilkan kondisi akhir table setelah dihapus
        ht.display();
    }
}
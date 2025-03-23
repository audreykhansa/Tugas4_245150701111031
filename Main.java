import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Selamat Datang di Swalayan Tiny ===");
        System.out.print("Masukkan Nama Anda: ");
        String nama = scanner.nextLine();

        System.out.print("Pilih Jenis Rekening (Silver/Gold/Platinum): ");
        String jenisRekening = scanner.nextLine().toLowerCase();

        System.out.print("Masukkan 8 digit terakhir nomor pelanggan: ");
        String nomorAkhir = scanner.nextLine();

        System.out.print("Masukkan PIN (4 digit): ");
        String pin = scanner.nextLine();

        String nomorAwal = switch (jenisRekening) {
            case "silver" -> "38";
            case "gold" -> "56";
            case "platinum" -> "74";
            default -> "00";
        };

        String nomorPelanggan = nomorAwal + nomorAkhir;
        Pelanggan pelanggan = new Pelanggan(nama, nomorPelanggan, pin, 1000000);

        System.out.println("Nomor Pelanggan Anda: " + pelanggan.getNomorPelanggan());

        while (true) {
            if (pelanggan.isDiblokir()) break;

            System.out.println("\nHai, " + pelanggan.getNama() + " (" + pelanggan.getJenisRekening() + ")");
            System.out.println("Saldo saat ini: " + Pelanggan.formatRupiah(pelanggan.getSaldo()));
            System.out.println("1. Top Up");
            System.out.println("2. Pembelian");
            System.out.println("3. Keluar");
            System.out.print("Pilih opsi: ");
            int opsi = scanner.nextInt();
            scanner.nextLine();

            if (opsi == 3) break;

            System.out.print("Masukkan PIN: ");
            String pinInput = scanner.nextLine();

            if (!pelanggan.autentikasi(pinInput)) continue;

            switch (opsi) {
                case 1 -> {
                    System.out.print("Masukkan jumlah top up: ");
                    int jumlahTopUp = scanner.nextInt();
                    scanner.nextLine();

                    if (pelanggan.topUp(jumlahTopUp)) {
                        System.out.println("Top-up berhasil! Saldo saat ini: " + Pelanggan.formatRupiah(pelanggan.getSaldo()));
                    } else {
                        System.out.println("Top-up gagal! Jumlah harus lebih dari 0.");
                    }
                }
                case 2 -> {
                    System.out.print("Masukkan jumlah pembelian: ");
                    int jumlahPembelian = scanner.nextInt();
                    scanner.nextLine();

                    int cashback = pelanggan.pembelian(jumlahPembelian);
                    if (cashback >= 0) {
                        System.out.println("Pembelian berhasil!");
                        System.out.println("Cashback yang didapatkan: " + Pelanggan.formatRupiah(cashback));
                        System.out.println("Saldo tersisa: " + Pelanggan.formatRupiah(pelanggan.getSaldo()));
                    }
                }
                default -> System.out.println("Pilihan tidak valid!");
            }
        }

        System.out.println("Terima kasih telah menggunakan layanan kami!");
        scanner.close();
    }
}

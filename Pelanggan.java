import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Pelanggan {
    private String nama, nomorPelanggan, pin;
    private int saldo, kesalahanPin;
    private boolean diblokir;

    public Pelanggan(String nama, String nomorPelanggan, String pin, int saldo) {
        this.nama = nama;
        this.nomorPelanggan = nomorPelanggan;
        this.pin = pin;
        this.saldo = saldo;
        this.kesalahanPin = 0;
        this.diblokir = false;
    }

    public boolean autentikasi(String pinInput) {
        if (diblokir) {
            System.out.println("Akun Anda diblokir! Silakan hubungi customer service.");
            return false;
        }

        if (this.pin.equals(pinInput)) {
            kesalahanPin = 0;
            return true;
        } else {
            kesalahanPin++;
            if (kesalahanPin >= 3) {
                diblokir = true;
                System.out.println("Akun diblokir! Terlalu banyak kesalahan dalam memasukkan PIN.");
            } else {
                System.out.println("PIN salah! Kesempatan tersisa: " + (3 - kesalahanPin));
            }
            return false;
        }
    }

    public boolean isDiblokir() {
        return diblokir;
    }

    public String getNama() {
        return nama;
    }

    public String getNomorPelanggan() {
        return nomorPelanggan;
    }

    public String getJenisRekening() {
        return switch (nomorPelanggan.substring(0, 2)) {
            case "38" -> "Silver";
            case "56" -> "Gold";
            case "74" -> "Platinum";
            default -> "Tidak Diketahui";
        };
    }

    public int getSaldo() {
        return saldo;
    }

    public boolean topUp(int jumlah) {
        if (jumlah > 0) {
            saldo += jumlah;
            return true;
        }
        return false;
    }

    public int pembelian(int jumlah) {
        if (saldo - jumlah < 10000) {
            System.out.println("Transaksi gagal! Saldo tidak mencukupi (Minimal saldo Rp10.000).");
            return -1;
        }

        int cashback = hitungCashback(jumlah);
        saldo -= (jumlah - cashback);
        return cashback;
    }

    private int hitungCashback(int jumlah) {
        int cashback = 0;
        if (getJenisRekening().equals("Silver") && jumlah > 1000000) {
            cashback = (int) (jumlah * 0.05);
        } else if (getJenisRekening().equals("Gold")) {
            if (jumlah > 1000000) cashback = (int) (jumlah * 0.07);
            else cashback = (int) (jumlah * 0.02);
        } else if (getJenisRekening().equals("Platinum")) {
            if (jumlah > 1000000) cashback = (int) (jumlah * 0.10);
            else cashback = (int) (jumlah * 0.05);
        }
        return cashback;
    }

    public static String formatRupiah(int amount) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in", "ID"));
        return "Rp" + nf.format(amount);
    }
}

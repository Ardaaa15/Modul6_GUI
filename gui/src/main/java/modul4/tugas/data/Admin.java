package modul4.tugas.data;

import modul4.tugas.books.Book;
import modul4.tugas.books.HistoryBook;
import modul4.tugas.books.StoryBook;
import modul4.tugas.books.TextBook;
import modul4.tugas.exceptions.IllegalAdminAccess;

import java.util.ArrayList;
import java.util.Scanner;

import static modul4.tugas.gui.LibraryApp.*;

public class Admin extends User {

    Scanner scanner = new Scanner(System.in);

    public Admin() {
        super("admin");
    }

    public void login() throws IllegalAdminAccess {
        System.out.print("Masukkan Username (admin): ");
        String username = scanner.next();
        System.out.print("Masukkan Password (admin): ");
        String password = scanner.next();
        if (isAdmin(username, password)) {
            System.out.println("Login berhasil sebagai Admin");
            menu();
        } else {
            throw new IllegalAdminAccess("User Admin tidak ditemukan");
        }
    }

    public boolean isAdmin(String username, String password) {
        return username.equals("admin") && password.equals("admin");
    }

    public void menu() {
        try {
            while (true) {
                System.out.println("Menu Admin");
                System.out.println("1. Tambah Mahasiswa");
                System.out.println("2. Tampilkan Mahasiswa");
                System.out.println("3. Input Buku");
                System.out.println("4. Tampilkan Daftar Buku");
                System.out.println("5. Logout");
                System.out.print("Pilih antara (1-5): ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        displayStudents();
                        break;
                    case 3:
                        inputBook();
                        break;
                    case 4:
                        displayBooks(daftarBuku);
                        break;
                    case 5:
                        System.out.println("Logout berhasil.");
                        return;
                    default:
                        System.out.println("Pilihan tidak valid!");
                }
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }

    @Override
    public void addStudent() {
        System.out.println("Menambahkan mahasiswa...");
        System.out.print("Masukkan Nama: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Masukkan NIM: ");
        String nim = scanner.next();
        scanner.nextLine();
        while (true) {
            if (nim.length() != 15) {
                System.out.print("Nim Harus 15 Digit!!!\n");
                System.out.print("Masukkan NIM: ");
                nim = scanner.nextLine();
            } else if (checkNim(nim)) {
                System.out.println("NIM sudah terdaftar");
                System.out.print("Masukkan NIM baru: ");
                nim = scanner.nextLine();
            } else {
                break;
            }
        }
        System.out.print("Masukkan Fakultas: ");
        String faculty = scanner.nextLine();
        System.out.print("Masukkan Program Studi: ");
        String studyProgram = scanner.nextLine();
        studentList.add(new Student(nim, name, faculty, studyProgram));
        System.out.println("Mahasiswa dengan NIM " + nim + " berhasil ditambahkan.");
    }

    private void inputBook() {
        System.out.println("Memasukkan buku...");
        System.out.println("Pilih jenis buku:");
        System.out.println("1. History Book");
        System.out.println("2. Story Book");
        System.out.println("3. Text Book");
        System.out.print("Pilih jenis buku (1-3): ");
        int bookType = scanner.nextInt();
        scanner.nextLine();

        String idBuku, judul, author, category;
        int stok;
        System.out.print("Masukkan judul buku: ");
        judul = scanner.nextLine();
        System.out.print("Masukkan author buku: ");
        author = scanner.nextLine();
        System.out.print("Masukkan category buku: ");
        category = scanner.nextLine();
        System.out.print("Masukkan stok buku: ");
        stok = scanner.nextInt();
        scanner.nextLine();

        switch (bookType) {
            case 1:
                idBuku = generateId("HB");
                daftarBuku.add(new HistoryBook(idBuku, judul, stok, category, author));
                break;
            case 2:
                idBuku = generateId("SB");
                daftarBuku.add(new StoryBook(idBuku, judul, stok, category, author));
                break;
            case 3:
                idBuku = generateId("TB");
                daftarBuku.add(new TextBook(idBuku, judul, stok, category, author));
                break;
            default:
                System.out.println("Pilihan tidak valid!");
                return;
        }
        System.out.println("Buku berhasil ditambahkan.");
    }

    @Override
    public void displayBooks(ArrayList<Book> bookList) {
        System.out.println("Daftar Buku Tersedia:");
        System.out.println("================================================================");
        System.out.println("|| No. || Id Buku || Nama Buku || Author || Category || Stock ||");
        int index = 1;
        for (Book book : bookList) {
            if (book != null) {
                System.out.println("|| " + index + " || " + book.getIdBuku() + " || " + book.getJudul() + " || " + book.getAuthor() + " || " + book.getCategory() + "  || " + book.getStok() + " ||");
                index++;
            }
        }
        System.out.println("================================================================");
    }

    private void displayStudents() {
        System.out.println("Daftar Mahasiswa yang terdaftar:");
        for (Student student : studentList) {
            System.out.println("\nNama: " + student.getName());
            System.out.println("NIM: " + student.getNim());
            System.out.println("Fakultas: " + student.getFaculty());
            System.out.println("Program Studi: " + student.getStudyProgram());
            if (!student.getBorrowedBooks().isEmpty()) {
                System.out.println("  Meminjam Buku:");
                for (Book book : student.getBorrowedBooks()) {
                    System.out.println("    - " + book.getJudul());
                }
            }
        }
    }

    private String generateId(String prefix) {
        int nextId = daftarBuku.size() + 1;
        return prefix + String.format("%03d", nextId);
    }
}

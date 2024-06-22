package modul4.tugas.data;

import modul4.tugas.books.Book;

import java.util.ArrayList;

public abstract class User {
    private String nim;

    public User(String nim) {
        this.nim = nim;
    }

    public String getNim() {
        return nim;
    }

    public abstract void addStudent();

    public abstract void displayBooks(ArrayList<Book> bookList);
}

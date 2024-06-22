package modul4.tugas.data;

import modul4.tugas.books.Book;

import java.util.ArrayList;

public class Student extends User  {

    private String name;
    private String faculty;
    private String studyProgram;
    private ArrayList<Book> borrowedBooks = new ArrayList<>();

    public Student() {
        super("student");
    }

    public Student(String nim, String name, String faculty, String studyProgram) {
        super(nim);
        this.name = name;
        this.faculty = faculty;
        this.studyProgram = studyProgram;
    }

    public void borrowBook(Book book) throws Exception {
        if (book.getStok() > 0) {
            book.setStok(book.getStok() - 1);
            borrowedBooks.add(book);
        } else {
            throw new Exception("Stok buku habis.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.remove(book)) {
            book.setStok(book.getStok() + 1);
        }
    }

    public String getName() {
        return name;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getStudyProgram() {
        return studyProgram;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public void displayBooks(ArrayList<Book> bookList) {}

    @Override
    public void addStudent() {}

}

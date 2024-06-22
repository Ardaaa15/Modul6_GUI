package modul4.tugas.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Modality;
import modul4.tugas.books.*;
import modul4.tugas.data.*;
import modul4.tugas.exceptions.IllegalAdminAccess;

import java.util.ArrayList;
import java.util.Objects;

public class LibraryApp extends Application {

    public static ArrayList<Book> daftarBuku = new ArrayList<>();
    public static ArrayList<Student> studentList = new ArrayList<>();

    private Stage primaryStage;
    private Student loggedInStudent;
    private Admin loggedInAdmin;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Library System");

        // Initialize data
        initializeData();
        showLoginSelectionScreen();
    }

    private void initializeData() {
        daftarBuku.add(new StoryBook("001", "Garis Waktu", 17, "Story", "Fiersa Besari"));
        daftarBuku.add(new HistoryBook("002", "10 Dosa Besar Soeharto", 2, "History", "Dimas"));
        daftarBuku.add(new TextBook("003", "Sewu Dino", 4, "Story", "Simpleman"));
        studentList.add(new Student("202310370311453", "ARDA", "Teknik", "Informatika"));
    }

    private void showLoginSelectionScreen() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(20));

        Button adminLoginButton = new Button("Login Admin");
        Button studentLoginButton = new Button("Login Mahasiswa");

        double buttonWidth = 400;
        double buttonHeight = 50;

        adminLoginButton.setPrefWidth(buttonWidth);
        adminLoginButton.setPrefHeight(buttonHeight);
        studentLoginButton.setPrefWidth(buttonWidth);
        studentLoginButton.setPrefHeight(buttonHeight);

        adminLoginButton.setOnAction(e -> showAdminLoginScreen());
        studentLoginButton.setOnAction(e -> showStudentLoginScreen());

        layout.getChildren().addAll(adminLoginButton, studentLoginButton);

        Scene scene = new Scene(layout, 200, 200);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showStudentLoginScreen() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(20));

        Label nimLabel = new Label("NIM:");
        PasswordField nimField = new PasswordField();
        Button loginButton = new Button("Login");
        Label messageLabel = new Label();
        messageLabel.getStyleClass().add("true-label");

        loginButton.setOnAction(e -> {
            String nim = nimField.getText();
            if (nim.length() == 15 && checkNim(nim)) {
                loggedInStudent = findStudentByNim(nim);
                showStudentMenu();
            } else {
                showErrorPopup("NIM tidak valid atau tidak terdaftar.");
            }
        });

        layout.getChildren().addAll(nimLabel, nimField, loginButton, messageLabel);

        Scene scene = new Scene(layout, 500, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAdminLoginScreen() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(20));

        Label adminLabel = new Label("Username:");
        TextField adminField = new TextField();
        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        Button loginButton = new Button("Login");
        Label messageLabel = new Label();
        messageLabel.getStyleClass().add("true-label");

        loginButton.setOnAction(e -> {
            String username = adminField.getText();
            String password = passField.getText();
            try {
                loggedInAdmin = new Admin();
                if (loggedInAdmin.isAdmin(username, password)) {
                    showAdminMenu();
                } else {
                    throw new IllegalAdminAccess("Username atau Password salah.");
                }
            } catch (IllegalAdminAccess ex) {
                showErrorPopup(ex.getMessage());
            }
        });

        layout.getChildren().addAll(adminLabel, adminField, passLabel, passField, loginButton, messageLabel);

        Scene scene = new Scene(layout, 500, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showStudentMenu() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(20));

        Button borrowedBooksButton = new Button("Buku Terpinjam");
        Button loanBookButton = new Button("Pinjam Buku");
        Button returnBookButton = new Button("Kembalikan Buku");
        Button logoutButton = new Button("Logout");

        double buttonWidth = 400;
        double buttonHeight = 50;

        borrowedBooksButton.setPrefWidth(buttonWidth);
        borrowedBooksButton.setPrefHeight(buttonHeight);
        loanBookButton.setPrefWidth(buttonWidth);
        loanBookButton.setPrefHeight(buttonHeight);
        returnBookButton.setPrefWidth(buttonWidth);
        returnBookButton.setPrefHeight(buttonHeight);

        borrowedBooksButton.setOnAction(e -> showBorrowedBooks());
        loanBookButton.setOnAction(e -> showLoanBook());
        returnBookButton.setOnAction(e -> showReturnBook());
        logoutButton.setOnAction(e -> {
            loggedInStudent = null;
            showLoginSelectionScreen();
        });

        layout.getChildren().addAll(borrowedBooksButton, loanBookButton, returnBookButton, logoutButton);

        Scene scene = new Scene(layout, 500, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAdminMenu() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(20));

        Button addStudentButton = new Button("Tambah Mahasiswa");
        Button displayStudentsButton = new Button("Tampilkan Mahasiswa");
        Button inputBookButton = new Button("Input Buku");
        Button displayBooksButton = new Button("Tampilkan Daftar Buku");
        Button logoutButton = new Button("Logout");

        double buttonWidth = 400;
        double buttonHeight = 50;

        addStudentButton.setPrefWidth(buttonWidth);
        addStudentButton.setPrefHeight(buttonHeight);
        displayStudentsButton.setPrefWidth(buttonWidth);
        displayStudentsButton.setPrefHeight(buttonHeight);
        inputBookButton.setPrefWidth(buttonWidth);
        inputBookButton.setPrefHeight(buttonHeight);
        displayBooksButton.setPrefWidth(buttonWidth);
        displayBooksButton.setPrefHeight(buttonHeight);

        addStudentButton.setOnAction(e -> showAddStudent());
        displayStudentsButton.setOnAction(e -> showDisplayStudents());
        inputBookButton.setOnAction(e -> showInputBook());
        displayBooksButton.setOnAction(e -> showDisplayBooks());
        logoutButton.setOnAction(e -> {
            loggedInAdmin = null;
            showLoginSelectionScreen();
        });

        layout.getChildren().addAll(addStudentButton, displayStudentsButton, inputBookButton, displayBooksButton, logoutButton);

        Scene scene = new Scene(layout, 500, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showInputBook() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(20));

        Label titleLabel = new Label("Judul:");
        TextField titleField = new TextField();
        Label authorLabel = new Label("Author:");
        TextField authorField = new TextField();
        Label categoryLabel = new Label("Kategori:");
        TextField categoryField = new TextField();
        Label stockLabel = new Label("Stok:");
        TextField stockField = new TextField();
        Button addButton = new Button("Tambah");
        Label messageLabel = new Label();
        messageLabel.getStyleClass().add("true-label");

        addButton.setOnAction(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String category = categoryField.getText();
            try {
                int stock = Integer.parseInt(stockField.getText());
                String idBuku = generateId(category);
                daftarBuku.add(new Book(idBuku, title, stock, category, author));
                messageLabel.setText("Buku berhasil ditambahkan.");
            } catch (NumberFormatException ex) {
                showErrorPopup("Stok harus berupa angka.");
            }
        });

        Button backButton = new Button("Kembali");
        backButton.setOnAction(e -> showAdminMenu());

        layout.getChildren().addAll(titleLabel, titleField, authorLabel, authorField, categoryLabel, categoryField, stockLabel, stockField, addButton, messageLabel, backButton);

        Scene scene = new Scene(layout, 500, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());
        primaryStage.setScene(scene);
    }

    private void showBorrowedBooks() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(20));

        Label borrowedBooksLabel = new Label("Buku yang Terpinjam:");
        ListView<String> listView = new ListView<>();
        listView.getItems().add("Buku 1");
        listView.getItems().add("Buku 2");
        listView.getItems().add("Buku 3");
        Button button = new Button("Read Selected Value");

        button.setOnAction(event -> {
        });


        VBox vBox = new VBox(listView, button);

        Scene scene = new Scene(vBox, 300, 120);
        primaryStage.setScene(scene);
        primaryStage.show();

        for (Book book : loggedInStudent.getBorrowedBooks()) {
            listView.getItems().add(book.getJudul());
        }

        Button backButton = new Button("Kembali");
        backButton.setOnAction(e -> showStudentMenu());

        layout.getChildren().addAll(borrowedBooksLabel, listView, backButton);

        scene = new Scene(layout, 500, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());
        primaryStage.setScene(scene);
    }

    private void showLoanBook() {
        VBox layout = new VBox(10);
        HBox layout2 = new HBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(20));

        Label availableBooksLabel = new Label("Buku yang Tersedia:");
        ListView<String> listView = new ListView<>();
        ListView<String> listView2 = new ListView<>();

        layout2.getChildren().addAll(listView, listView2);

        for (Book book : daftarBuku) {
            if (book.getStok() > 0) {
                listView.getItems().add(book.getJudul());
                listView2.getItems().add(book.getAuthor());
            }
        }

        Label durationLabel = new Label("Durasi (hari):");
        TextField durationField = new TextField();

        Button loanButton = new Button("Pinjam");
        Label messageLabel = new Label();
        messageLabel.getStyleClass().add("true-label");

        loanButton.setOnAction(e -> {
            String selectedBookTitle = listView.getSelectionModel().getSelectedItem();
            Book selectedBook = findBookByTitle(selectedBookTitle);
            if (selectedBook != null) {
                try {
                    int duration = Integer.parseInt(durationField.getText());
                    selectedBook.setDuration(duration);
                    loggedInStudent.borrowBook(selectedBook);
                    messageLabel.setText("Buku berhasil dipinjam.");
                } catch (NumberFormatException ex) {
                    showErrorPopup("Durasi harus berupa angka.");
                } catch (Exception ex) {
                    showErrorPopup("Terjadi kesalahan: " + ex.getMessage());
                }
            } else {
                showErrorPopup("Buku tidak ditemukan.");
            }
        });

        Button backButton = new Button("Kembali");
        backButton.setOnAction(e -> showStudentMenu());

        layout.getChildren().addAll(availableBooksLabel, layout2, durationLabel, durationField, loanButton, messageLabel, backButton);

        Scene scene = new Scene(layout, 500, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());
        primaryStage.setScene(scene);
    }

    private void showReturnBook() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(20));

        Label borrowedBooksLabel = new Label("Buku yang Terpinjam:");
        ListView<String> listView = new ListView<>();
        listView.getItems().add("Buku 1");
        listView.getItems().add("Buku 2");
        listView.getItems().add("Buku 3");

        for (Book book : loggedInStudent.getBorrowedBooks()) {
            listView.getItems().add(book.getJudul());
        }

        Button returnButton = new Button("Kembalikan");
        Label messageLabel = new Label();
        messageLabel.getStyleClass().add("true-label");

        returnButton.setOnAction(e -> {
            String selectedBookTitle = listView.getSelectionModel().getSelectedItem();
            Book selectedBook = findBookByTitle(selectedBookTitle);
            if (selectedBook != null) {
                loggedInStudent.returnBook(selectedBook);
                messageLabel.setText("Buku berhasil dikembalikan.");
            } else {
                showErrorPopup("Buku tidak ditemukan.");
            }
        });

        Button backButton = new Button("Kembali");
        backButton.setOnAction(e -> showStudentMenu());

        layout.getChildren().addAll(borrowedBooksLabel, listView, returnButton, messageLabel, backButton);

        Scene scene = new Scene(layout, 500, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());
        primaryStage.setScene(scene);
    }

    private void showAddStudent() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(20));

        Label nameLabel = new Label("Nama:");
        TextField nameField = new TextField();
        Label nimLabel = new Label("NIM:");
        TextField nimField = new TextField();
        Label facultyLabel = new Label("Fakultas:");
        TextField facultyField = new TextField();
        Label studyProgramLabel = new Label("Program Studi:");
        TextField studyProgramField = new TextField();
        Button addButton = new Button("Tambah");
        Label messageLabel = new Label();
        messageLabel.getStyleClass().add("true-label");

        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String nim = nimField.getText();
            String faculty = facultyField.getText();
            String studyProgram = studyProgramField.getText();
            if (nim.length() == 15 && !nimExists(nim)) {
                studentList.add(new Student(nim, name, faculty, studyProgram));
                messageLabel.setText("Mahasiswa berhasil ditambahkan.");
            } else {
                showErrorPopup("NIM tidak valid atau sudah terdaftar.");
            }
        });

        Button backButton = new Button("Kembali");
        backButton.setOnAction(e -> showAdminMenu());

        layout.getChildren().addAll(nameLabel, nameField, nimLabel, nimField, facultyLabel, facultyField, studyProgramLabel, studyProgramField, addButton, messageLabel, backButton);

        Scene scene = new Scene(layout, 500, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());
        primaryStage.setScene(scene);
    }

    private void showDisplayStudents() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(20));

        Label studentsLabel = new Label("Daftar Mahasiswa:");
        ListView<String> listView = new ListView<>();

        for (Student student : studentList) {
            listView.getItems().add(student.getName() + " - " + student.getNim());
        }

        Button backButton = new Button("Kembali");
        backButton.setOnAction(e -> showAdminMenu());

        layout.getChildren().addAll(studentsLabel, listView, backButton);

        Scene scene = new Scene(layout, 500, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());
        primaryStage.setScene(scene);
    }

    private void showDisplayBooks() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(20));

        Label booksLabel = new Label("Daftar Buku:");
        ListView<String> listView = new ListView<>();

        for (Book book : daftarBuku) {
            listView.getItems().add(book.getJudul() + " - " + book.getAuthor());
        }

        Button backButton = new Button("Kembali");
        backButton.setOnAction(e -> showAdminMenu());

        layout.getChildren().addAll(booksLabel, listView, backButton);

        Scene scene = new Scene(layout, 500, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());
        primaryStage.setScene(scene);
    }

    public static boolean checkNim(String nim) {
        for (Student student : studentList) {
            if (student.getNim().equals(nim)) {
                return true;
            }
        }
        return false;
    }

    public static boolean nimExists(String nim) {
        for (Student student : studentList) {
            if (student.getNim().equals(nim)) {
                return true;
            }
        }
        return false;
    }

    public static Student findStudentByNim(String nim) {
        for (Student student : studentList) {
            if (student.getNim().equals(nim)) {
                return student;
            }
        }
        return null;
    }

    public static Book findBookByTitle(String title) {
        for (Book book : daftarBuku) {
            if (book.getJudul().equals(title)) {
                return book;
            }
        }
        return null;
    }

    public static String generateId(String prefix) {
        return prefix + String.format("%03d", daftarBuku.size() + 1);
    }

    private void showErrorPopup(String message) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(new Label(message));
        Scene dialogScene = new Scene(dialogVbox, 200, 200);
        dialogScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());
        dialog.setScene(dialogScene);
        dialog.show();
    }
}

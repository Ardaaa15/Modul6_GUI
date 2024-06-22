module modul4.tugas {
    requires javafx.controls;
    requires javafx.fxml;

    opens modul4.tugas.gui to javafx.fxml;
    exports modul4.tugas.gui;
    exports modul4.tugas.books;
    exports modul4.tugas.data;
    exports modul4.tugas.exceptions;
}

module ija.project.ijarobots {
    requires javafx.controls;
    requires javafx.fxml;


    opens ija.project.ijarobots to javafx.fxml;
    exports ija.project.ijarobots;
}
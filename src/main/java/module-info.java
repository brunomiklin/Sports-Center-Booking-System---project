open module hr.tvz.sportapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires jakarta.json.bind;
    requires java.sql;
    requires java.xml.crypto;


    exports hr.tvz.sportapp.app;
    exports hr.tvz.sportapp.controller;


}

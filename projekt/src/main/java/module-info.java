module tvz.hr.projekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires google.maps.services;
    requires com.h2database;
    requires org.slf4j;

    opens tvz.hr.projekt to javafx.fxml;
    exports tvz.hr.projekt.iznimke;
    exports tvz.hr.projekt;
    exports tvz.hr.projekt.controllers;
    opens tvz.hr.projekt.controllers to javafx.fxml;
    exports tvz.hr.projekt.login;
    opens tvz.hr.projekt.login to javafx.fxml;
    exports tvz.hr.projekt.entiteti;
    opens tvz.hr.projekt.entiteti to javafx.fxml;
}
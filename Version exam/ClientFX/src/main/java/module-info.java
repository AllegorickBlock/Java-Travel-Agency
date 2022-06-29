module be.groupe18.clientfx.clientfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires Common;

    opens be.groupe18.clientfx.projetvoyage.views to javafx.fxml;
    //exports be.groupe18.clientfx.projetvoyage.models;
    exports be.groupe18.clientfx.projetvoyage.controllers;
    exports be.groupe18.clientfx.projetvoyage.views;
}
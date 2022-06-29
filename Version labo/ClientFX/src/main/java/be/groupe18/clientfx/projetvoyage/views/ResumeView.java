package be.groupe18.clientfx.projetvoyage.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ResumeView {

    @FXML
    private Button newTripButton;

    @FXML
    private VBox listTrips;

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @FXML
    void onNewTripClick(ActionEvent event) throws IOException {
        listener.onNewTripClick();
    }

    public void addNewTrip(AnchorPane pane) {
        listTrips.getChildren().add(pane);
    }

    public void removeTrip(AnchorPane pane) {listTrips.getChildren().remove(pane);}


    public interface Listener {
        void onNewTripClick() throws IOException;
    }



}
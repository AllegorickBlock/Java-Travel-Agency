package be.groupe18.clientfx.projetvoyage.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ResumeView {

    @FXML
    private Button newTripButton;

    @FXML
    private VBox listTrips;

    @FXML
    private Label numberClientLabel;

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

    public void removeTrip(int index) {
        System.out.println(index);
        listTrips.getChildren().remove(index);
    }

    public void updateNumberClient(int numberClient) {
        if(numberClient==1)numberClientLabel.setText("Il y a "+numberClient+" client connecté");
        else numberClientLabel.setText("Il y a "+numberClient+" clients connectés");
    }


    public interface Listener {
        void onNewTripClick() throws IOException;
    }



}
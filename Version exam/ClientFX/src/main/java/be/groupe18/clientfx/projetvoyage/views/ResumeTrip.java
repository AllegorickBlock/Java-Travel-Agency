package be.groupe18.clientfx.projetvoyage.views;

import be.groupe18.common.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.text.DecimalFormat;

public class ResumeTrip {

    private Listener listener;

    private StepList stepList;

    @FXML
    private Label tripName;

    @FXML
    private Button openTripButton;

    @FXML
    private Button deleteTripButton;

    @FXML
    private Label startDate;

    @FXML
    private Label totalPrice;

    @FXML
    private Label totalHour;

    @FXML
    private Label totalKm;

    @FXML
    private Label firstCity;

    public void setListener(ResumeTrip.Listener listener) {
        this.listener = listener;
    }

    private static final DecimalFormat df = new DecimalFormat("0.00");


/**
 * When the user clicks the open trip button, call the onOpenTripClick() function in the listener.
 */
    @FXML
    void onOpenTripClick() throws IOException {
        listener.onOpenTripClick();
    }

    @FXML
    void onRemoveTripClick(){
        listener.onRemoveTripClick();
    }

    public void setStepList(StepList stepList) {
        this.stepList=stepList;
    }

    public void refreshTitle() {
        try {
            tripName.setText(stepList.getNameTrip());
            startDate.setText(stepList.getStartDate());
            totalPrice.setText(df.format(stepList.getTotalPrice()) + " €");
            totalHour.setText(df.format(stepList.getTotalHour()) + " hour");
            totalKm.setText(df.format(stepList.getTotalDistance()) + " km");
            firstCity.setText(stepList.getStartCity().getName());
        }
        catch (Exception e){
            System.out.println("Trip non instancié");
        }
    }

    public void lockStepList() {
        deleteTripButton.setDisable(true);
        openTripButton.setDisable(true);
    }

    public void unlockStepList() {
        deleteTripButton.setDisable(false);
        openTripButton.setDisable(false);
    }

    public StepList getTrip() {return stepList;}

    // An interface that is used to create a listener.
    public interface Listener {
        void onOpenTripClick() throws IOException;

        void onRemoveTripClick();
    }
}

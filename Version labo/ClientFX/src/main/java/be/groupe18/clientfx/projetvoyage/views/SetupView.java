package be.groupe18.clientfx.projetvoyage.views;

import be.groupe18.common.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class SetupView {

    @FXML
    private VBox listSetup;

    private Listener listener;

    private City startCity;

    private StepList stepList;

    private Label footerHotel;

    @FXML
    private Label resumeLabel;

    @FXML
    public Button openStartCity;

    @FXML
    public TextField tripName;

    @FXML
    public DatePicker dateStartPicker;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void addNewStep(AnchorPane pane) {
        listSetup.getChildren().add(pane);
    }

    @FXML
    void onNewPlaneClick(){
        listener.onNewPlaneClick();
    }

    @FXML
    void onNewHotelClick(){
        listener.onNewHotelClick();
    }

    @FXML
    void onChangeNameTrip(){listener.onChangeTripName();}

    @FXML
    void onChangeDestination() throws IOException {listener.onChangeDestination();}

    @FXML
    void onChangeStartDate(){listener.onDatePickerChanger();}

    public void removePane(AnchorPane pane) {
        listSetup.getChildren().remove(pane);
    }

    public String getTripNameField(){
        return tripName.getText();
    }

    public String getDatePickerDate(){
        return dateStartPicker.getValue().toString();
    }

    public void addStartCity(City selectedItem) {
        this.startCity = selectedItem;
    }

    public void setStep(Plane plane) {
    }

    public void setCityName(City city) {
        openStartCity.setText(city.getName()+" ("+city.getCountry()+")");
    }

    public void setStepList(StepList stepList) {
        this.stepList = stepList;
    }

    public void updateResumeLabel() {
        stepList.getTotalPrice();

        stepList.getTotalHour();
        resumeLabel.setText(df.format(stepList.getTotalDistance())+" km, "+df.format(stepList.getTotalHour())+" heures, "+df.format(stepList.getTotalPrice())+" euros");

    }

    public void setStartCity() {
        openStartCity.setText(stepList.getStartCity().getName());
    }

    public void setSetupData() {
        setStartCity();
        setTripName();
        setTripDate();
        updateResumeLabel();
    }

    private void setTripName() {
        tripName.setText(stepList.getNameTrip());
    }

    private void setTripDate() {
        dateStartPicker.setPromptText(stepList.getStartDate());
    }

    public interface Listener {
        void onChangeDestination() throws IOException;
        void onNewPlaneClick();
        void onNewHotelClick();
        void onChangeTripName();
        void onDatePickerChanger();
    }
}

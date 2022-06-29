package be.groupe18.clientfx.projetvoyage.views;

import be.groupe18.common.models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class SetupPlaneView implements Initializable {

    private Plane plane;

    private static final DecimalFormat df = new DecimalFormat("0.00");


    @FXML
    private Label resumeLabel;

    @FXML
    private Button destButton;

    @FXML
    private Button buttonTest;

    @FXML
    private Spinner<Integer> waitingTimeSpinner;

    @FXML
    private ToggleGroup speedGroup = new ToggleGroup();

    @FXML
    private RadioButton speed700;

    @FXML
    private ChoiceBox<String> choicePriceKm = new ChoiceBox<>();

    @FXML
    private RadioButton speed900;

    @FXML
    private TitledPane titlePlane;

    private Listener listener;

    int spinnerMin=0;
    int spinnerMax=1440;
    SpinnerValueFactory<Integer> spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(spinnerMin,spinnerMax);

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @FXML
    void onClosePlaneClick(){
        listener.onClosePlaneClick();
    }

    @FXML
    void onWaitingTimeChange(){
        plane.setWaitingTime(Integer.parseInt(waitingTimeSpinner.getValue().toString()));
        listener.updateLabel();
    }

    @FXML
    void onRadioChange(){
        RadioButton rb = (RadioButton) speedGroup.getSelectedToggle();
        plane.setFlightSpeed(Integer.parseInt(rb.getText().substring(0,3)));
        listener.updateLabel();
    }

    @FXML
    void onPriceKmChange(){
        plane.setPriceKm(Double.parseDouble(String.valueOf(choicePriceKm.getValue())));
        listener.updateLabel();
    }

    @FXML
    void onDestClick() throws IOException {
        listener.onDestClick();
    }


    public void setPlane(Plane plane) {
        this.plane=plane;
    }

    public void updateLabel() {
        double time = plane.getHours();
        double price = plane.getPrice();
        double distance = plane.getDistance();
        resumeLabel.setText(df.format(distance)+" km, "+df.format(time)+" heures, "+df.format(price)+" euros");
        titlePlane.setText("Voyage en avion ("+plane.getCityName()+", "+df.format(distance)+" km, "+df.format(time)+" heures, "+df.format(price)+" euros)");

    }

    public void refreshTitleText() {
        destButton.setText(plane.getCityName()+" ("+plane.getCountry()+")");
    }

    public void refreshDistance(){
        listener.getLastCity(plane);
    }

    public void setAllData() {
        try{
        System.out.println((Math.round(plane.getWaitingTime())*60));
        destButton.setText(plane.getCityName());
        double data = plane.getWaitingTime();
        waitingTimeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(spinnerMin,spinnerMax, (int) data));
        chooseRadio();
        choicePriceKm.setValue(String.valueOf(plane.getPriceKM()));
        }
        catch (Exception e){
            System.out.println(e);
        }
        updateLabel();
    }

    private void chooseRadio() {
        switch (plane.getFlightSpeed()) {
            case 700 -> speedGroup.selectToggle(speed700);
            case 900 -> speedGroup.selectToggle(speed900);
        }
    }

    public interface Listener {
        void onClosePlaneClick();
        void onDestClick() throws IOException;
        void updateLabel();
        void getLastCity(Plane plane);
    }

    @FXML
    public void spinnersAction(ActionEvent event){
        waitingTimeSpinner.setValueFactory(spinner);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        waitingTimeSpinner.setValueFactory(spinner);
    }
}

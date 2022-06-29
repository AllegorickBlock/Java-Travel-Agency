package be.groupe18.clientfx.projetvoyage.views;

import be.groupe18.common.models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SetupHotelView implements Initializable {



    private int nbrNight;
    private int priceNight;
    private Hotel hotel;

    public int getNbrNight() {
        return nbrNight;
    }

    public int getPriceNight() {
        return priceNight;
    }

    @FXML
    private Label footerHotel;

    @FXML
    private Label breakfastLabel;

    @FXML
    private TitledPane hotelTitle;

    @FXML
    private Button buttonTest;

    @FXML
    private Spinner<Integer> priceNightSpinner;

    @FXML
    private Spinner<Integer> priceBreakfastSpinner;

    @FXML
    private CheckBox toggleBreakfast;

    @FXML
    private Spinner<Integer> nbrNightSpinner;
    int spinnerMin=0;
    int spinnerMax=30;
    int spinnerMinBreakfast=1;
    int spinnerMaxBreakfast=1000;
    SpinnerValueFactory<Integer> spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(spinnerMin,spinnerMax);
    SpinnerValueFactory<Integer> spinner2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(spinnerMin,spinnerMax);
    SpinnerValueFactory<Integer> spinner3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(spinnerMinBreakfast,spinnerMaxBreakfast);

    private Listener listener;

    public void setListener(Listener listener) {this.listener = listener;}

    @FXML
    void onCloseHotelClick(){
        listener.onCloseHotelClick();
    }

    @FXML
    void onNbrNightChange(){
        hotel.setNbrNight(Integer.parseInt(nbrNightSpinner.getValue().toString()));
        listener.updateLabels();
    }

    @FXML
    void onPriceNightChange(){
        hotel.setPriceByNight(Integer.parseInt(priceNightSpinner.getValue().toString()));
        listener.updateLabels();
    }

    @FXML
    void onPriceBreakfastChange(){
        hotel.setBreakfastPrice(Integer.parseInt(priceBreakfastSpinner.getValue().toString()));
        listener.updateLabels();
    }

    @FXML
    void onToggleBreakfast(){
        boolean check = toggleBreakfast.isSelected();
        toggleBreakfastVisible(check);
        hotel.setBreakfast(check);
        listener.updateLabels();

    }

    private void toggleBreakfastVisible(boolean check) {
        priceBreakfastSpinner.setVisible(check);
        breakfastLabel.setVisible(check);
    }

    public void setHotel(Hotel hotel) {
        this.hotel=hotel;
    }

    public void updateLabel() {
        footerHotel.setText(hotel.getNbrNight()+" nuit(s) à l'hôtel pour "+ hotel.getPrice()+" euros");
        hotelTitle.setText(hotel.getNbrNight()+" nuit(s) à l'hôtel pour "+ hotel.getPrice()+" euros");
    }

    public void setAllData() {
        int nbrNightSelected = hotel.getNbrNight();
        int priceNight = hotel.getPriceNight();
        int priceBreakfast = hotel.getBreakfastPrice();
        boolean breakfastVisible = hotel.getBreakfast();

        toggleBreakfast.setSelected(breakfastVisible);
        priceNightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(spinnerMin,spinnerMax,priceNight));
        nbrNightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(spinnerMin,spinnerMax,nbrNightSelected));
        priceBreakfastSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(spinnerMinBreakfast,spinnerMaxBreakfast,priceBreakfast));
        toggleBreakfastVisible(breakfastVisible);
    }

    public interface Listener {
        void onCloseHotelClick();
        void updateLabels();
    }

    @FXML
    public void spinnersAction(ActionEvent event){
        priceNightSpinner.setValueFactory(spinner);
        nbrNightSpinner.setValueFactory(spinner2);
        priceBreakfastSpinner.setValueFactory(spinner3);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        priceNightSpinner.setValueFactory(spinner);
        nbrNightSpinner.setValueFactory(spinner2);
        priceBreakfastSpinner.setValueFactory(spinner3);
    }
}

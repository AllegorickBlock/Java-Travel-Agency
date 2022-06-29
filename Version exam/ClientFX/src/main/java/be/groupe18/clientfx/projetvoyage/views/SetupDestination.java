package be.groupe18.clientfx.projetvoyage.views;

import be.groupe18.common.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;

import java.util.List;


public class SetupDestination {

    @FXML
    private Button btn_Choose;

    public ListView<City> getCity_List() {
        return city_List;
    }
    
    private Listener listener;

    @FXML
    private ListView<City> city_List;

    @FXML
    private TextField city_Search;

    private CityList cityList;

    @FXML
    void onCloseDestination(){
        listener.onCloseDestination(city_List.getSelectionModel().getSelectedItem());
    }


    public void populateListSearch(){
        List<City> list = cityList.getSearchListObject(city_Search.getText());
        populatelist(list);

    }

    public void setCity_List(CityList cityList2){
        this.cityList = cityList2;
        populatelist(this.cityList.getCityList());
    }

    private void populatelist(List<City> list) {
        ObservableList<City> myObservable = FXCollections.observableList(list);
        city_List.setItems(myObservable);

        city_List.setCellFactory(new Callback<ListView<City>, ListCell<City>>() {
            @Override
            public ListCell<City> call(ListView<City> cityListView) {
                ListCell<City> cell = new ListCell<City>(){
                    @Override
                    protected void updateItem(City t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getName() + " (" + t.getCountry()+")");
                        }
                        else{
                            setOnMouseClicked(mouseEvent -> {
                                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() ==2){
                                    listener.onCloseDestination(city_List.getSelectionModel().getSelectedItem());
                                }
                            });
                        }
                    }
                };
                return cell;
            }
        });
    }

    public void fillCityList() {
        populatelist(this.cityList.getCityList());
    }

    public void setListener(Listener listener) {this.listener = listener;}


    public interface Listener {
        void onCloseDestination(City city);
    }

}

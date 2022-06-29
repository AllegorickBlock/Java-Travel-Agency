package be.groupe18.clientfx.projetvoyage.controllers;

import be.groupe18.common.ObjectSocket;
import be.groupe18.common.models.*;
import be.groupe18.clientfx.projetvoyage.views.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SetupCnntroller {
    private final SetupController listener;
    private final Stage stage;
    private StepList stepList;
    private final ListController controllerList;
    private SetupView controllerSetup;
    private final CityList cityList;
    private ObjectSocket osocket;

    public SetupCnntroller(CityList cityList, Stage stage, ObjectSocket socket, SetupController listener) {
        this.listener = listener;
        this.stage = stage;
        controllerList = new ListController();
        this.cityList = cityList;
        this.osocket = socket;
    }

    public void show() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(SetupView.class.getResource("setup.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Définir son voyage");
        stage.setOnCloseRequest(event -> {
            stage.close();
            try {
                listener.closeSetup();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        controllerSetup = fxmlLoader.getController();
        controllerSetup.setStepList(stepList);
        if(stepList.isSet()){
            controllerSetup.setSetupData();
            generateListStep();

        }
        controllerSetup.setListener(new SetupView.Listener() {
            @Override
            public void onChangeDestination() throws IOException {
                Stage destStage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(SetupView.class.getResource("setupDestination.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                SetupDestination destinationController = fxmlLoader.getController();
                destinationController.setCity_List(cityList);
                destinationController.fillCityList();
                destinationController.setListener(city -> {
                    setStartCity(city);
                    controllerSetup.setCityName(city);
                    destStage.close();
                    stepList.refreshAllDistance();
                    controllerSetup.updateResumeLabel();
                });

                destStage.initModality(Modality.WINDOW_MODAL);
                destStage.initOwner(stage.getScene().getWindow());
                destStage.setScene(scene);
                destStage.setTitle("Choisir une destination");
                destStage.show();
            }

            @Override
            public void onNewPlaneClick() {
                FXMLLoader loader = new FXMLLoader(ResumeView.class.getResource("setupPlane.fxml"));

                try {
                    AnchorPane pane = loader.load();
                    SetupPlaneView controllerPlane = loader.getController();
                    Plane plane = new Plane();
                    stepList.addTripToList(plane);
                    controllerPlane.setPlane(plane);
                    controllerList.addController(controllerPlane);



                    controllerPlane.setListener(new SetupPlaneView.Listener() {
                        @Override
                        public void onClosePlaneClick() {
                            controllerSetup.removePane(pane);
                            stepList.removeStep(plane);
                            stepList.refreshAllDistance();
                            controllerList.refreshAllPLane();
                            controllerSetup.updateResumeLabel();
                        }

                        @Override
                        public void onDestClick() throws IOException {
                            Stage destStage = new Stage();
                            FXMLLoader fxmlLoader = new FXMLLoader(SetupView.class.getResource("setupDestination.fxml"));
                            Scene scene = new Scene(fxmlLoader.load());
                            SetupDestination destinationController = fxmlLoader.getController();
                            destinationController.setCity_List(cityList);
                            destinationController.fillCityList();
                            destinationController.setListener(city -> {
                                plane.setCity(city);
                                destStage.close();
                                controllerPlane.refreshTitleText();
                                controllerList.refreshAllPLane();
                                stepList.refreshAllDistance();
                                //stepList.updateDistanceNextCity(plane);
                                controllerPlane.refreshDistance();
                                controllerSetup.updateResumeLabel();

                            });

                            destStage.initModality(Modality.WINDOW_MODAL);
                            destStage.initOwner(stage.getScene().getWindow());
                            destStage.setScene(scene);
                            destStage.setTitle("Choisir une destination");
                            destStage.show();
                        }

                        @Override
                        public void updateLabel() {
                            controllerPlane.updateLabel();
                            setTotalResume();
                            controllerSetup.updateResumeLabel();
                        }

                        @Override
                        public void getLastCity(Plane plane) {
                            int index = stepList.getIndexList(plane);
                            plane.setDistance(getLastCityOnList(index));
                            controllerPlane.updateLabel();
                        }

                    });

                    controllerSetup.addNewStep(pane);

                } catch (IOException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onNewHotelClick() {
                FXMLLoader loader = new FXMLLoader(ResumeView.class.getResource("setupHotel.fxml"));

                try {
                    AnchorPane pane = loader.load();
                    SetupHotelView controllerHotel = loader.getController();

                    Hotel hotel = new Hotel();
                    controllerHotel.setHotel(hotel);
                    stepList.addTripToList(hotel);
                    controllerHotel.setListener(new SetupHotelView.Listener() {
                        @Override
                        public void onCloseHotelClick() {
                            controllerSetup.removePane(pane);
                            stepList.removeStep(hotel);
                            controllerSetup.updateResumeLabel();
                        }

                        @Override
                        public void updateLabels() {
                            controllerHotel.updateLabel();
                            setTotalResume();
                            controllerSetup.updateResumeLabel();
                        }
                    });

                    controllerSetup.addNewStep(pane);

                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onChangeTripName() {
                stepList.setTripName(controllerSetup.getTripNameField());
            }

            @Override
            public void onDatePickerChanger() {
                stepList.setStartDate(controllerSetup.getDatePickerDate());
            }
        });

        stage.setScene(scene);
        stage.show();

    }

    private void generateListStep() {
        ArrayList<AbstractStep> abstractSteps = stepList.getStepList();
        for (AbstractStep abstractStep : abstractSteps) {
            if(abstractStep instanceof Plane) {
                generatePlane(abstractStep);
            }
            else generateHotel(abstractStep);
        }
    }

    private void generateHotel(AbstractStep abstractStep) {

        Hotel hotel = (Hotel) abstractStep;

        FXMLLoader loader = new FXMLLoader(ResumeView.class.getResource("setupHotel.fxml"));

        try {
            AnchorPane pane = loader.load();
            SetupHotelView controllerHotel = loader.getController();
            controllerHotel.setHotel(hotel);
            controllerHotel.setAllData();
            controllerHotel.updateLabel();

            controllerHotel.setListener(new SetupHotelView.Listener() {
                @Override
                public void onCloseHotelClick() {
                    controllerSetup.removePane(pane);
                    stepList.removeStep(hotel);
                    controllerSetup.updateResumeLabel();
                }

                @Override
                public void updateLabels() {
                    controllerHotel.updateLabel();
                    setTotalResume();
                    controllerSetup.updateResumeLabel();
                }
            });

            controllerSetup.addNewStep(pane);

        } catch (IOException e){
            e.printStackTrace();
        }
    }


    private void generatePlane(AbstractStep abstractStep) {

        Plane  plane = (Plane) abstractStep;

        FXMLLoader loader = new FXMLLoader(ResumeView.class.getResource("setupPlane.fxml"));

        try {
            AnchorPane pane = loader.load();
            SetupPlaneView controllerPlane = loader.getController();
            controllerPlane.setPlane(plane);
            controllerPlane.setAllData();
            controllerPlane.setListener(new SetupPlaneView.Listener() {
                @Override
                public void onClosePlaneClick() {
                    controllerSetup.removePane(pane);
                    stepList.removeStep(plane);
                    stepList.refreshAllDistance();
                    controllerList.refreshAllPLane();
                    controllerSetup.updateResumeLabel();
                }

                @Override
                public void onDestClick() throws IOException {
                    Stage destStage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(SetupView.class.getResource("setupDestination.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    SetupDestination destinationController = fxmlLoader.getController();
                    destinationController.setCity_List(cityList);
                    destinationController.fillCityList();
                    destinationController.setListener(city -> {
                        plane.setCity(city);
                        destStage.close();
                        controllerPlane.refreshTitleText();
                        controllerList.refreshAllPLane();
                        stepList.refreshAllDistance();
                        stepList.updateDistanceNextCity(plane);
                        controllerPlane.refreshDistance();
                        controllerSetup.updateResumeLabel();

                    });

                    destStage.initModality(Modality.WINDOW_MODAL);
                    destStage.initOwner(stage.getScene().getWindow());
                    destStage.setScene(scene);
                    destStage.setTitle("Choisir une destination");
                    destStage.show();
                }

                @Override
                public void updateLabel() {
                    controllerPlane.updateLabel();
                    setTotalResume();
                    controllerSetup.updateResumeLabel();
                }

                @Override
                public void getLastCity(Plane plane) {
                    int index = stepList.getIndexList(plane);
                    plane.setDistance(getLastCityOnList(index));
                    controllerPlane.updateLabel();
                }
            });

            controllerSetup.addNewStep(pane);

        } catch (IOException e){
            System.out.println(e.getMessage());
        }


    }

    private void setTotalResume() {
        double totalPrice = stepList.getTotalPrice();
        double totalDistance = stepList.getTotalDistance();
        double totalHour = stepList.getTotalHour();
        stepList.setTotal(totalDistance,totalPrice,totalHour);
    }

    public City getLastCityOnList(int index){

        if (stepList.getLastCity(index)==null){
            return stepList.getStartCity();
        }
        else return stepList.getLastCity(index);
    }

    public void setStartCity(City city){
        stepList.setStartCity(city);
    }

    public void setStepList(StepList stepList) {
        this.stepList=stepList;
    }

    public interface SetupController{
        void closeSetup() throws IOException;
    }

    static class ListController{

        ArrayList<SetupPlaneView> controllerPlaneList = new ArrayList<>();

        public void addController(SetupPlaneView controllerPlane) {
            this.controllerPlaneList.add(controllerPlane);
        }

        public void refreshAllPLane(){
            for (SetupPlaneView controller:controllerPlaneList){
                //System.out.println(controller.getWaitingTime());
            }
        }
    }
}

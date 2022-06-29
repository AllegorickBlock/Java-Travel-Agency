package be.groupe18.clientfx.projetvoyage.controllers;

import be.groupe18.clientfx.projetvoyage.network.NetworkCommunicationThread;
import be.groupe18.common.DoubleDispatch.LockingTrip;
import be.groupe18.common.DoubleDispatch.NumberClient;
import be.groupe18.common.DoubleDispatch.RemoveTrip;
import be.groupe18.common.DoubleDispatch.UpdateTrip;
import be.groupe18.common.ObjectSocket;
import be.groupe18.common.models.*;
import be.groupe18.common.NetworkProtocol;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class MainController extends Application {

    private CityList cityList;
    public static final Object key = new Object();
    private TripList tripList;
    private Stage resumeStage;
    private NetworkCommunicationThread thread;

    private ObjectSocket osocket;

    private ResumeCnntroller resumeController;
    @Override
    public void start(Stage stage) throws Exception {
        this.resumeStage=stage;

        initNetwork();
        resumeController = new ResumeCnntroller(stage,osocket, new ResumeCnntroller.ResumeController() {
            @Override
            public void onOpenTripClick(StepList stepList) throws IOException {
                stage.hide();
                openTrip(stepList);
            }

            @Override
            public void saveTripsToBin() {
                SaveToFile(resumeController.getTripsList());
            }

            @Override
            public void closeThread() {
                thread.closeThread();
            }

            @Override
            public void sendNewTripServer(StepList stepList){
                thread.sendNewTrip(stepList);
            }

            @Override
            public void removeTrip(StepList stepList) {
                int index = tripList.getResumeTrips().indexOf(stepList);
                System.out.println(index);
                thread.removeTrip(stepList,index);
            }

        });
        this.tripList = new TripList();
        resumeController.show();
        resumeController.setTripList(tripList);
        synchronized (key){
            try{
                resumeController.setTripList(this.tripList);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    private synchronized void initNetwork() {
        try {
            this.osocket =new ObjectSocket(new Socket("localhost", NetworkProtocol.PORT));
            this.thread = new NetworkCommunicationThread(osocket, new NetworkCommunicationThread.Listener() {
                @Override
                public void onSetCityList(CityList cityList) {
                    setCityList(cityList);
                }

                @Override
                public void onSetTripList(TripList tripList) {
                    setTripList(tripList);
                }

                @Override
                public void lockTrip(LockingTrip read) {
                    Platform.runLater(()->resumeController.lockTrip(read.getIndex()));
                }

                @Override
                public void newTrip() {
                    Platform.runLater(()-> resumeController.newTrip());

                }

                @Override
                public void updateTrip(UpdateTrip trip) {
                    Platform.runLater(()-> resumeController.updateTrip(trip.getStepList(), trip.getIndex()));
                }

                @Override
                public void deleteTrip(StepList read, int index) {
                    //Platform.runLater(()-> resumeController.);
                }

                @Override
                public void removeTrip(RemoveTrip trip) {
                    Platform.runLater(()-> resumeController.deleteTrip(trip));
                }

                @Override
                public void updateNumberClient(NumberClient numberClient) {
                    Platform.runLater(()-> resumeController.updateClientNumber(numberClient.getNumberClient()));
                }


            });
            thread.setDaemon(true);
            thread.start();
        } catch (IOException e) {
            System.err.println("Serveur non atteint");
        }
    }

    private void setTripList(TripList tripList) {
        this.tripList = tripList;
    }

    private void setCityList(CityList cityList) {
        this.cityList = cityList;
    }

    private void SaveToFile(TripList listStepList){
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("SavedTrips.bin"))) {
                out.writeObject(listStepList);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
    }

    private void openTrip(StepList stepList) throws IOException {
        Stage setupStage = new Stage();
        SetupCnntroller setupCnntroller = new SetupCnntroller(cityList,setupStage,this.osocket, () -> {
            setupStage.close();
            resumeController.refreshListTitle();
            resumeStage.show();
            thread.updateTrip(stepList,tripList.getResumeTrips().indexOf(stepList));
        });
        setupCnntroller.setStepList(stepList);
        thread.sendWorkingTrip(tripList.getResumeTrips().indexOf(stepList));
        setupCnntroller.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

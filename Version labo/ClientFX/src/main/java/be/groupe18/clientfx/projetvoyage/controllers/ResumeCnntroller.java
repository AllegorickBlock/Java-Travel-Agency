package be.groupe18.clientfx.projetvoyage.controllers;

import be.groupe18.common.ObjectSocket;
import be.groupe18.common.models.*;
import be.groupe18.clientfx.projetvoyage.views.ResumeTrip;
import be.groupe18.clientfx.projetvoyage.views.ResumeView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ResumeCnntroller {
    private final ResumeController listener;
    private final Stage stage;
    private ResumeView resumeController;
    private ObjectSocket osocket;

    private TripList tripList;
    private ArrayList<ResumeTrip> tripListController = new ArrayList<>();

    public ResumeCnntroller(Stage stage, ObjectSocket socket, ResumeController listener){
        this.stage = stage;
        this.listener = listener;
        this.osocket = socket;
    }
    public void show()throws IOException{

        this.tripList = new TripList();
        FXMLLoader fxmlLoader = new FXMLLoader(ResumeView.class.getResource("resume.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);


        resumeController = fxmlLoader.getController();
        resumeController.setListener(this::newTrip);


        /* Lecture du fichier txt (debug */
        stage.setOnCloseRequest(event -> {
            stage.close();
            try {
                listener.saveTripsToBin();
                listener.closeThread();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        stage.setTitle("Voyages");
        stage.setScene(scene);
        stage.show();
    }

    public void newTrip() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String source = stackTraceElements[2].getMethodName();
        FXMLLoader loader = new FXMLLoader(ResumeView.class.getResource("resumeTrip.fxml"));
        try {
            AnchorPane pane = loader.load();
            ResumeTrip controllerTrip = loader.getController();
            StepList stepList = new StepList();
            controllerTrip.setStepList(stepList);
            tripListController.add(controllerTrip);
            tripList.addToList(stepList);
            if(source.equals("onNewTripClick"))listener.sendNewTripServer(stepList);
            controllerTrip.setListener(new ResumeTrip.Listener() {
                @Override
                public void onOpenTripClick() throws IOException {
                   listener.onOpenTripClick(controllerTrip.getTrip());

                }

                @Override
                public void onRemoveTripClick() {
                    resumeController.removeTrip(pane);
                    tripList.removeToList(stepList);
                    listener.removeTrip(stepList);
                }
            });

            resumeController.addNewTrip(pane);

        } catch (IOException a) {
            a.printStackTrace();
        }
    }

    public ArrayList<StepList> getList() {
        return tripList.getResumeTrips();
    }

    public void refreshListTitle() {
        for (ResumeTrip resumeTrip: tripListController) {
            resumeTrip.refreshTitle();
        }
    }

    public void setTripList(TripList tripList) throws IOException {
        this.tripList = tripList;
        generateListTrip();
    }
    public void setTripList() {
        this.tripList = new TripList();
    }

    private void generateListTrip() {
        ArrayList<StepList> trips = this.tripList.getResumeTrips();

        for (StepList trip:trips) {
            FXMLLoader loader = new FXMLLoader(ResumeView.class.getResource("resumeTrip.fxml"));

            try {
                AnchorPane pane = loader.load();
                ResumeTrip controllerTrip = loader.getController();
                controllerTrip.setStepList(trip);
                this.tripListController.add(controllerTrip);

                controllerTrip.setListener(new ResumeTrip.Listener() {
                    @Override
                    public void onOpenTripClick() throws IOException {
                        listener.onOpenTripClick(controllerTrip.getTrip());
                    }

                    @Override
                    public void onRemoveTripClick() {
                        resumeController.removeTrip(pane);
                        tripList.removeToList(trip);
                        listener.removeTrip(trip);
                    }
                });

                resumeController.addNewTrip(pane);

            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setOnCloseRequest(event -> {
                stage.close();
                try {
                    listener.saveTripsToBin();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            refreshListTitle();
        }
    }

    public TripList getTripsList() {
        return this.tripList;
    }

    public void lockTrip(int read) {
        tripListController.get(read).lockStepList();
    }

    public void updateTrip(StepList trip, int index) {
        tripList.updateTrip(trip,index);
        tripListController.get(index).setStepList(trip);
        tripListController.get(index).unlockStepList();
        refreshListTitle();
    }

    public void deleteTrip(StepList trip, int index) {
        tripList.removeToList(trip);
        tripListController.remove(index);

    }

    public interface ResumeController{
        void onOpenTripClick(StepList stepList) throws IOException;
        void saveTripsToBin();
        void closeThread();
        void sendNewTripServer(StepList stepList) throws IOException;
        void removeTrip(StepList stepList);
    }
}

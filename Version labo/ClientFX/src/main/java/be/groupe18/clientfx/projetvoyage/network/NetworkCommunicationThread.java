package be.groupe18.clientfx.projetvoyage.network;

import be.groupe18.clientfx.projetvoyage.controllers.MainController;
import be.groupe18.common.ObjectSocket;
import be.groupe18.common.models.CityList;
import be.groupe18.common.models.StepList;
import be.groupe18.common.models.TripList;


import java.io.IOException;

public class NetworkCommunicationThread extends Thread{
    private final Listener listener;
    private ObjectSocket objectSocket;
    private Boolean isRunning = true;


    public NetworkCommunicationThread(ObjectSocket objectSocket, Listener listener){
        this.listener = listener;
        this.objectSocket = objectSocket;
    }

    public void closeThread(){
        this.isRunning = false;
    }

    /**
     * Initialize the socket with the server. First the server send all airport available
     * and after the serveur send the current list of trip.
     */
    @Override
    public void run() {
        try {
            synchronized (MainController.key){
                CityList cityList = objectSocket.read();
                listener.onSetCityList(cityList);
                TripList tripList = objectSocket.read();
                listener.onSetTripList(tripList);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        while(isRunning){
            try {
                Object read = objectSocket.read();
                if(read instanceof Integer){
                    listener.lockTrip((int) read);
                }
                if(read instanceof StepList){
                    int index = objectSocket.read();
                    if (index ==-1000){
                        listener.newTrip();
                    }
                    else if (index < 0) {
                        index *=-1;
                        listener.deleteTrip((StepList) read,index);
                    }
                    else listener.updateTrip((StepList) read,index);
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This function sends the index of the working trip to the server to lock all buttopns on this trip
     * 
     * @param indexOf The index of the trip in the list of trips.
     */
    public void sendWorkingTrip(int indexOf) throws IOException {
        objectSocket.write(indexOf);
    }

    /**
     * It sends a new trip to the server, and then sends an index of -1000 to indicate that the
     * trip is new
     * 
     * @param stepList A new list of steps.
     */
    public void sendNewTrip(StepList stepList){
        try {
            objectSocket.write(stepList);
            int index = -1000;
            objectSocket.write(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   /**
    * It send a StepList and the index of this steplist to update them
    * 
    * @param stepList A list of steps that the user has taken.
    * @param index the index of the trip in the list of trips
    */
    public void updateTrip(StepList stepList, int index) {
        try {
            objectSocket.write(stepList);
            objectSocket.write(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * It takes a StepList and an index, and sends them to the server
     * 
     * @param stepList A list of steps.
     * @param index the index of the trip to be removed
     */
    public void removeTrip(StepList stepList, int index) {
        try {
            index *=-1;
            objectSocket.write(stepList);
            objectSocket.write(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface Listener {
        void onSetCityList(CityList cityList);
        void onSetTripList(TripList tripList);
        void lockTrip(int read);
        void newTrip();
        void updateTrip(StepList read, int index);
        void deleteTrip(StepList read, int index);
    }
}

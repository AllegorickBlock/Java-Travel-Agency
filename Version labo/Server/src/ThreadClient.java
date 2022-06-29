import be.groupe18.common.ObjectSocket;
import be.groupe18.common.models.StepList;

import java.io.IOException;

public class ThreadClient extends Thread {
    private final Server server;
    private ObjectSocket osocket;

    public ThreadClient(ObjectSocket osocket, Server server) {
        this.osocket = osocket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            osocket.write(server.getCityList());
            System.out.println("Citylist envoyée");
            if(server.getListTrips()!=null){
                osocket.write(server.getListTrips());
            }else osocket.write(server.getListTrips());
            System.out.println("TripList envoyée");

            while (true){
                Object read = osocket.read();
                if(read instanceof Integer){
                    server.broadcastLock((int)read,osocket);
                }
                if(read instanceof StepList){
                    StepList stepList = (StepList) read;
                    int index = osocket.read();

                    if (index == -1000){
                        server.addNewTrip(stepList,osocket);
                    }
                    else if(index < 0) {
                        System.out.println("index to delete "+index);
                        server.deleteTrip(stepList,osocket);
                    }
                    else server.updateTrip(stepList,index,osocket);
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
                int index = osocket.read();

            } catch (IOException | ClassNotFoundException e) {
            System.err.println("Client disconnected");
            server.terminate(this);
        }


    }

    public ObjectSocket getSocket() {return osocket;}

    /**
     * It sends the index of the lock the trip to lock
     * 
     * @param index The index of the lock to be sent.
     */
    public void sendLock(int index) throws IOException {
        osocket.write(index);
    }

    /**
     * "This function sends a new trip to the server."
     *
     */
    public void newTrip() {
        try {
            osocket.write(new StepList());
            osocket.write((int)-1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * It writes a StepList object to the server, then writes an integer -1 to the server
     * 
     * @param stepList A list of steps that the user has inputted.
     */
    public void deleteTrip(StepList stepList) {
        try {
            osocket.write(stepList);
            osocket.write((int)-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Sending the stepList and the index to the client to update them.
    public void updateTrip(StepList stepList, int index) {
        try {
            osocket.write(stepList);
            osocket.write(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
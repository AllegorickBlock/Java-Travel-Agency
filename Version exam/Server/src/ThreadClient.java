import be.groupe18.common.DoubleDispatch.*;
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
            server.broadcastNumberClient();

            while (true){
                AbstractFunction function = osocket.read();
                function.accept(new Function() {
                    @Override
                    public void newTrip(NewTrip trip) {
                        server.addNewTrip(trip,osocket);
                    }

                    @Override
                    public void updatetrip(UpdateTrip trip) {
                        server.updateTrip(trip,osocket);
                    }

                    @Override
                    public void removeTrip(RemoveTrip trip) {
                        server.removeTrip(trip,osocket);
                    }

                    @Override
                    public void lockTrip(LockingTrip trip) throws IOException {

                        server.broadcastLock(trip,osocket);
                    }

                    @Override
                    public void numberClient(NumberClient numberClient) {
                        //only server->client
                    }
                });
            }
        } catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
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
    public void sendLock(LockingTrip index) throws IOException {
        osocket.write(index);
    }

    /**
     * "This function sends a new trip to the server."
     *
     * @param newTrip
     */
    public void newTrip(NewTrip newTrip) {
        try {
            osocket.write(newTrip);
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
    public void updateTrip(UpdateTrip trip) {
        try {
            osocket.write(trip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void removeTrip(RemoveTrip trip) {
        try {
            osocket.write(trip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void numberClient(NumberClient size) {
        try {
            osocket.write(size);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
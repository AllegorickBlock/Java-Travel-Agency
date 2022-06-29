import be.groupe18.common.DoubleDispatch.*;
import be.groupe18.common.NetworkProtocol;
import be.groupe18.common.ObjectSocket;
import be.groupe18.common.models.CityList;
import be.groupe18.common.models.StepList;
import be.groupe18.common.models.TripList;
import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {

    private ArrayList<ThreadClient> threadClients;
    private final CityList cityList = new CityList();
    private TripList tripList;

    public static void main(String[] args) {
        Server server = new Server();
        server.go();
    }

    /**
     * "If the file SavedTrips.bin exists, read it and return the TripList object it contains. If the
     * file doesn't exist, return a new TripList object."
     *
     * 
     * @return The method is returning a TripList object.
     */
    private TripList reloadSavedTrips() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("SavedTrips.bin"))) {
            return (TripList) in.readObject();
        }
        catch (Exception ex) {
            return new TripList();
        }
    }

    private void saveToFile(){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("SavedTrips.bin"))) {
            //System.out.println(listStepList.getResumeTrips().get(0).getStartCity().getName());
            //out.writeObject(listStepList);
            out.writeObject("test");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void go() {
        this.threadClients = new ArrayList<>();
        try {

            ServerSocket serverSocket = new ServerSocket(NetworkProtocol.PORT);
            this.tripList = reloadSavedTrips();

           // Waiting for a client to connect to the server.
            while (true) {

                System.out.println("En attente d'un client");
                ObjectSocket osocket = new ObjectSocket(serverSocket.accept());
                System.out.println("Client accepté");

                ThreadClient threadClient = new ThreadClient(osocket, this);
                this.threadClients.add(threadClient);
                threadClient.start();
            }
        }
        catch (IOException e) {
                System.out.println("Le serveur n'a pas pu démarrer");
        }
    }

    /**
     * This function returns the cityList  
     * @return The cityList object.
     */
    public CityList getCityList() {return this.cityList;}

    /**
     * This function returns the tripList.
     * 
     * @return The tripList object.
     */
    public TripList getListTrips() {return this.tripList;}

    /**
     * It sends a message to all clients except the one that sent the message to disable the button of the selected trip
     *  @param trip the index of the object that is being locked
     * @param osocket the socket of the client that is sending the message
     */
    public void broadcastLock(LockingTrip trip, ObjectSocket osocket) throws IOException {
        for (ThreadClient client : threadClients) {
            if (client.getSocket() != osocket) {
                client.sendLock(trip);
            }
        }
    }

    /**
     * This function removes the threadClient from the list of threadClients
     * 
     * @param threadClient The ThreadClient object that is being terminated.
     */
    public void terminate(ThreadClient threadClient) {
        this.threadClients.remove(threadClient);
        broadcastNumberClient();
    }

    public void broadcastNumberClient() {
        for (ThreadClient client : threadClients) {
            client.numberClient(new NumberClient(this.threadClients.size()));
        }
    }

    /**
     * This function adds a new trip to the trip list and broadcasts the new trip to all connected
     * clients
     *
     * @param osocket the ObjectSocket that the client is using to communicate with the server
     * @param newTrip
     */
    public void addNewTrip(NewTrip newTrip, ObjectSocket osocket) {
        tripList.addToList(new StepList());
        broadcastNewTrip(newTrip,osocket);
    }

    /**
     * The function delete a trip from the tripList and broadcasts the deletion to all other clients
     * 
     * @param stepList The stepList object that is to be deleted
     * @param osocket the ObjectSocket of the client who sent the deleteTrip request
     */
    public void deleteTrip(StepList stepList, ObjectSocket osocket) {
        tripList.removeToList(stepList);
        broadcastDeleteTrip(stepList,osocket);
    }

    /**
     * It take the trip to delete and send it to all connected clients exceptthe sender
     * 
     * @param stepList a list of steps that the user has taken
     * @param osocket the socket of the client who sent the message
     */
    private void broadcastDeleteTrip(StepList stepList, ObjectSocket osocket) {
        for (ThreadClient client : threadClients) {
            if (client.getSocket() != osocket) {
                client.deleteTrip(stepList);
            }
        }
    }

    /**
     * If the client is not the one who sent the message, then send a new trip message to the client.
     *
     * @param newTrip
     * @param osocket the socket that the client is connected to
     */
    private void broadcastNewTrip(NewTrip newTrip, ObjectSocket osocket) {
        for (ThreadClient client : threadClients) {
            if (client.getSocket() != osocket) {
                client.newTrip(newTrip);
            }
        }
    }

    /**
     * The function updates the tripList with the new stepList and then broadcasts the update to all
     * the other clients
     * 
     * @param trip
     * @param osocket the socket of the user who is updating the trip
     */
    public void updateTrip(UpdateTrip trip, ObjectSocket osocket) {
        tripList.updateTrip(trip.getStepList(), trip.getIndex());
        broadcastUpdateTrip(trip,osocket);
    }

    /**
     * For each client connected to the server, if the client is not the one who sent the message, then
     * send the update of the trip.
     * 
     * @param trip
     * @param osocket the socket of the client who sent the message
     */
    private void broadcastUpdateTrip(UpdateTrip trip, ObjectSocket osocket) {
        for (ThreadClient client : threadClients) {
            if (client.getSocket() != osocket) {
                client.updateTrip(trip);
            }
        }
    }


    public void removeTrip(RemoveTrip trip, ObjectSocket osocket) {
        System.out.println(trip.getIndex());
        tripList.removeToList(trip.getStepList());
        for (ThreadClient client : threadClients) {
            if (client.getSocket() != osocket) {
                client.removeTrip(trip);
            }
        }
    }
}

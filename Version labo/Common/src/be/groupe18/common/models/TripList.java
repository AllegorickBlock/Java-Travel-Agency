package be.groupe18.common.models;

import java.io.Serializable;
import java.util.ArrayList;

public class TripList implements Serializable {

    private ArrayList<StepList> resumeTrips;

    public TripList() {resumeTrips = new ArrayList<>();}

    /**
     * This function returns an ArrayList of StepList objects
     * 
     * @return The resumeTrips ArrayList is being returned.
     */
    public ArrayList<StepList> getResumeTrips() {return resumeTrips;}

    public void setResumeTrips(ArrayList<StepList> resumeTrips) {this.resumeTrips = resumeTrips;}

    /**
     * This function adds a StepList object to the resumeTrips ArrayList
     * 
     * @param stepList The StepList object that you want to add to the list of trips.
     */
    public void addToList(StepList stepList) {this.resumeTrips.add(stepList);}

    /**
     * It removes a stepList from the resumeTrips list.
     * 
     * @param stepList The StepList to be removed from the list.
     */
    public void removeToList(StepList stepList) {this.resumeTrips.remove(stepList);}

    // Updating the resumeTrips ArrayList at the index specified with the stepList object.
    public void updateTrip(StepList stepList, int index) {this.resumeTrips.set(index,stepList);}
}

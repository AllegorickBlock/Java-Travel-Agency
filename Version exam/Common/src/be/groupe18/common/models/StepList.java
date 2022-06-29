package be.groupe18.common.models;

import java.io.Serializable;
import java.util.ArrayList;

public class StepList implements Serializable {
    private City startCity;

    private String tripName;

    private String startDate;

    private ArrayList<AbstractStep> abstractStepList = new ArrayList<>();
    private double totalDistance;
    private double totalPrice;
    private double totalHour;

    /**
     * This function sets the start city of the trip
     * 
     * @param startCity The city that the tour starts at
     */
    public void setStartCity(City startCity) {this.startCity = startCity;}

    /**
     * This function returns the startCity of the trip
     * 
     * @return The startCity object.
     */
    public City getStartCity() {return startCity;}

    // A method that adds a step to the list of steps.
    public void addTripToList(AbstractStep abstractStep){
        abstractStepList.add(abstractStep);}

    /**
     * It takes a list of steps, and returns the sum of the prices of each step
     * 
     * @return The total price of all the steps in the stepList.
     */
    public double getTotalPrice(){return abstractStepList.stream().mapToDouble(AbstractStep::getPrice).sum();}

    /**
     * It takes a list of steps, and returns the sum of the distances of each step
     * 
     * @return The total distance of all the steps in the stepList.
     */
    public double getTotalDistance(){return abstractStepList.stream().mapToDouble(AbstractStep::getDistance).sum();}

    /**
     * Get the sum of all the hours in the step list.
     * 
     * @return The sum of all the hours in the stepList.
     */
    public double getTotalHour() {return abstractStepList.stream().mapToDouble(AbstractStep::getHour).sum();}

    /**
     * This function sets the value of the tripName
     * 
     * @param tripNameField The name of the trip.
     */
    public void setTripName(String tripNameField) {this.tripName = tripNameField;}

    /**
     * This function sets the start date of the trip.
     * 
     * @param datePickerDate The date that the user selects from the date picker.
     */
    public void setStartDate(String datePickerDate) {
        this.startDate = datePickerDate;
    }

    /**
     * This function returns the start date of the trip
     * 
     * @return The startDate variable is being returned.
     */
    public String getStartDate(){
        return this.startDate;
    }

    /**
     * This function removes a step from the step list
     * 
     * @param steap The step to be removed from the list.
     */
    public void removeStep(AbstractStep steap) {
        abstractStepList.remove(steap);
    }

    /**
     * This function returns the index of the plane in the list
     * 
     * @param plane The plane to get the index of.
     * @return The index of the plane in the list.
     */
    public int getIndexList(Plane plane) {
        return abstractStepList.indexOf(plane);
    }

    /**
     * It returns the last city before the current city
     * 
     * @param index the index of the current step
     * @return The last city.
     */
    public City getLastCity(int index){
        for (int i = index-1; i >= 0;i--){
            if(abstractStepList.get(i).getClass()  == Plane.class){
                return abstractStepList.get(i).getCity();
            }
        }
        return null;
    }

    /**
     * This function sets the total distance, total price, and total hour of the trip
     * 
     * @param totalDistance the total distance of the trip
     * @param totalPrice the total price of the trip
     * @param totalHour total hours of the trip
     */
    public void setTotal(double totalDistance, double totalPrice, double totalHour) {
        this.totalDistance = totalDistance;
        this.totalPrice = totalPrice;
        this.totalHour = totalHour;
    }

    /**
     * This function is used to update the distance of the next city for the plane
     * 
     * @param plane1 the plane that is being updated
     */
    public void updateDistanceNextCity(Plane plane1) {
        int index = this.getIndexList(plane1);

        for(int i = index; i < abstractStepList.size(); i++){
            AbstractStep abstractStep = abstractStepList.get(i);
            if (abstractStep.getClass() == Plane.class) {
                Plane plane = (Plane) abstractStep;
                plane.setDistance(plane.getCity());
            }
        }
    }

    /**
     * Refresh distance of all plane
     */
    public void refreshAllDistance() {
        boolean firstplane=true;
        for (AbstractStep abstractStep : abstractStepList) {
            if (abstractStep.getClass() == Plane.class) {
                if (firstplane) {
                    ((Plane) abstractStep).setDistance(this.startCity);
                    firstplane = false;
                } else {
                    int j = getIndexList((Plane) abstractStep);
                    ((Plane) abstractStep).setDistance(getLastCity(j));
                }
            }
        }
    }

    /**
     * This function returns the name of the trip
     * 
     * @return The name of the trip.
     */
    public String getNameTrip() {
        return this.tripName;
    }

    /**
     * This function checks if the stepList is empty or not
     * 
     * @return The size of the stepList.
     */
    public boolean isSet() {
        int size= abstractStepList.size();
        return size != 0;
    }

    /**
     * This function returns the stepList
     * 
     * @return The stepList
     */
    public ArrayList<AbstractStep> getStepList() {
        return this.abstractStepList;
    }
}

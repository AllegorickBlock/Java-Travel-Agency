package be.groupe18.common.models;

public class Plane extends AbstractStep {

    private City city;
    private double priceKm;
    private double waitingTime;
    private int flightSpeed;
    private double price;
    private int hours;
    private double distance;


    // It's a constructor.
    public Plane() {
        this.flightSpeed=700;
        this.waitingTime=0;
        this.priceKm=0.2;
    }

    /**
     * The function getPrice() returns the price of the flight
     * 
     * @return The price of the trip.
     */
    @Override
    public double getPrice() {return priceKm * distance;}

    /**
     * This function returns the total time it takes for the plane to fly to the destination and waiting time
     * for flight
     * 
     * @return The hours of the flight.
     */
    public double getHours(){return (distance/flightSpeed)+waitingTime;}

    /**
     * This function returns the distance between the two cities
     * 
     * @return The distance between the two cities.
     */
    public double getDistance(){return distance;}

    /**
     * This function returns the total time it takes for the plane to fly to the destination and waiting time
     * for flight
     * 
     * @return The hours of the day.
     */
    @Override
    public double getHour() {return getHours();}

    /**
     * Set the time of the flight
     * 
     * @param speed the speed of the plane
     * @param distance the distance between the two cities
     * @param waiting the waiting time before the flight
     */
    private void setTotalFlightTime(int speed, int distance, int waiting){
        this.hours = (speed/distance)+waiting;
    }

    /**
     * This function takes in a city object and sets the distance between the current city and the city
     * passed in
     * 
     * @param city2 The city that you want to find the distance between.
     */
    public void setDistance(City city2){this.distance = city.getDistanceBetweenCities(city2);}

    /**
     * This function takes in a double value and sets the waiting time of the passenger to the value
     * divided by 60 to fit with the time in base 10
     * 
     * @param waitingTime The time the customer has been waiting in the queue.
     */
    public void setWaitingTime(double waitingTime) {this.waitingTime = waitingTime/60;}

    /**
     * This function sets the flight speed.
     * 
     * @param flightSpeed The speed of the flight in knots.
     */
    public void setFlightSpeed(int flightSpeed) {this.flightSpeed=flightSpeed;}

    /**
     * This function sets the price per kilometer of the flight.
     * 
     * @param priceKm The price per kilometer.
     */
    public void setPriceKm(double priceKm) {this.priceKm=priceKm;}

    /**
     * This function sets the city of departure city.
     * 
     * @param city The city object that contains the city name and the city ID.
     */
    public void setCity(City city) {this.city=city;}

    /**
     * This function returns the name of the city
     * 
     * @return The name of the city.
     */
    public String getCityName() {return city.getName();}

    /**
     * This function returns the country of the city
     * 
     * @return The country of the city.
     */
    public String getCountry() {return city.getCountry();}

    /**
     * This function returns the city of the current object
     * 
     * @return The city object.
     */
    public City getCity(){return city;}

    /**
     * Returns the waiting time in minutes.
     * 
     * @return The waiting time in minutes.
     */
    public double getWaitingTime() {return this.waitingTime*60;}

    /**
     * This function returns the price per kilometer of the car.
     * 
     * @return The price per kilometer.
     */
    public double getPriceKM() {return this.priceKm;}

    /**
     * This function returns the flight speed.
     * 
     * @return The flight speed of the plane.
     */
    public int getFlightSpeed() {return this.flightSpeed;}
}

package be.groupe18.common.models;

public class Hotel extends Step {

    private int nbrNight;
    private int priceByNight;

    // It's a constructor.
    public Hotel() {
        this.nbrNight = 0;
        priceByNight = 0;
    }

/**
 * The function getPrice() returns the price of the room for the number of nights
 * 
 * @return The price of the room for the number of nights.
 */
    @Override
    public double getPrice() {
        return nbrNight*priceByNight;
    }

    @Override
    public City getCity() {
        return null;
    }

    @Override
    public double getDistance() {
        return 0;
    }

    /**
     * The function getHour() returns the number of hours.
     * 
     * @return The number of hours.
     */
    @Override
    public double getHour() {
        return this.nbrNight*24;
    }

    /**
     * This function sets the price by night
     * 
     * @param price the price of the reservation
     */
    public void setPriceByNight(int price){
        this.priceByNight = price;
    }

    /**
     * This function sets the number of nights
     * 
     * @param nbrNight number of nights
     */
    public void setNbrNight(int nbrNight) {this.nbrNight = nbrNight;}

    /**
     * This function returns the number of nights
     * 
     * @return The number of nights.
     */
    public int getNbrNight() {return nbrNight;}

    /**
     * This function returns the price per night.
     * 
     * @return The price by night.
     */
    public int getPriceNight() {return this.priceByNight;}
}

package be.groupe18.common.models;

public class Hotel extends AbstractStep {

    private int nbrNight;
    private int priceByNight;
    private boolean breakfast;
    private int breakfastPrice;

    // It's a constructor.
    public Hotel() {
        this.nbrNight = 0;
        this.priceByNight = 0;
        this.breakfastPrice = 1;
    }

/**
 * The function getPrice() returns the price of the room for the number of nights
 * 
 * @return The price of the room for the number of nights.
 */
    @Override
    public double getPrice() {
        double result = nbrNight*priceByNight;
        if(this.breakfast) result+= this.breakfastPrice*this.nbrNight;

        return result;
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

    public void setBreakfast(boolean bool) {this.breakfast = bool;}

    public void setBreakfastPrice(int breakfastPrice) {this.breakfastPrice = breakfastPrice;}


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

    public boolean getBreakfast() {return this.breakfast;}

    public int getBreakfastPrice() {return this.breakfastPrice;}


}

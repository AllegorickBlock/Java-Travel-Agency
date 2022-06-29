package be.groupe18.common.models;

import java.io.Serializable;

public class City implements Serializable{

    private String name;
    private String nameASCII;
    private double latitude;
    private double longitude;
    private String country;
    private String countryISO2;
    private String getCountryISO3;
    private String adminCapital;
    private String capital;
    private String population;
    private String id;


    public String getId() {return id;}


    // A constructor.
    public City(String name, String nameASCII, double latitude, double longitude, String country, String countryISO2, String getCountryISO3, String adminCapital, String capital, String population, String id) {
        this.name = name;
        this.nameASCII = nameASCII;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.countryISO2 = countryISO2;
        this.getCountryISO3 = getCountryISO3;
        this.adminCapital = adminCapital;
        this.capital = capital;
        this.population = population;
        this.id = id;
    }


    /**
     * This function returns the name of the city.
     * 
     * @return The name of the city.
     */
    public String getName() {return this.name;}

    /**
     * > This function returns the latitude of the current location
     * 
     * @return The latitude of the location.
     */
    public double getLatitude() {return  this.latitude;}

    /**
     * > This function returns the longitude of the current location
     * 
     * @return The longitude of the location.
     */
    public double getLongitude(){return this.longitude;}

    /**
     * Returns the country
     * 
     * @return The country.
     */
    public String getCountry() {return country;}

    /**
     * The function takes two cities as input and returns the distance between them in Kilometers
     *
     * @param city2 The city that we are trying to find the distance to.
     * @return The distance between two cities in Kilometers.
     */
    public double getDistanceBetweenCities(City city2) {
        final int R = 6371; // Radius of the earth
        double latDistance = Math.toRadians(city2.getLatitude() - this.getLatitude());
        double lonDistance = Math.toRadians(city2.getLongitude() - this.getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(this.getLatitude())) *
                Math.cos(Math.toRadians(city2.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // convert to KM
        distance = Math.pow(distance, 2);
        return Math.sqrt(distance);
    }


}

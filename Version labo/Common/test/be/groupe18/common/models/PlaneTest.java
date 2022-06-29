package be.groupe18.common.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    Plane plane = new Plane();

    @Test
    void getPrice() {
        City city1 = new City("Tokyo","Tokyo",-6.2146,106.8451,"Japan","JP","JPN","Tōkyō","primary","37977000","1392685764");
        City city2 = new City("Jakarta","Jakarta",35.6897,139.6922,"Indonesia","ID","IDN","Jakarta","primary","34540000","1360771077");
        plane.setPriceKm(2);
        plane.setCity(city1);
        plane.setDistance(city2);
        Assertions.assertEquals(plane.getPrice(),11574,3);
    }

    @Test
    void getHours() {
        City city1 = new City("Tokyo","Tokyo",-6.2146,106.8451,"Japan","JP","JPN","Tōkyō","primary","37977000","1392685764");
        City city2 = new City("Jakarta","Jakarta",35.6897,139.6922,"Indonesia","ID","IDN","Jakarta","primary","34540000","1360771077");
        plane.setCity(city1);
        plane.setDistance(city2);
        plane.setWaitingTime(5);
        Assertions.assertEquals(plane.getHours(),8.35,1);
    }

    @Test
    void getDistance() {
        City city1 = new City("Tokyo","Tokyo",-6.2146,106.8451,"Japan","JP","JPN","Tōkyō","primary","37977000","1392685764");
        City city2 = new City("Jakarta","Jakarta",35.6897,139.6922,"Indonesia","ID","IDN","Jakarta","primary","34540000","1360771077");
        plane.setPriceKm(2);
        plane.setCity(city1);
        plane.setDistance(city2);
        Assertions.assertEquals(plane.getDistance(),5787,3);
    }

    @Test
    void getWaitingTime() {
        plane.setWaitingTime(5);
        Assertions.assertEquals(plane.getWaitingTime(),5);
    }

    @Test
    void getFlightSpeed() {
        plane.setFlightSpeed(5);
        Assertions.assertEquals(plane.getFlightSpeed(),5);
    }

    @Test
    void getPriceKM() {
        plane.setPriceKm(5);
        Assertions.assertEquals(plane.getPriceKM(),5);
    }

    @Test
    void getCity() {
        City city1 = new City("Tokyo","Tokyo",-6.2146,106.8451,"Japan","JP","JPN","Tōkyō","primary","37977000","1392685764");
        plane.setCity(city1);
        Assertions.assertEquals(plane.getCity(),city1);
    }

    @Test
    void getCityName() {
        City city1 = new City("Tokyo","Tokyo",-6.2146,106.8451,"Japan","JP","JPN","Tōkyō","primary","37977000","1392685764");
        plane.setCity(city1);
        Assertions.assertEquals(plane.getCityName(),"Tokyo");
    }

    @Test
    void getCountry() {
        City city1 = new City("Tokyo","Tokyo",-6.2146,106.8451,"Japan","JP","JPN","Tōkyō","primary","37977000","1392685764");
        plane.setCity(city1);
        Assertions.assertEquals(plane.getCountry(),"Japan");
    }

}
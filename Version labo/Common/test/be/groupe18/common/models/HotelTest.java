package be.groupe18.common.models;

import be.groupe18.common.models.Hotel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HotelTest {

    Hotel hotel = new Hotel();

    @Test
    void getPrice() {
        hotel.setPriceByNight(5);
        hotel.setNbrNight(5);
        Assertions.assertEquals(hotel.getPrice(),25);
    }

    @Test
    void getHour() {
        hotel.setNbrNight(5);
        Assertions.assertEquals(hotel.getHour(),5*24);
    }

    @Test
    void setPriceByNight() {
        hotel.setPriceByNight(5);
        Assertions.assertEquals(hotel.getPriceNight(),5);
    }

    @Test
    void setNbrNight() {
        hotel.setNbrNight(5);
        Assertions.assertEquals(hotel.getNbrNight(),5);
    }
}

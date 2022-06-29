package be.groupe18.common.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TripListTest {
    TripList tripList = new TripList();
    StepList stepList1 = new StepList();
    StepList stepList2 = new StepList();
    ArrayList<StepList> stepListArrayList = new ArrayList<>();
    ArrayList<StepList> stepListArrayList2 = new ArrayList<>();

    @Test
    void addToList() {
        tripList.addToList(stepList1);
        tripList.addToList(stepList1);
        Assertions.assertEquals(tripList.getResumeTrips().size(),2);
    }

    @Test
    void removeToList() {
        tripList.addToList(stepList1);
        tripList.addToList(stepList2);
        tripList.removeToList(stepList1);
        Assertions.assertEquals(tripList.getResumeTrips().size(),1);
    }

    @Test
    void updateTrip() {
        stepListArrayList2.add(stepList1);
        tripList.addToList(stepList1);
        tripList.updateTrip(stepList2,0);
        Assertions.assertEquals(tripList.getResumeTrips().get(0),stepList2);
    }
}
package be.groupe18.common.DoubleDispatch;

import java.io.IOException;

public interface Function {
    void newTrip(NewTrip trip);
    void updatetrip(UpdateTrip trip);
    void removeTrip(RemoveTrip trip);
    void lockTrip(LockingTrip trip) throws IOException;
    void numberClient(NumberClient numberClient);
}

package be.groupe18.common.DoubleDispatch;

import be.groupe18.common.models.StepList;

import java.io.IOException;

public class LockingTrip extends AbstractFunction{
    int index;

    StepList stepList;

    public LockingTrip(int index) {this.index = index;}

    public int getIndex() {
        return index;
    }

    @Override
    public void accept(Function f) throws IOException {f.lockTrip(this);}
}

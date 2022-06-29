package be.groupe18.common.DoubleDispatch;

import be.groupe18.common.models.StepList;

import java.io.Serializable;

public class Trip implements Serializable {
    int index;

    StepList stepList;

    public Trip(int index, StepList stepList) {
        this.index = index;
        this.stepList = stepList;
    }

    public int getIndex() {
        return index;
    }

    public StepList getStepList() {
        return stepList;
    }
}

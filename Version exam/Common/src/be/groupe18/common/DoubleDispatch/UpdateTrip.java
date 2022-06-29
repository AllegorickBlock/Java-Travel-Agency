package be.groupe18.common.DoubleDispatch;

import be.groupe18.common.models.StepList;

public class UpdateTrip extends AbstractFunction{
    int index;

    StepList stepList;

    public UpdateTrip(int index, StepList stepList) {
        this.index = index;
        this.stepList = stepList;
    }

    public int getIndex() {
        return index;
    }

    public StepList getStepList() {
        return stepList;
    }
    @Override
    public void accept(Function f) {f.updatetrip(this);}
}

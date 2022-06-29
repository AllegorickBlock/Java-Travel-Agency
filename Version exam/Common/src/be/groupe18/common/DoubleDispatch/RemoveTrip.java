package be.groupe18.common.DoubleDispatch;

import be.groupe18.common.models.StepList;

public class RemoveTrip extends AbstractFunction{
    int index;

    StepList stepList;

    public RemoveTrip(int index, StepList stepList) {
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
    public void accept(Function f) {f.removeTrip(this);}
}

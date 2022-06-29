package be.groupe18.common.DoubleDispatch;

import be.groupe18.common.models.StepList;

public class NewTrip extends AbstractFunction {
    @Override
    public void accept(Function f) {f.newTrip(this);}
}

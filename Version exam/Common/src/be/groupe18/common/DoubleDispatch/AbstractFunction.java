package be.groupe18.common.DoubleDispatch;

import java.io.IOException;
import java.io.Serializable;

public abstract class AbstractFunction implements Serializable {
    public abstract void accept(Function f) throws IOException;
}

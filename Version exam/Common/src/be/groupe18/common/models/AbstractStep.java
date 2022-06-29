package be.groupe18.common.models;

import java.io.Serializable;

public abstract class AbstractStep implements Serializable {
    public abstract double getPrice();

    public abstract City getCity();

    public abstract double getDistance();

    public abstract double getHour();
}

package be.groupe18.common.DoubleDispatch;

import java.io.IOException;

public class NumberClient extends AbstractFunction{
    int numberClient;

    public NumberClient(int index) {this.numberClient = index;}

    public int getNumberClient() {
        return this.numberClient;
    }

    @Override
    public void accept(Function f) throws IOException {f.numberClient(this);}
}
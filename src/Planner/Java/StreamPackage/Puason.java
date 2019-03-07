package Planner.Java.StreamPackage;

public class Puason extends Stream {
    @Override
    public int next() {
        double
        t= (-1/getLamda())*Math.log(Math.random());
        if(t>1)
            return (int)(t);
        else return 1;

    }

    public Puason(int lamda) {
        super(lamda);
    }
}

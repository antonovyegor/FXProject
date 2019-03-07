package Planner.Java.StreamPackage;
import java.util.Random;

public class Erlang extends Stream {
    private int k;


    @Override
    public int next() {
        double t=0;
        Random random = new Random();
        double del = (double)-1/(getLamda()/(double)k);
        for (int i=0;i<k;i++){
            t+= del*Math.log(random.nextDouble());
        }
        if(t>1)
        return (int)(t);
        else return 1;
    }

    public Erlang(double lamda,int k) {
        super(lamda);
        this.k=k;
    }
}

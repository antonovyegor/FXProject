package Generator.Objects;
import Generator.Controllers.ChartController;

import java.util.*;
import java.lang.Math;
public class Gen {
    private static int ABSOLUTE_COUNT=0;
    private double [] X;
    private double M;
    private SortedSet<W> setW1  ;
    private SortedSet<W> setW2  ;
    private double D;
    private int m_garm;
    private int Wmax;
    private int N;
    private double deltaT;
    private double [] R;
    private double [] F;
    private double [] F1_Real;
    private double [] F1_Im;
    private double [] F2_Real;
    private double [] F2_Im;
    private int time;
    private Random rand1, rand2;

    public void run(){
        this.X = new double [N];
        System.out.println("------------------");
        printGen();
        runX();
        runM();
        runD();
        System.out.println("------------------");
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public void  runM() {
        for (int i=0;i<getN()-1;i++)
            M+=X[i];
        M/=getN();
        System.out.println("Мат ожидание = "+M);
    }

    public void runD() {
        for (int i=0;i<getN()-1;i++)
            D+=Math.pow((X[i]-M),2);
        D/=getN()-1;
        System.out.println("Дисперсия  = "+D);
    }

    public double getX(int i) {
        return X[i];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gen gen = (Gen) o;
        return m_garm == gen.m_garm &&
                Wmax == gen.Wmax &&
                getN() == gen.getN() &&
                Double.compare(gen.deltaT, deltaT) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_garm, Wmax, getN(), deltaT);
    }

    public Gen (int m, int Wmax, int N, double delta){
        this.m_garm=m;
        this.Wmax = Wmax;
        this.N=N;
        this.deltaT = delta;
        this.X = new double [N];

    }

    public void  printGen() {
        System.out.format( "m=%d; \nWmax = %d;\nN= %d;\nDelta T=%f\n",m_garm,Wmax,N,deltaT);
    }

    public void runX(){
        rand1= new Random( );
        rand2= new Random( );
        long timestart = System.nanoTime();
        for (int p =0 ; p<m_garm; p++){
            double A = rand1.nextDouble();
            double fi = rand2.nextDouble();
            for (int i = 0 ; i<N; i++){
                X[i]+=A* Math.sin(    ((Wmax*(p+1)/m_garm) * deltaT * i)  + fi    );
            }
        }
        time =(int) (System.nanoTime() - timestart)/1000;
        System.out.println("Time (micro):" + this.time+ " N="+N);
        System.out.println("------------------");
    }

    public double getM() {
        return M;
    }

    public double getD() {
        return D;
    }

    public  void runR(Gen gen2, int _tau) {
        R = new double[_tau];
        if (getN() != gen2.getN()) {
            System.out.println("Error! Несовпадение размерностей. Массивы изменены");
            if(getN()>gen2.getN()){
                gen2.setN(getN());
                gen2.run();
            }
            else{
                setN(gen2.getN());
                run();
            }
        }

        for (int tau = 0; tau < _tau; tau++) {
            double bufR = 0;
            for (int t = 0; t < N - tau; t++) {
                bufR += ((X[t] - M) * (gen2.getX(t + tau) - gen2.getM())) ;
            }
            bufR/= (N  -  tau - 1);
            R[tau] = bufR;
        }
    }

    public double getR(int i) {
        return R[i];
    }

    public void runW(){
        this.setW1 = new TreeSet<>();
        for(int p=0;p<getN();p++)
            for (int k=0;k<getN();k++){
                setW1.add(new W(p*k,Math.cos(2*Math.PI*p*k/(getN())),Math.sin(2*Math.PI*p*k/(getN()))));
            }
    }

    public W getW1(int id){
        Iterator iterator = setW1.iterator();
        while (iterator.hasNext()){
            Object o = iterator.next();
            if(o.equals(new W(id))){
                return (W)o;
            }
        }
        return null;
    }

    public double getF(int i) {
        return F[i];
    }

    public void runF(){
        this.F = new double [N];
        runW();
        for (int p=0;p<getN();p++){
            double real=0;
            double im=0;
            for(int k=0;k<getN();k++){
                W w=getW1(p*k);
                real+=getX(k)*w.getReal();
                im+=getX(k)*w.getImag();
            }
            F[p] = Math.sqrt(real*real + im*im);
            System.out.println(p+"  : "+F[p]);
        }
    }

    public void runFFT( int i) {

        if (i==1) {
            F1_Real = new double[getN()/2];
            F1_Im = new double[getN()/2];
            F = new double[getN()];
        }
        if (i==0) {
            F2_Real = new double[getN()/2];
            F2_Im = new double[getN()/2];
        }


        for (int p = 0; p < getN() / 2; p++) {

            for (int k = 0; k < getN() / 2; k++) {
                W w = getW1(p * (2*k+i));
                if (i==0) {
                    F2_Real[p] += getX(2 * k + i) * w.getReal();
                    F2_Im[p] += getX(2 * k + i) * w.getImag();
                }
                if (i==1) {
                    F1_Real[p] += getX(2 * k + i) * w.getReal();
                    F1_Im[p] += getX(2 * k + i) * w.getImag();
                }
            }

        }
        ChartController.START.countDown();
        try {
            ChartController.START.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        runF1_F2(i);

    }

    public void initF() {
        F1_Real = new double[getN()/2];
        F1_Im = new double[getN()/2];
        F2_Real = new double[getN()/2];
        F2_Im = new double[getN()/2];

    }

    public void setF(double[] f) {
        F = f;
    }

    public void runF1_F2(int i) {

        for (int p = 0; p < getN() / 2; p++) {

            if (i==1) {
                W w = getW1(p);
                double real,im;

                real = F2_Real[p] + w.getReal() * F1_Real[p] + w.getImag() * F1_Im[p];
                im = F2_Im[p] + w.getReal() * F1_Im[p] + w.getImag() * F1_Real[p];
                F[p] = Math.sqrt(real * real + im * im);
            }
            if (i==0) {
                //W w = getW1(p+getN() / 2);
                W w = getW1(p);
                double real,im;
                real = F2_Real[p] + w.getReal() * F1_Real[p] + w.getImag() * F1_Im[p];
                im = F2_Im[p] + w.getReal() * F1_Im[p] + w.getImag() * F1_Real[p];
//                real =  w.getReal() * F2_Real[p] +  w.getImag() * F2_Im[p] - F1_Real[p];
//                im =  w.getReal() * F2_Im[p] + w.getImag() * F2_Real[p]   - F2_Im[p];
                F[p + getN() / 2] = Math.sqrt(real * real + im * im);
            }
            ChartController.FIHISH.countDown();
        }

    }

    public double nextDouble(){
        rand1= new Random( );
        rand2= new Random( );
        double X =0;
        for (int p =0 ; p<m_garm; p++){
            double A = rand1.nextDouble();
            double fi = rand2.nextDouble();
                X+=A* Math.sin(    ((Wmax*(p+1)/m_garm) * deltaT * ABSOLUTE_COUNT)  + fi    );

        }
        ABSOLUTE_COUNT++;
        return X;
    }
}

package Planner.Java.StreamPackage;

import Planner.Controllers.ModulatorController;


public abstract class Stream {
    private double lamda;
    private int time;

    public  void setNextTime(){
        setNextTime(ModulatorController.TIME + next());
    }

    public void setNextTime(int time) {
        this.time=time;
    }

    public int getTime() {
        return time;
    }

    public abstract int next();

    public double  getLamda() {
        return lamda;
    }

    public Stream (double lamda){
        this.lamda = lamda;
        this.time=0;
    }


}

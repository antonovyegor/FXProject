package Planner.Java.Objects;

import Planner.Controllers.ModulatorController;
import Planner.Java.Modulator;
import Planner.Java.StreamPackage.Stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class QueueEDP extends LinkedList<Task> {
    private int id;
    //private int time_to_next;
    private double average_size;
    private ArrayList<Stream> streams;

    public void addStream(Stream stream){
        streams.add(stream);
    }
    public ArrayList<Stream> getStream(){
        return streams;
    }

    public QueueEDP(int id) {
        this.id =id;
        average_size=0;
        this.streams = new ArrayList<>();
    }

    public int getId() {
        return id;
    }



    @Override
    public boolean add(Task o) {
        boolean result = super.add(o);
        sort();
        //setTime_to_next();

        return result;
    }

    public void sort(){
        sort(Comparator.comparing(Task::getDead_time));
    }

    public double getAverage_size() {
        return average_size/(double) ModulatorController.END_MODULATION_TIME;
    }

    public void setAverage_size(){
        average_size+=  (double) size();
    }
}

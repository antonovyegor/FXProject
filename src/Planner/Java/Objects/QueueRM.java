package Planner.Java.Objects;

import Planner.Controllers.ModulatorController;
import Planner.Java.ModulatorRM;
import Planner.Java.StreamPackage.Stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class QueueRM extends LinkedList<PriorityTask> {

    private int id;
    private double average_size;
    private ArrayList<Stream> streams;


    public void addStream(Stream stream){
        streams.add(stream);
    }
    public ArrayList<Stream> getStream(){
        return  streams;
    }

    public QueueRM(int id) {
        this.id =id;
        average_size=0;
        this.streams = new ArrayList<>();


    }

    public int getId() {
        return id;
    }



    @Override
    public boolean add(PriorityTask o) {
        boolean result = super.add(o);
        sort();
        return result;
    }

    public void sort(){
        sort(Comparator.comparing(PriorityTask::getPriority).reversed());
    }

    public double getAverage_size() {
        return average_size/(double) ModulatorController.END_MODULATION_TIME;
    }

    public void setAverage_size(){
        average_size+=  (double) size();
    }
}

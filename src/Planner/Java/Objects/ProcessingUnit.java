package Planner.Java.Objects;

import Planner.Controllers.ModulatorController;
import java.util.ArrayList;
import java.util.ListIterator;

public class ProcessingUnit {
    private int id;
    private int end_time;
    private ArrayList<Task> list;
    private double loadFactor;


    public ProcessingUnit(int id) {
        this.id = id;
        this.list = new ArrayList<>();
        this.end_time = 0;
        loadFactor=-1;
    }

    public void PutOnExec (Task task){
        if (task != null){
            list.add(task);
            task.setStart_time(ModulatorController.TIME);
            end_time=task.getEndtime();
        }
    }

    public ArrayList<Task> getList() {
        return list;
    }

    public int getId() {
        return id;
    }

    public int getEnd_time() {
        return end_time;
    }

    public double getLoadFactor() {
        if (loadFactor==-1){
            ListIterator<Task> iterator = list.listIterator();
            double time=0;
            while(iterator.hasNext()){
                time+= (double)iterator.next().getExec_time()*100/(double) ModulatorController.END_MODULATION_TIME;
            }
            //loadFactor = (double)time/Modulator.END_MODULATION_TIME;
            if(time>100) time=100;
            loadFactor =time;
            return loadFactor;
        }else
            return loadFactor;
    }

}

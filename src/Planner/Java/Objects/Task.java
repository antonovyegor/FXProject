package Planner.Java.Objects;

public class Task {
    private int exec_time;
    private int start_time;
    private int incoming_time;
    private int wait_time;
    private int endtime;
    private int dead_time;
    private static final int koef = 1 ;

    public Task(int incoming_time, int exec_time) {
        this.incoming_time = incoming_time;
        this.exec_time  = exec_time;
        dead_time = incoming_time + koef * exec_time;
        start_time = -1;
        endtime = -1;
        wait_time=-1;
    }

    public int getExec_time() {
        return exec_time;
    }

    public int getEndtime() {
        return endtime;
    }

    public int getDead_time() {
        return dead_time;
    }

    public int getWait_time() {
        return wait_time;
    }

    public void setStart_time(int start_time) {
        wait_time = start_time-incoming_time;
        this.start_time = start_time;
        endtime = start_time + exec_time;
    }
}

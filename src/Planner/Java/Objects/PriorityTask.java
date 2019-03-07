package Planner.Java.Objects;

public class PriorityTask extends Task {
    private double priority;
    public PriorityTask(int incoming_time, int exec_time, double priority) {
        super(incoming_time, exec_time);
        this.priority = priority;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }
}

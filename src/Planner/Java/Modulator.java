package Planner.Java;
// update
import Planner.Controllers.ModulatorController;
import Planner.Java.Objects.ProcessingUnit;
import Planner.Java.Objects.QueueEDP;
import Planner.Java.Objects.Task;
import Planner.Java.StreamPackage.Erlang;
import Planner.Java.StreamPackage.Stream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class Modulator extends Application {
    public static Stage MainStage;
    public static Parent root;

    /*public static XYChart.Series average_wait_time_series ;
    public static XYChart.Series loadfactor_series ;
    public static XYChart.Series dead_tasks_series ;*/

    public static void main(String args[]){

        ModulatorController.average_wait_time_series = new XYChart.Series();
        ModulatorController.loadfactor_series = new XYChart.Series();
        ModulatorController.dead_tasks_series = new XYChart.Series();

        for(double lamda = 10;lamda>0; lamda-=0.1)
            {
            ModulatorController.TIME = 0;
            ArrayList<Task> dead_list = new ArrayList<>();
            ArrayList<Task> exec_list = new ArrayList<>();

            ArrayList<QueueEDP> QueueList = new ArrayList<>();
            ArrayList<ProcessingUnit> procs = new ArrayList<>();
            ListIterator<QueueEDP> iteratorQueue;
            ListIterator<ProcessingUnit> iteratorProc;
            Stream stream1 = new Erlang(lamda,3);
            QueueEDP q = new QueueEDP(1);
            q.addStream(stream1);
            QueueList.add(q);
            procs.add(new ProcessingUnit(1));

            while (ModulatorController.TIME < ModulatorController.END_MODULATION_TIME) {

                // Появление новой задачи
                // Расчет время появления следующей
                iteratorQueue = QueueList.listIterator(0);
                while (iteratorQueue.hasNext()) {
                    QueueEDP currentQueue = iteratorQueue.next();
                    ListIterator<Stream> streamListIterator = currentQueue.getStream().listIterator(0);
                    while (streamListIterator.hasNext()){
                        Stream currentStream = streamListIterator.next();
                        if (ModulatorController.TIME == currentStream.getTime()) {
                            currentStream.setNextTime();
                            currentQueue.add(new Task(ModulatorController.TIME, (int) (Math.random() * 10) + 1));
                        }
                    }
                }


                //  Удаление задач которые достигли дедлайна
                //  Добавление их в список пропущеных задач
                iteratorQueue = QueueList.listIterator(0);
                while (iteratorQueue.hasNext()) {
                    QueueEDP currentQueue = iteratorQueue.next();
                    ListIterator<Task> iteratorTask = currentQueue.listIterator(0);
                    while (iteratorTask.hasNext()) {
                        Task task = iteratorTask.next();
                        if (task.getDead_time() <= ModulatorController.TIME) {
                            iteratorTask.remove();
                            dead_list.add(task);
                            currentQueue.remove(task) ;

                        }
                    }
                }
                //  Проверка массива с задачами
                //  Проверка свободных процессоров
                //  Ставим задачу на выполениея определенного процессора
                iteratorQueue = QueueList.listIterator(0);
                while (iteratorQueue.hasNext()) {
                    QueueEDP currentQueue = iteratorQueue.next();
                    if (!currentQueue.isEmpty())
                        for (int i = 0; i < procs.size(); i++)
                            if (ModulatorController.TIME >= procs.get(i).getEnd_time()) {
                                // Processing Unit IS FREE

                                Task exec_task = currentQueue.poll();
                                procs.get(i).PutOnExec(exec_task);
                                exec_list.add(exec_task);
                            }
                    currentQueue.setAverage_size();
                }

                ModulatorController.TIME++;

            }
            iteratorQueue = QueueList.listIterator(0);
            while (iteratorQueue.hasNext()) {
                QueueEDP currentQueue = iteratorQueue.next();
                System.out.println("Средняя длина очереди " + currentQueue.getId() + " = " + currentQueue.getAverage_size());
            }
            ListIterator<Task> iteratorExecTask = exec_list.listIterator(0);
            double average_wait_time = 0;
            while (iteratorExecTask.hasNext()) {
                Task currentTask = iteratorExecTask.next();
                average_wait_time += currentTask.getWait_time();
            }
            average_wait_time /= exec_list.size();
            ModulatorController.average_wait_time_series.getData().add(new XYChart.Data(String.format("%.2f",lamda), average_wait_time));
            System.out.println("Lamda = " + lamda);
            System.out.println("Среднее время ожидания = " + average_wait_time);
            System.out.println("Соотношение откинутых задач ко всему кол-ву пришедших =");
            double koef_dead= (double) dead_list.size() / (dead_list.size() + exec_list.size());
            System.out.println(koef_dead*100);
            ModulatorController.dead_tasks_series.getData().add(new XYChart.Data(String.format("%.2f",lamda), koef_dead*100));

            iteratorProc = procs.listIterator(0);
            double average_loadfactor = 0;
            while (iteratorProc.hasNext()) {
                average_loadfactor += iteratorProc.next().getLoadFactor();
            }
            average_loadfactor /= procs.size();
            ModulatorController.loadfactor_series.getData().add(new XYChart.Data(String.format("%.2f",lamda), average_loadfactor));
            System.out.println("Среднея загруженность = " + average_loadfactor);
            System.out.println("=======================================");

        }

        launch(args);



    }

    @Override
    public void start(Stage stage) throws Exception {
        MainStage=stage;
        root = FXMLLoader.load(getClass().getResource("../FXML/Modulator.fxml"));
        Scene scene = new Scene(root);
        MainStage.setScene(scene);
        MainStage.show();
    }
}

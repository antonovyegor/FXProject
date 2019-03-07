package Planner.Java;

import Planner.Controllers.ModulatorController;
import Planner.Java.Objects.ProcessingUnit;

import Planner.Java.Objects.PriorityTask;
import Planner.Java.Objects.QueueRM;
import Planner.Java.StreamPackage.Erlang;
import Planner.Java.StreamPackage.Stream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.ListIterator;

public class ModulatorRM extends Application {
    public static Stage MainStage;
    public static Parent root;

    public static void main(String args[]){

        ModulatorController.average_wait_time_series = new XYChart.Series();
        ModulatorController.loadfactor_series = new XYChart.Series();
        ModulatorController.dead_tasks_series = new XYChart.Series();

        for(double lamda = 1;lamda>=0.1; lamda-=0.1)
            {
                ModulatorController.TIME = 0;
            ArrayList<PriorityTask> dead_list = new ArrayList<>();
            ArrayList<PriorityTask> exec_list = new ArrayList<>();

            ArrayList<QueueRM> QueueList = new ArrayList<>();
            ArrayList<ProcessingUnit> procs = new ArrayList<>();
            ListIterator<QueueRM> iteratorQueue;
            ListIterator<ProcessingUnit> iteratorProc;
            Stream stream1 = new Erlang(lamda,3);
            Stream stream2 = new Erlang(lamda+0.1,3);
            Stream stream3 = new Erlang(lamda+0.2,3);
            Stream stream4 = new Erlang(lamda+0.3,3);
            QueueRM q = new QueueRM(1);
            q.addStream(stream1);
            q.addStream(stream2);
            q.addStream(stream3);
            q.addStream(stream4);
            QueueList.add(q);
            procs.add(new ProcessingUnit(1));

            while (ModulatorController.TIME < ModulatorController.END_MODULATION_TIME) {

                // Появление новой задачи
                // Расчет время появления следующей
                iteratorQueue = QueueList.listIterator(0);
                while (iteratorQueue.hasNext()) {
                    QueueRM currentQueue = iteratorQueue.next();
                    ListIterator<Stream> streamListIterator = currentQueue.getStream().listIterator(0);
                    while (streamListIterator.hasNext()){
                        Stream currentStream = streamListIterator.next();
                        if (ModulatorController.TIME == currentStream.getTime()) {
                            currentStream.setNextTime();
                            currentQueue.add(new PriorityTask(ModulatorController.TIME, (int) (Math.random() * 10) + 1 , currentStream.getLamda()));
                        }
                    }
                }


                //  Удаление задач которые достигли дедлайна
                //  Добавление их в список пропущеных задач
                iteratorQueue = QueueList.listIterator(0);
                while (iteratorQueue.hasNext()) {
                    QueueRM currentQueue = iteratorQueue.next();
                    ListIterator<PriorityTask> iteratorPriorityTask = currentQueue.listIterator(0);
                    while (iteratorPriorityTask.hasNext()) {
                        PriorityTask PriorityTask = iteratorPriorityTask.next();
                        if (PriorityTask.getDead_time() <= ModulatorController.TIME) {
                            iteratorPriorityTask.remove();
                            dead_list.add(PriorityTask);
                            currentQueue.remove(PriorityTask) ;

                        }
                    }
                }
                //  Проверка массива с задачами
                //  Проверка свободных процессоров
                //  Ставим задачу на выполениея определенного процессора
                iteratorQueue = QueueList.listIterator(0);
                while (iteratorQueue.hasNext()) {
                    QueueRM currentQueue = iteratorQueue.next();
                    if (!currentQueue.isEmpty())
                        for (int i = 0; i < procs.size(); i++)
                            if (ModulatorController.TIME >= procs.get(i).getEnd_time()) {
                                // Processing Unit IS FREE
                                PriorityTask exec_PriorityTask = currentQueue.poll();
                                procs.get(i).PutOnExec(exec_PriorityTask);
                                exec_list.add(exec_PriorityTask);
                            }
                    currentQueue.setAverage_size();
                }

                ModulatorController.TIME++;

            }
            iteratorQueue = QueueList.listIterator(0);
            while (iteratorQueue.hasNext()) {
                QueueRM currentQueue = iteratorQueue.next();
                System.out.println("Средняя длина очереди " + currentQueue.getId() + " = " + currentQueue.getAverage_size());
            }
            ListIterator<PriorityTask> iteratorExecPriorityTask = exec_list.listIterator(0);
            double average_wait_time = 0;
            while (iteratorExecPriorityTask.hasNext()) {
                PriorityTask currentPriorityTask = iteratorExecPriorityTask.next();
                average_wait_time += currentPriorityTask.getWait_time();
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

package Planner.Controllers;


import Planner.Java.Modulator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;

public class ChartController {



    @FXML
    private NumberAxis yAxis;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private LineChart<?, ?> chart;

    @FXML
    void initialize() {
        assert yAxis != null : "fx:id=\"yAxis\" was not injected: check your FXML file 'Chart.fxml'.";
        assert xAxis != null : "fx:id=\"xAxis\" was not injected: check your FXML file 'Chart.fxml'.";
        assert chart != null : "fx:id=\"chart\" was not injected: check your FXML file 'Chart.fxml'.";

    }

    public void draw(FXMLLoader loader , String id){

        chart.getData().clear();
        if(id.equals("ButLoadFactor")){
            chart.getData().add( ModulatorController.loadfactor_series);
            ModulatorController.loadfactor_series.setName("Load Factor");
            chart.setTitle("Загруженность процессора в зависимости от интенсивности");

        }
        if (id.equals("ButAverWaitTime")) {
            chart.getData().add( ModulatorController.average_wait_time_series);
            ModulatorController.average_wait_time_series.setName("Average waiting");
            chart.setTitle("Среднее время ожидания в зависимости от интенсивности");

        }
        if (id.equals("ButDeadKoef")) {
            chart.getData().add( ModulatorController.dead_tasks_series);
            ModulatorController.dead_tasks_series.setName("Dead");
            chart.setTitle("Процент откинутых задач");

        }

        Stage stage = new Stage();
        Scene scene = new Scene(loader.getRoot());
        stage.setScene(scene);
        stage.show();
    }
}

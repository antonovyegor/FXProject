package Planner.Controllers;

import java.io.IOException;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class ModulatorController {
    public static XYChart.Series average_wait_time_series ;
    public static XYChart.Series loadfactor_series ;
    public static XYChart.Series dead_tasks_series ;
    public static int TIME;
    public static final int END_MODULATION_TIME=100000;
    @FXML
    private Button ButWaitTime;
    @FXML
    private Button ButDeadKoef;
    @FXML
    private Label Label_Queue_Size;

    @FXML
    private Button ButAverWaitTime;

    @FXML
    private Label Label_Dead_Task;

    @FXML
    private Button ButLoadFactor;


    @FXML
    public void show(ActionEvent e) {
        Button button = null;
        if (e.getSource().getClass().equals(Button.class))
            button = (Button) e.getSource();


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../FXML/Chart.fxml"));
        try{
            loader.load();
        }
        catch (IOException exc){
            System.err.println(exc);
        }
        ChartController cc = loader.getController();
        cc.draw(loader,button.getId());



    }

    @FXML
    void initialize() {
       assert ButWaitTime != null : "fx:id=\"ButWaitTime\" was not injected: check your FXML file 'Modulator.fxml'.";
        assert Label_Queue_Size != null : "fx:id=\"Label_Queue_Size\" was not injected: check your FXML file 'Modulator.fxml'.";
        assert ButDeadKoef != null : "fx:id=\"ButDeadKoef\" was not injected: check your FXML file 'Modulator.fxml'.";
        assert ButAverWaitTime != null : "fx:id=\"ButAverWaitTime\" was not injected: check your FXML file 'Modulator.fxml'.";
        assert Label_Dead_Task != null : "fx:id=\"Label_Dead_Task\" was not injected: check your FXML file 'Modulator.fxml'.";
        assert ButLoadFactor != null : "fx:id=\"ButLoadFactor\" was not injected: check your FXML file 'Modulator.fxml'.";
    }
}





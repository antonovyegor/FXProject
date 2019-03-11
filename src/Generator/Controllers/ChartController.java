/**
 * Sample Skeleton for 'Chart.fxml' Controller Class
 */

package Generator.Controllers;

import Generator.Objects.FFT;
import Generator.Objects.Gen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.util.concurrent.CountDownLatch;

public class ChartController {
    private static Gen genX;
    private static Gen genY;


    public  static  CountDownLatch START,FIHISH ;
    //Условная длина гоночной трассы
    @FXML // fx:id="yAxis"
    private NumberAxis yAxis;// Value injected by FXMLLoader

    @FXML // fx:id="xAxis"
    private   CategoryAxis xAxis; // Value injected by FXMLLoader

    @FXML // fx:id="chart"
    private LineChart<?, ?> chart; // Value injected by FXMLLoader

    @FXML
    private Label Label;


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert yAxis != null : "fx:id=\"yAxis\" was not injected: check your FXML file 'Chart.fxml'.";
        assert xAxis != null : "fx:id=\"xAxis\" was not injected: check your FXML file 'Chart.fxml'.";
        assert chart != null : "fx:id=\"chart\" was not injected: check your FXML file 'Chart.fxml'.";
        assert Label != null : "fx:id=\"Label\" was not injected: check your FXML file 'Chart.fxml'.";

    }

    public void draw(FXMLLoader loader, String id, Gen genX,Gen genY){
        long start;

        if (!genY.equals(this.genY)) {
            this.genY=genY;
            genY.run();
        }
        if (!genX.equals(this.genX)) {
            this.genX=genX;
            genX.run();
        }

        XYChart.Series series = new XYChart.Series();
        chart.getData().clear();
        switch (id) {

            case "ButShowX":
                chart.setTitle("График X(t)");
                series.setName("X");
                for (int i=0;i<genX.getN();i++)
                    series.getData().add(new XYChart.Data(  Integer.toString(i), this.genX.getX(i)));
                break;
            case "ButShowY" :
                chart.setTitle("График Y(t)");
                series.setName("Y");
                for (int i=0;i<genY.getN();i++)
                    series.getData().add(new XYChart.Data(  Integer.toString(i), this.genY.getX(i)));
                break;
            case "ButRxx" :
                chart.setTitle("График Rxx(t)");
                series.setName("Rxx");
                int tau = genX.getN()/5;
                this.genX.runR(genX,tau);
                for (int i=0;i<tau;i++)
                    series.getData().add(new XYChart.Data(  Integer.toString(i), this.genX.getR(i)));
                break;
            case "ButRxy" :
                chart.setTitle("График Rxy(t)");
                series.setName("Rxy");
                tau = genX.getN()/5;
                this.genX.runR(genY,tau);
                for (int i=0;i<tau;i++)
                    series.getData().add(new XYChart.Data(  Integer.toString(i), this.genX.getR(i)));
                break;
           case "ButF" :
               start = System.currentTimeMillis();
               chart.setTitle("График F");
               series.setName("F");
               this.genX.runF();
               for (int i=0;i<genX.getN();i++)
                   series.getData().add(new XYChart.Data(  Integer.toString(i), this.genX.getF(i)));
               Label.setText("Time = "+(System.currentTimeMillis() - start));
               break;
           case "ButFFT" :
               start = System.currentTimeMillis();
               this.genX.runW();
               chart.setTitle("График FFT");
               series.setName("FFT");
               START = new CountDownLatch(2);
               FIHISH = new CountDownLatch(2);
               FFT T2 = new FFT(this.genX,0);
               T2.setName("2");
               FFT T1 = new FFT(this.genX,1);
               T1.setName("1");

               T1.start();
               T2.start();

               try {
                   ChartController.FIHISH.await();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }

               for (int i=0;i<genX.getN();i++) {
                   series.getData().add(new XYChart.Data(Integer.toString(i), this.genX.getF(i)));
                  // System.out.println(i + "  : " + this.genX.getF(i));
               }
               Label.setText("Time = "+(System.currentTimeMillis() - start));

               break;
        }
        chart.getData().add(series);
        Stage stage = new Stage();
        Scene scene = new Scene(loader.getRoot());
        stage.setScene(scene);
        stage.show();
    }
}

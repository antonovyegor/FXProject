/**
 * Sample Skeleton for 'Main.fxml' Controller Class
 */

package Generator.Controllers;
import Generator.Objects.Gen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import java.io.IOException;

public class MainController {

        private Gen genX,genY;


        @FXML
        private Button ButShowY;

        @FXML
        private Button ButShowX;

        @FXML
        private TextField N_x_field;

        @FXML
        private TextField N_y_field;

        @FXML
        private TextField m_x_field;

        @FXML
        private TextField m_y_field;

        @FXML
        private TextField delta_y_field;

        @FXML
        private Button ButGenX;

        @FXML
        private TextField w_y_field;

        @FXML
        private Button ButRxy;

        @FXML
        private TextField delta_x_field;

        @FXML
        private Button ButRxx;

        @FXML
        private Button ButF;
        @FXML
        private Button ButFFT;

        @FXML
        private TextField w_x_field;



        @FXML
        void initialize() {
            assert ButShowY != null : "fx:id=\"ButShowY\" was not injected: check your FXML file 'Main.fxml'.";
            assert ButShowX != null : "fx:id=\"ButShowX\" was not injected: check your FXML file 'Main.fxml'.";
            assert N_x_field != null : "fx:id=\"N_x_field\" was not injected: check your FXML file 'Main.fxml'.";
            assert N_y_field != null : "fx:id=\"N_y_field\" was not injected: check your FXML file 'Main.fxml'.";
            assert m_x_field != null : "fx:id=\"m_x_field\" was not injected: check your FXML file 'Main.fxml'.";
            assert m_y_field != null : "fx:id=\"m_y_field\" was not injected: check your FXML file 'Main.fxml'.";
            assert delta_y_field != null : "fx:id=\"delta_y_field\" was not injected: check your FXML file 'Main.fxml'.";
            assert ButGenX != null : "fx:id=\"ButGenX\" was not injected: check your FXML file 'Main.fxml'.";
            assert w_y_field != null : "fx:id=\"w_y_field\" was not injected: check your FXML file 'Main.fxml'.";
            assert ButRxy != null : "fx:id=\"ButRxy\" was not injected: check your FXML file 'Main.fxml'.";
            assert delta_x_field != null : "fx:id=\"delta_x_field\" was not injected: check your FXML file 'Main.fxml'.";
            assert ButRxx != null : "fx:id=\"ButRxx\" was not injected: check your FXML file 'Main.fxml'.";
            assert ButF != null : "fx:id=\"ButF\" was not injected: check your FXML file 'Main.fxml'.";
            assert ButFFT != null : "fx:id=\"ButFFT\" was not injected: check your FXML file 'Main.fxml'.";
            assert w_x_field != null : "fx:id=\"w_x_field\" was not injected: check your FXML file 'Main.fxml'.";

        }

       /* @FXML
        public void run(ActionEvent e){
            Button button = null;
            if (e.getSource().getClass().equals(Button.class)){
                button = (Button) e.getSource();
                if (button.getId().equals("ButGenX")){
                    genX = new Gen(Integer.valueOf(m_x_field.getText()),Integer.valueOf(w_x_field.getText()),Integer.valueOf(N_x_field.getText()),
                        Double.valueOf(delta_x_field.getText()));
                    genX.run();
                }
                if (button.getId().equals("ButGenY")){
                    genY = new Gen(Integer.valueOf(m_y_field.getText()),Integer.valueOf(w_y_field.getText()),Integer.valueOf(N_y_field.getText()),
                        Double.valueOf(delta_y_field.getText()));
                    genY.run();
                }
            }



        }*/

        @FXML
        public void show(ActionEvent e) {

        Button button = null;
        if (e.getSource().getClass().equals(Button.class))
             button = (Button) e.getSource();

        Gen genX = new Gen(Integer.valueOf(m_x_field.getText()),Integer.valueOf(w_x_field.getText()),Integer.valueOf(N_x_field.getText()),
                Double.valueOf(delta_x_field.getText()));
        Gen genY = new Gen(Integer.valueOf(m_y_field.getText()),Integer.valueOf(w_y_field.getText()),Integer.valueOf(N_y_field.getText()),
                Double.valueOf(delta_y_field.getText()));

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../FXML_View/Chart.fxml"));
        try{
            loader.load();
        }
        catch (IOException exc){
            System.err.println(exc);
        }
        ChartController cc = loader.getController();
        cc.draw(loader,button.getId(),genX,genY);


    }
}

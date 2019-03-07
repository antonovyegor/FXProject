package Generator;


import Generator.Objects.Gen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage MainStage;
    public static Parent root;

    public static Gen genX,genY;

    @Override
    public void start(Stage stage) throws Exception {
        this.MainStage=stage;

        root = FXMLLoader.load(getClass().getResource("FXML_View/Main.fxml"));
        Scene scene = new Scene(root,700,700);

        MainStage.setScene(scene);
        MainStage.show();
    }


    public static void main(String[] args)
    {
        launch(args);
    }



}
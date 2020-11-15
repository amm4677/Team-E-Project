/*package Appl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

public class GUITest extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {

        Stage stage = primaryStage;
        stage.setTitle("This is a title");

        BorderPane mainPane = new BorderPane();

        Label lable1 = new Label("Please, end our suffering");
        Label lable2 = new Label("Please, end our suffering");
        Label lable3 = new Label("Please, end our suffering");
        Label lable4 = new Label("Please, end our suffering");



        GridPane middle = new GridPane();
        middle.add(lable1, 0,0);
        middle.add(lable2,1,0);
        middle.add(lable3, 0,1);
        middle.add(lable4, 1, 1);

        mainPane.setCenter(middle);
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
*/
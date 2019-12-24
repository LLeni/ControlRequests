package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    //TODO: Красное выделение тех записей таблицы "Заказы", которые просрочены. Когда они станут выполнены, то тогда это выделение пропадает
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/style.css");
        primaryStage.setScene(scene);
        primaryStage.setWidth(1400);
        primaryStage.setHeight(600);
        primaryStage.setTitle("Введение заявок");
        primaryStage.show();
    }
}

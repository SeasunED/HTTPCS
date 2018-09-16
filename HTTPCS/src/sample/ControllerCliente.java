package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.image.Image;

/**
 * Método que va a desplegar la interfaz del usuario
 */
public class ControllerCliente extends Application {
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("cliente.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cliente");
        primaryStage.setResizable(false);
        //primaryStage.getIcons().add(new Image("/Users/seasun/Downloads/HTTPCS/src/sample/Anoneta.PNG"));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                try{
                    System.exit(0);
                }catch (Exception e){
                    System.out.println(e.getStackTrace());
                }
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
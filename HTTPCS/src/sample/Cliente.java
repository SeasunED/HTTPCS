package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Cliente implements Initializable {
    /**
     * Se crean variables cadena para ingresar la URL y la bandera para que siempre
     * se encuentre en ejecución
     */
    public static String cadena= "";
    static boolean bandera= true;

    /**
     * Se inicializan todos los objetos que se usan en la interfaz del usuario
     */
    @FXML
    private TextField URLTextField;

    @FXML
    private Button enviarButton;

    @FXML
    private ImageView encendido;

    /**
     * Evento para el botón de ''enviar'' o en su defecto que sólo envíen los datos con ''enter''
     * envia al método cliente para que mande la petición
     * @param event
     */
    @FXML
    void enviarInformacion(ActionEvent event) {
        cadena= URLTextField.getText();
        Cliente(cadena);
        URLTextField.setDisable(true);
        enviarButton.setDisable(true);
        encendido.setOpacity(1);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Se crea el método de cliente, pasa como parametro un String llamado cadena
     * @param cadena
     */
    public static void Cliente(String cadena){
        try{
            //Se inicializa el método de comunicación el cual pasa un host y un puerto
            Socket socket= new Socket("127.0.0.1",5000);
            System.out.println("Conectado.");
            //Se crean dos variables las cuales nos ayudarán con el paso de datos de entrada y de salida
            DataOutputStream buffer= new DataOutputStream(socket.getOutputStream());
            DataInputStream buffer2 = new DataInputStream(socket.getInputStream());
            //Mientras sea verdadera la bandera se procesara la petición
            while (bandera) {
                System.out.println("Ingrese el URL");
                /**
                 * Se le asigna un formato automatico a la URL
                 * ingresada por el usuario.
                 */
                if(cadena!= null || cadena!= ""){
                    //Se le da el formato a la página solicitada
                    cadena = darFormato(cadena);
                    //Se envia al servidor
                    buffer.writeUTF(cadena);
                    System.out.println("Enviando: "+cadena);

                    //Se crea el archivo html recibido por el servidor
                    File html = new File("html.html");
                    FileWriter fw = new FileWriter(html);
                    BufferedWriter bw = new BufferedWriter(fw);

                    bw.write(buffer2.readUTF());
                    bandera=false;

                    bw.close();
                    fw.close();
                    //Se despliega automaticamente el archivo html
                    Desktop.getDesktop().open(html);
                }
            }


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Método para dar formato a la URL solicitada
     * @param URL
     * @return
     */
    public static String darFormato(String URL){
        URL = "http://"+URL+":8080";
        return URL;
    }
}

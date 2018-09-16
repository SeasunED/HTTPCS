package sample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MainServidor {
    /**
     * Puerto
     */
    public static final int puerto = 5000;

    /**
     * Identificador para el hilo
     */
    public static int idHilo = 0;

    /**
     * Para controlar la ejecución de los hilos
     */
    public static ThreadPoolExecutor piscina;

    /**
     * Ejecuta el servidor
     */
    public void run() {
        //para crear hilos según sea necesario
        //Para volver a usar los hilos destruidos cuando estén disponibles
        ExecutorService ejecutor = Executors.newCachedThreadPool();
        String variable;
        piscina = (ThreadPoolExecutor) ejecutor;
        //String mensajes;

        try {

            //Ejecuta el hilo que imprime las estadísticas de creación de hilos
            ejecutor.submit(new ClientesHilo());

            //Socket de servidor para esperar peticiones de la red
            ServerSocket servidorSocket = new ServerSocket(puerto);

            System.out.println("Servidor iniciado");

            while (true) {
                try {
                    System.out.println();
                    //en espera de conexion, si existe la acepta
                    Socket clientSocket = servidorSocket.accept();
                    //crea un nuevo hilo para la petición
                    ejecutor.submit(new ProcesoCliente(clientSocket, ++idHilo));

                } catch (IOException ex) {//error
                    System.err.println(ex.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


    }

    /**
     * Hilo que imprime la cantidad de hilos activos y ejecutados en la aplicación
     * restamos 1 para no tomar en cuenta este mismo hilo
     */
    public static class ClientesHilo implements Runnable {

        @Override
        public void run() {
            if (piscina != null)
                while (true) {
                    try {
                        Thread.sleep(6000);//6 segundos
                    } catch (InterruptedException ex) {
                        System.err.println(ex.getMessage());
                    }

                    System.out.println("Hilo en ejecución: (" + (piscina.getActiveCount() - 1) + ")");
                }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainServidor server = new MainServidor();
        server.run();
    }

}

package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ProcesoCliente implements Runnable {
    /**
     * Socket para la comunicación cliente-servidor
     */
    private Socket clienteSocket;

    /**
     * identificador para el hilo
     */
    private int id;

    /**
     *
     * @param clienteSocket
     * @param id
     */
    public ProcesoCliente(Socket clienteSocket, int id){
        this.clienteSocket = clienteSocket;
        this.id = id;
    }

    @Override
    public void run(){
        try{
            //para imprimir datos de salida
            InputStream aux = clienteSocket.getInputStream();
            DataInputStream flujo = new DataInputStream( aux );
            String cadena=flujo.readUTF();
            System.out.println("Nueva petición aceptada [id: " + id + "]");

            //Se lee la petición del cliente, en este caso una URL
            String cadena2 = recibirPeticion(cadena);

            OutputStream aux2 = clienteSocket.getOutputStream();
            DataOutputStream flujo2 = new DataOutputStream( aux2 );
            flujo2.writeUTF(cadena2);
            System.out.println("Hilo [id: " + id + "] procesando...");

            //duerme el hilo unos segundos para emular procesos que pueden durar varios segundos o minutos,etc
            Ayudante.fn.sleep();

            //Cierra la conexión
            clienteSocket.close();

            //notifica muerte del hilo
            System.out.println("Hilo [id: "+ id + "] termina");

        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    private String  recibirPeticion(String URL){
        String http, direccionRecurso,respuesta="";
        int puerto;
        try{
            /**
             * Verificamos que cumpla el protocolo de transferencia hyper texto.
             * */
            http= URL.substring(0,7);
            if(!http.equals("http://")){
                //throw new Exception ("Conexión inválida");
                System.out.println("Conexión inválida");
            }
            /**
             * Extraemos los puertos.
             * */
            puerto= Integer.parseInt(URL.substring(URL.lastIndexOf(':')+1));
            /**
             * Extraemos la dirección del recurso.
             */

            System.out.println("hola");
            direccionRecurso= URL.substring(7,URL.lastIndexOf(':'));
            respuesta = procesarPeticion(direccionRecurso, puerto);
        }catch (Exception e){
            System.out.println("Lo escribiste mal, manco");
        }
        return respuesta;
    }

    /**
     * Se crea un método que procesara la información solicitada por el cliente
     * en este caso se creará una mini página web
     * @param direccionRecurso
     * @param puerto
     * @return
     */
    private String procesarPeticion(String direccionRecurso, int puerto){
        String respuesta;
        switch (direccionRecurso){
            case "www.facebook.com":
                respuesta = Documento("Facebook");
                break;
            case "www.google.com":
                respuesta = Documento("Google");
                break;
            case "www.youtube.com":
                respuesta = Documento("YouTube");
                break;
            default:
                respuesta = Documento("404 Page Not Found");

        }
        return respuesta;
    }

    /**
     * Se da el formato de HTML
     * @param pagina
     * @return
     */
    private String Documento(String pagina){

        String doc = "<!DOCTYPE html>\n";
        doc += "\t<head>\n";
        doc += "\t\t<title>"+pagina+"</title>\n";
        doc += "\t\t<meta charset=\"UTF-8\">\n";
        doc += "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n";
        doc += "\t</head>\n";
        doc += "\t<body>\n";
        doc += "\t\t<h1>"+pagina+"</h1>\n";
        doc += "\t</body>\n";
        doc += "\t</html>\n";
        return doc;
    }
}

package sample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    Thread thread = new Thread( new handlingConnections());

    public class handlingConnections implements Runnable {


        private ServerSocket serverSocket;
        Thread[] threads = null;

        public handlingConnections() {
            try {
                this.serverSocket = new ServerSocket(12345);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            System.out.println("Thread is active");
            while(true) {
                try {
                    System.out.println("Now accepting socket connections");
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Connection received");

                    //Thread thread = new Thread(new Thread(new ))
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}



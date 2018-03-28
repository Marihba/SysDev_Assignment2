package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    private ServerSocket serverSocket;
    Thread[] threads = new Thread[10];
    public Server() {
        try {
            this.serverSocket = new ServerSocket(12345);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        Server serv = new Server();
        try{
            Socket clientSocket = serv.serverSocket.accept();
            Thread listenerThread = new Thread(new ConnectionHandler(clientSocket));
            listenerThread.start();
        }catch(IOException e){e.printStackTrace();}
    }
}
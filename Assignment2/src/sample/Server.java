//Adam Bozzo 100523248 Abhiram Sinnarajah 100569671
package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    public static void main(String[] args) throws IOException{
        try{
            ServerSocket serverSocket = new ServerSocket(12345);
            while(true){
                Socket clientSocket = serverSocket.accept();
                Thread listenerThread = new Thread(new ConnectionHandler(clientSocket));
                listenerThread.start();
            }
        }catch(IOException e){
            System.err.println("Server Already Running");
        }
    }
}
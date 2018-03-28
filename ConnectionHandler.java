package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionHandler implements Runnable{
    Socket clientSocket;
    BufferedReader inputClient;
    File clientFile = new File("Client/");
    File serverFile = new File("Server/");

    public ConnectionHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
        sendDIR(clientSocket);
    }

    //sending server list directory to client
    public void sendDIR(Socket client){
        try {
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            out.writeObject(serverFile);
            client.shutdownOutput();
        } catch (IOException e) {
            System.err.println("cannot connect");
        }
    }
    public void run(){
        try {
            inputClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String temp;
            while((temp = inputClient.readLine()) != null){
                System.out.println(temp);
               if(temp.equals("uploadPress")){
                   copyFile(serverFile);
               }else{
                   copyFile(clientFile);
               }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyFile(File fileDIR) {
        try {
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            String fileName = input.readUTF();
            OutputStream output = new FileOutputStream((fileDIR + "/" + fileName));
            int size = (int)input.readLong();
            byte[] byteArray = new byte[size];
            int bytes;
            while (size > 0 && (bytes = input.read(byteArray, 0, size)) > - 1) {
                output.write(byteArray, 0, bytes);
                size = size - bytes;
            }
            output.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
}
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
    private InputStream io;
    public ConnectionHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
        sendDIR(clientSocket);
    }

    //sending server list directory to client
    public void sendDIR(Socket client){
        try {
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            out.writeObject(serverFile);
        } catch (IOException e) {
            System.err.println("cannot connect");
        }
    }

    public long getId() {
        return Thread.currentThread().getId();
    }

    public void run(){
        try{
            io = clientSocket.getInputStream();
            try {
                inputClient = new BufferedReader(new InputStreamReader(io));
                String temp;
                while((temp = inputClient.readLine()) != null){
                    System.out.println(temp);
                    if(temp.equals("uploadPress")){
                        copyFile(serverFile);
                    }else{
                        copyFile(clientFile);
                    }
                }
            }catch (IOException e) {
                System.err.println("Socket Disconnect");
            }
        } catch (IOException e) {
            System.err.println("Socket Disconnect");
        } finally{
            System.out.println("Client " + getId() + " disconnected.");
        }
    }

    //method to copy file to file directory
    public void copyFile(File fileDIR) {
        try {
            //gets client input with all data bytes and writes information to file
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            OutputStream output = new FileOutputStream((fileDIR + "/" + input.readUTF()));
            int size = (int)input.readLong();
            byte[] byteArray = new byte[size];
            int bytes;
            while (size > 0 && (bytes = input.read(byteArray, 0, size)) > - 1) {
                output.write(byteArray, 0, bytes);
                size = size - bytes;
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}

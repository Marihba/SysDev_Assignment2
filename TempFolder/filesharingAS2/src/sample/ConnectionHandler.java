package sample;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler implements Runnable{
    Socket clientSocket;
    BufferedReader inputClient;
    File clientFile = new File("Client/");
    File serverFile = new File("Server/");

    public ConnectionHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    public void run(){
        try {
            inputClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String temp;
            while((temp = inputClient.readLine()) != null){
                System.out.println(temp);
                if(temp.equals("uploadPress")){
                    transferTo(serverFile);
                }else{
                    transferTo(clientFile);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void transferTo(File FILE_DIR) {
        try {
            int bytesRead;
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            String fileName = dis.readUTF();
            OutputStream os = new FileOutputStream((FILE_DIR + "/" + fileName));

            long size = dis.readLong();
            byte[] byteArray = new byte[7000000];
            while (size > 0 && (bytesRead = dis.read(byteArray, 0, (int) Math.min(byteArray.length, size))) > -1) {
                os.write(byteArray, 0, bytesRead);
                size -= bytesRead;
            }
            os.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
}
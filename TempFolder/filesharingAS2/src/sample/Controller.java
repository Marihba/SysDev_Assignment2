package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML ListView<File> serverFileL = new ListView();
    @FXML ListView<File> clientFileL = new ListView();
    File clientFile = new File("Client/");
    File serverFile = new File("Server/");
    ObservableList clientList = FXCollections.observableArrayList(clientFile.list());
    ObservableList serverList = FXCollections.observableArrayList(serverFile.list());
    static Socket clientSocket;
    public void initialize(){
        serverFileL.setItems(serverList);
        clientFileL.setItems(clientList);
        try {
            clientSocket = new Socket("localhost",12345);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void downloadPress(){
        if(!clientFileL.getItems().contains(clientFileL.getSelectionModel().getSelectedItem()) && serverFileL.getSelectionModel().getSelectedItem() != null) {
            try {
                PrintStream type = new PrintStream(clientSocket.getOutputStream());
                type.println("downloadPress");
                transferFile(serverFile + "/" + serverFileL.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                e.printStackTrace();
            }
            clientList.add(serverFileL.getSelectionModel().getSelectedItem());
        }
    }

    public void uploadPress() {
        //PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        if(!serverFileL.getItems().contains(clientFileL.getSelectionModel().getSelectedItem()) && clientFileL.getSelectionModel().getSelectedItem() != null) {
            try {
                PrintStream type = new PrintStream(clientSocket.getOutputStream());
                type.println("uploadPress");
                transferFile(clientFile + "/" + clientFileL.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                e.printStackTrace();
            }
            serverList.add(clientFileL.getSelectionModel().getSelectedItem());
        }
    }

    public static void transferFile(String fileName) {
        try {
            File myFile = new File(fileName);
            byte[] byteArray = new byte[(int) myFile.length()];
            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(byteArray, 0, byteArray.length);
            OutputStream os = clientSocket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(myFile.getName());
            dos.writeLong(byteArray.length);
            dos.write(byteArray, 0, byteArray.length);
            dos.flush();
            System.out.println("Transferred " + myFile.getName());
        } catch (Exception e) { e.printStackTrace(); }
    }
}
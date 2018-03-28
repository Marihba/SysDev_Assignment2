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
    @FXML
    ListView<File> serverFileL = new ListView();
    @FXML
    ListView<File> clientFileL = new ListView();
    File clientFile = new File("Client/");
    File serverFile = new File("Server/");
    ObservableList clientList = FXCollections.observableArrayList(clientFile.list());
    ObservableList serverList;
    static Socket clientSocket;

    public void initialize() {
            try {
                //setting up the directory for the server file list
                clientSocket = new Socket("localhost", 12345);
                ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
                serverFile = (File) input.readObject();
                serverList = FXCollections.observableArrayList(serverFile.list());
                clientSocket.shutdownInput();
            } catch (IOException e) {
                System.err.println("Not connected to server");
            } catch (ClassNotFoundException e) {
                System.err.println("cannot read object");
            }

        //setting values of list view items
        serverFileL.setItems(serverList);
        clientFileL.setItems(clientList);
    }
    public void downloadPress(){
        if(!clientFileL.getItems().contains(serverFileL.getSelectionModel().getSelectedItem()) && serverFileL.getSelectionModel().getSelectedItem() != null) {
            try {
                PrintStream type = new PrintStream(clientSocket.getOutputStream());
                type.println("downloadPress");
                type.flush();
                writeFile(serverFile + "/" + serverFileL.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                e.printStackTrace();
            }
            clientList.add(serverFileL.getSelectionModel().getSelectedItem());
        }
    }
    public void uploadPress() {
        if(!serverFileL.getItems().contains(clientFileL.getSelectionModel().getSelectedItem()) && clientFileL.getSelectionModel().getSelectedItem() != null) {
            try {
                PrintStream type = new PrintStream(clientSocket.getOutputStream());
                type.println("uploadPress");
                type.flush();
                writeFile(clientFile + "/" + clientFileL.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                e.printStackTrace();
            }
            serverList.add(clientFileL.getSelectionModel().getSelectedItem());
        }
    }

    public static void writeFile(String fileName) {
        try {
            File myFile = new File(fileName);
            byte[] byteArray = new byte[(int) myFile.length()];
            FileInputStream fileStream = new FileInputStream(myFile);
            BufferedInputStream inputBuffer = new BufferedInputStream(fileStream);
            DataInputStream dataInput= new DataInputStream(inputBuffer);
            dataInput.readFully(byteArray, 0, byteArray.length);
            DataOutputStream dataOutput = new DataOutputStream(clientSocket.getOutputStream());
            dataOutput.writeUTF(myFile.getName());
            dataOutput.writeLong(byteArray.length);
            dataOutput.write(byteArray, 0, byteArray.length);
            dataOutput.flush();
            System.out.println("Transferred " + myFile.getName());
        } catch (Exception e) { e.printStackTrace(); }
    }
}

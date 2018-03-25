package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.File;
import java.io.IOException;

import java.net.Socket;


public class Controller {
    @FXML
    ListView<File> serverFileL = new ListView();
    @FXML
    ListView<File> clientFileL = new ListView();
    File clientFile = new File("Client/");
    File serverFile = new File("Server/");
    ObservableList clientList = FXCollections.observableArrayList(clientFile.list());
    ObservableList serverList = FXCollections.observableArrayList(serverFile.list());
    Socket clientSocket;
    public void initialize(){
        serverFileL.setItems(serverList);
        clientFileL.setItems(clientList);
        /*try {
            //clientSocket = new Socket("localhost",12345);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }
    public void downloadPress(){
        if(!clientFileL.getItems().contains("hello")) {
            clientList.add("hello");
        }
    }
    public void uploadPress() throws IOException {
        //PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        if(!serverFileL.getItems().contains(clientFileL.getSelectionModel().getSelectedItem()) && clientFileL.getSelectionModel().getSelectedItem() != null) {
            serverList.add(clientFileL.getSelectionModel().getSelectedItem());
        }
    }
}
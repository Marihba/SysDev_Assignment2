package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    ListView serverFileL = new ListView();

    @FXML
    ListView clientFileL = new ListView();

    public void initialize(){
        List<String> hello =new ArrayList();
        ObservableList<String> temp = FXCollections.observableArrayList();
        temp.addAll("hello","stuff","1233");
        serverFileL.setItems(temp);
        clientFileL.setItems(temp);
        //serverFileL.getSelectionModel().getSelectedItems();
        System.out.println(serverFileL.getSelectionModel().getSelectedItem());
    }
}

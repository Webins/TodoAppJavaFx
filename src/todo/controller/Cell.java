package todo.controller;
/**
 * Sample Skeleton for 'cell.fxml' Controller Class
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import todo.model.Tasks;
import todo.controller.EditTodo;
import java.io.IOException;
import java.sql.SQLException;

public class Cell extends JFXListCell<Tasks> {

    public static String user_log;
    @FXML // fx:id="rootCell"
    private AnchorPane rootCell; // Value injected by FXMLLoader

    @FXML // fx:id="image"
    private ImageView image; // Value injected by FXMLLoader

    @FXML // fx:id="taskLabel"
    private Label taskLabel; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionLabel"
    private Label descriptionLabel; // Value injected by FXMLLoader

    @FXML // fx:id="createdAtLabel"
    private Label createdAtLabel; // Value injected by FXMLLoader

    @FXML // fx:id="deleteBtn"
    private JFXButton deleteBtn; // Value injected by FXMLLoader

    @FXML // fx:id="editBtn"
    private JFXButton editBtn; // Value injected by FXMLLoader

    @FXML // fx:id="count"
    private Label count; // Value injected by FXMLLoader

    private FXMLLoader fxmlLoader;

    @FXML
    void initialize(){

    }

    @Override
    protected void updateItem(Tasks item, boolean empty) {
        super.updateItem(item, empty);

        if(empty || item == null){
            setText(null);
            setGraphic(null);
        }else{
            if(fxmlLoader == null){
                fxmlLoader = new FXMLLoader(getClass().getResource("/todo/view/cell.fxml"));
                fxmlLoader.setController(this);
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            taskLabel.setText(item.getTask());
            descriptionLabel.setText(item.getDescription());
            createdAtLabel.setText(item.getCreatedAt().toString());
            count.setText(String.valueOf(item.count));

            deleteBtn.setOnAction(event->{
                try {
                    item.delete(item);
                    getListView().getItems().remove(getItem());

                    int total=  Integer.valueOf(TodoList.getTotalCount()) -1;
                    TodoList.setTotalCount(String.valueOf(total));
                    this.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/todo/view/todoList.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Parent list = loader.getRoot();
                    Stage listStage = new Stage();
                    listStage.setScene(new Scene(list));
                    fxmlLoader = new FXMLLoader(getClass().getResource("/todo/view/cell.fxml"));
                    fxmlLoader.setController(this);
                    try {
                        fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    listStage.show();



                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
            editBtn.setOnAction(event->{
                rootCell.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/todo/view/editTodo.fxml"));
                EditTodo.setActualTaks(item);
                EditTodo.user_log = user_log;
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent edit = loader.getRoot();
                Stage editStage = new Stage();
                editStage.setScene(new Scene(edit));
                editStage.show();
            });

            setText(null);
            setGraphic(rootCell);
        }
    }
}
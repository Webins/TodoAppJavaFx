/**
 * Sample Skeleton for 'todoList.fxml' Controller Class
 */

package todo.controller;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import todo.model.Tasks;
import com.jfoenix.controls.JFXListView;

public class TodoList {
    public static Integer user_id;
    public static String totalCount = "0";
    public static String user_log;
    public static ArrayList<Tasks> todoList;
    private ObservableList<Tasks> tasks;



    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;



    @FXML // fx:id="list"
    private JFXListView<Tasks> list; // Value injected by FXMLLoader

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="anchorPane"
    private AnchorPane anchorPane; // Value injected by FXMLLoader

    @FXML // fx:id="flashMsg"
    private Label flashMsg; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private JFXButton backButton; // Value injected by FXMLLoader

    @FXML // fx:id="userLog"
    private Label userLog; // Value injected by FXMLLoader

    @FXML // fx:id="total"
    private Label total; // Value injected by FXMLLoader

    @FXML // fx:id="addNewTask"
    private JFXButton addNewTask; // Value injected by FXMLLoader

    @FXML // fx:id="logout"
    private HBox logout; // Value injected by FXMLLoader


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        total.setText(totalCount);
        userLog.setText(getUser_log());


            tasks = FXCollections.observableArrayList();
            for(Tasks todo : todoList)
                tasks.add(todo);
            list.setItems(tasks);
            Cell.user_log= user_log;
            list.setCellFactory(Cell -> new Cell());


        logout.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/todo/view/login.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            logout.getScene().getWindow().hide();
            Parent loginPage = loader.getRoot();
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(loginPage));
            loginStage.show();
        });
        addNewTask.setOnAction(event ->{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/todo/view/addItemForm.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            backButton.getScene().getWindow().hide();
            AddItemForm addItemFormController = new AddItemForm();
            addItemFormController.set_user_id(getUser_id());
            Parent addItem = loader.getRoot();
            Stage addItemStage = new Stage();
            addItemStage.setScene(new Scene(addItem));
            addItemStage.show();
        });
        backButton.setOnAction(event ->{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/todo/view/addItem.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            backButton.getScene().getWindow().hide();
            Parent addItem = loader.getRoot();
            Stage addItemStage = new Stage();
            addItemStage.setScene(new Scene(addItem));
            addItemStage.show();
        });

    }

    public static Integer getUser_id() {
        return user_id;
    }

    public static void setUser_id(Integer user_id) {
        TodoList.user_id = user_id;
    }

    public static String getUser_log() {
        return user_log;
    }

    public Label getUserLog() { return userLog; }
    public static void setTotalCount(String totalCount) { TodoList.totalCount = totalCount; }
    public static String getTotalCount() {
        return totalCount;
    }
    public static void setUser_log(String user_log) {
        TodoList.user_log = user_log;
    }

    public static ArrayList<Tasks> getTodoList() {
        return TodoList.todoList;
    }

    public static void setTodoList(ArrayList<Tasks> todoList) {
        TodoList.todoList = todoList;
    }
    public Label getTotal() {
        return total;
    }

    public void setTotal(Label total) {
        this.total = total;
    }
}

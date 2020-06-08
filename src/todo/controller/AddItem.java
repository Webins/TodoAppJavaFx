package todo.controller;
/**
 * Sample Skeleton for 'addItem.fxml' Controller Class
 */

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import todo.animation.Shaker;
import todo.model.Tasks;

public class AddItem {

    public static Integer user_id;
    public static String user_log;

    @FXML // fx:id="anchorPane"
    private AnchorPane anchorPane; // Value injected by FXMLLoader

    @FXML // fx:id="listOfTodos"
    private JFXButton listOfTodos; // Value injected by FXMLLoader

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // fx:id="addItemAdd"
    private ImageView addItemAdd; // Value injected by FXMLLoader

    @FXML // fx:id="flashMsg"
    private Label flashMsg; // Value injected by FXMLLoader

    @FXML // fx:id="AddItemMsg"
    private Label AddItemMsg; // Value injected by FXMLLoader

    @FXML // fx:id="logout"
    private HBox logout; // Value injected by FXMLLoader

    @FXML // fx:id="userLog"
    private Label userLog; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        userLog.setText(user_log);
        listOfTodos.setOnAction(event->{
            Tasks task = new Tasks();
            try {
                ArrayList<Tasks> tasks = task.getAllTask(getUser_id());
                if(tasks.size() == 0) {
                    flashMsg.setText("Oops. You haven't created a task yet!");
                    Thread thread = new Thread(new DisplayMsg());
                    thread.start();
                    Shaker shaker = new Shaker(listOfTodos);
                    shaker.shake();
                }else{

                    TodoList.user_id = getUser_id();
                    TodoList.user_log = getUser_log();
                    TodoList.todoList = tasks;
                    TodoList.totalCount =String.valueOf(tasks.size());


                    AnchorPane formPane = FXMLLoader.load(getClass().getResource("/todo/view/todoList.fxml"));

                    FadeTransition todoListTransition = new FadeTransition(Duration.millis(2000), formPane);
                    todoListTransition.setFromValue(0f);
                    todoListTransition.setToValue(1f);
                    todoListTransition.setCycleCount(1);
                    todoListTransition.setAutoReverse(false);
                    todoListTransition.play();
                    anchorPane.getChildren().setAll(formPane);
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
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

        addItemAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            Shaker shaker = new Shaker(addItemAdd);
            shaker.shake();

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(4000), addItemAdd);
            FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(4000), AddItemMsg);

            //remove
            addItemAdd.relocate(0, 20);
            AddItemMsg.relocate(0,100);
            addItemAdd.setOpacity(0);
            AddItemMsg.setOpacity(0);

            fadeTransition2.setFromValue(1f);
            fadeTransition2.setToValue(0f);
            fadeTransition2.setCycleCount(1);
            fadeTransition2.setAutoReverse(false);
            fadeTransition2.play();

            fadeTransition.setFromValue(1f);
            fadeTransition.setToValue(0f);
            fadeTransition.setCycleCount(1);
            fadeTransition.setAutoReverse(false);


            fadeTransition.play();

            try {
                AnchorPane formPane = FXMLLoader.load(getClass().getResource("/todo/view/addItemForm.fxml"));

                FadeTransition rootTransition = new FadeTransition(Duration.millis(2000), formPane);
                AddItemForm.user_id = getUser_id();
                AddItemForm.user_log = getUser_log();
                rootTransition.setFromValue(0f);
                rootTransition.setToValue(1f);
                rootTransition.setCycleCount(1);
                rootTransition.setAutoReverse(false);
                rootTransition.play();
                anchorPane.getChildren().setAll(formPane);
            } catch (IOException e) {
                e.printStackTrace();
            }


        });

    }
    public static String getUser_log() {
        return user_log;
    }

    public static void setUser_log(String user_log) {
        AddItem.user_log = user_log;
    }
    public void setFlashMsg(String msg){
        flashMsg.setText(msg);
    }
    public void setId(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public Label getUserLog() {
        return userLog;
    }

    public void callDisplayMsg(){
        flashMsg.setStyle("-fx-background-color:#d4edda ; -fx-border-style:solid; -fx-border-color:#c3e6cb;");
        flashMsg.setTextFill(Paint.valueOf("#155724"));
        Thread thread = new Thread(new DisplayMsg());
        thread.start();
    }

    class DisplayMsg implements Runnable {

        @Override
        public void run() {
            flashMsg.setVisible(true);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flashMsg.setStyle("-fx-background-color:#f8d7da ; -fx-border-style:solid; -fx-border-color:#f5c6cb;");
            flashMsg.setTextFill(Paint.valueOf("#721c24"));
            flashMsg.setVisible(false);

        }
    }
}

/**
 * Sample Skeleton for 'editTodo.fxml' Controller Class
 */

package todo.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import todo.animation.Shaker;
import todo.model.Tasks;

import java.io.IOException;
import java.sql.SQLException;

public class EditTodo {
    public static String user_log;
    private Shaker shaker;
    private static Tasks actualTaks;

    @FXML // fx:id="task"
    private JFXTextField task; // Value injected by FXMLLoader

    @FXML // fx:id="submit"
    private JFXButton submit; // Value injected by FXMLLoader

    @FXML // fx:id="description"
    private JFXTextArea description; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private JFXButton backButton; // Value injected by FXMLLoader

    @FXML // fx:id="flashMsg"
    private Label flashMsg; // Value injected by FXMLLoader

    @FXML
    private Label userLog;

    @FXML // fx:id="logout"
    private HBox logout; // Value injected by FXMLLoader
    @FXML
    void initialize(){
        userLog.setText(user_log);
        task.setText(actualTaks.getTask());
        description.setText(actualTaks.getDescription());

        backButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/todo/view/todoList.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            backButton.getScene().getWindow().hide();
            Parent todoList = loader.getRoot();
            Stage todoListStage = new Stage();
            todoListStage.setScene(new Scene(todoList));
            todoListStage.show();
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
        submit.setOnAction(event ->{
            if(task.getText().isEmpty()){
                flashMsg.setText("The task can not be empty");
                Thread thread = new Thread(new DisplayMsg());
                thread.start();
                shaker = new Shaker(task);
                shaker.shake();
            }else if(description.getText().isEmpty()){
                flashMsg.setText("The description can not be empty");
                Thread thread = new Thread(new DisplayMsg());
                thread.start();
                shaker = new Shaker(description);
                shaker.shake();
            }else{
                Tasks tasks = new Tasks(actualTaks.getTaskid(), task.getText(), description.getText(), true);
                try {
                    tasks.update(tasks);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                description.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/todo/view/addItem.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent addItem = loader.getRoot();

                Stage addItemStage = new Stage();
                addItemStage.setScene(new Scene(addItem));
                AddItem addItemController = loader.getController();
                addItemController.setFlashMsg("Task edited \"" + task.getText() + "\" successfully");
                addItemController.callDisplayMsg();
                addItemController.setId(actualTaks.getUserid());
                addItemStage.show();
            }
            });
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

    public static Tasks getActualTaks() {
        return actualTaks;
    }

    public static void setActualTaks(Tasks actualTaks) {
        EditTodo.actualTaks = actualTaks;
    }

}

package todo.controller;

/**
 * Sample Skeleton for 'addItemForm.fxml' Controller Class
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
import todo.model.Users;

public class AddItemForm {

    public static Integer user_id;
    public static String user_log;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="task"
    private JFXTextField task; // Value injected by FXMLLoader

    @FXML // fx:id="submit"
    private JFXButton submit; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private JFXButton backButton; // Value injected by FXMLLoader

    @FXML // fx:id="flashMsg"
    private Label flashMsg; // Value injected by FXMLLoader

    @FXML // fx:id="description"
    private JFXTextArea description; // Value injected by FXMLLoader

    @FXML // fx:id="logout"
    private HBox logout; // Value injected by FXMLLoader

    @FXML
    private Label userLog;
    private Shaker shaker;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        userLog.setText(user_log);
        backButton.setOnAction(event->{
            backButton.getScene().getWindow().hide();
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
            addItemStage.show();
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

        submit.setOnAction(event->{
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
                Tasks tasks = new Tasks(getUser_id(), task.getText(), description.getText());
                try {
                    tasks.insert(tasks);
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
                addItemController.setFlashMsg("Task added \"" + task.getText() + "\" successfully");
                addItemController.callDisplayMsg();
                addItemController.setId(getUser_id());
                addItemStage.show();

            }
        });

    }
    public void set_user_id(Integer user_id) {
        this.user_id = user_id;
    }
    public Integer getUser_id() {
        return user_id;
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

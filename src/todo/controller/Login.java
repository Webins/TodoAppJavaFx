package todo.controller;

/**
 * Sample Skeleton for 'login.fxml' Controller Class
 */
import javafx.scene.paint.Paint;
import todo.animation.Shaker;
import  todo.db.dbHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import todo.model.Users;

public class Login {

    private short count = 0;
    public static Integer user_id;
    private Shaker shaker;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="loginUsername"
    private JFXTextField loginUsername; // Value injected by FXMLLoader

    @FXML // fx:id="loginPassword"
    private JFXPasswordField loginPassword; // Value injected by FXMLLoader

    @FXML // fx:id="login"
    private JFXButton loginSubmit; // Value injected by FXMLLoader

    @FXML // fx:id="loginSignUp"
    private JFXButton loginSignUp; // Value injected by FXMLLoader

    @FXML // fx:id="flashMsg"
    private Label flashMsg; // Value injected by FXMLLoader





    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws InterruptedException {

       loginSubmit.setOnAction(event -> {
           /*Check for empty inputs*/
           if(loginPassword.getText().isEmpty()){
               flashMsg.setText("The password is empty");
               Thread thread = new Thread(new DisplayMsg());
               thread.start();
               return;
           }else if(loginUsername.getText().isEmpty()){
               flashMsg.setText("The username is empty");
               Thread thread = new Thread(new DisplayMsg());
               thread.start();
               return;
           }


           try {
               Users check = new Users();
               ResultSet rs = check.select("SELECT username, password FROM Users WHERE BINARY password=\"" + loginPassword.getText()+ "\" AND BINARY    username=\"" + loginUsername.getText() + "\";");
               while (rs.next()){
                   count++;
               }

           } catch (SQLException throwables) {
               flashMsg.setText("The username or password are invalid");
               Thread thread = new Thread(new DisplayMsg());
               thread.start();
               return;
           } catch (ClassNotFoundException e) {
               e.printStackTrace();
           }


           if(count == 1) {
               count=0;
               loginUsername.getScene().getWindow().hide();
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
               addItemController.setFlashMsg("Welcome Back user \"" + loginUsername.getText() + "\"");
               addItemController.setUser_log(loginUsername.getText());
               addItemController.getUserLog().setText(AddItem.getUser_log());
               addItemController.callDisplayMsg();
               Users user = new Users();
               try {
                   user_id = user.getId(loginUsername.getText());

               } catch (SQLException throwables) {
                   throwables.printStackTrace();
               } catch (ClassNotFoundException e) {
                   e.printStackTrace();
               }
               addItemController.setId(user_id);
               addItemStage.show();
           }else{
               flashMsg.setText("The username or password are invalid");
               Thread thread = new Thread(new DisplayMsg());
               thread.start();
               shaker = new Shaker(loginUsername);
               shaker.shake();
               shaker = new Shaker(loginPassword);
               shaker.shake();
           }



       });
       loginSignUp.setOnAction(event -> {
           loginSignUp.getScene().getWindow().hide();
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("/todo/view/signup.fxml"));

           try {
               loader.load();
           } catch (IOException e) {
               e.printStackTrace();
           }

           Parent signup = loader.getRoot();
           Stage signupStage = new Stage();
           signupStage.setScene(new Scene(signup));
           signupStage.showAndWait();
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
}

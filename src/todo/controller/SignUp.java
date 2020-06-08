/**
 * Sample Skeleton for 'signup.fxml' Controller Class
 */

package todo.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import todo.animation.Shaker;
import todo.db.dbHandler;
import todo.model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUp {
        @FXML // ResourceBundle that was given to the FXMLLoader
        private ResourceBundle resources;

        @FXML // URL location of the FXML file that was given to the FXMLLoader
        private URL location;

        @FXML // fx:id="signupName"
        private JFXTextField signupName; // Value injected by FXMLLoader

        @FXML // fx:id="signupUsername"
        private JFXTextField signupUsername; // Value injected by FXMLLoader

        @FXML // fx:id="signupLocation"
        private JFXTextField signupLocation; // Value injected by FXMLLoader

        @FXML // fx:id="signupOther"
        private JFXCheckBox signupOther; // Value injected by FXMLLoader

        @FXML // fx:id="signupMale"
        private JFXCheckBox signupMale; // Value injected by FXMLLoader

        @FXML // fx:id="signupFemale"
        private JFXCheckBox signupFemale; // Value injected by FXMLLoader

        @FXML // fx:id="signupPassword"
        private JFXPasswordField signupPassword; // Value injected by FXMLLoader

        @FXML // fx:id="signupConfirm"
        private JFXPasswordField signupConfirm; // Value injected by FXMLLoader

        @FXML // fx:id="SignupSubmit"
        private JFXButton SignupSubmit; // Value injected by FXMLLoader

        @FXML // fx:id="flashMsg"
        private Label flashMsg; // Value injected by FXMLLoader

        @FXML // fx:id="signupBack"
        private JFXButton signupBack; // value injected by FXMLLoader

        private short count = 0;
        private Integer user_id;
        private Shaker shaker;

        @FXML // This method is called by the FXMLLoader when initialization is complete
        void initialize() {
                signupBack.setOnAction(event -> {
                        signupBack.getScene().getWindow().hide();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/todo/view/login.fxml"));
                        try {
                                loader.load();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                        Parent login = loader.getRoot();
                        Stage loginStage = new Stage();
                        loginStage.setScene(new Scene(login));
                        loginStage.show();
                });

                signupMale.setOnAction(event -> {
                        if(signupMale.isSelected()){
                                signupFemale.setSelected(false);
                                signupOther.setSelected(false);
                        }
                });

                signupFemale.setOnAction(event -> {
                        if(signupFemale.isSelected()){
                                signupMale.setSelected(false);
                                signupOther.setSelected(false);
                        }
                });

                signupOther.setOnAction(event -> {
                        if(signupOther.isSelected()){
                                signupMale.setSelected(false);
                                signupFemale.setSelected(false);
                        }
                });

                SignupSubmit.setOnAction(actionEvent -> {
                        if(signupName.getText().isEmpty()){
                                flashMsg.setText("The name can not be empty");
                                Thread thread = new Thread(new DisplayMsg());
                                thread.start();
                                shaker = new Shaker(signupName);
                                shaker.shake();
                                return;
                        }
                        else if(signupUsername.getText().isEmpty()){
                                flashMsg.setText("The username can not be empty");
                                Thread thread = new Thread(new DisplayMsg());
                                thread.start();
                                shaker = new Shaker(signupUsername);
                                shaker.shake();
                                return;
                        }
                        else if(signupPassword.getText().isEmpty()){
                                flashMsg.setText("The password can not be empty");
                                Thread thread = new Thread(new DisplayMsg());
                                thread.start();
                                shaker = new Shaker(signupPassword);
                                shaker.shake();
                                return;
                        }
                        else if(!signupConfirm.getText().equals(signupPassword.getText())){
                                flashMsg.setText("The two password are not equal");
                                Thread thread = new Thread(new DisplayMsg());
                                thread.start();
                                shaker = new Shaker(signupPassword);
                                shaker.shake();
                                shaker = new Shaker(signupConfirm);
                                shaker.shake();
                                return;
                        }
                        else if(!signupMale.isSelected() && !signupFemale.isSelected() && !signupOther.isSelected()){
                                flashMsg.setText("You must select a gender");
                                Thread thread = new Thread(new DisplayMsg());
                                thread.start();
                                shaker = new Shaker(signupMale);
                                shaker.shake();
                                shaker = new Shaker(signupFemale);
                                shaker.shake();
                                shaker = new Shaker(signupOther);
                                shaker.shake();
                                return;
                        }
                        else if(signupLocation.getText().isEmpty()){
                                flashMsg.setText("The location can not be empty");
                                Thread thread = new Thread(new DisplayMsg());
                                thread.start();
                                shaker = new Shaker(signupLocation);
                                shaker.shake();
                                return;
                        }

                        try {
                                Users check = new Users();
                                ResultSet rs = check.select("SELECT username FROM Users WHERE username=\"" + signupUsername.getText()+ "\";");
                                while (rs.next()){
                                        System.out.println(count);
                                        count++;
                                }

                        } catch (SQLException throwables) {
                                throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                        }

                        if(count ==0){
                                String gender = null;
                                if(signupMale.isSelected()){
                                        gender = "Male";
                                }else if(signupFemale.isSelected()){
                                        gender = "Female";
                                }else if(signupOther.isSelected()){
                                        gender="Other";
                                }
                                try {
                                        Users user = new Users(signupName.getText(), signupUsername.getText(), signupPassword.getText(), gender, signupLocation.getText());
                                        user.insert(user);
                                        signupBack.getScene().getWindow().hide();
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
                                        addItemController.setFlashMsg("Welcome new user \"" + signupUsername.getText() + "\"");
                                        addItemController.callDisplayMsg();
                                        user_id = user.getId(signupUsername.getText());
                                        addItemController.setId(user_id);
                                        addItemController.setUser_log(signupUsername.getText());
                                        addItemController.getUserLog().setText(AddItem.getUser_log());
                                        addItemStage.show();
                                } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                }
                        }else{
                                flashMsg.setText("The username already exist. Choose another");
                                Thread thread = new Thread(new DisplayMsg());
                                thread.start();
                                shaker = new Shaker(signupUsername);
                                shaker.shake();
                                count=0;
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
}

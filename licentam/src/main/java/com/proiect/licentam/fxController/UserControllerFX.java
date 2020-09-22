package com.proiect.licentam.fxController;


import com.proiect.licentam.utils.MyApplicationContextSingleton;
import com.proiect.licentam.model.User;
import com.proiect.licentam.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@FxmlView("Login.fxml")
public class UserControllerFX {

    @FXML
    public TextField userNameTB;
    @FXML
    public TextField passwordTB;
    @FXML
    public Button loginB;
    @FXML
    public Button registerB;
    @FXML
    public CheckBox registerCheckBox;

    MyApplicationContextSingleton myApplicationContextSingleton;

    private UserService userService;


    @Autowired
    public UserControllerFX(UserService userService) {
        this.userService = userService;
    }

    public void registerUser(ActionEvent actionEvent) {

        User toBeSavedUser = new User();
        toBeSavedUser.setUserName(userNameTB.getText());
        toBeSavedUser.setPassword(passwordTB.getText());
        if(userService.registerUser(toBeSavedUser).getUserName() != null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getDialogPane().getScene().getStylesheets().add(UserControllerFX.class.getResource("appStyle.css").toExternalForm());
            alert.setTitle("Register");
            alert.setHeaderText("Registered succesfully");
            alert.setContentText("Now login to enjoy the app");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Register");
            alert.getDialogPane().getScene().getStylesheets().add(UserControllerFX.class.getResource("errorStyle.css").toExternalForm());
            alert.setHeaderText("Register failed");
            alert.setContentText("Please try another username or password");
            alert.showAndWait();
        }
    }


    public void loginUser(ActionEvent actionEvent) throws IOException {


        User toBeLoggedUser = new User();
        toBeLoggedUser.setUserName(userNameTB.getText());
        toBeLoggedUser.setPassword(passwordTB.getText());
        User loggedUser = userService.findUserByUsernamePassword(toBeLoggedUser);


        if (loggedUser != null) {
            MyApplicationContextSingleton myApplicationContextSingleton = this.myApplicationContextSingleton.getInstance();
            FxWeaver fxWeaver = myApplicationContextSingleton.getApplicationContext().getBean(FxWeaver.class);
            Parent root = fxWeaver.loadView(HouseControllerFX.class);
            HouseControllerFX houseControllerFX = fxWeaver.getBean(HouseControllerFX.class);
            houseControllerFX.setCurrentUser(loggedUser);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(HouseControllerFX.class.getResource("appStyle.css").toExternalForm());
            Stage stage = (Stage) loginB.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Real Estate Investment App");
            stage.getIcons().add(new Image(UserControllerFX.class.getResourceAsStream("reia.png")));
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login");
            alert.getDialogPane().getScene().getStylesheets().add(UserControllerFX.class.getResource("errorStyle.css").toExternalForm());
            alert.setHeaderText("Login failed");
            alert.setContentText("Try another username & password");
            alert.showAndWait();
        }
    }
}

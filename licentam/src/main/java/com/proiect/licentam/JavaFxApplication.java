package com.proiect.licentam;


import com.proiect.licentam.utils.MyApplicationContextSingleton;
import com.proiect.licentam.fxController.UserControllerFX;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;



public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext applicationContext;
    public MyApplicationContextSingleton myApplicationContextSingleton;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
                .sources(LicentamApplication.class)
                .run(args);

        MyApplicationContextSingleton myApplicationContextSingleton = this.myApplicationContextSingleton.getInstance();
        myApplicationContextSingleton.setApplicationContext(applicationContext);
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }

    @Override
    public void start(Stage stage){
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(UserControllerFX.class);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(UserControllerFX.class.getResource("appStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Real Estate Investment App");
        stage.getIcons().add(new Image(UserControllerFX.class.getResourceAsStream("reia.png")));

        stage.show();
//        FXMLLoader loader = new FXMLLoader();
//        URL xmlUrl = getClass().getResource("com/proiect/licentam/fxController/Login.fxml");
//        loader.setLocation(xmlUrl);
//        Parent root = loader.load();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
    }

}


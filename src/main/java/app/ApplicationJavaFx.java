package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.ConfigService;

import java.io.IOException;
import java.util.Objects;

public class ApplicationJavaFx extends Application {
    private final ConfigService configService = new ConfigService();
    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;


        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationJavaFx.class.getResource("/fxml/main.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        Scene scene = new Scene(anchorPane);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(ApplicationJavaFx.class.getResourceAsStream("/img/logo_3.png"))));
        primaryStage.setTitle("Generator usług REST Web Services");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        //log.info("Application started");
        configService.createConfigFileIfNotExists();
    }

    public static void switchScene(String fxml) throws IOException {
        Parent parent = FXMLLoader.load(ApplicationJavaFx.class.getResource("/fxml/" + fxml));
        primaryStage.getScene().setRoot(parent);
    }
}

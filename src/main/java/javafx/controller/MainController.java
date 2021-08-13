package javafx.controller;

import javafx.ApplicationJavaFx;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainController {

    public void switchScene(ActionEvent actionEvent) throws IOException {
        var button = (Button) actionEvent.getSource();
        ApplicationJavaFx.switchScene( button.getId() + ".fxml");
    }

    public void closeApplication(){
        Platform.exit();
    }

}

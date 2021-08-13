package javafx.controller;

import javafx.ApplicationJavaFx;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import java.io.IOException;

public class OptionsController {

    public void switchScene(ActionEvent actionEvent) throws IOException {
        var button = (Button) actionEvent.getSource();
        ApplicationJavaFx.switchScene( button.getId() + ".fxml");
    }

}

package controller;

import app.RestWebServiceGenerator;
import config.Extension;
import controller.customization.ListViewCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import service.GeneratorService;
import service.LoaderService;
import service.ServerService;

import java.io.IOException;
import java.util.Arrays;

public class GeneratorController {

    @FXML
    private ChoiceBox<String> httpMethodChooser;
    @FXML
    private ListView<String> restListView;
    @FXML
    private CheckBox jerseyServer;
    ServerService serverService = new ServerService();

    @FXML
    void initialize() {
        fillHttpMethodChooser();
        setListenerForHttpMethodChooser();
        setCellFactoryForRestListView();
        setListenerForJerseyServer();
    }

    private void fillHttpMethodChooser() {
        httpMethodChooser.getItems().addAll(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    }

    private void setListenerForHttpMethodChooser() {
        httpMethodChooser.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var uriContainer = LoaderService.loadUriContainerFile();
                    cleanRestListView(restListView);
                    switch (newValue) {
                        case "GET":
                            restListView.getItems().addAll(uriContainer.getGetServices());
                            break;
                        case "POST":
                            restListView.getItems().addAll(uriContainer.getPostServices());
                            break;
                        case "PUT":
                            restListView.getItems().addAll(uriContainer.getPutServices());
                            break;
                        case "DELETE":
                            restListView.getItems().addAll(uriContainer.getDeleteServices());
                            break;
                        default:
                            cleanRestListView(restListView);
                    }
                });
    }

    private void setListenerForJerseyServer() {
        jerseyServer.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                var config = LoaderService.loadConfigFile();
                serverService.serverStart(config);
            } else {
                ServerService.stopServer();
            }
        });
    }

    private void setCellFactoryForRestListView() {
        restListView.setCellFactory(param -> new ListViewCell());
    }

    private void cleanRestListView(ListView<String> restListView) {
        restListView.getItems().clear();
    }

    public void switchScene(ActionEvent actionEvent) throws IOException {
        var button = (Button) actionEvent.getSource();
        RestWebServiceGenerator.switchScene(button.getId() + Extension.FXML);
    }

    public void generateServices() {
        new GeneratorService();
    }
}

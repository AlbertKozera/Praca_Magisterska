package controller;

import app.RestWebServiceGenerator;
import config.Extension;
import controller.customization.ListViewCell;
import dto.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import service.ConfigService;
import service.GeneratorService;
import service.LoaderService;
import service.ServerService;

import java.io.IOException;
import java.util.Arrays;

public class GeneratorController {
    private final ConfigService configService = new ConfigService();

    @FXML
    private ChoiceBox<String> httpMethodChooser;
    @FXML
    private ListView<String> restListView;
    @FXML
    private CheckBox jerseyServer;
    ServerService serverService = new ServerService();

    @FXML
    void initialize() {
        isServerUp();
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

    private void isServerUp() {
        var config = LoaderService.loadConfigFile();
        jerseyServer.setSelected(config.isServerUp());
    }

    private void setListenerForJerseyServer() {
        jerseyServer.selectedProperty().addListener((observable, oldValue, newValue) -> {
            var config = LoaderService.loadConfigFile();
            if (newValue) {
                serverService.serverStart(config);
                saveChanges(setConfigProperties(config,true));
            } else {
                ServerService.stopServer();
                saveChanges(setConfigProperties(config,false));
            }
        });
    }

    public void saveChanges(Config config) {
        configService.saveConfig(config);
    }

    private Config setConfigProperties(Config config, boolean isServerUp) {
        return Config.builder()
                .url(config.getUrl())
                .username(config.getUsername())
                .password(config.getPassword())
                .catalogName(config.getCatalogName())
                .schemaName(config.getSchemaName())
                .jdbcDriverPath(config.getJdbcDriverPath())
                .driverClassName(config.getDriverClassName())
                .hostname(config.getHostname())
                .port(config.getPort())
                .serverPath(config.getServerPath())
                .isServerUp(isServerUp).build();
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

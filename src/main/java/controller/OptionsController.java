package controller;

import app.RestWebServiceGenerator;
import config.Extension;
import dto.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;
import service.ConfigService;
import service.LoaderService;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class OptionsController {
    private final ConfigService configService = new ConfigService();

    @FXML
    private TextField url;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField jdbcDriverPath;
    @FXML
    private TextField driverClassName;
    @FXML
    private TextField hostname;
    @FXML
    private TextField port;
    @FXML
    private TextField serverPath;

    @FXML
    void initialize() {
        var config = LoaderService.loadConfigFile();
        if(config != null) {
            fillOptionsFields(config);
        }
    }

    public void saveChanges() {
        configService.saveConfig(getConfigProperties());
    }

    private void fillOptionsFields(Config config) {
        url.setText(config.getUrl());
        username.setText(config.getUsername());
        password.setText(config.getPassword());
        jdbcDriverPath.setText(config.getJdbcDriverPath());
        driverClassName.setText(config.getDriverClassName());
        hostname.setText(config.getHostname());
        port.setText(config.getPort());
        serverPath.setText(config.getServerPath());
    }

    private Config getConfigProperties() {
        return Config.builder()
                .url(url.getText())
                .username(username.getText())
                .password(password.getText())
                .jdbcDriverPath(jdbcDriverPath.getText())
                .driverClassName(driverClassName.getText())
                .hostname(hostname.getText())
                .port(port.getText())
                .serverPath(serverPath.getText()).build();
    }

    public void choosePatchForJdbcDriver() {
        var fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java archive files", Extension.JAR));
        try {
            var file = fileChooser.showOpenDialog(null);
            if (Objects.nonNull(file)) {
                jdbcDriverPath.setText(file.getAbsolutePath());
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    public void switchScene(ActionEvent actionEvent) throws IOException {
        var button = (Button) actionEvent.getSource();
        RestWebServiceGenerator.switchScene( button.getId() + Extension.FXML);
    }

}

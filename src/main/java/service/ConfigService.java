package service;

import com.google.gson.Gson;
import config.Extension;
import config.Path;
import dto.Config;
import lombok.extern.log4j.Log4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Log4j
public class ConfigService {

    public void createConfigFileIfNotExists() {
        try {
            var configFile = new File(Path.PROJECT_RESOURCES + "config" + Extension.JSON);
            if (configFile.createNewFile())
                log.info("In directory " + Path.PROJECT_RESOURCES + " config file " + configFile.getName() + " has been created");
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    private Config getConfigProperties(File dataStorageDirectory) {
        return Config.builder()
                //.jdbcDriverPath()
               // .saveTo(dataStorageDirectory.getAbsolutePath())
                .build();
    }

    public void saveConfig(Config config) {
        try (var fileWriter = new FileWriter(Path.PROJECT_RESOURCES + "config" + Extension.JSON)) {
            fileWriter.write(new Gson().toJson(config));
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

}

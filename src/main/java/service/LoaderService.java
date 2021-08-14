package service;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import config.Extension;
import config.Path;
import dto.Config;
import dto.UriContainer;

import java.io.FileReader;
import java.io.IOException;

public class LoaderService {
    public static Config loadConfigFile() {
        try (var jsonReader = new JsonReader(new FileReader(Path.PROJECT_RESOURCES + "config" + Extension.JSON))) {
            return new Gson().fromJson(jsonReader, Config.class);
        } catch (IOException e) {
            return Config.builder().build();
        }
    }

    public static UriContainer loadUriContainerFile() {
        try (var jsonReader = new JsonReader(new FileReader(Path.AUTOGENERATED + "URI container" + Extension.JSON))) {
            return new Gson().fromJson(jsonReader, UriContainer.class);
        } catch (IOException e) {
            return UriContainer.builder().build();
        }
    }
}





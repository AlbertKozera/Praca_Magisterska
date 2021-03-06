package generator.content;

import com.google.gson.Gson;
import config.Common;
import config.Extension;
import config.Path;
import dto.ColumnMetadata;
import dto.Config;
import dto.UriContainer;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
public class UriContainerGenerator {

    public UriContainerGenerator(Map<String, LinkedList<ColumnMetadata>> metadata, Config config) {
        List<String> getServices = new ArrayList<>();
        List<String> postServices = new ArrayList<>();
        List<String> putServices = new ArrayList<>();
        List<String> deleteServices = new ArrayList<>();
        String uri = "http://" + config.getHostname() + ":" + config.getPort();
        if(!Common.empty(config.getServerPath())) {
            uri += "/" + config.getServerPath();
        }
        for(var entry : metadata.entrySet()) {
            getServices.add(uri + "/" + entry.getKey().toLowerCase());
            getServices.add(uri + "/" + entry.getKey().toLowerCase() + "/{id}");
            postServices.add(uri + "/" + entry.getKey().toLowerCase());
            putServices.add(uri + "/" + entry.getKey().toLowerCase() + "/{id}");
            deleteServices.add(uri + "/" + entry.getKey().toLowerCase() + "/{id}");
        }
        saveUriContainer(UriContainer.builder()
                .getServices(getServices)
                .postServices(postServices)
                .putServices(putServices)
                .deleteServices(deleteServices).build());
    }

    private void saveUriContainer(UriContainer uriContainer) {
        try (var fileWriter = new FileWriter(Path.AUTOGENERATED + "URI container" + Extension.JSON)) {
            fileWriter.write(new Gson().toJson(uriContainer));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

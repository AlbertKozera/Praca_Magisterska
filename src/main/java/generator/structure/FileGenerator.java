package generator.structure;

import config.Extension;
import config.Path;
import dto.ColumnMetadata;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import static config.Common.*;

@Slf4j
public class FileGenerator {
    private final Map<String, LinkedList<ColumnMetadata>> metadata;

    public FileGenerator(Map<String, LinkedList<ColumnMetadata>> metadata) {
        this.metadata = metadata;
    }

    public void generateFiles() {
        generateConfig();
        generateDto();
        generateService();
        generateServiceImpl();
        generateController();
        generateUriContainer();
    }

    public void generateConfig() {
        createProjectFile(Path.AUTOGENERATED_CONFIG, "DbCon" + Extension.JAVA);
    }

    public void generateController() {
        metadata.forEach((t, c) -> createProjectFile(Path.AUTOGENERATED_CONTROLLER, getControllerClassName(t) + Extension.JAVA));
    }

    public void generateDto() {
        metadata.forEach((t, c) -> createProjectFile(Path.AUTOGENERATED_DTO, getDtoClassName(t) + Extension.JAVA));
    }

    public void generateService() {
        createProjectFile(Path.AUTOGENERATED_SERVICE, "ServiceTemplate" + Extension.JAVA);
    }

    public void generateServiceImpl() {
        metadata.forEach((t, c) -> createProjectFile(Path.AUTOGENERATED_SERVICE_IMPL, getServiceClassName(t) + Extension.JAVA));
    }

    public void generateUriContainer() {
        createProjectFile(Path.AUTOGENERATED, "URI container" + Extension.JSON);
    }

    private void createProjectFile(String path, String fileName) {
        try {
            var file = new File(path + fileName);
            if (file.createNewFile())
                log.info("In directory " + path + " file " + file.getName() + " has been created");
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }
}
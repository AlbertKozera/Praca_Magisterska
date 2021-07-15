package generator;

import config.DbConfig;
import generator.content.*;
import generator.structure.DirectoryCreator;
import generator.structure.FileGenerator;
import dto.ColumnMetadata;
import dto.DbParameters;
import lombok.extern.slf4j.Slf4j;
import service.MetadataService;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;

@Slf4j
public class Generator {
    private DirectoryCreator directoryCreator = new DirectoryCreator();
    private FileGenerator fileGenerator = new FileGenerator(getMetadata());
    private ConfigGenerator configGenerator = new ConfigGenerator();
    private DtoGenerator dtoGenerator = new DtoGenerator(getMetadata());
    private ServiceGenerator serviceGenerator = new ServiceGenerator();
    private ServiceImplGenerator serviceImplGenerator = new ServiceImplGenerator(getMetadata());
    private ControllerGenerator controllerGenerator = new ControllerGenerator(getMetadata());

    public void generate() {
        directoryCreator.generateStructure();
        fileGenerator.generateFiles();
        configGenerator.generateConfig(DbParameters.builder()
                .driver("oracle.jdbc.driver.OracleDriver")
                .url("jdbc:oracle:thin:@localhost:1521:orcl")
                .user("c##albert")
                .password("albert").build());
        dtoGenerator.generateDto();
        serviceGenerator.generateService();
        serviceImplGenerator.generateServiceImpl();
        controllerGenerator.generateController();
    }

    private Map<String, LinkedList<ColumnMetadata>> getMetadata() {
        var connection = DbConfig.connection();
        try {
            var metadataService = new MetadataService();
            return metadataService.getMetadata(connection);
        } catch (SQLException sqlException) {
            log.error(sqlException.getMessage());
            throw new RuntimeException();
        }
    }
}
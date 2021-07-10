package generator;

import config.DbConfig;
import config.DirectoryCreator;
import dto.ColumnMetadata;
import dto.DbParameters;
import generator.content.ConfigGenerator;
import generator.content.DtoGenerator;
import generator.content.ServiceGenerator;
import lombok.extern.slf4j.Slf4j;
import service.MetadataService;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;

@Slf4j
public class Generator {
    private DirectoryCreator directoryCreator = new DirectoryCreator();
    private FileGenerator fileGenerator = new FileGenerator(getMetadata());
    private ServiceGenerator serviceGenerator = new ServiceGenerator();
    private ConfigGenerator configGenerator = new ConfigGenerator();
    private DtoGenerator dtoGenerator = new DtoGenerator(getMetadata());

    public void generate() {
        directoryCreator.generateRESTWebServicesStructure();
        fileGenerator.generateRESTWebServicesFiles();
        serviceGenerator.generateServiceTemplate();
        configGenerator.generateConfig(DbParameters.builder()
                .driver("oracle.jdbc.driver.OracleDriver")
                .url("jdbc:oracle:thin:@localhost:1521:orcl")
                .user("c##albert")
                .password("albert").build());
        dtoGenerator.generateDto();

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

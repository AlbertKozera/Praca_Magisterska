package generator;

import config.DbCon;
import config.DirectoryCreator;
import dto.ColumnMetadata;
import generator.content.ServiceGenerator;
import lombok.extern.slf4j.Slf4j;
import service.MetadataService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;

@Slf4j
public class Generator {
    private DirectoryCreator directoryCreator = new DirectoryCreator();
    private FileGenerator fileGenerator = new FileGenerator(getMetadata());

    public void generate() {
        directoryCreator.generateRESTWebServicesStructure();
        fileGenerator.generateRESTWebServicesFiles();
        ServiceGenerator serviceGenerator = new ServiceGenerator();
        serviceGenerator.generateServiceTemplate();

    }

    private Map<String, LinkedList<ColumnMetadata>> getMetadata() {
        var connection = DbCon.connection();
        try {
            var metadataService = new MetadataService();
            return metadataService.getMetadata(connection);
        } catch (SQLException sqlException) {
            log.error(sqlException.getMessage());
            throw new RuntimeException();
        }
    }

}

package generator;

import config.DbCon;
import config.DirectoryCreator;
import dto.ColumnMetadata;
import lombok.extern.slf4j.Slf4j;
import service.DbMetadata;

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
    }

    private Map<String, LinkedList<ColumnMetadata>> getMetadata() {
        var connection = DbCon.connection();
        try {
            var dbMetadata = new DbMetadata();
            return dbMetadata.getMetadata(connection);
        } catch (SQLException sqlException) {
            log.error(sqlException.getMessage());
            throw new RuntimeException();
        }
    }

}

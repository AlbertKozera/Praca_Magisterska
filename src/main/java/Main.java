import config.DbCon;
import config.DirectoryCreator;
import generator.Generator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import service.DbMetadata;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Generator generator = new Generator();
        generator.generate();





    }

}

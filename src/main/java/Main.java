import config.DbCon;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public class Main {
    public static void main(String[] args) {
        var connection = DbCon.connection();

        try {
            var dbMetadata = new DbMetadata();
            dbMetadata.getMetadata(connection);
        } catch (SQLException sqlException) {
            log.error(sqlException.getMessage());
            throw new RuntimeException();
        }
    }

}

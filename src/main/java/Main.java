import config.DbConfig;
import generator.Generator;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class Main {
    public static void main(String[] args) throws SQLException {
        var generator = new Generator();
        generator.generate();


/*        var query = "SELECT * FROM TMP";
        var statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
        boolean b = rsmd.isSearchable(1);*/

    }
}

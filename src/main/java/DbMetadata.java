import dto.ColumnMetadata;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class DbMetadata {

    public List<String> getListOfTables(Connection connection) throws SQLException {
        var databaseMetaData = connection.getMetaData();
        List<String> listOfTables = new ArrayList<String>();
        ResultSet rs = databaseMetaData.getTables(null, "C##ALBERT", "%", new String[]{"TABLE"});
        while (rs.next()) {
            listOfTables.add(rs.getString("TABLE_NAME"));
        }
        return listOfTables;
    }

    public Map<String, LinkedList<ColumnMetadata>> getMetadata(Connection connection) throws SQLException {
        Map<String, LinkedList<ColumnMetadata>> metadata = new LinkedHashMap<>();
        List<ColumnMetadata> columnMetadataList = new LinkedList<>();
        String previousTableName = null;
        var databaseMetaData = connection.getMetaData();
        ResultSet rs = databaseMetaData.getColumns(null, "C##ALBERT", null, null);

        while (rs.next()) {
            var tableName = getProperty(rs, "TABLE_NAME");
            if(!tableName.equals(previousTableName) && previousTableName != null) {
                metadata.put(previousTableName, new LinkedList<>(columnMetadataList));
                columnMetadataList.clear();
            }
            columnMetadataList.add(getColumnMetadata(rs));
            previousTableName = getProperty(rs, "TABLE_NAME");
        }
        metadata.put(previousTableName, new LinkedList<>(columnMetadataList));
        return metadata;
    }

    private ColumnMetadata getColumnMetadata(ResultSet rs) {
        return ColumnMetadata.builder()
                .name(getProperty(rs, "COLUMN_NAME"))
                .type(getProperty(rs, "TYPE_NAME"))
                .isNullable(getProperty(rs, "IS_NULLABLE"))
                .isAutoincrement(getProperty(rs, "IS_AUTOINCREMENT")).build();
    }

    private String getProperty(ResultSet rs, String property) {
        try {
            return rs.getString(property);
        } catch (SQLException sqlException) {
            log.error(sqlException.getMessage());
            throw new RuntimeException();
        }
    }
}

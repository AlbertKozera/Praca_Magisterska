package service;

import config.JdbcClassConverter;
import dto.ColumnMetadata;
import dto.Config;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class MetadataService {

    public Map<String, LinkedList<ColumnMetadata>> getMetadata(Connection connection, Config config) throws SQLException {
        Map<String, LinkedList<ColumnMetadata>> metadata = new LinkedHashMap<>();
        List<ColumnMetadata> columnMetadataList = new LinkedList<>();
        String previousTableName = null;
        var databaseMetaData = connection.getMetaData();
        ResultSet rs = databaseMetaData.getColumns(null, config.getSchemaName(), null, null);
        while (rs.next()) {
            var tableName = getProperty(rs, "TABLE_NAME");
            if(!tableName.equals(previousTableName) && previousTableName != null) {
                metadata.put(previousTableName, new LinkedList<>(columnMetadataList));
                columnMetadataList.clear();
            }
            columnMetadataList.add(getColumnMetadata(rs, getPrimaryKeyColumnForTable(databaseMetaData, config.getSchemaName(), tableName)));
            previousTableName = getProperty(rs, "TABLE_NAME");
        }
        metadata.put(previousTableName, new LinkedList<>(columnMetadataList));
        return metadata;
    }

    private ColumnMetadata getColumnMetadata(ResultSet rs, String primaryKeyColumn) {
        String columnName = getProperty(rs, "COLUMN_NAME");
        var columnMetadataBuilder = ColumnMetadata.builder()
                .name(columnName)
                .databaseType(getProperty(rs, "TYPE_NAME"))
                .javaType(JdbcClassConverter.getName(getProperty(rs, "DATA_TYPE")))
                .javaTypePackage(JdbcClassConverter.getPackageName(getProperty(rs, "DATA_TYPE")));
                //.isNullable(getProperty(rs, "IS_NULLABLE"))
                //.isAutoincrement(getProperty(rs, "IS_AUTOINCREMENT"));
        if(columnName.equals(primaryKeyColumn))
            columnMetadataBuilder.isPrimaryKey(true);
        return columnMetadataBuilder.build();
    }

    private String getPrimaryKeyColumnForTable(DatabaseMetaData databaseMetaData, String schemaName, String tableName) throws SQLException {
        ResultSet rs = databaseMetaData.getPrimaryKeys(null, schemaName, tableName);
        rs.next();
        return getProperty(rs, "COLUMN_NAME");
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

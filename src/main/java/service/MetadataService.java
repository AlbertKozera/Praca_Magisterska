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

import static config.Common.empty;

@Slf4j
public class MetadataService {

    public Map<String, LinkedList<ColumnMetadata>> getMetadata(Connection connection, Config config) throws SQLException {
        Map<String, LinkedList<ColumnMetadata>> metadata = new LinkedHashMap<>();
        List<ColumnMetadata> columnMetadataList = new LinkedList<>();
        String previousTableName = null;
        String catalog = null;
        String schemaPattern = null;
        var databaseMetaData = connection.getMetaData();
        if(!empty(config.getSchemaName())) {
            schemaPattern = config.getSchemaName();
        }
        if(!empty(config.getCatalogName())) {
            catalog = config.getCatalogName();
        }
        ResultSet rs = databaseMetaData.getColumns(catalog, schemaPattern, null, null);
        while (rs.next()) {
            var tableName = getProperty(rs, "TABLE_NAME");
            if(!tableName.equals(previousTableName) && previousTableName != null) {
                metadata.put(previousTableName, new LinkedList<>(columnMetadataList));
                columnMetadataList.clear();
            }
            var primaryKey = getPrimaryKeyColumnForTable(databaseMetaData, catalog, schemaPattern, tableName);
            if(primaryKey != null) {
                columnMetadataList.add(getColumnMetadata(rs, primaryKey));
            }
            previousTableName = getProperty(rs, "TABLE_NAME");
        }
        metadata.put(previousTableName, new LinkedList<>(columnMetadataList));
        metadata.entrySet().removeIf(element -> element.getValue().isEmpty());
        return metadata;
    }

    private ColumnMetadata getColumnMetadata(ResultSet rs, String primaryKeyColumn) {
        String columnName = getProperty(rs, "COLUMN_NAME").toUpperCase();
        var columnMetadataBuilder = ColumnMetadata.builder()
                .name(columnName)
                .databaseType(getProperty(rs, "TYPE_NAME"))
                .javaType(JdbcClassConverter.getName(getProperty(rs, "DATA_TYPE")))
                .javaTypePackage(JdbcClassConverter.getPackageName(getProperty(rs, "DATA_TYPE")));
        if(columnName.equals(primaryKeyColumn))
            columnMetadataBuilder.isPrimaryKey(true);
        return columnMetadataBuilder.build();
    }

    private String getPrimaryKeyColumnForTable(DatabaseMetaData databaseMetaData, String catalog, String schemaPattern, String tableName) throws SQLException {
        ResultSet rs = databaseMetaData.getPrimaryKeys(catalog, schemaPattern, tableName);
        String primaryKey;
        if(!rs.next()) {
            return null;
        }
        else {
            primaryKey = getProperty(rs, "COLUMN_NAME").toUpperCase();
        }

        if(rs.next()) {
            return null;
        }
        else {
            return primaryKey;
        }
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

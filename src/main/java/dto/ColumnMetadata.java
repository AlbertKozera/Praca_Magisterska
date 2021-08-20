package dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ColumnMetadata {
    String name;
    String databaseType;
    String javaType;
    String javaTypePackage;
    boolean isPrimaryKey;
}

package dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ColumnMetadata {
    String name;
    String type;
    String isNullable;
    String isAutoincrement;
}

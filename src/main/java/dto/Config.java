package dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Config {
    String url;
    String username;
    String password;
    String catalogName;
    String schemaName;
    String jdbcDriverPath;
    String driverClassName;
    String hostname;
    String port;
    String serverPath;
}

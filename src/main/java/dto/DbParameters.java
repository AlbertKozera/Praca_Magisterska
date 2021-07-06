package dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DbParameters {
    String driver;
    String url;
    String user;
    String password;
}

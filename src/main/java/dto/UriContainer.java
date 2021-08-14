package dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UriContainer {
    List<String> getServices;
    List<String> postServices;
    List<String> putServices;
    List<String> deleteServices;
}

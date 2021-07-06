package config;

public class Path {
    private Path() {
    }

    public static final String PROJECT = System.getProperty("user.dir");
    public static final String AUTOGENERATED = PROJECT + "\\src\\main\\java\\autogenerated\\";
    public static final String AUTOGENERATED_CONFIG = PROJECT + "\\src\\main\\java\\autogenerated\\config\\";
    public static final String AUTOGENERATED_CONTROLLER = PROJECT + "\\src\\main\\java\\autogenerated\\controller\\";
    public static final String AUTOGENERATED_DTO = PROJECT + "\\src\\main\\java\\autogenerated\\dto\\";
    public static final String AUTOGENERATED_SERVICE = PROJECT + "\\src\\main\\java\\autogenerated\\service\\";
    public static final String AUTOGENERATED_SERVICE_IMPL = PROJECT + "\\src\\main\\java\\autogenerated\\service\\impl\\";
}

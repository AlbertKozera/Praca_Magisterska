package config;

public class Path {
    private Path() {
    }

    public static final String PROJECT = System.getProperty("user.dir");
    public static final String PROJECT_JAVA = System.getProperty("user.dir") + "\\src\\main\\java\\";
    public static final String PROJECT_RESOURCES = System.getProperty("user.dir") + "\\src\\main\\resources\\";
    public static final String AUTOGENERATED = PROJECT_JAVA + "autogenerated\\";
    public static final String AUTOGENERATED_CONFIG = PROJECT_JAVA + "autogenerated\\config\\";
    public static final String AUTOGENERATED_CONTROLLER = PROJECT_JAVA + "autogenerated\\controller\\";
    public static final String AUTOGENERATED_DTO = PROJECT_JAVA + "autogenerated\\dto\\";
    public static final String AUTOGENERATED_SERVICE = PROJECT_JAVA + "autogenerated\\service\\";
    public static final String AUTOGENERATED_SERVICE_IMPL = PROJECT_JAVA + "autogenerated\\service\\impl\\";
}

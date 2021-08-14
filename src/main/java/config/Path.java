package config;

public class Path {
    private Path() {
    }

    public static final String PROJECT = System.getProperty("user.dir");
    public static final String PROJECT_JAVA = PROJECT + "\\src\\main\\java\\";
    public static final String PROJECT_RESOURCES = PROJECT + "\\src\\main\\resources\\";
    public static final String PROJECT_RESOURCES_IMG = PROJECT_RESOURCES + "img\\";
    public static final String PROJECT_RESOURCES_CSS = PROJECT_RESOURCES + "css\\";
    public static final String AUTOGENERATED = PROJECT_JAVA + "autogenerated\\";
    public static final String AUTOGENERATED_CONFIG = PROJECT_JAVA + "autogenerated\\config\\";
    public static final String AUTOGENERATED_CONTROLLER = PROJECT_JAVA + "autogenerated\\controller\\";
    public static final String AUTOGENERATED_DTO = PROJECT_JAVA + "autogenerated\\dto\\";
    public static final String AUTOGENERATED_SERVICE = PROJECT_JAVA + "autogenerated\\service\\";
    public static final String AUTOGENERATED_SERVICE_IMPL = PROJECT_JAVA + "autogenerated\\service\\impl\\";
}

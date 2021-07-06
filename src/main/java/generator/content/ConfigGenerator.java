package generator.content;

import config.Path;
import dto.DbParameters;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class ConfigGenerator {
    public void generateConfig(DbParameters dbP) {
        String dbConContent = "package autogenerated.config;\n" +
                "\n" +
                "import lombok.extern.slf4j.Slf4j;\n" +
                "\n" +
                "import java.sql.Connection;\n" +
                "import java.sql.DriverManager;\n" +
                "\n" +
                "@Slf4j\n" +
                "public class DbCon {\n" +
                "    public static Connection connection() {\n" +
                "        try {\n" +
                "            Class.forName(\"" + dbP.getDriver() + "\");\n" +
                "            return DriverManager.getConnection(\"" + dbP.getUrl() + "\", \"" + dbP.getUser() + "\", \"" + dbP.getPassword() + "\");\n" +
                "        } catch (Exception e) {\n" +
                "            log.error(e.getMessage());\n" +
                "            throw new RuntimeException();\n" +
                "        }\n" +
                "    }\n" +
                "}";
        writeToFile(Path.AUTOGENERATED_CONFIG, "DbCon.java", dbConContent);
    }

    public void writeToFile(String path, String fileName, String content) {
        try (var bufferedWriter = new BufferedWriter(new FileWriter(path + fileName))) {
            bufferedWriter.write(content);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }
}

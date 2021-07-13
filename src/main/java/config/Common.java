package config;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class Common {
    private Common() {
    }

    public static void writeToJavaFile(String packageName, TypeSpec typeSpec) {
        var javaFile = JavaFile.builder(packageName, typeSpec).skipJavaLangImports(true).build();
        try {
            javaFile.writeTo(new File(Path.PROJECT_JAVA));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    public static String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static Class<?> getTypeClassByName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }
}



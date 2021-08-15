package generator.content;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import config.Common;
import config.Package;
import dto.Config;
import lombok.extern.slf4j.Slf4j;

import javax.lang.model.element.Modifier;
import java.sql.Connection;
import java.sql.DriverManager;

@Slf4j
public class ConfigGenerator {
    public ConfigGenerator(Config config) {
        Common.writeToJavaFile(Package.AUTOGENERATED_CONFIG, TypeSpec.classBuilder("DbConfig")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Slf4j.class)
                .addMethod(generateMethodConnection(config)).build());
    }

    private MethodSpec generateMethodConnection(Config config) {
        return MethodSpec.methodBuilder("connection")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(Connection.class)
                .beginControlFlow("try")
                .addStatement("Class.forName($S)", config.getDriverClassName())
                .addStatement("return $T.getConnection($S, $S, $S)", DriverManager.class, config.getUrl(), config.getUsername(), config.getPassword())
                .nextControlFlow("catch ($T e)", Exception.class)
                .addStatement("log.error(e.getMessage())")
                .addStatement("throw new $T()", RuntimeException.class)
                .endControlFlow().build();
    }
}

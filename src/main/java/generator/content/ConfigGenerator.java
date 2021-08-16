package generator.content;

import com.squareup.javapoet.*;
import config.Common;
import config.Package;
import dto.Config;
import lombok.extern.slf4j.Slf4j;

import javax.lang.model.element.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Logger;

import static config.Common.empty;

@Slf4j
public class ConfigGenerator {
    public ConfigGenerator(Config config) {
        Common.writeToJavaFile(Package.AUTOGENERATED_CONFIG, TypeSpec.classBuilder("DbConfig")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Slf4j.class)
                .addMethod(generateMethodConnection(config))
                .addType(genereteNestedClassDriverShim())
                .build());
    }

    private MethodSpec generateMethodConnection(Config config) {
        var method = MethodSpec.methodBuilder("connection")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(Connection.class)
                .beginControlFlow("try")
                .addStatement("var urlClassLoader = new $T(new $T { new $T($S) })", URLClassLoader.class, URL[].class, URL.class, "jar:file:/" + config.getJdbcDriverPath() + "!/")
                .addStatement("$T d = ($T)$T.forName($S, true, urlClassLoader).getDeclaredConstructor().newInstance()", Driver.class, Driver.class, Class.class, config.getDriverClassName())
                .addStatement("$T.registerDriver(new DriverShim(d))", DriverManager.class)
                .addStatement("var connection = $T.getConnection($S, $S, $S)", DriverManager.class, config.getUrl(), config.getUsername(), config.getPassword());
        if(!empty(config.getSchemaName())) {
            method.addStatement("connection.setSchema($S)", config.getSchemaName());
        }
        if(!empty(config.getCatalogName())) {
            method.addStatement("connection.setCatalog($S)", config.getCatalogName());
        }
        return method.addStatement("return connection")
                .nextControlFlow("catch ($T e)", Exception.class)
                .addStatement("log.error(e.getMessage())")
                .addStatement("throw new $T()", RuntimeException.class)
                .endControlFlow().build();
    }

    private TypeSpec genereteNestedClassDriverShim() {
        return TypeSpec.classBuilder("DriverShim")
                .addModifiers(Modifier.STATIC)
                .addSuperinterface(Driver.class)
                .addField(Driver.class, "driver", Modifier.PRIVATE)
                .addMethods(generateMethodsForDriverShim())
                .build();
    }

    private Iterable<MethodSpec> generateMethodsForDriverShim() {
        return Arrays.asList(generateConstructorForDriverShim(),
                generateMethodConnectForDriverShim(),
                generateMethodAcceptsURLForDriverShim(),
                generateMethodGetPropertyInfoForDriverShim(),
                generateMethodGetMajorVersionForDriverShim(),
                generateMethodGetMinorVersionForDriverShim(),
                generateMethodJdbcCompliantForDriverShim(),
                generateMethodGetParentLoggerForDriverShim());
    }

    private MethodSpec generateConstructorForDriverShim() {
        return MethodSpec.constructorBuilder()
                .addParameter(Driver.class, "d")
                .addStatement("this.driver = d").build();
    }

    private MethodSpec generateMethodConnectForDriverShim() {
        return MethodSpec.methodBuilder("connect")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(Connection.class)
                .addParameter(String.class, "url")
                .addParameter(Properties.class, "info")
                .addException(SQLException.class)
                .addStatement("return this.driver.connect(url, info)").build();
    }

    private MethodSpec generateMethodAcceptsURLForDriverShim() {
        return MethodSpec.methodBuilder("acceptsURL")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(boolean.class)
                .addParameter(String.class, "url")
                .addException(SQLException.class)
                .addStatement("return this.driver.acceptsURL(url)").build();
    }

    private MethodSpec generateMethodGetPropertyInfoForDriverShim() {
        return MethodSpec.methodBuilder("getPropertyInfo")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(DriverPropertyInfo[].class)
                .addParameter(String.class, "url")
                .addParameter(Properties.class, "info")
                .addException(SQLException.class)
                .addStatement("return this.driver.getPropertyInfo(url, info)").build();
    }

    private MethodSpec generateMethodGetMajorVersionForDriverShim() {
        return MethodSpec.methodBuilder("getMajorVersion")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(int.class)
                .addStatement("return this.driver.getMajorVersion()").build();
    }

    private MethodSpec generateMethodGetMinorVersionForDriverShim() {
        return MethodSpec.methodBuilder("getMinorVersion")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(int.class)
                .addStatement("return this.driver.getMinorVersion()").build();
    }

    private MethodSpec generateMethodJdbcCompliantForDriverShim() {
        return MethodSpec.methodBuilder("jdbcCompliant")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(boolean.class)
                .addStatement("return this.driver.jdbcCompliant()").build();
    }

    private MethodSpec generateMethodGetParentLoggerForDriverShim() {
        return MethodSpec.methodBuilder("getParentLogger")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(Logger.class)
                .addStatement("return null").build();
    }
}

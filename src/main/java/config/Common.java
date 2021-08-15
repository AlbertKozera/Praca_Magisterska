package config;

import com.squareup.javapoet.*;
import dto.ColumnMetadata;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class Common {
    private Common() {
    }

    public static String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
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

    public static TypeName getType(String type, String table) {
        TypeName typeName = null;
        switch (type) {
            case "List<Dto>":
                typeName = ParameterizedTypeName.get(
                        ClassName.get(List.class),
                        ClassName.get(Package.AUTOGENERATED_DTO, capitalizeFirstLetter(table)));
                break;

            case "Dto":
                typeName = ClassName.get(Package.AUTOGENERATED_DTO, capitalizeFirstLetter(table));
                break;
        }
        return typeName;
    }

    public static Class<?> getClassByName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    public static ColumnMetadata getPrimaryKeyColumn(List<ColumnMetadata> columnMetadataList) {
        return columnMetadataList.stream().filter(ColumnMetadata::isPrimaryKey).findFirst().orElseThrow(RuntimeException::new);
    }

    public static Class<?> getPrimaryKeyColumnClass(List<ColumnMetadata> columnMetadataList) {
        return getClassByName(getPrimaryKeyColumn(columnMetadataList).getJavaTypePackage());
    }

    public static TypeName getPrimaryKeyColumnType(List<ColumnMetadata> columnMetadataList) {
        return TypeName.get(getClassByName(getPrimaryKeyColumn(columnMetadataList).getJavaTypePackage()));
    }

    public static ParameterSpec.Builder getIdParameter(List<ColumnMetadata> columnMetadataList) {
        return ParameterSpec.builder(getPrimaryKeyColumnClass(columnMetadataList), "id");
    }

    public static ParameterSpec getDtoParameter(String table) {
        return ParameterSpec.builder(getType("Dto", table), getDtoObjectName(table)).build();
    }

    public static String getControllerClassName(String table) {
        return capitalizeFirstLetter(table) + "Controller";
    }

    public static String getServiceClassName(String table) {
        return capitalizeFirstLetter(table) + "Service";
    }

    public static String getServiceObjectName(String table) {
        return table.toLowerCase() + "Service";
    }

    public static String getDtoObjectName(String table) {
        return table.toLowerCase();
    }

    public static String getDtoClassName(String table) {
        return capitalizeFirstLetter(table);
    }
}



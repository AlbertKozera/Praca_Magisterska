package config;

import java.sql.Types;

public class JdbcClassConverter {

    public static Class<?> toJavaType(int type) {
        Class<?> result = Object.class;
        switch (type) {
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
                result = String.class;
                break;

            case Types.NUMERIC:
            case Types.DECIMAL:
                result = java.math.BigDecimal.class;
                break;

            case Types.BIT:
                result = Boolean.class;
                break;

            case Types.TINYINT:
                result = Byte.class;
                break;

            case Types.SMALLINT:
                result = Short.class;
                break;

            case Types.INTEGER:
                result = Integer.class;
                break;

            case Types.BIGINT:
                result = Long.class;
                break;

            case Types.REAL:
                result = Float.class;
                break;

            case Types.FLOAT:
            case Types.DOUBLE:
                result = Double.class;
                break;

            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
                result = Byte[].class;
                break;

            case Types.DATE:
                result = java.sql.Date.class;
                break;

            case Types.TIME:
                result = java.sql.Time.class;
                break;

            case Types.TIMESTAMP:
                result = java.sql.Timestamp.class;
                break;

            case Types.CLOB:
                result = java.sql.Clob.class;
                break;

            case Types.BLOB:
                result = java.sql.Blob.class;
                break;

            case Types.ARRAY:
                result = java.sql.Array.class;
                break;

            case Types.STRUCT:
                result = java.sql.Struct.class;
                break;

            case Types.REF:
                result = java.sql.Ref.class;
                break;
        }
        return result;
    }

    public static String getName(String code) {
        String name = toJavaType(Integer.parseInt(code)).getName();
        name = name.substring(name.lastIndexOf(".") + 1);
        return name;
    }

    public static String getPackageName(String code) {
        return toJavaType(Integer.parseInt(code)).getName();
    }

}

package aaron.common.utils;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 用于判断异常类型
 * @author xiaoyouming
 * @version 1.0
 * @since 2020-04-18
 */
public class ExceptionUtils {
    public static final String FK = "23000";
    public static boolean isForeignKeyViolation(Exception e){
        if (e.getCause() instanceof SQLIntegrityConstraintViolationException){
            SQLIntegrityConstraintViolationException sqlException = (SQLIntegrityConstraintViolationException) e.getCause();
            if (FK.equals(sqlException.getSQLState())){
                return true;
            }
        }
        return false;
    }
}

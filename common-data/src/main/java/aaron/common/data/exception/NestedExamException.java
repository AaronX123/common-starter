package aaron.common.data.exception;

/**
 * @author xiaoyouming
 * @version 1.0
 * @since 2020-03-03
 */
public class NestedExamException extends RuntimeException{
    private String errorCode;

    public NestedExamException(String errorMessage, String errorCode){
        super(errorCode + " : " + errorMessage);
        this.errorCode = errorCode;
    }

}

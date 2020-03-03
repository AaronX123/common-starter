package aaron.common.data.exception;

/**
 * @author xiaoyouming
 * @version 1.0
 * @since 2020-03-03
 */
public class StarterException extends NestedExamException {

    public StarterException(String errorMessage, String errorCode) {
        super(errorMessage, errorCode);
    }

    public StarterException(StarterError error){
        super(error.getMsg(),error.getMsg());
    }

    public StarterException(StarterError error, Object ... o){
        super(String.format(error.getMsg(),o),error.getCode());
    }
}

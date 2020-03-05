package aaron.common.data.common;

import aaron.common.data.exception.NestedExamException;
import aaron.common.data.exception.StarterError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author xiaoyouming
 * @version 1.0
 * @since 2020-03-05
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class CommonExceptionHandler {
    @Autowired
    CommonState state;
    /**
     * 处理系统内异常
     * @param e
     * @return
     */
    @ExceptionHandler(NestedExamException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<String> examException(NestedExamException e){
        log.error("业务异常：{}",e.getMessage());
        log.error("异常码：{}",e.getErrorCode());
        return new CommonResponse<String>(state.getVersion(),e.getErrorCode(),e.getMessage(),state.FAIL);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<String> otherException(Throwable t){
        log.error("业务异常：{}",StarterError.UNKNOWN_ERROR.getMsg());
        log.error("异常码：{}",StarterError.UNKNOWN_ERROR.getCode());
        return new CommonResponse<String>(state.getVersion(),StarterError.UNKNOWN_ERROR.getCode(),StarterError.UNKNOWN_ERROR.getMsg(),state.FAIL);
    }
}

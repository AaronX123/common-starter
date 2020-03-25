package aaron.common.data.common;

import aaron.common.data.exception.NestedExamException;
import aaron.common.data.exception.StarterError;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
        log.error("业务异常：{}",StarterError.SYSTEM_UNKNOWN_ERROR.getMsg());
        log.error("真实异常：",t);
        log.error("异常码：{}",StarterError.SYSTEM_UNKNOWN_ERROR.getCode());
        return new CommonResponse<String>(state.getVersion(),StarterError.SYSTEM_UNKNOWN_ERROR.getCode(),StarterError.SYSTEM_UNKNOWN_ERROR.getMsg(),state.FAIL);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public CommonResponse handleBindException(MethodArgumentNotValidException ex){
        log.error("参数校验异常:",ex);
        FieldError fieldError = ex.getBindingResult().getFieldError();
        CommonResponse commonError = new CommonResponse();
        commonError.setCode(StarterError.SYSTEM_PARAMETER_VALUE_INVALID.getCode());
        commonError.setMsg(String.format(StarterError.SYSTEM_PARAMETER_VALUE_INVALID.getMsg(),fieldError.getField(),fieldError.getDefaultMessage()));
        return commonError;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public CommonResponse handleBindException(MissingServletRequestParameterException ex){
        log.error("必填校验异常:",ex);
        CommonResponse commonError = new CommonResponse();
        commonError.setCode(StarterError.SYSTEM_PARAMETER_VALUE_INVALID.getCode());
        commonError.setMsg(String.format(StarterError.SYSTEM_PARAMETER_VALUE_INVALID.getMsg(),ex.getParameterName(),ex.getMessage()));
        return commonError;
    }

    @ExceptionHandler(HystrixBadRequestException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public CommonResponse handleHystrixBadRequestException(HystrixBadRequestException ex) {
        log.error("微服务调用异常:",ex);
        CommonResponse commonError = new CommonResponse();
        String[] errors = ex.getMessage().split(":");
        commonError.setCode(errors[0]);
        commonError.setMsg(ex.getMessage().substring(errors[0].length()+1));
        return commonError;
    }
}

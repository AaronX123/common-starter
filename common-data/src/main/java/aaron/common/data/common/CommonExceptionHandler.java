package aaron.common.data.common;

import aaron.common.data.exception.NestedExamException;
import aaron.common.data.exception.StarterError;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

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
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<String> examException(NestedExamException e){
        log.error("业务异常：{}",e.getMessage());
        log.error("异常码：{}",e.getErrorCode());
        return new CommonResponse<String>(state.getVersion(),generateTraceCode(e.getErrorCode()),e.getMessage(),state.FAIL);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CommonResponse handleBindException(MethodArgumentNotValidException ex){
        log.error("参数校验异常:",ex);
        FieldError fieldError = ex.getBindingResult().getFieldError();
        CommonResponse commonError = new CommonResponse();
        commonError.setCode(generateTraceCode(StarterError.SYSTEM_UNKNOWN_ERROR.getCode()));
        commonError.setMsg(String.format(StarterError.SYSTEM_PARAMETER_VALUE_INVALID.getMsg(),fieldError.getField(),fieldError.getDefaultMessage()));
        return commonError;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CommonResponse handleBindException(MissingServletRequestParameterException ex){
        log.error("必填校验异常:",ex);
        CommonResponse commonError = new CommonResponse();
        commonError.setCode(generateTraceCode(StarterError.SYSTEM_PARAMETER_VALUE_INVALID.getCode()));
        commonError.setMsg(String.format(StarterError.SYSTEM_PARAMETER_VALUE_INVALID.getMsg(),ex.getParameterName(),ex.getMessage()));
        return commonError;
    }

    @ExceptionHandler(HystrixBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CommonResponse handleHystrixBadRequestException(HystrixBadRequestException ex) {
        log.error("微服务调用异常:",ex);
        CommonResponse commonError = new CommonResponse();
        String[] errors = ex.getMessage().split(":");
        commonError.setCode(generateTraceCode(errors[0]));
        commonError.setMsg(ex.getMessage().substring(errors[0].length()+1));
        return commonError;
    }

    @ExceptionHandler(RedisConnectionFailureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CommonResponse redisException(RedisConnectionFailureException e){
        log.error("Redis异常：",e);
        CommonResponse error = new CommonResponse();
        error.setCode(generateTraceCode(StarterError.SYSTEM_REDIS_CONNECT_FAILURE.getCode()));
        error.setMsg(StarterError.SYSTEM_REDIS_CONNECT_FAILURE.getMsg());
        return error;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse otherException(Exception t){
        if (t instanceof RedisConnectionFailureException){
            return redisException((RedisConnectionFailureException) t);
        }else if (t instanceof HystrixBadRequestException){
            return handleHystrixBadRequestException((HystrixBadRequestException) t);
        }else if (t instanceof MissingServletRequestParameterException){
            return handleBindException((MissingServletRequestParameterException) t);
        }else if (t instanceof MethodArgumentNotValidException){
            return handleBindException((MethodArgumentNotValidException) t);
        }else {
            log.error("业务异常：{}",StarterError.SYSTEM_UNKNOWN_ERROR.getMsg());
            log.error("真实异常：",t);
            log.error("异常码：{}",StarterError.SYSTEM_UNKNOWN_ERROR.getCode());
            return new CommonResponse<String>(state.getVersion(),generateTraceCode(StarterError.SYSTEM_UNKNOWN_ERROR.getCode()),StarterError.SYSTEM_UNKNOWN_ERROR.getMsg(),state.FAIL);

        }
    }

    private String generateTraceCode(String errorCode){
        String res = errorCode + ":" + get32UUID().substring(0,8);
        log.error("跟踪码为:{}，业务异常码为:{}",res,errorCode);
        return res;
    }

    private static String get32UUID() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new UUID(random.nextLong(), random.nextLong()).toString().replace("-", "");
    }
}

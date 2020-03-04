package aaron.common.logging;

import aaron.common.data.common.CommonRequest;
import aaron.common.data.common.CommonResponse;
import aaron.common.data.exception.StarterError;
import aaron.common.data.exception.StarterException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoyouming
 * @version 1.0
 * @since 2020-03-03
 */
@Slf4j
@ConfigurationProperties(prefix = "aaron")
@Aspect
@Configuration
public class LoggingAspect {

    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Pointcut("@annotation(aaron.common.logging.annotation.MethodEnhancer)")
    public void joinPoint(){}

    @Before(value = "aaron.common.logging.LoggingAspect.joinPoint()")
    public void logRequest(JoinPoint joinPoint){
        Object[] arg = joinPoint.getArgs();
        log.info("\n\n\n请求的方法为:{}\n",joinPoint.getSignature().getName());
        if (arg.length != 0){
            for (Object o : arg) {
                log.info("\n请求参数为: {}\n\n",o.toString());
                if (o instanceof CommonRequest){
                    // 版本不匹配
                    if (version != null && !version.equals(((CommonRequest) o).getVersion())){
                        throw new StarterException(StarterError.VERSION_NOT_MATCH);
                    }
                }
            }
        }else {
            throw new StarterException(StarterError.PARAMETER_IS_NULL);
        }
    }

    @AfterReturning(value = "aaron.common.logging.LoggingAspect.joinPoint()",returning = "o")
    public void logResponse(JoinPoint joinPoint, Object o){
        log.info("\n\n{}方法响应为: {}\n\n",joinPoint.getSignature().getName(),o.toString());
        if (o instanceof CommonResponse){
            ((CommonResponse) o).setVersion(version);
        }
    }
}

package aaron.common.aop;

import aaron.common.aop.annotation.FullCommonField;
import aaron.common.aop.annotation.FullCommonFieldU;
import aaron.common.aop.enums.EnumOperation;
import aaron.common.data.common.BaseDto;
import aaron.common.data.common.CommonField;
import aaron.common.data.exception.NestedExamException;
import aaron.common.data.exception.StarterError;
import aaron.common.data.exception.StarterException;
import aaron.common.utils.CommonUtils;
import aaron.common.utils.SnowFlake;
import aaron.common.utils.TokenUtils;
import aaron.common.utils.jwt.JwtUtil;
import aaron.common.utils.jwt.UserPermission;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author xiaoyouming
 * @version 1.0
 * @since 2020-03-04
 */
@Slf4j
@Component
@Aspect
public class FullCommonFieldAspect {
    @Autowired
    SnowFlake snowFlake;

    @Pointcut("@annotation(annotation.FullCommonFieldU)")
    public void pointCutU(){}

    @Pointcut("@annotation(annotation.FullCommonField)")
    public void pointCut(){}

    @Around(value = "pointCut()")
    public Object full(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("填充公共字段");
        Object[] params = joinPoint.getArgs();
        if (CommonUtils.isEmpty(params)){
            throw new StarterException(StarterError.PARAMETER_IS_NULL);
        }
        Object param = params[0];
        if (param instanceof BaseDto){
            BaseDto dto = (BaseDto) param;
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            FullCommonField fullCommonField = signature.getMethod().getAnnotation(FullCommonField.class);
            EnumOperation op = fullCommonField.operation();
            String token = TokenUtils.getToken();
            UserPermission permission = new UserPermission();
            try {
                permission = JwtUtil.parseJwt(token);
            } catch (Exception e) {
                if (e instanceof NestedExamException){
                    throw (NestedExamException)e;
                }
                throw new StarterException(StarterError.TOKEN_PARSE_ERROR);
            }
            Long orgId = permission.getOrgId();
            Long companyId = permission.getCompanyId();
            Long operator = permission.getId();
            if (!CommonUtils.notNull(orgId,companyId,operator)){
                throw new StarterException(StarterError.REQUIRED_PARAM_MISSING);
            }
            if (EnumOperation.INSERT.equals(op)){
                dto.setId(snowFlake.nextId());
                dto.setCompanyId(companyId);
                dto.setOrgId(orgId);
                dto.setCreatedBy(operator);
                dto.setCreatedTime(new Date());
                dto.setUpdatedBy(operator);
                dto.setUpdatedTime(dto.getCreatedTime());
                dto.setVersion(dto.getCreatedTime().getTime());
            }else {
                dto.setUpdatedBy(operator);
                dto.setUpdatedTime(new Date());
                dto.setVersion(dto.getUpdatedTime().getTime());
            }
            return joinPoint.proceed(params);
        }
        throw new StarterException(StarterError.PARAMETER_TYPE_NOT_MATCH);
    }

    @Around(value = "pointCutU()")
    public Object fullU(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("填充公共字段");
        Object[] params = joinPoint.getArgs();
        if (CommonUtils.isEmpty(params)){
            throw new StarterException(StarterError.PARAMETER_IS_NULL);
        }
        Object param = params[0];
        if (param instanceof BaseDto){
            BaseDto dto = (BaseDto) param;
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            FullCommonFieldU fullCommonField = signature.getMethod().getAnnotation(FullCommonFieldU.class);
            EnumOperation op = fullCommonField.operation();
            String token = TokenUtils.getToken();
            UserPermission permission = new UserPermission();
            try {
                permission = JwtUtil.parseJwt(token);
            } catch (Exception e) {
                if (e instanceof NestedExamException){
                    throw (NestedExamException)e;
                }
                throw new StarterException(StarterError.TOKEN_PARSE_ERROR);
            }
            Long operator = permission.getId();
            if (!CommonUtils.notNull(operator)){
                throw new StarterException(StarterError.REQUIRED_PARAM_MISSING);
            }
            if (EnumOperation.INSERT.equals(op)){
                dto.setId(snowFlake.nextId());
                dto.setCreatedBy(operator);
                dto.setCreatedTime(new Date());
                dto.setUpdatedBy(operator);
                dto.setUpdatedTime(dto.getCreatedTime());
                dto.setVersion(dto.getCreatedTime().getTime());
            }else {
                dto.setUpdatedBy(operator);
                dto.setUpdatedTime(new Date());
                dto.setVersion(dto.getUpdatedTime().getTime());
            }
            return joinPoint.proceed(params);
        }
        throw new StarterException(StarterError.PARAMETER_TYPE_NOT_MATCH);
    }
}

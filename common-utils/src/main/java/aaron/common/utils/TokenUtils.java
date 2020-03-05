package aaron.common.utils;

import aaron.common.data.exception.StarterError;
import aaron.common.data.exception.StarterException;
import aaron.common.utils.jwt.JwtUtil;
import aaron.common.utils.jwt.UserPermission;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiaoyouming
 * @version 1.0
 * @since 2020-03-04
 */
public class TokenUtils {
    public static String getToken(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            return request.getHeader("X-Token");
        }
        throw new StarterException(StarterError.SYSTEM_TOKEN_IS_NULL);
    }

    public static UserPermission getUser(){
        String token = getToken();
        try {
            return JwtUtil.parseJwt(token);
        } catch (Exception e) {
            throw new StarterException(StarterError.SYSTEM_TOKEN_PARSE_ERROR);
        }
    }
}

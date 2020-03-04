package aaron.common.aop.annotation;

import aaron.common.aop.enums.EnumOperation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiaoyouming
 * @version 1.0
 * @since 2020-03-04
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FullCommonFieldU {
    EnumOperation operation() default EnumOperation.INSERT;
}

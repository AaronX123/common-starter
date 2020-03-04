package aaron.common.utils;

import aaron.common.data.exception.StarterError;
import aaron.common.data.exception.StarterException;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author xiaoyouming
 * @version 1.0
 * @since 2020-03-03
 */
public class CommonUtils {
    public static <T> T copyProperties(Class<T> targetClass, Object src){
        try {
            T res = targetClass.newInstance();
            BeanUtils.copyProperties(src,res);
            return res;
        } catch (ReflectiveOperationException e) {
            // do nothing
        }
        throw new StarterException(StarterError.COPY_PROPERTIES_ERROR);
    }

    public static <T> List<T> convertList(Class<T> targetClass, List<?> src){
        if (isEmpty(src) || isEmpty(targetClass)){
            throw new StarterException(StarterError.PARAMETER_IS_NULL);
        }
        List<T> res = new ArrayList<>();
        for (Object o : src) {
            try {
                T t = targetClass.newInstance();
                BeanUtils.copyProperties(o,t);
                res.add(t);
            } catch (Exception e) {
                throw new StarterException(StarterError.COPY_PROPERTIES_ERROR);
            }
        }
        return res;
    }
    public static boolean isEmpty(Collection collection){
        return collection == null || collection.size() == 0;
    }

    public static boolean isEmpty(Object[] array){
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(Object o){ return  o == null;}
    public static boolean notNull(Object ... o){
        if (o == null){return false;}
        for (Object o1 : o) {
            if (o1 == null){
                return false;
            }
        }
        return true;
    }

}

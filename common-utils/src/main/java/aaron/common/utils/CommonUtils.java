package aaron.common.utils;

import aaron.common.data.exception.StarterError;
import aaron.common.data.exception.StarterException;
import org.springframework.beans.BeanUtils;

import java.util.Collection;

/**
 * @author xiaoyouming
 * @version 1.0
 * @since 2020-03-03
 */
public class CommonUtils {
    public <T> T copyProperties(Class<T> targetClass, Object src){
        try {
            T res = targetClass.newInstance();
            BeanUtils.copyProperties(src,res);
            return res;
        } catch (ReflectiveOperationException e) {
            // do nothing
        }
        throw new StarterException(StarterError.COPY_PROPERTIES_ERROR);
    }

    public boolean isEmpty(Collection collection){
        return collection == null || collection.size() == 0;
    }

    public boolean isEmpty(Object[] array){
        return array == null || array.length == 0;
    }

    public boolean notNull(Object ... o){
        if (o == null){return false;}
        for (Object o1 : o) {
            if (o1 == null){
                return false;
            }
        }
        return true;
    }

}

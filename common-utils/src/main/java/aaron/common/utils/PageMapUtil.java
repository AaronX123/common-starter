package aaron.common.utils;



import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoyouming
 */
public class PageMapUtil {
    public static <T> Map<String, Object> getPageMap(List<T> list, Page page){
        PageInfo<T> pageInfo = new PageInfo<>(list);
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", page.getTotal());
        pageMap.put("pageInfo", pageInfo);
        return pageMap;
    }

}

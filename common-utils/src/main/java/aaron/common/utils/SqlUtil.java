package aaron.common.utils;

import java.util.regex.Pattern;

/**
 * @author xiaoyouming
 * @version 1.0
 * @since 2020-04-20
 */
public class SqlUtil {
    /**
     * 全匹配 1000W次耗时1.7s
     * @param pattern
     * @param data
     * @return
     */
    public static boolean like(String pattern, String data){
        return Pattern.matches(pattern + ".*",data);
    }

    public static boolean likeIgnoreCase(String pattern, String data){
        return Pattern.matches(pattern.toLowerCase()+ ".*",data);
    }

    public static void main(String[] args) {
        long t = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            like("王","鬼鬼王");
        }
        System.out.println(System.currentTimeMillis() - t);
    }
}

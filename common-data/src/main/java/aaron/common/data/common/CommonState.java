package aaron.common.data.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author xiaoyouming
 * @version 1.0
 * @since 2020-03-03
 */
@Component
public class CommonState {
    public static final String SUCCESS = "000";
    public static final String FAIL = "100";
    public static final String SUCCESS_MSG = "请求成功";
    public static final String FAIL_MSG = "请求失败";

    public String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

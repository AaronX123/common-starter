package aaron.common.data.exception;

/**
 * @author xiaoyouming
 * @version 1.0
 * @since 2020-03-03
 */
public enum  StarterError {
    SYSTEM_VERSION_NOT_MATCH("000001","版本不一致拒绝访问"),
    SYSTEM_PARAMETER_IS_NULL("000002","请求参数为空"),
    SYSTEM_COPY_PROPERTIES_ERROR("000003","属性拷贝异常"),
    SYSTEM_PARAMETER_TYPE_NOT_MATCH("000004","参数类型不一致，无法填充"),
    SYSTEM_TOKEN_EXPIRED("000005","Token过期，请重新获取"),
    SYSTEM_TOKEN_IS_NULL("000006","Token为空"),
    SYSTEM_TOKEN_PARSE_ERROR("000007","Token解析异常"),
    SYSTEM_REQUIRED_PARAM_MISSING("000009","缺失必要参数"),
    SYSTEM_UNKNOWN_ERROR("000010","未知异常"),
    SYSTEM_ACCESS_INVALID("000011","非法访问"),
    SYSTEM_REDIS_CACHE_SET_FAILURE("000012","redis缓存放置失败"),
    SYSTEM_REDIS_CONNECT_FAILURE("000013","redis连接失败"),
    SYSTEM_PARAMETER_VALUE_INVALID("000014","参数值非法[%s]%s"),
    SYSTEM_RPC_ERROR("000015","RPC调用错误"),
    SYSTEM_SERVICE_UNAVAILABLE("000016","服务暂停"),
    SYSTEM_TOKEN_REJECTED("000017","此Token无此访问权限"),
    SYSTEM_USED_TOKEN("000018","Token已经被使用"),
    ;
    private String msg;
    private String code;

    StarterError(String code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

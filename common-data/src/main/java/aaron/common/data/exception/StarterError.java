package aaron.common.data.exception;

/**
 * @author xiaoyouming
 * @version 1.0
 * @since 2020-03-03
 */
public enum  StarterError {
    VERSION_NO_MATCH("000001","版本不一致拒绝访问"),
    PARAMETER_IS_NULL("000002","请求参数为空"),
    COPY_PROPERTIES_ERROR("000003","属性拷贝异常"),
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

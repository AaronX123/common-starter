package aaron.common.data.exception;

/**
 * @author xiaoyouming
 * @version 1.0
 * @since 2020-03-03
 */
public enum  StarterError {
    VERSION_NOT_MATCH("000001","版本不一致拒绝访问"),
    PARAMETER_IS_NULL("000002","请求参数为空"),
    COPY_PROPERTIES_ERROR("000003","属性拷贝异常"),
    PARAMETER_TYPE_NOT_MATCH("000004","参数类型不一致，无法填充"),
    TOKEN_EXPIRED("000005","Token过期，请重新获取"),
    TOKEN_IS_NULL("000006","Token为空"),
    TOKEN_PARSE_ERROR("000007","Token解析异常"),
    REQUIRED_PARAM_MISSING("000009","缺失必要参数"),
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

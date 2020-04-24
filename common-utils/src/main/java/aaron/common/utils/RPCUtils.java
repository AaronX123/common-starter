package aaron.common.utils;

import aaron.common.data.common.CommonResponse;
import aaron.common.data.exception.StarterError;
import aaron.common.data.exception.StarterException;
import com.alibaba.fastjson.JSON;

/**
 * @author xiaoyouming
 * @version 1.0
 * @since 2020-04-23
 */
public class RPCUtils {
    public static final int BASEINFO = 1;
    public static final int USER = 2;
    public static final int PAPER = 3;
    public static final int EXAM = 4;
    public static final int AUTH = 5;

    public static boolean isSuccess(CommonResponse response){
        return "200".equals(response.getCode());
    }

    public static <T> T parseResponse(CommonResponse response, Class<T> resultType, int RPC_TYPE){
        if (isSuccess(response)){
            return JSON.parseObject(JSON.toJSONString(response.getData()),resultType);
        }
        switch (RPC_TYPE) {
            case BASEINFO:
                throw new StarterException(StarterError.SYSTEM_API_ERROR_TYPE, "基础信息服务", response.getMsg());
            case USER:
                throw new StarterException(StarterError.SYSTEM_API_ERROR_TYPE, "用户服务", response.getMsg());
            case PAPER:
                throw new StarterException(StarterError.SYSTEM_API_ERROR_TYPE,"试卷服务",response.getMsg());
            case EXAM:
                throw new StarterException(StarterError.SYSTEM_API_ERROR_TYPE,"考试服务",response.getMsg());
            case AUTH:
                throw new StarterException(StarterError.SYSTEM_API_ERROR_TYPE,"认证服务",response.getMsg());
            default:
                throw new StarterException(StarterError.SYSTEM_API_ERROR,response.getMsg());
        }
    }
}

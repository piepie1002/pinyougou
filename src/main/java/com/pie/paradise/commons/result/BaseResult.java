package com.pie.paradise.commons.result;
import lombok.Data;

/**
 * @author LIN
 * @since JDK 1.8
 *  基础返回结果封装类
 *  
 */
@Data
public class BaseResult<T> {
    /**
     * 返回码
     */
    private int retCode;
    /**
     * 返回说明
     */
    private String retMsg;

    /**
     * 提示
     */
    private String tips;

    /**
     * 返回数据
     */
    private T data;

    public BaseResult() {
    }

    public BaseResult(T data ){
        this();
        this.data=data;
    }
    public BaseResult(ResultCodeEnum code ,T data){
        this.retCode=code.retCode;
        this.retMsg = code.retMsg;
        this.tips = code.tips;
        this.data= data;
    }
    public BaseResult(ResultCodeEnum code) {
        this.retCode = code.retCode;
        this.retMsg = code.retMsg;
        this.tips = code.tips;
    }
    public static <T> BaseResult<T> success(T data) {
        return success(ResultCodeEnum.SUCCESS, data);
    }

    /**
     * @param codeEnum
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResult<T> success(ResultCodeEnum codeEnum, T data) {
        return new BaseResult<>(codeEnum, data);
    }

    public static <T> BaseResult<T> error() {
        return new BaseResult<T>(ResultCodeEnum.ERROR);
    }


    public static <T> BaseResult<T> error(ResultCodeEnum codeEnum) {
        return error(codeEnum,null);
    }

    public static <T> BaseResult<T> error(ResultCodeEnum codeEnum, T data) {
        return new BaseResult<>(codeEnum, data);
    }

    public static <T> BaseResult<T> error(String traceId, int retCode, String retMsg) {
        BaseResult<T> baseResult = new BaseResult<>();
        baseResult.setRetCode(retCode);
        baseResult.setRetMsg(retMsg);
        return baseResult;
    }
}

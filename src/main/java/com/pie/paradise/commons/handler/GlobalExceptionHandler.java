package com.pie.paradise.commons.handler;

import com.pie.paradise.commons.exception.ServiceException;
import com.pie.paradise.commons.result.BaseResult;
import com.pie.paradise.commons.result.ResultCodeEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author LIN
 * @since JDK 1.8
 * 
 *全局异常类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public BaseResult<Object>handler(Exception exception){
        if (exception instanceof ServiceException){
            return BaseResult.error(ResultCodeEnum.SYSTEM_UNKNOWN_ERROR);
        }
        return BaseResult.error();
    }
}

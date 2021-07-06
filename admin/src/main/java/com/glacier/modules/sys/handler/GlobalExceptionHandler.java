package com.glacier.modules.sys.handler;

import com.glacier.common.core.entity.Result;
import com.glacier.common.core.factory.ResultBuildFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理
 *
 * @author glacier
 * @version 1.0
 * date 2020-05-03 09:54
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
     * 处理 认证异常
     *
     * @param baseException 异常
     * @return 异常响应
     */
    @ExceptionHandler(value = Exception.class)
    public Result<String> handler(Exception baseException) {
        log.error("异常：", baseException);
        return ResultBuildFactory.error(baseException.getMessage());
    }
}

package com.enjoyor.bigdata.EnloopUtilXMLService.aspect;


import com.enjoyor.bigdata.EnloopUtilXMLService.exception.ConvertException;
import com.enjoyor.bigdata.EnloopUtilXMLService.exception.Dom4jException;
import com.enjoyor.bigdata.EnloopUtilXMLService.exception.ParamException;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.common.ResponseResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/23 9:53
 * @email aaron19940628@gmail.com
 * @date 2017/11/23 9:53.
 * @description 这个切点用于统一返回参数格式
 */
@Aspect
@Component
public class ExceptionAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAspect.class);

    @Pointcut("execution(public * com.enjoyor.bigdata.EnloopUtilXMLService.controller.*.*(..))")
    public void throwExceptionMethod() {
    }

    private ResponseResult<?> handlerException(String url,ProceedingJoinPoint proceedingJoinPoint, Throwable e) {
        ResponseResult<?> result = new ResponseResult();
        result.setUrl(url);
        // 已知异常
        if (e instanceof ParamException) {
            result.setStatusCode(((ParamException) e).getStatus().value());
            result.setMessage(e.getMessage());
        } else if (e instanceof ConvertException) {
            result.setStatusCode(((ConvertException) e).getStatus().value());
            result.setMessage(e.getMessage());
        } else if (e instanceof Dom4jException) {
            result.setStatusCode(((Dom4jException) e).getStatus().value());
            result.setMessage(e.getMessage());
        } else {
            LOGGER.error(proceedingJoinPoint.getSignature() + " error ", e);
            //TODO 未知的异常，应该格外注意，可以发送邮件通知等
            result.setMessage(e.getMessage());
            result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return result;
    }

    @Around("throwExceptionMethod()")
    public Object handlerControllerMethod(ProceedingJoinPoint proceedingJoinPoint) {
        long startTime = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURI().toString();
        ResponseResult<?> result = null;
        try {

            result = (ResponseResult<?>) proceedingJoinPoint.proceed();
            result.setUrl(url);
            LOGGER.info(proceedingJoinPoint.getSignature() + " EXECUTE TIME : " + (System.currentTimeMillis() - startTime) + " ms;");
        } catch (Throwable e) {
            LOGGER.error(" CATCH EXCEPTION : " + e.toString());
            result = handlerException(url,proceedingJoinPoint, e);
        }
        return result;
    }

}

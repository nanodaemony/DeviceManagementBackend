package com.nano.msc.common.exceptions;



import com.nano.msc.common.vo.CommonResult;
import com.nano.msc.system.log.service.SystemLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理
 * Created by macro on 2020/2/27.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private SystemLogService logger;

    /**
     * 处理全局异常
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public CommonResult handle(Exception e) {
        // 记录到异常系统日志中
        logger.error(e.getMessage());
        e.printStackTrace();
        return CommonResult.failed("哦豁，发生了异常:" + Arrays.toString(e.getStackTrace()));
    }

    /**
     * 处理自定义异常
     */
    @ResponseBody
    @ExceptionHandler(value = CommonException.class)
    public CommonResult handle(CommonException e) {
        logger.error(e.getMessage());
        return CommonResult.failed("ErrorCode: " + e.getErrorCode() + ", msg: " + e.getMsg());
    }

    /**
     * 用来处理bean validation异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public CommonResult resolveConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ConstraintViolation constraintViolation : constraintViolations) {
                msgBuilder.append(constraintViolation.getMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            return CommonResult.failed(errorMessage);
        }
        return CommonResult.failed(ex.getMessage());
    }

    /**
     * 用来处理在方法上的参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public CommonResult resolveMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        if (!CollectionUtils.isEmpty(objectErrors)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ObjectError objectError : objectErrors) {
                msgBuilder.append(objectError.getDefaultMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            return CommonResult.failed(errorMessage);
        }
        return CommonResult.failed(ex.getMessage());
    }


}

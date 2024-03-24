package com.lyl.yph.common.exception;

import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice //Controller增强器
public class GlobalExceptionHandler {

    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR);
    }

    //自定义异常处理
    @ExceptionHandler(GuiguException.class)
    @ResponseBody
    public Result error(GuiguException e) {
        return Result.build(null, e.getResultCodeEnum());
    }

}

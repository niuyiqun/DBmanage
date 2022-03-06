package com.example.dbmanege.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author niu
 * @Description:
 * @date 2021/5/5 14:34
 */


@Data
public class Result {
    private Boolean success;
    private Integer code;
    private String message;
    private Map<String,Object> data = new HashMap<String ,Object>();

    private Result(){}

    public static Result success(){ //默认的成功返回值
        Result r = new Result();
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setSuccess(ResultCodeEnum.SUCCESS.getStatus());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    public static Result error(){     //未知错误返回值
        Result r = new Result();
        r.setCode(ResultCodeEnum.UNKNOW_REASON.getCode());
        r.setSuccess(ResultCodeEnum.UNKNOW_REASON.getStatus());
        r.setMessage(ResultCodeEnum.UNKNOW_REASON.getMessage());
        return r;
    }

    public static Result success(ResultCodeEnum codeEnum){    //传入一个枚举值的成功返回值
        Result r = new Result();
        r.setCode(codeEnum.getCode());
        r.setSuccess(codeEnum.getStatus());
        r.setMessage(codeEnum.getMessage());
        return r;
    }

    public static Result error(ResultCodeEnum codeEnum){    //传入一个枚举值的失败返回值
        Result r = new Result();
        r.setCode(codeEnum.getCode());
        r.setSuccess(codeEnum.getStatus());
        r.setMessage(codeEnum.getMessage());
        return r;
    }

    public Result data(Map<String,Object> map){     //给返回值传入data信息，参数为map
        this.setData(map);
        return this;
    }


    public Result data(String key,Object value){    //给返回值传入data信息，参数为键值
        this.data.put(key,value);
        return this;
    }

    public Result message(String message){       //动态设置message
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }
}

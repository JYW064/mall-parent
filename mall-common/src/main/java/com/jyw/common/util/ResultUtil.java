package com.jyw.common.util;

import com.jyw.common.vo.ResultVO;

/**
 * @program: mall-parent
 * @description:
 * @author: ji yi wei
 * @create: 2022-04-12
 */
public class ResultUtil<T> {
    private ResultVO<T> result;

    public ResultUtil(){
        result=new ResultVO<>();
        result.setMsg("success");
        result.setCode("200");
    }

    public ResultVO<T> setData(T t){
        this.result.setResult(t);
        this.result.setCode("200");
        return this.result;
    }

    public ResultVO<T> setData(T t, String msg){
        this.result.setResult(t);
        this.result.setCode("200");
        this.result.setMsg(msg);
        return this.result;
    }

    public ResultVO<T> setErrorMsg(String msg){
        this.result.setMsg(msg);
        this.result.setCode("500");
        return this.result;
    }

    public ResultVO<T> setErrorMsg(String  code, String msg){
        this.result.setMsg(msg);
        this.result.setCode(code);
        return this.result;
    }
}

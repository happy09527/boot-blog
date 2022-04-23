package com.example.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hap
 * @date 2022/4/16 19:09
 * @describe  返回结果集，对结果按开发文档规范进行封装
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private boolean success;
    private int code;
    private String msg;
    private Object data;

    public static Result success(Object data) {
        return new Result(true, 200, "success", data);
    }

    public static Result fail(int code, String msg) {
        return new Result(false, code, msg, null);
    }

}

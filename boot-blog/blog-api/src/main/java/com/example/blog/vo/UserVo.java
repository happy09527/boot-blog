package com.example.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author hap
 * @date 2022/4/18 11:32
 * @describe
 */
@Data
public class UserVo {

    private String nickname;

    private String avatar;
//    @JsonSerialize(using = ToStringSerializer.class)
    private String id;
}
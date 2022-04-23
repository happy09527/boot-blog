package com.example.blog.vo;

/**
 * @author hap
 * @date 2022/4/16 19:51
 * @describe 标签view object类
 */
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class TagVo {
    private String id;
    private String avatar;
    private String tagName;
}

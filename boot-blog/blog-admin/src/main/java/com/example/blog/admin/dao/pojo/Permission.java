package com.example.blog.admin.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.lang.model.element.NestingKind;
import java.security.PrivateKey;

/**
 * @author hap
 * @date 2022/4/20 15:01
 * @describe
 */
@Data
public class Permission {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String path;
    private String description;
}

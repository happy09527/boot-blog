package com.example.blog.admin.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.admin.dao.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author hap
 * @date 2022/4/20 14:55
 * @describe
 */

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
//    List<Permission> findPermissionsByAdminId(Long adminId);
}

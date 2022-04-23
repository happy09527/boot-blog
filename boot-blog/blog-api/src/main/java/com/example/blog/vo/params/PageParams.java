package com.example.blog.vo.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hap
 * @date 2022/4/16 19:05
 * @describe  分页参数
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParams {
    private int page = 1;
    private int pageSize = 10;
    private Long categoryId;

    private Long tagId;

    private String year;

    private String month;

    //传递6的话变成06
    public String getMonth(){
        if (this.month != null && this.month.length() == 1){
            return "0"+this.month;
        }
        return this.month;
    }
}

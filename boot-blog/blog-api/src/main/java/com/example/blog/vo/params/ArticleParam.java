package com.example.blog.vo.params;

import com.example.blog.vo.CategoryVo;
import com.example.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @author hap
 * @date 2022/4/18 19:48
 * @describe
 */
@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;

}

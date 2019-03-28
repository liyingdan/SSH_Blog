package com.blog.domain;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class Article {
    private Integer article_id;
    private String article_title;
    private Long article_time;
    private String article_content;
    private String article_pic;
    private String article_desc;
    private Category category;

    @Override
    public String toString() {
        return "Article{" +
                "article_id=" + article_id +
                ", article_title='" + article_title + '\'' +
                ", article_time=" + article_time +
                ", article_content='" + article_content + '\'' +
                ", article_pic='" + article_pic + '\'' +
                ", article_desc='" + article_desc + '\'' +
                ", category=" + category +
                '}';
    }

}

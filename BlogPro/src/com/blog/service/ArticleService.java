package com.blog.service;

import com.blog.domain.Article;
import com.blog.domain.Category;
import com.blog.domain.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface ArticleService {
    //查询所有的文章业务
    List<Article> getAllArticle();
    //获取pageBean
    PageBean getPageData(DetachedCriteria detachedCriteria, Integer currPage, int pageSize);

    void delete(Article article);


    List<Category> getCategory(Integer parentid);

    void save(Article article);


    Article getOneArticle(Integer article_id);

    void update(Article article);
}

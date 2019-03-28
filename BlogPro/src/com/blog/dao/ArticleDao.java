package com.blog.dao;

import com.blog.domain.Article;
import com.blog.domain.Category;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface ArticleDao {
    List<Article> getAllArticle();

    //获取总记录数
    Integer getTotalCount(DetachedCriteria detachedCriteria);

    List<Article> getPageData(DetachedCriteria detachedCriteria, Integer index, Integer pageSize);

    void delete(Article article);


    List<Category> getCategory(Integer parentid);

    void save(Article article);

    Article getOneArticle(Integer article_id);

    void update(Article article);
}

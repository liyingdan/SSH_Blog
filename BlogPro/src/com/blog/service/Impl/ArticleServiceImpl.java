package com.blog.service.Impl;

import com.blog.dao.ArticleDao;
import com.blog.domain.Article;
import com.blog.domain.Category;
import com.blog.domain.PageBean;
import com.blog.service.ArticleService;
import lombok.Setter;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Setter
    private ArticleDao articleDao;

    @Override
    public List<Article> getAllArticle() {
        return articleDao.getAllArticle();
    }

    @Override
    public PageBean getPageData(DetachedCriteria detachedCriteria, Integer currPage, int pageSize) {
        PageBean<Article> pageBean = new PageBean<>();
        //设置当前页
        pageBean.setCurrentPage(currPage);
        //设置当前一页有多少条数据
        pageBean.setPageSize(pageSize);
        //获取总记录数--从数据库查询总记录
        Integer totalCount = articleDao.getTotalCount(detachedCriteria);
        //设置总记录数
        pageBean.setTotalCount(totalCount);
        //设置总页数
        pageBean.setTotalPage(pageBean.getTotalPage());
        //设置当前页数据
        List<Article> list = articleDao.getPageData(detachedCriteria,pageBean.getIndex(),pageBean.getPageSize());
        pageBean.setList(list);
        return pageBean;
    }

    @Override
    public void delete(Article article) {
        articleDao.delete(article);
    }

    /*根据id查找分类*/
    @Override
    public List<Category> getCategory(Integer parentid) {
        List<Category> list = articleDao.getCategory(parentid);
        return list;
    }

    @Override
    public void save(Article article) {
        articleDao.save(article);
    }

    @Override
    public Article getOneArticle(Integer article_id) {
        Article resArticle = articleDao.getOneArticle(article_id);
        return resArticle;
    }

    @Override
    public void update(Article article) {
        articleDao.update(article);
    }


}


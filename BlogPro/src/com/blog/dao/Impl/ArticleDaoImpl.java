package com.blog.dao.Impl;

import com.blog.dao.ArticleDao;
import com.blog.domain.Article;
import com.blog.domain.Category;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class ArticleDaoImpl extends HibernateDaoSupport implements ArticleDao {
    @Override
    public List<Article> getAllArticle() {
        System.out.println("dao---*");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Article.class);
        List<Article> list = (List<Article>)this.getHibernateTemplate().findByCriteria(detachedCriteria);
        return list;
    }

    /*查询总记录数*/
    @Override
    public Integer getTotalCount(DetachedCriteria detachedCriteria) {
        //查询总记录数
        detachedCriteria.setProjection(Projections.rowCount());
        List<Long> list = (List<Long>)this.getHibernateTemplate().findByCriteria(detachedCriteria);
        if(list.size() > 0){
            return list.get(0).intValue();
        }
        return 0;
    }

    /*查询分页的数据*/
    @Override
    public List<Article> getPageData(DetachedCriteria detachedCriteria, Integer index, Integer pageSize) {
        //清空查询总记录数条件
        detachedCriteria.setProjection(null);
        List<Article> list = (List<Article>)this.getHibernateTemplate().findByCriteria(detachedCriteria, index, pageSize);
        return list;
    }

    @Override
    public void delete(Article article) {
        this.getHibernateTemplate().delete(article);
    }

    /*根据id查找分类*/
    @Override
    public List<Category> getCategory(Integer parentid) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Category.class);
        detachedCriteria.add(Restrictions.eq("parentid",parentid));
        List<Category> list = (List<Category>)this.getHibernateTemplate().findByCriteria(detachedCriteria);
//        System.out.println(list);
        return list;
    }

    @Override
    public void save(Article article) {
        this.getHibernateTemplate().save(article);
    }

    @Override
    public Article getOneArticle(Integer article_id) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Article.class);
        detachedCriteria.add(Restrictions.eq("article_id",article_id));
        List<Article> list = (List<Article>)this.getHibernateTemplate().findByCriteria(detachedCriteria);
        if(list.size()>0){
            return list.get(0);
        }
        return null;

    }

    @Override
    public void update(Article article) {
        this.getHibernateTemplate().update(article);
    }

}

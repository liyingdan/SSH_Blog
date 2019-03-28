package com.blog.dao.Impl;

import com.blog.dao.CategoryDao;
import com.blog.domain.Category;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class CategoryDaoImpl extends HibernateDaoSupport implements CategoryDao {
    @Override
    public void saveCategory(Category category) {
        System.out.println("dao层"+category);
        this.getHibernateTemplate().save(category);

    }

    @Override
    public List<Category> list() {
        System.out.println("查询 dao层");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Category.class);
        List<Category> list = (List<Category>)this.getHibernateTemplate().findByCriteria(detachedCriteria);
        return list;
    }

    @Override
    public Category getOneCategory(Integer cid) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Category.class);
        //设置条件
        detachedCriteria.add(Restrictions.eq("cid",cid));
        List<Category> list = (List<Category>)this.getHibernateTemplate().findByCriteria(detachedCriteria);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public void update(Category category) {
        System.out.println("dao层"+category);
        this.getHibernateTemplate().update(category);
    }

    @Override
    public void delete(Category category) {
        this.getHibernateTemplate().delete(category);
    }
}

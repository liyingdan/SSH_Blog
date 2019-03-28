package com.blog.service.Impl;

import com.blog.dao.CategoryDao;
import com.blog.domain.Category;
import com.blog.service.CategoryService;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Setter
    private CategoryDao categoryDao;
    @Override
    public void save(Category category) {
//        System.out.println("业务层"+category);
        categoryDao.saveCategory(category);

    }

    @Override
    public List<Category> list() {
        return categoryDao.list();
    }

    @Override
    public Category getOneCategory(Integer cid) {
        //调用dao层
        Category category2 = categoryDao.getOneCategory(cid);
        return category2;
    }

    @Override
    public void update(Category category) {
        categoryDao.update(category);
    }

    @Override
    public void delete(Category category) {
        categoryDao.delete(category);
    }
}

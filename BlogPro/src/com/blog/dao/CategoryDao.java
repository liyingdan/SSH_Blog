package com.blog.dao;

import com.blog.domain.Category;

import java.util.List;

public interface CategoryDao {
    public void saveCategory(Category category);
    public List<Category> list();

    Category getOneCategory(Integer cid);

    void update(Category category);

    void delete(Category category);
}

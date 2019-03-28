package com.blog.service;

import com.blog.domain.Category;

import java.util.List;

public interface CategoryService {
    public void save(Category category);
    public List<Category> list();
    /*修改 根据id查询..*/
    Category getOneCategory(Integer cid);

    void update(Category category);

    void delete(Category category);

}

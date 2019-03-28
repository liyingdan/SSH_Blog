package com.blog.web;

import com.blog.domain.Category;
import com.blog.service.CategoryService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;

import java.io.IOException;
import java.util.List;

public class CategoryAction extends ActionSupport implements ModelDriven<Category> {
    private Category category = new Category();
    @Setter
    private CategoryService categoryService;
    @Override
    public Category getModel() {
        return category;
    }
    /*添加*/
    public String add(){
        System.out.println("CategoryAction-----");
        categoryService.save(category);
        return "listAction";
    }

    /*查询*/
    public String list(){
        List<Category> list = categoryService.list();
        System.out.println(list);
        /*把数据存到值栈中*/
        ActionContext.getContext().getValueStack().set("categoryList",list);
        return "list";
    }

    /*修改UI*/
    public String updateUI() throws IOException {
//        System.out.println("update----------"+category.getCid());
        //调用业务层
        Category category2 = categoryService.getOneCategory(category.getCid());
        System.out.println(category2);
        //把数据给页面
        //以json（数据格式）响应给页面
        JSONArray jsonArray = JSONArray.fromObject(category2, new JsonConfig());
        System.out.println(jsonArray);
        //响应给页面
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().println(jsonArray.toString());
        return null;

    }
    /*修改*/
    public String update(){
        System.out.println("update---"+category);
        categoryService.update(category);
        return "listAction";
    }

    /*删除*/
    public String delete(){
        System.out.println("删除....."+category.getCid());
        categoryService.delete(category);
        return "deleteAction";
    }

}

package com.blog.web;

import com.blog.domain.Article;
import com.blog.domain.Category;
import com.blog.domain.PageBean;
import com.blog.service.ArticleService;
import com.opensymphony.xwork2.ActionSupport;
import lombok.Setter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class WebAction extends ActionSupport {
    @Setter
    private ArticleService articleService;
    @Setter
    private Integer currPage = 1;
    @Setter
    private Integer parentid;
    @Setter
    private Integer cid;
    //前端分页
    public void getPageList() throws IOException {
        System.out.println("我是WebAction中getPagelist方法中的玩意，id是："+parentid);
        //离线查询条件
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Article.class);

        if(parentid != null){
            List<Category> categorys = articleService.getCategory(parentid);
            System.out.println(categorys);
            //构建一个数组
            Object[] cidArrays = new Object[categorys.size()];
            for(int i=0; i<categorys.size(); i++){
                Category category = categorys.get(i);
                cidArrays[i] = category.getCid();
            }
            //设置条件
//            System.out.println("啦啦啦啦啦"+Arrays.toString(cidArrays));
            detachedCriteria.add(Restrictions.in("category.cid",cidArrays));
        }

        if(cid != null){
            detachedCriteria.add(Restrictions.eq("category.cid",cid));
        }

        PageBean pageBean = articleService.getPageData(detachedCriteria,currPage,7);
        //以json（数据格式）响应给页面
        JsonConfig jsonConfig = new JsonConfig();
        //hibernate的懒加载 （如果对象有关联对象 就加上这句话，之前因为也要查询关联的对象 所以不报错 而这次不需要查询关联的对象）
        jsonConfig.setExcludes(new String[]{"handler", "hibernateLazyInitializer"});
        JSONObject jsonObject = JSONObject.fromObject(pageBean, jsonConfig);
        //响应给页面
        ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().println(jsonObject.toString());
//        System.out.println(pageBean);

    }


    //根据id获取指定的文章
    @Setter
    private Integer id;
    public void getDetail() throws IOException {
        Article oneArticle = articleService.getOneArticle(id);
        //以json（数据格式）响应给页面
        JsonConfig jsonConfig = new JsonConfig();
        //hibernate的懒加载
        jsonConfig.setExcludes(new String[]{"handler", "hibernateLazyInitializer"});
        JSONObject jsonObject = JSONObject.fromObject(oneArticle, jsonConfig);
        //响应给页面
        ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().println(jsonObject.toString());
    }
























}

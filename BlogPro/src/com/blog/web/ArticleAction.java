package com.blog.web;

import com.blog.domain.Article;
import com.blog.domain.Category;
import com.blog.domain.PageBean;
import com.blog.service.ArticleService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.aspectj.util.FileUtil;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ArticleAction extends ActionSupport implements ModelDriven<Article> {
    private Article article = new Article();

    @Override
    public Article getModel() {
        return article;
    }

    @Setter
    private ArticleService articleService;

    /*获取列表*/
    public String list(){
        System.out.println("list-----");
        //调用业务层
        List<Article> allArticle = articleService.getAllArticle();
        System.out.println(allArticle);
        //存值栈
        ActionContext.getContext().getValueStack().set("allArticle",allArticle);
        return "list";
    }

    /*获取分页数据*/
    @Setter
    private Integer currPage = 1;
    //搜索的关键字
    @Setter
    private String keyWord;
    public String pageList(){
        System.out.println(currPage);
        System.out.println(keyWord);
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Article.class);
        //设置搜索查询条件
        if(keyWord != null){
            detachedCriteria.add(Restrictions.like("article_title","%"+keyWord+"%"));
        }
        /*5代表一页有5条数据*/
        PageBean pageBean = articleService.getPageData(detachedCriteria,currPage,5);
        ActionContext.getContext().getValueStack().push(pageBean);
        return "list";
    }

    //删除
    public String delete(){
        Article article2 = new Article();
        article2.setArticle_id(article.getArticle_id());
//        System.out.println("删除---"+article_id);
        articleService.delete(article2);
        return "listres";
    }

    /*添加文章获取分类*/
    @Setter
    private Integer parentid;
    public String getCategory() throws IOException {
//        System.out.println(parentid);
        //根据parentid查询分类
        List<Category> list = articleService.getCategory(parentid);
        /*把查询的结果转成json*/
        //以json（数据格式）响应给页面
        JSONArray jsonArray = JSONArray.fromObject(list, new JsonConfig());
//        System.out.println(jsonArray);
        //响应给页面
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().println(jsonArray.toString());
        return null;
    }

    /*添加文章上传图片*/
    /**
     * 文件上传提供的三个属性:
     */
    @Setter
    private String uploadFileName; // 文件名称
    @Setter
    private File upload; // 上传文件
    @Setter
    private String uploadContentType; // 文件类型

    public String add() throws IOException {
        System.out.println("add----------");
        /*上传图片*/
        if(upload != null){
            //随机生成文件名称
            //1.获取文件扩展名  ssh.jpg
            int index = uploadFileName.lastIndexOf(".");
            String etx = uploadFileName.substring(index);
            //2.随机生成文件名 拼接扩展名
            String uuid = UUID.randomUUID().toString();
            String uuidFileName = uuid.replace("-","")+etx;

            //确定上传的路径
            String path = ServletActionContext.getServletContext().getRealPath("/upload");
            File file = new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
            //拼接新文件路径
            File desFile = new File(path + "/" + uuidFileName);
            //文件上传
            FileUtils.copyFile(upload,desFile);

            //设置图片
            article.setArticle_pic(uuidFileName);
        }
        //设置时间
        article.setArticle_time(new Date().getTime());
        System.out.println(article);

        //调用业务层，保存到数据库中
        articleService.save(article);

        return "listres";




    }


    //修改UI
    public String edit(){
        System.out.println("修改"+article.getArticle_id());
        //从数据库当中获取当前的文章
        Article resAticle = articleService.getOneArticle(article.getArticle_id());
//        System.out.println(resAticle);
        //把查询的数据保存到值栈
        ActionContext.getContext().getValueStack().push(resAticle);
        //跳转到编辑界面
        return "edit";
    }

    //修改
    public String update() throws IOException {
        //判断有没有新上传的图片
        if(upload != null){
            //确定上传路径
            String path = ServletActionContext.getServletContext().getRealPath("/upload");
            //获取图片名称
            String picName = article.getArticle_pic();
            //删除原来的图片
            if(picName != null || "".equals(picName)){
                File file = new File(path + picName);
                file.delete();
            }
            //上传新图片
            int index = uploadFileName.lastIndexOf(".");
            String etx = uploadFileName.substring(index);
            String uuid = UUID.randomUUID().toString();
            String uuidFileName = uuid.replace("-","")+etx;
            File file = new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
            File desFile = new File(path + "/" + uuidFileName);
            FileUtils.copyFile(upload,desFile);
            article.setArticle_pic(uuidFileName);
        }
        //设置时间
        article.setArticle_time(System.currentTimeMillis());
        //调用业务更新文章
        articleService.update(article);
        return "listres";
    }
}

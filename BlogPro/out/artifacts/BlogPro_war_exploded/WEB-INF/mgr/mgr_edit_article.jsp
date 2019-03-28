<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/amazeui.min.css" />
    <script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/js/umedit/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/js/umedit/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${ctpageContext.request.contextPathx }/js/umedit/lang/zh-cn/zh-cn.js"></script>
    <style>
        .update_pic{
            margin-bottom: 220px;
        }
        #imageview{
            width: 350px;
            height: 250px;
        }
    </style>
</head>
<body>


<div class="main_top">
    <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">修改文章
        </strong><small></small></div>
    </div>
    <hr>
    <form id="blog_form" action="${pageContext.request.contextPath}/article_update.action" method=post  enctype="multipart/form-data">
        <div class="edit_content">
            <div class="item1">
                <div>
                    <span>文章标题：</span>
                    <input type="text" class="am-form-field" name="article_title" style="width: 300px"
                    value="<s:property value="article_title"/> ">&nbsp;&nbsp;
                </div>
            </div>

            <input type="text" name="article_desc" id="article_desc" style="display: none;">


            <div class="item1">
                <span>所属分类：</span>
                <select id="category_select" name="category.parentid" style="width: 150px">&nbsp;&nbsp;</select>
                <select id="skill_select" name="category.cid" style="width: 150px">&nbsp;&nbsp;
                </select>
            </div>

            <div class="item1 update_pic" >
                <span>摘要图片：</span>
                <img src="${pageContext.request.contextPath }/upload/<s:property value="article_pic"/>" id="imageview" class="item1_img"  >
                <label for="fileupload" id="label_file">上传文件</label>
                <input type="file" name="upload" id="fileupload"/>
            </div>

            <div id="editor" name="article_content" style="width:900px;height:400px;"></div>
            <input type="hidden" id="resContent" value="<s:property value="article_content"/>">
            <input type="hidden" name="article_id" value="<s:property value="article_id"/>">
            <input type="hidden" name="article_pic" value="<s:property value="article_pic"/>">
            <button class="am-btn am-btn-default" type="button" id="send" style="margin-top: 10px;">
                修改</button>
        </div>

    </form>

</div>
<script>
    $(function () {
        //发送请求获取分类的数据（一进来就加载）
        $.post("${pageContext.request.contextPath}/article_getCategory.action",{"parentid":0},function (data) {
            /*遍历*/
            $(data).each(function (i,obj) {
                /*追加结点到页面中*/
                $("#category_select").append("<option value="+obj.cid+">"+obj.cname+"</option>");
            });
            //设置默认分类
            $("#category_select option[value=<s:property value="category.parentid"/>]").prop("selected",true);
        },"json");

        var parentid = <s:property value="Category.parentid"/>
        $.post("${pageContext.request.contextPath}/article_getCategory.action",{"parentid": parentid},function (data) {
            /*清空之前的标签*/
            $("#skill_select").empty();
            /*遍历*/
            $(data).each(function (i,obj) {
                /*追加结点到页面中*/
                $("#skill_select").append("<option value="+obj.cid+">"+obj.cname+"</option>");
            });
            //设置默认分类
            $("#skill_select option[value=<s:property value="category.cid"/> ]").prop("selected",true);

        },"json");

        /*监听分类select的改变*/
        $("#category_select").on("change",function () {
            /*获取当前选中的id*/
            var cid = $("#category_select").val();
            //发送请求获取分类的数据（一进来就加载）
            $.post("${pageContext.request.contextPath}/article_getCategory.action",{"parentid":cid},function (data) {
                // console.log(data);
                /*清空之前的标签*/
                $("#skill_select").empty();
                /*遍历*/
                $(data).each(function (i,obj) {
                    /*追加结点到页面中*/
                    $("#skill_select").append("<option value="+obj.cid+">"+obj.cname+"</option>");
                });
            },"json");
        });


        /*原理是把本地图片路径："D(盘符):/image/..."(url地址)转为"http://..."格式路径来进行显示图片*/
        $("#fileupload").change(function() {
            var $file = $(this);
            var objUrl = $file[0].files[0];
            //获得一个http格式的url路径:mozilla(firefox)||webkit or chrome
            var windowURL = window.URL || window.webkitURL;
            //createObjectURL创建一个指向该参数对象(图片)的URL
            var dataURL;
            dataURL = windowURL.createObjectURL(objUrl);
            $("#imageview").attr("src",dataURL);
            console.log($('#imageview').attr('style'));
            if($('#imageview').attr('style') === 'display: none;'){
                $('#imageview').attr('style','inline');
                $('#imageview').width("300px");
                $('#imageview').height("200px");
                $('.update_pic').attr('style', 'margin-bottom: 80px;');
            }
        });


        //初始化富文本编辑器
        var ue = UE.getEditor('editor');
        ue.ready(function () {
            ue.execCommand("inserthtml",$("#resContent").val());
        });

        $("#send").click(function () {
            //获取文本内容
            var text = ue.getContentTxt();
            //设置文本描述
            text = text.substring(0,150)+"...";
            $("#article_desc").val(text);
            //提交表单
            $("#blog_form").submit();
        });


    });


</script>



</body>
</html>
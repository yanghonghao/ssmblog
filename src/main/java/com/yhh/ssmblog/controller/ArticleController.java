package com.yhh.ssmblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.yhh.ssmblog.entity.ArticleInfo;
import com.yhh.ssmblog.service.ArticleService;
import com.yhh.ssmblog.service.ArticleServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @RequestMapping("listMyBlog")
    @ResponseBody
    public JSONObject listMyBlog(HttpServletRequest request){
        int draw = Integer.parseInt(request.getParameter("draw"));
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        int n = Integer.parseInt(request.getParameter("order[0][column]"));
        boolean orderable = Boolean.parseBoolean(request.getParameter("columns["+String.valueOf(n)+"][orderable]"));
        String column = null;
        String order = null;
        if(orderable){
            column = request.getParameter("columns["+String.valueOf(n)+"][data]");
            order = request.getParameter("order[0][dir]");
        }
        String categoryName = request.getParameter("categoryName");
        String filter = request.getParameter("filter");

        String username=(String)SecurityUtils.getSubject().getPrincipal();
        int count=articleService.countArticle(username,null,filter);
        List<ArticleInfo> infos = articleService.listArticleInfo(column,order,start,length,username,categoryName,filter);
        JSONObject json = new JSONObject();
        json.put("draw",JSONObject.toJSON(draw));
        json.put("recordsTotal",JSONObject.toJSON(count));
        json.put("recordsFiltered",JSONObject.toJSON(count));
        json.put("data",JSONObject.toJSON(infos));
        return json;
    }

    @RequestMapping("listArticle")
    @ResponseBody
    public JSONObject listArticle(HttpServletRequest request){
        String categoryName = request.getParameter("categoryName");
        String filter = request.getParameter("filter");
        Integer start = Integer.parseInt(request.getParameter("start"));
        Integer length = Integer.parseInt(request.getParameter("length"));
        String column = request.getParameter("columne");
        String order = request.getParameter("order");
        if(column==null)
            column="createTime";
        if(order==null)
            order="desc";
        List<ArticleInfo> articles = articleService.listArticleInfo(column,order,start,length,null,categoryName,filter);
        int total = articleService.countArticle(null,categoryName,filter);
        JSONObject json = new JSONObject();
        json.put("articles",articles);
        json.put("total",total);
        return json;
    }

    @RequestMapping("loadMarkdown")
    @ResponseBody
    public JSONObject loadMarkdown(HttpServletRequest requrst){
        String uuid = requrst.getParameter("uuid");
        JSONObject json = new JSONObject();
        String username=(String)SecurityUtils.getSubject().getPrincipal();
        if(!articleService.checkArticle(uuid,username)){
            json.put("result","false");
            return json;
        }

        ArticleInfo info = articleService.getArticleInfo(uuid);
        String markdown = articleService.getArticleMarkdown(uuid);
        json.put("result","true");
        json.put("info",info);
        json.put("markdown",markdown);
        return json;
    }

    @RequestMapping("loadArticle")
    @ResponseBody
    public JSONObject loadArticle(HttpServletRequest requrst){
        String uuid = requrst.getParameter("uuid");
        JSONObject json = new JSONObject();
        ArticleInfo info = articleService.getArticleInfo(uuid);
        String markdown = articleService.getArticleMarkdown(uuid);
        if(!articleService.checkArticle(uuid)) {
            json.put("result", "false");
            return json;
        }
        json.put("result","true");
        json.put("info",info);
        json.put("markdown",markdown);
        return json;
    }

    @RequestMapping("saveArticle")
    @ResponseBody
    public String saveArticle(HttpServletRequest request){
        String uuid = request.getParameter("uuid");
        String username=(String)SecurityUtils.getSubject().getPrincipal();
        JSONObject json = new JSONObject();
        String title = request.getParameter("title");
        String categoryName = request.getParameter("categoryName");
        String markdown = request.getParameter("markdown");
        if(!articleService.checkArticle(uuid,username)){
            return String.valueOf(createArticle(title,categoryName,markdown));
        }else{
            return String.valueOf(articleService.updateArticle(uuid,title,categoryName,markdown));
        }
    }

    private boolean createArticle(String title,String categoryName,String markdown){
        if(title==null||markdown==null)
            return false;
        if(categoryName==null)
            categoryName="default";
        String username=(String)SecurityUtils.getSubject().getPrincipal();
        return articleService.insertArticle(title,username,categoryName,markdown);
    }

    @RequestMapping("deleteArticle")
    @ResponseBody
    public String deleteArticle(HttpServletRequest request){
        String uuid = request.getParameter("uuid");
        String username=(String)SecurityUtils.getSubject().getPrincipal();
        if(!articleService.checkArticle(uuid,username))
            return "false";
        return String.valueOf(articleService.deleteArticle(uuid));
    }


    @RequestMapping("test")
    @ResponseBody
    public String test(HttpServletRequest request){

        return "haha";
    }
}

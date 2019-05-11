package com.yhh.ssmblog.service;

import com.yhh.ssmblog.dao.ArticleDao;
import com.yhh.ssmblog.dao.UserDao;
import com.yhh.ssmblog.entity.ArticleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    ArticleDao articleDao;
    @Autowired
    UserDao userDao;

    @Override
    public boolean checkArticle(String uuid, String username) {
        if(articleDao.checkUuid(uuid)==0||articleDao.checkUser(username)==0)
            return false;
        return articleDao.checkArticle(uuid,username)!=0;
    }

    @Override
    public boolean checkArticle(String uuid) {
        if(articleDao.checkUuid(uuid)==0)
            return false;
        return articleDao.checkArticle(uuid,null)!=0;
    }


    @Override
    public int countArticle(String username,String categoryName,String filter) {
        if(username!=null&&articleDao.checkUser(username)==0)
            return 0;
        if(categoryName!=null&&articleDao.checkCategory(categoryName)==0)
            categoryName = null;
        if(filter!=null&&!filter.trim().equals(""))
            filter="%"+filter+"%";
        return articleDao.countArticle(username,categoryName,filter);
    }

    @Override
    public List<ArticleInfo> listArticleInfo(String column, String order, Integer start, Integer length,String username,String categoryName,String filter) {
        if(articleDao.checkColumn(column)==0)
            column=null;
        if(username!=null){
            if(articleDao.checkUser(username)==0)
                username=null;
        }
        if(categoryName!=null){
            if(articleDao.checkCategory(categoryName)==0)
                categoryName=null;
        }
        if(!(order.equals("desc")||order.equals("asc")))
            order="asc";
        if(start==null||start<0)
            start=0;
        if(length==null||length<0)
            length=10;
        if(filter!=null&&!filter.trim().equals(""))
            filter="%"+filter+"%";
        if(start>articleDao.countArticle(username,categoryName,filter))
            start=0;
        return articleDao.listArticleInfo(column,order,start,length,username,categoryName,filter);
    }

    @Override
    public String getArticleMarkdown(String uuid) {
        return articleDao.getArticleMarkdown(uuid);
    }

    @Override
    public ArticleInfo getArticleInfo(String uuid) {
        return articleDao.getArticleInfo(uuid);
    }

    @Override
    public boolean updateArticle(String uuid, String title, String categoryName, String markdown) {
        Date updateTime = new Date(new java.util.Date().getTime());
        if(articleDao.checkUuid(uuid)==0)
            return false;
        String description = null;
        int a=0;
        if(markdown!=null){
            a+=articleDao.updateArticleContent(uuid,markdown);
            if(markdown.length()<120)
                description=markdown;
            else
                description=markdown.substring(0,120);
        }
        if(articleDao.checkCategory(categoryName)==0)
            categoryName=null;
        a+=articleDao.updateArticleInfo(uuid,updateTime,title,description,categoryName);
        return a!=0;
    }


    @Override
    public boolean insertArticle(String title,String username,String categoryName,String markdown) {
        String uuid=UUID.randomUUID().toString();
        for(;articleDao.checkUuid(uuid)>0;) {
            uuid=UUID.randomUUID().toString();
        }
        Date createTime=new Date(new java.util.Date().getTime());
        if(articleDao.checkUser(username)==0)
            return false;
        String description=null;
        if(markdown!=null){
            if(markdown.length()<120)
                description=markdown;
            else
                description=markdown.substring(0,120);
        }
        if(articleDao.insertArticleInfo(uuid, title,description, createTime, username, categoryName)<1) {
            try {
                throw new Exception("save falied");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(articleDao.insertArticleContent(uuid, markdown)<1) {
            try {
                throw new Exception("save falied");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean deleteArticle(String uuid){
        if(articleDao.checkUuid(uuid)==0)
            return false;
        articleDao.deleteArticleInfo(uuid);
        articleDao.deleteArticleContent(uuid);
        return true;
    }
}

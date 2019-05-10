package com.yhh.ssmblog.dao;

import com.yhh.ssmblog.entity.ArticleContent;
import com.yhh.ssmblog.entity.ArticleInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ArticleDao {
    public int countArticle(@Param("username")String username
            ,@Param("categoryName")String categoryName
            ,@Param("filter")String filter);
    public List<ArticleInfo> listArticleInfo(@Param("column")String column
            ,@Param("order")String order
            ,@Param("start")int start
            ,@Param("length")int length
            ,@Param("username")String username
            ,@Param("categoryName")String categoryName
            ,@Param("filter")String filter);

    public ArticleInfo getArticleInfo(@Param("uuid")String uuid);
    public String getArticleMarkdown(@Param("uuid")String uuid);

    public int updateArticleInfo(@Param("uuid")String uuid
            ,@Param("updateTime")Date updateTime
            ,@Param("title")String title
            ,@Param("description")String description
            ,@Param("categoryName")String categoryName);
    public int updateArticleContent(@Param("uuid")String uuid
            ,@Param("markdown")String markdown);
    public int insertArticleInfo(@Param("uuid")String uuid
            ,@Param("title")String title
            ,@Param("description")String description
            ,@Param("createTime")Date createTime
            ,@Param("username")String username
            ,@Param("categoryName")String categoryName);
    public int insertArticleContent(@Param("uuid")String uuid
            ,@Param("markdown")String markdownl);
    public int deleteArticleInfo(@Param("uuid")String uuid);
    public int deleteArticleContent(@Param("uuid")String uuid);

    public int checkUuid(@Param("uuid")String uuid);
    public int checkColumn(@Param("column")String column);
    public int checkCategory(@Param("categoryName")String categoryName);
    public int checkUser(@Param("username")String username);
    public int checkArticle(@Param("uuid")String uuid,@Param("username")String username);
}

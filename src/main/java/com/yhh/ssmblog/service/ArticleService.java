package com.yhh.ssmblog.service;

import com.yhh.ssmblog.entity.ArticleInfo;

import java.util.List;

public interface ArticleService {
    public boolean checkArticle(String uuid,String username);
    public boolean checkArticle(String uuid);
    public int countArticle(String username,String categoryName,String filter);
    public List<ArticleInfo> listArticleInfo(String column, String order, Integer start, Integer length,String username,String categoryName,String filter);

    public String getArticleMarkdown(String uuid);
    public ArticleInfo getArticleInfo(String uuid);

    public boolean updateArticle(String uuid,String title,String categoryName,String markdown);
    public boolean insertArticle(String title,String username,String categoryName,String markdown);
    public boolean deleteArticle(String uuid);
}

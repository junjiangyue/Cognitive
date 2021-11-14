package com.example.cognitive.Bean;

public class Articles {
    private String newsTitle;//新闻标题
    private String newsDate; //新闻发布时间
    private String newsImgUrl; // 新闻图片Url地址
    private String newsUrl; //新闻详情Url地址

    public String getNewsTitle() {
        return newsTitle;
    }
    public void setNewsTitle(String title) {
        this.newsTitle = title;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String date) {
        this.newsDate = date;
    }

    public String getNewsImgUrl() {
        return newsImgUrl;
    }

    public void setNewsImgUrl(String newsImgUrl) {
        this.newsImgUrl = newsImgUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }


    public void add(Articles data) {
        this.newsDate = data.newsDate;
        this.newsTitle = data.newsTitle;
        this.newsImgUrl = data.newsImgUrl;
        this.newsUrl = data.newsUrl;
    }
}

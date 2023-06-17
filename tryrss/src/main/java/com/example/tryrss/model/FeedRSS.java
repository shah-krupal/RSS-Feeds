package com.example.tryrss.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "Feeds")
public class FeedRSS {
    @Id
    Integer feedId;
    Date lastBuildDate;
    String generator;
    String title;
    String url;
    List<ItemRSS> itemRSSList;

    public Integer getFeedId() {
        return feedId;
    }

    public void setFeedId(Integer feedId) {
        this.feedId = feedId;
    }


    public Date getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(Date lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ItemRSS> getItemRSSList() {
        return itemRSSList;
    }

    public void setItemRSSList(List<ItemRSS> itemRSSList) {
        this.itemRSSList = itemRSSList;
    }
}

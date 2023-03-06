package com.cn.textsinglelinescrolling.model;

/**
 * Created by Chenning on 2023-03-06
 */
public class NewsModel {
    private String id;
    private String title;

    public NewsModel(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

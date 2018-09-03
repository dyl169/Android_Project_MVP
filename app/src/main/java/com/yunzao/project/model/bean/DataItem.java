package com.yunzao.project.model.bean;

import java.util.ArrayList;

/**
 * @auth ike
 * @date 2018/8/30
 * @desc 类描述：
 */

public class DataItem {
    /**
     * _id : 5b7105eb9d212234189c24ce
     * createdAt : 2018-08-13T12:15:39.942Z
     * desc : 又一个Android权限管理器。
     * publishedAt : 2018-08-28T00:00:00.0Z
     * source : chrome
     * type : Android
     * url : https://github.com/yanzhenjie/AndPermission
     * used : true
     * who : lijinshanmx
     * images : ["https://ww1.sinaimg.cn/large/0073sXn7ly1fupho39u2qg30rs0rs0vl","https://ww1.sinaimg.cn/large/0073sXn7ly1fupho3kq10g31400tce3i","https://ww1.sinaimg.cn/large/0073sXn7ly1fupho3piw6g30rs0rswj8","https://ww1.sinaimg.cn/large/0073sXn7ly1fupho3vyj3g31bg0wjgvp","https://ww1.sinaimg.cn/large/0073sXn7ly1fupho41j70g30rs0rs0ut"]
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private ArrayList<String> images;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "_id='" + _id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", who='" + who + '\'' +
                ", images=" + images +
                '}';
    }
}

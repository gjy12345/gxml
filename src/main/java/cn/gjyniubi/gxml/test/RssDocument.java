package cn.gjyniubi.gxml.test;

import java.util.List;

public class RssDocument {

    @XmlRule("/rss/channel/title")
    private String title;//频道名称
    @XmlRule("/rss/channel/link")
    private String link;//频道url
    @XmlRule("/rss/channel/Description")
    private String Description;//频道描述
    @XmlRule("/rss/channel/language")
    private String language;//频道所用语言
    @XmlRule("/rss/channel/copyright")
    private String copyright;//频道版权
    @XmlRule("/rss/channel/managingEditor")
    private String managingEditor;//编辑email
    @XmlRule("/rss/channel/ttl")
    private Long ttl;//可缓存时间 分钟为单位
    @XmlRule("/rss/channel/image")
    private String image;//与频道一起展示的图片
    @XmlRule("/rss/channel/lastBuildDate")
    private String lastBuildDate;
    @XmlRule("/rss/channel/item")
    private List<RssItem> item;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getManagingEditor() {
        return managingEditor;
    }

    public void setManagingEditor(String managingEditor) {
        this.managingEditor = managingEditor;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "RssDocument{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", Description='" + Description + '\'' +
                ", language='" + language + '\'' +
                ", copyright='" + copyright + '\'' +
                ", managingEditor='" + managingEditor + '\'' +
                ", ttl=" + ttl +
                ", image='" + image + '\'' +
                ", lastBuildDate='" + lastBuildDate + '\'' +
                ", item=" + item +
                '}';
    }
}

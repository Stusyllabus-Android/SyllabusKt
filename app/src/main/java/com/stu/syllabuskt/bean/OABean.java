package com.stu.syllabuskt.bean;

/**
 * Create by yuan on 2020/12/5
 */
public class OABean {

    /**
     * title : 关于举办汕头大学第二十四届“院际杯”辩论赛的通知
     * publishDate : 2020-12-05T00:00:00
     * department : 校团委
     * content : null
     * id : 23039
     * attachments : null
     */

    private String title;
    private String publishDate;
    private String department;
    private Object content;
    private int id;
    private Object attachments;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getAttachments() {
        return attachments;
    }

    public void setAttachments(Object attachments) {
        this.attachments = attachments;
    }
}

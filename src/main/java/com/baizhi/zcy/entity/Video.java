package com.baizhi.zcy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Document(indexName = "yingx", type = "video")
@Table(name = "yx_video")
@Data
public class Video {
    @Id
    private String id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String brief;
    @Column(name = "video_path")
    @Field(type = FieldType.Keyword)
    private String videoPath;
    @Column(name = "cover_path")
    @Field(type = FieldType.Keyword)
    private String coverPath;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "pulish_date")
    @Field(type = FieldType.Date)
    private Date pulishDate;

    @Column(name = "category_id")
    @Field(type = FieldType.Keyword)
    private String categoryId;
    @Column(name = "user_id")
    @Field(type = FieldType.Keyword)
    private String userId;
    @Column(name = "group_id")
    @Field(type = FieldType.Keyword)
    private String groupId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath == null ? null : videoPath.trim();
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath == null ? null : coverPath.trim();
    }

    public Date getPulishDate() {
        return pulishDate;
    }

    public void setPulishDate(Date pulishDate) {
        this.pulishDate = pulishDate;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public Video(String id, String title, String brief, String videoPath, String coverPath, Date pulishDate, String categoryId, String userId, String groupId) {
        this.id = id;
        this.title = title;
        this.brief = brief;
        this.videoPath = videoPath;
        this.coverPath = coverPath;
        this.pulishDate = pulishDate;
        this.categoryId = categoryId;
        this.userId = userId;
        this.groupId = groupId;
    }

    public Video() {
    }
}
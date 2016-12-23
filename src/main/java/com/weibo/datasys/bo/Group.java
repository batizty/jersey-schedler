package com.weibo.datasys.bo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tuoyu on 23/12/2016.
 */
@Entity
@Table(name = "mm_group")
public class Group implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private String groupName;//组名
    private Date loginDate;//登录时间
    private String creator;//创造者
    private String createAt;//创造时间
    private String catalogueName;//目录名

    public Group(String groupName,  Date loginDate, String creator, String createAt, String catalogueName) {
        this.groupName = groupName;
        this.loginDate = loginDate;
        this.creator = creator;
        this.createAt = createAt;
        this.catalogueName = catalogueName;
    }

    public Group() {
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getGroupName() { return groupName; }

    public void setGroupName(String groupName) { this.groupName = groupName; }

    public Date getLoginDate() { return loginDate; }

    public void setLoginDate(Date loginDate) { this.loginDate = loginDate; }

    public String getCreator() { return creator; }

    public void setCreator(String creator) { this.creator = creator; }

    public String getCreateAt() { return createAt; }

    public void setCreateAt(String createAt) { this.createAt = createAt; }

    public String getCatalogueName() { return catalogueName; }

    public void setCatalogueName(String catalogueName) { this.catalogueName = catalogueName; }
}
package com.weibo.datasys.bo;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tuoyu on 23/12/2016.
 */
@Entity
@Table(name = "mm_user")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    //===============Login Info ==========================
    @Column(unique = true, nullable = true)

    private String userName;//用户名
    private String password;//登录原始密码
    private long groupId;//组id
    private Date lastLoginTime;//最后登录时间
    private int state;// 0:unactive 1:active
    private Date createAt;//创建时间
    private int indentfy;//身份：1、普通用户 100、超级管理员

    public User(String userName, String password, long groupId, Date lastLoginTime, int state, Date createAt, int indentfy) {
        this.userName = userName;
        this.password = password;
        this.groupId = groupId;
        this.lastLoginTime = lastLoginTime;
        this.state = state;
        this.createAt = createAt;
        this.indentfy = indentfy;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public int getIndentfy() {
        return indentfy;
    }

    public void setIndentfy(int indentfy) {
        this.indentfy = indentfy;
    }

}

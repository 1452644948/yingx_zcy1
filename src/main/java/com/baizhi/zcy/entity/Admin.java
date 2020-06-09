package com.baizhi.zcy.entity;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "yx_admin")
public class Admin implements Serializable {
    @Id
    private Integer id;
    private String admin_name;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", admin_name='" + admin_name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Admin() {
    }

    public Admin(Integer id, String admin_name, String password) {
        this.id = id;
        this.admin_name = admin_name;
        this.password = password;
    }
}

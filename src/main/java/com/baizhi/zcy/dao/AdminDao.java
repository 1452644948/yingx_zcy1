package com.baizhi.zcy.dao;

import com.baizhi.zcy.entity.Admin;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminDao {
    List<Admin> queryAdmins();

    Admin queryAdmin(@Param("username") String username, @Param("password") String password);

    Admin queryByName(String admin_name);
}

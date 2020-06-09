package com.baizhi.zcy.service;

import com.baizhi.zcy.entity.Admin;
import java.util.Map;

public interface AdminService {

    Admin queryAdmin(String username, String password);

    Map<String, Object> login(Admin admin, String encode);
}

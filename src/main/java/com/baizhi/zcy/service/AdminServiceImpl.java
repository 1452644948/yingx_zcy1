package com.baizhi.zcy.service;

import com.baizhi.zcy.dao.AdminDao;
import com.baizhi.zcy.entity.Admin;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    HttpSession session;

    @Override
    public Admin queryAdmin(String username, String password) {
        return adminDao.queryAdmin(username, password);
    }

    @Override
    public Map<String, Object> login(Admin admin, String enCode) {
        HashMap<String, Object> map = new HashMap<>();
        String code = (String) session.getAttribute("code");
        System.out.println("encode = " + enCode);
        if (enCode.equals(code)) {
            Admin admin1 = adminDao.queryByName(admin.getAdmin_name());
            System.out.println("admin1 = " + admin1);
            if (admin1 != null) {
                if (admin.getPassword().equals(admin1.getPassword())) {
                    session.setAttribute("admin", admin);
                    map.put("status", "100");
                    map.put("message", "校验通过！");
                } else {
                    map.put("status", "101");
                    map.put("message", "密码错误！");
                }
            } else {
                map.put("status", "101");
                map.put("message", "该用户不存在！");
            }
        } else {
            map.put("status", "101");
            map.put("message", "验证码错误！");
        }
        return map;
    }
}

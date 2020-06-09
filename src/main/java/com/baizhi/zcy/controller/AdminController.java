package com.baizhi.zcy.controller;

import com.baizhi.zcy.entity.Admin;
import com.baizhi.zcy.service.AdminServiceImpl;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @ResponseBody
    @RequestMapping("login")
    public Map<String, Object> adminLogin(Admin admin, String enCode) {
        System.out.println("admin = " + admin);
        Map<String, Object> map = adminServiceImpl.login(admin, enCode);
        return map;
    }

    @RequestMapping("exit")
    public String exit(HttpSession session) {
        session.removeAttribute("admin");
        return "redirect:/login/login.jsp";
    }
}

package com.baizhi.zcy.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.zcy.entity.User;
import com.baizhi.zcy.service.UserServiceImpl;
import com.baizhi.zcy.util.SendSms;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @ResponseBody
    @RequestMapping("showUser")
    public Map<String, Object> showUser(Integer page, Integer rows) {
        Map<String, Object> map = userService.showUsers(page, rows);
        return map;
    }

    @ResponseBody
    @RequestMapping("editStatus")
    public void editStatus(String status, String id, String oper) {
        System.out.println("id = " + id);
        if (oper.equals("edit")) {
            userService.editStatus(status, id);
        }
    }

    @ResponseBody
    @RequestMapping("edit")
    public String edit(User user, String oper) {
        String uid = null;
        if (oper.equals("add")) {
            uid = userService.add(user);
        }
        if (oper.equals("del")) {
            userService.delete(user);
        }
        if (oper.equals("edit")) {
            userService.update(user);
        }
        return uid;
    }

    //文件上传
    @RequestMapping("uploadUserCover")
    public void uploadUserCover(MultipartFile headImg, String id, HttpServletRequest request) {
        System.out.println("UserController.uploadUserCover");
        userService.uploadUserCover(headImg, id, request);

    }

    @ResponseBody
    @RequestMapping("sendCode")
    public void sendCode(String phone, String code) {
        String random = SendSms.getRandom(6);
        SendSms.sendCode(phone, random);

    }

    @RequestMapping("fileExport")
    public String fileExport() {
        //System.out.println("UserController.fileExport");
        List<User> users = userService.selectAll();
        //${path}/upload/photo/

        for (User user : users) {
            user.setHeadImg("src/main/webapp/upload/photo/" + user.getHeadImg());
            //System.out.println("user.getHeadImg() = " + user.getHeadImg());
        }
        ExportParams exportParams = new ExportParams("用户信息", "用户信息表");
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, User.class, users);

        try {
            workbook.write(new FileOutputStream(new File("E://javase//zcy//后期项目//用户信息导入.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "main/main";
    }
}

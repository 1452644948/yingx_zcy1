package com.baizhi.zcy;

import com.baizhi.zcy.dao.*;
import com.baizhi.zcy.entity.Log;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YingxZcyApplicationTests {

    @Resource
    AdminMapper adminMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    CategoryMapper categoryMapper;
    @Resource
    VideoMapper videoMapper;
    @Resource
    LogDao logDao;

    @Test
    public void contextLoads() {
        Log log = new Log(UUID.randomUUID().toString(), "ADMIN", new Date(), "添加管理员", "成功");
        logDao.addLog(log);
        List<Log> logs = logDao.queryAll();
        for (Log log1 : logs) {
            System.out.println("log1 = " + log1);
        }
     /*   String s="zhumu_0.mp4";
        String[] split = s.split("\\.");
        for (String s1 : split) {
            System.out.println("s1 = " + s1);
        }*/
       /* Video video = videoMapper.selectByPrimaryKey("784306f0-d8d8-41e3-8a15-d93991452fda");
        System.out.println("video = " + video);
       // video.setId("cde61552-fe3d-4f94-b836-72dba4971261");
        video.setBrief("颖儿111");
        videoMapper.updateByPrimaryKeySelective(video);*/
        /*List<Admin> admins = adminDao.queryAdmins();
        for (Admin admin : admins) {
            System.out.println("admin = " + admin);
        }*/
       /* User user = new User();
        user.setBrief("1231");
        System.out.println("user = " + user);*/
        /*List<User> admins = userMapper.selectAll();
        for (User admin : admins) {
            System.out.println("admin = " + admin);
        }*/
       /* List<Category> categories = categoryMapper.selectAll();
        for (Category category : categories) {
            System.out.println("category = " + category);
        }*/
      /*  List<Category> categories = categoryMapper.queryByOne(1);
        for (Category category : categories) {
            System.out.println("category = " + category);
        }*/
      /*  List<Category> categories = categoryMapper.selectByRowBounds(new Category(), new RowBounds());
        for (Category category : categories) {
            System.out.println("category = " + category);
        }
        System.out.println("***********************************************");
        List<Category> categories1 = categoryMapper.queryByOne(new Category(), new RowBounds(), 1);
        for (Category category : categories1) {
            System.out.println("category = " + category);
        }*/
       /* Admin admin = adminDao.queryByName("hague");
        System.out.println("admin = " + admin);*/
       /* int i = userMapper.selectCount(new User());
        System.out.println("i = " + i);*/
       /* Admin admin1 = new Admin();
        String admin_name = admin1.getAdmin_name();
        System.out.println("admin_name = " + admin_name);
        AdminExample example = new AdminExample();
        System.out.println("example = " + example);*/
      /*  example.createCriteria().andIdBetween("1", "3");
        List<Admin> admins = adminMapper.selectByExample(example);
        for (Admin admin : admins) {
            System.out.println("admin = " + admin);
        }*/
        /*Admin admin = new Admin();
        admin.setAdmin_name("admin1");
        admin.setId(1);
        adminMapper.updateByPrimaryKeySelective(admin);*/
       /* Admin admin = adminDao.queryByName("admin1");
        System.out.println("admin = " + admin);*/
       /* List<Admin> admins = adminMapper.selectAll();
        for (Admin admin : admins) {
            System.out.println("admin = " + admin);
        }*/
      /*  Admin admin = adminMapper.selectByPrimaryKey("4");
        System.out.println("admin = " + admin);*/
    }

}

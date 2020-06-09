package com.baizhi.zcy.service;

import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.AddLog;
import com.baizhi.annotation.DelCache;
import com.baizhi.zcy.dao.UserMapper;
import com.baizhi.zcy.entity.City;
import com.baizhi.zcy.entity.Month;
import com.baizhi.zcy.entity.User;
import com.baizhi.zcy.entity.UserExample;
import java.io.File;
import java.io.IOException;
import java.util.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    @DelCache
    public void editStatus(String status, String id) {
        // System.out.println("修改service的status = " + status);
        //User user = new User();
        User user = userMapper.selectByPrimaryKey(id);
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(id);
        if (status.equals("1")) {
            user.setStatus("0");
        } else if (status.equals("0")) {
            user.setStatus("1");
        }
        // System.out.println("Service 中 user = " + user);
        userMapper.updateByExampleSelective(user, example);
    }

    @Override
    @AddCache
    public Map<String, Object> showUsers(Integer page, Integer rows) {
       /* List<User> users = userMapper.selectAll();
        Integer num = userMapper.selectCount(new User());//共有数据总条数
        Integer totalPage=null;//总页数
        totalPage=num%rows==0?num/rows:num/rows+1;
        Map<String,Object> map=new HashMap<>();
        map.put("rows",users);
        map.put("page",page);
        map.put("total",totalPage);
        map.put("records",num);*/
        HashMap<String, Object> map = new HashMap<>();
        //总条数   records
        UserExample example = new UserExample();
        Integer records = userMapper.selectCountByExample(example);
        map.put("records", records);

        //总页数  totals    总条数/每页展示条数
        Integer totals = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", totals);

        //当前页  page
        map.put("page", page);
        //数据   rows   分页
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> users = userMapper.selectByRowBounds(new User(), rowBounds);
        map.put("rows", users);
        return map;
    }

    @AddLog(description = "添加用户")
    @DelCache
    @Override
    public String add(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setCreateDate(new Date());
        user.setStatus("1");
        userMapper.insertSelective(user);
        return user.getId();
    }

    @Override
    public void uploadUserCover(MultipartFile headImg, String id, HttpServletRequest request) {
        //1.获取文件上传的路径  根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");

        //2.判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();  //创建文件夹
        }
        //获取文件名
        String filename = headImg.getOriginalFilename();
        //System.out.println("filename= " + filename);
       /* String filename1 = headImg.getName();
        System.out.println("filename1 = " + filename1);*/
        //创建一个新的名字    原名称-时间戳  10.jpg-1590390153130
        String newName = new Date().getTime() + "-" + filename;
        // System.out.println("newName = " + newName);
        //3.文件上传
        try {
            headImg.transferTo(new File(realPath, newName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //4.修改图片路径
        //修改的条件
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(id);

        User user = new User();
        user.setHeadImg(newName); //设置修改的结果

        //修改
        userMapper.updateByExampleSelective(user, example);
    }

    @Override
    @AddLog(description = "删除用户")
    @DelCache
    public void delete(User user) {
        userMapper.delete(user);
    }

    @Override
    @AddLog(description = "修改用户")
    public String update(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(user.getId());
        userMapper.updateByExampleSelective(user, example);

        return user.getId();
    }

    @Override
    public List<User> selectAll() {
        UserExample example = new UserExample();
        List<User> users = userMapper.selectByExample(example);
        return users;
    }


    @Override
    public List<City> queryCity(String sex) {
        return userMapper.queryCity(sex);
    }

    @Override
    public List<Month> queryMonth(String sex) {
        return userMapper.queryMonth(sex);
    }
}

package com.baizhi.zcy.service;

import com.baizhi.zcy.entity.City;
import com.baizhi.zcy.entity.Month;
import com.baizhi.zcy.entity.User;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;

public interface UserService {
    void editStatus(String status, String id);

    Map<String, Object> showUsers(Integer page, Integer rows);

    String add(User user);

    void uploadUserCover(MultipartFile headImg, String id, HttpServletRequest request);

    void delete(User user);

    String update(User user);

    List<User> selectAll();

    List<City> queryCity(String sex);

    List<Month> queryMonth(String sex);
}

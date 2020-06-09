package com.baizhi.zcy.dao;

import com.baizhi.zcy.entity.City;
import com.baizhi.zcy.entity.Month;
import com.baizhi.zcy.entity.User;
import java.util.List;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
    List<City> queryCity(String sex);

    List<Month> queryMonth(String sex);
}

package com.baizhi.zcy.dao;

import com.baizhi.zcy.entity.Category;
import java.util.List;
import tk.mybatis.mapper.common.Mapper;

public interface CategoryMapper extends Mapper<Category> {
    @Override
    List<Category> selectAll();
}
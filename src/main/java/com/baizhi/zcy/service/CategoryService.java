package com.baizhi.zcy.service;

import com.baizhi.zcy.entity.Category;
import java.util.HashMap;
import java.util.List;

public interface CategoryService {
    HashMap<String, Object> showOneLevels(Integer page, Integer rows);

    HashMap<String, Object> showTwoLevels(Integer page, Integer rows, String rowId);

    HashMap<String, Object> delete(Category category);

    void update(Category category);

    void add(Category category);

    List<Category> queryAllCategory();
}

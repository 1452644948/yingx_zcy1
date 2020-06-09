package com.baizhi.zcy.controller;

import com.baizhi.zcy.dao.CategoryMapper;
import com.baizhi.zcy.entity.Category;
import com.baizhi.zcy.service.CategoryServiceImpl;
import java.util.HashMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RequestMapping("cate")
@RestController
public class CategoryController {
    @Resource
    CategoryServiceImpl categoryService;
    @Resource
    CategoryMapper categoryMapper;

    @RequestMapping("showOneLevels")
    public HashMap<String, Object> showOneLevels(Integer page, Integer rows) {
        HashMap<String, Object> map = categoryService.showOneLevels(page, rows);
        return map;
    }

    @RequestMapping("showTwoLevels")
    public HashMap<String, Object> showTwoLevels(Integer page, Integer rows, String rowId) {
        //System.out.println("展示二级rowId = " + rowId);
        HashMap<String, Object> map = categoryService.showTwoLevels(page, rows, rowId);
        return map;
    }

    @RequestMapping("edit")
    public HashMap<String, Object> edit(Category category, String oper) {
        //System.out.println("controller category = " + category);
        if (oper.equals("del")) {
            HashMap<String, Object> map = categoryService.delete(category);
            return map;
        }
        if (oper.equals("edit")) {
            categoryService.update(category);
        }
        if (oper.equals("add")) {
            categoryService.add(category);
        }
        return null;
    }
}

package com.baizhi.zcy.service;

import com.baizhi.annotation.AddLog;
import com.baizhi.zcy.dao.CategoryMapper;
import com.baizhi.zcy.entity.Category;
import com.baizhi.zcy.entity.CategoryExample;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Resource
    CategoryMapper categoryMapper;

    public HashMap<String, Object> showOneLevels(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();

        //当前页   page
        map.put("page", page);

        //总条数   records
        CategoryExample example = new CategoryExample();
        example.createCriteria().andLevelsEqualTo(1);
        int records = categoryMapper.selectCountByExample(example);
        map.put("records", records);

        //总页数   total
        //总页数=总条数/每页展示条数   有余数加一页
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);

        //数据    rows   参数：略过几条，要几条
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows", categories);

        return map;
    }

    public HashMap<String, Object> showTwoLevels(Integer page, Integer rows, String rowId) {
        HashMap<String, Object> map = new HashMap<>();

        //当前页   page
        map.put("page", page);

        //总条数   records
        CategoryExample example = new CategoryExample();
        example.createCriteria().andParentIdEqualTo(rowId);
        int records = categoryMapper.selectCountByExample(example);
        map.put("records", records);

        //总页数   total
        //总页数=总条数/每页展示条数   有余数加一页
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);

        //数据    rows   参数：略过几条，要几条
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows", categories);

        return map;
    }

    @AddLog(description = "删除类别")
    public HashMap<String, Object> delete(Category category) {
        HashMap<String, Object> map = new HashMap<>();
        Category category1 = categoryMapper.selectByPrimaryKey(category.getId());
        System.out.println("删除service category = " + category1);
        if (category1.getLevels() == 1) {
            CategoryExample example = new CategoryExample();
            example.createCriteria().andParentIdEqualTo(category.getId());
            int twoLevels = categoryMapper.selectCountByExample(example);
            System.out.println("twoLevels = " + twoLevels);
            if (twoLevels == 0) {
                map.put("status", "100");
                map.put("message", "删除成功");
                categoryMapper.deleteByPrimaryKey(category);
            } else {
                map.put("status", "101");
                map.put("message", "该类别下有子类别，不能被删除");
            }
        } else {
            categoryMapper.deleteByPrimaryKey(category);
            map.put("status", "100");
            map.put("message", "删除成功");
        }
        return map;
    }

    @AddLog(description = "修改类别")
    public void update(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    @AddLog(description = "添加类别")
    @Override
    public void add(Category category) {
        if (category.getParentId() == null) {
            category.setLevels(1);
        } else {
            category.setLevels(2);
        }
        category.setId(UUID.randomUUID().toString());
        categoryMapper.insertSelective(category);
    }

    @Override
    public List<Category> queryAllCategory() {
        return categoryMapper.selectAll();
    }
}

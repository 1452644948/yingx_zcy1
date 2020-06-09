package com.baizhi.zcy.service;

import com.baizhi.zcy.dao.LogDao;
import com.baizhi.zcy.entity.Log;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Resource
    LogDao logDao;

    @Override
    public Map<String, Object> showLogs(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();

        //当前页   page
        map.put("page", page);

        //总条数   records
        Integer records = logDao.queryCount();
        map.put("records", records);

        //总页数   total
        //总页数=总条数/每页展示条数   有余数加一页
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);

        //数据    rows   参数：略过几条，要几条
        List<Log> logs = logDao.queryAll();
        map.put("rows", logs);

        return map;
    }
}

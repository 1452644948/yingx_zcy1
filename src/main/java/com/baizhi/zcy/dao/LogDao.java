package com.baizhi.zcy.dao;

import com.baizhi.zcy.entity.Log;
import java.util.List;

public interface LogDao {
    void addLog(Log log);

    List<Log> queryAll();

    Integer queryCount();
}

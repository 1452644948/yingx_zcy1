package com.baizhi.zcy.controller;

import com.baizhi.zcy.service.LogService;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;

@Controller
public class LogController {
    @Resource
    LogService logServiceImpl;

    @ResponseBody
    @RequestMapping("log")
    public Map<String, Object> showLogs(Integer page, Integer rows) {
        Map<String, Object> map = logServiceImpl.showLogs(page, rows);
        return map;
    }
}

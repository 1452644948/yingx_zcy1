package com.baizhi.zcy.controller;


import com.alibaba.fastjson.JSON;
import com.baizhi.zcy.entity.City;
import com.baizhi.zcy.entity.Common;
import com.baizhi.zcy.entity.Month;
import com.baizhi.zcy.service.UserService;
import io.goeasy.GoEasy;
import java.util.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping("echarts")
public class EchartsController {
    @Resource
    UserService userService;

    @RequestMapping("getEchartsUserData")
    public HashMap<String, Object> getEchartsUserData() {
        System.out.println("展示用户统计Controller");
        //SELECT concat(month(create_date),"月") month,count(id) count from yx_user where sex='女' GROUP BY month(create_date)
        //SELECT concat(month(create_date),"月") month,count(id) count from yx_user where sex='男' GROUP BY month(create_date)
        //1月 2   2月 1   4月 1
        HashMap<String, Object> map = new HashMap<>();
        List<String> months = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月");
        List<Month> monthBoys = userService.queryMonth("男");
        List<Month> monthGirls = userService.queryMonth("女");
        //1 3 6拿到所有月份的名称
        ArrayList<String> boyNames = new ArrayList<String>();
        for (Month monthBoy : monthBoys) {
            boyNames.add(monthBoy.getName());
        }
        for (String month : months) {//判断 如果不包含则添加一个新对象
            if (!boyNames.contains(month)) {
                monthBoys.add(new Month(month, "0"));
            }
        }
        Collections.sort(monthBoys);
        Collections.sort(monthGirls);
        ArrayList<String> girlNames = new ArrayList<String>();
        for (Month monthGril : monthGirls) {
            girlNames.add(monthGril.getName());
        }
        for (String month : months) {//判断 如果不包含则添加一个新对象
            if (!girlNames.contains(month)) {
                monthGirls.add(new Month(month, "0"));
            }
        }
        ArrayList<String> girlCount = new ArrayList<String>();
        for (Month monthGril : monthGirls) {
            girlCount.add(monthGril.getValue());
        }
        ArrayList<String> boyCount = new ArrayList<String>();
        for (Month monthBoy : monthBoys) {
            boyCount.add(monthBoy.getValue());
        }
        map.put("month", months);//新list集合
        map.put("boys", monthBoys);//Arrays.asList(100,300,800,500,400,700)
        map.put("girls", monthGirls);
        //System.out.println(map);
        String content = JSON.toJSONString(map);
        //设置GoEasy连接参数：指定服务器地址，自己的AppkAppkey
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-50256662ac7e4725a0e3816b126e7d8b");
        //发布消息  参数：通道名称，发送的内容
        goEasy.publish("yingx_zcy", content);
        return map;
    }


    @RequestMapping("getEchartsChinaData")
    public ArrayList<Common> getEchartsChinaData() {

        ArrayList<Common> commonList = new ArrayList<>();

        List<City> citiesBoy = userService.queryCity("男");
        List<City> citiesGirl = userService.queryCity("女");

        commonList.add(new Common("小男孩", citiesBoy));
        commonList.add(new Common("小女孩", citiesGirl));

        return commonList;
    }
}

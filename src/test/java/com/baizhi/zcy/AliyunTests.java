package com.baizhi.zcy;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baizhi.zcy.dao.CategoryMapper;
import com.baizhi.zcy.dao.VideoMapper;
import com.baizhi.zcy.entity.Month;
import com.baizhi.zcy.service.UserService;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AliyunTests {
    @Resource
    VideoMapper videoMapper;
    @Resource
    CategoryMapper categoryMapper;
    @Resource
    UserService userService;

    @Test
    public void test1() {
        String s = "1月";
        System.out.println(s.charAt(0));
    }

    @Test
    public void test2() {

        List<String> months = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月");
        List<Month> monthBoys = userService.queryMonth("男");
        List<Month> monthGirls = userService.queryMonth("女");
        //1 3 6
        ArrayList<String> names = new ArrayList<String>();
        for (Month monthBoy : monthBoys) {
            names.add(monthBoy.getName());
        }
        for (String month : months) {
            System.out.println("month = " + month);
            if (!names.contains(month)) {
                //System.out.println("monthBoys.contains(month) = " + monthBoys.contains(month));
                monthBoys.add(new Month(month, "0"));
            }
        }
        System.out.println("*****************");
        Collections.sort(monthBoys);
        for (Month monthBoy : monthBoys) {
            System.out.println(monthBoy);
        }


    }

    @Test
    public void uploadFile() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4GE3yHsA5k9tqTsuGfrZ";
        String accessKeySecret = "6nrAppRYO7oLheegtpwXbK94TPMhOI";
        String bucketName = "yingx-zcy";
// <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String objectName = "1.jpg";

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。
        String content = "Hello OSS";
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));

// 关闭OSSClient。
        ossClient.shutdown();

    }
}

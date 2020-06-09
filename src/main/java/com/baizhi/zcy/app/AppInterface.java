package com.baizhi.zcy.app;


import com.baizhi.zcy.common.CommonResult;
import com.baizhi.zcy.entity.Category;
import com.baizhi.zcy.service.CategoryService;
import com.baizhi.zcy.service.VideoService;
import com.baizhi.zcy.util.SendSms;
import com.baizhi.zcy.vo.VideoVo;
import com.baizhi.zcy.vo.VideoVo1;
import com.baizhi.zcy.vo.VideoVoAttention;
import java.util.HashMap;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping("app")
public class AppInterface {


    /*前台APP发送验证码接口
     * phone:接收验证码手机号
     * 返回json数据
     * */
    @RequestMapping("getPhoneCodess")
    public HashMap<String, Object> getPhoneCodess(String phone) {

        //调用发送验证码方法发送验证码
        //1.获取验证码随机数
        HashMap<String, Object> map = new HashMap<>();

        String message = null;
        try {
            String randomCode = SendSms.getRandom(6);

            //2.存储验证码随机数
            System.out.println("randomCode: " + randomCode);
            //3.发送验证码
            message = SendSms.sendCode(phone, randomCode);

            //给对应的返回值

            map.put("data", phone);
            map.put("message", message);
            map.put("status", "100");
        } catch (Exception e) {
            //e.printStackTrace();
            map.put("data", phone);
            map.put("message", message);
            map.put("status", "104");
        }

        return map;
    }

    @RequestMapping("getPhoneCode")
    public CommonResult getPhoneCodes(String phone) {

        //调用发送验证码方法发送验证码
        //1.获取验证码随机数

        String message = null;
        try {
            String randomCode = SendSms.getRandom(6);

            //2.存储验证码随机数
            System.out.println("randomCode: " + randomCode);
            //3.发送验证码
            message = SendSms.sendCode(phone, randomCode);

            if (message.equals("发送成功")) {
                //给对应的返回值
                return new CommonResult().success("100", message, phone);
            } else {
                return new CommonResult().failed("104", message);
            }
        } catch (Exception e) {
            return new CommonResult().failed("104", message);
        }
    }

    @Resource
    VideoService videoService;

    @RequestMapping("queryByReleaseTime")
    public CommonResult queryByReleaseTime() {

        try {
            List<VideoVo> videoVoList = videoService.queryByReleaseTimes();
            return new CommonResult().success(videoVoList);
        } catch (Exception e) {
            return new CommonResult().failed();
        }
    }

    @RequestMapping("queryByLikeVideoName")
    public CommonResult queryByLikeVideoName(String content) {

        try {
            List<VideoVo1> videoVoList = videoService.queryByLikeVideoName(content);
            return new CommonResult().success(videoVoList);
        } catch (Exception e) {
            return new CommonResult().failed();
        }
    }

    @Resource
    CategoryService categoryService;

    @RequestMapping("queryAllCategory")
    public CommonResult queryAllCategory() {

        try {
            List<Category> categoryList = categoryService.queryAllCategory();
            return new CommonResult().success(categoryList);
        } catch (Exception e) {
            return new CommonResult().failed();
        }
    }

    //queryByUserMoving
    @RequestMapping("queryByUserMoving")
    public CommonResult queryByUserMoving(String userId) {
        try {
            List<VideoVoAttention> videoVoList = videoService.queryByUserMoving(userId);
            return new CommonResult().success(videoVoList);
        } catch (Exception e) {
            return new CommonResult().failed();
        }
    }
}

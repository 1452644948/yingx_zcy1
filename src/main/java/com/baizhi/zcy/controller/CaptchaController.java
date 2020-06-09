package com.baizhi.zcy.controller;

import com.baizhi.zcy.util.ImageCodeUtil;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller

public class CaptchaController {

    @RequestMapping("/captcha")
    public void generateCaptcha(HttpSession session,
                                HttpServletResponse response) throws IOException {
        //1. 获取验证码随机数
        String securityCode = ImageCodeUtil.getSecurityCode();
/**
 * 将随机数保存到session中
 */

        session.setAttribute("code", securityCode);
        //2.生成验证码图片
        BufferedImage image = ImageCodeUtil.createImage(securityCode);
        //3.响应到客户端
        ServletOutputStream out = response.getOutputStream();

/**
 * 参数一： 验证码图片对象
 * 参数二： 图片的类型
 * 参数三： 输出流对象
 */

        ImageIO.write(image, "png", out);
    }
}















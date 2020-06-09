package com.baizhi.zcy.controller;

import com.baizhi.zcy.entity.Video;
import com.baizhi.zcy.service.VideoService;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("video")
public class VideoController {
    @Resource
    VideoService videoServiceImpl;

    @ResponseBody
    @RequestMapping("queryByPage")
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        Map<String, Object> map = videoServiceImpl.queryByPage(page, rows);
        return map;
    }

    @ResponseBody
    @RequestMapping("edit")
    public String edit(Video video, String oper) {
        // System.out.println("添加 video = " + video);
        if (oper.equals("add")) {
            String vid = videoServiceImpl.add(video);
            return vid;
        }
        if (oper.equals("edit")) {
            String vid = videoServiceImpl.update(video);
            return vid;
        }
        if (oper.equals("del")) {
            videoServiceImpl.delete(video);
        }
        return "";
    }

    @RequestMapping("uploadVideo")
    public void uploadVideo(MultipartFile videoPath, String id, HttpServletRequest request) {
        System.out.println("上传图片" + videoPath.getName() + "id=" + id);
        if (videoPath != null) {
            videoServiceImpl.uploadFile(videoPath, id, request);
        }
    }

    @ResponseBody
    @RequestMapping("querySearch")
    public List<Video> querySearch(String content) {
        List<Video> videos = videoServiceImpl.querySearchs(content);
        return videos;
    }
}

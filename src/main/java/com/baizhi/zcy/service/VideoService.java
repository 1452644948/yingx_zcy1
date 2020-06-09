package com.baizhi.zcy.service;

import com.baizhi.zcy.entity.Video;
import com.baizhi.zcy.vo.VideoVo;
import com.baizhi.zcy.vo.VideoVo1;
import com.baizhi.zcy.vo.VideoVoAttention;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;

public interface VideoService {
    Map<String, Object> queryByPage(Integer page, Integer rows);

    String add(Video video);

    String update(Video video);

    void delete(Video video);

    void uploadFile(MultipartFile videoPath, String id, HttpServletRequest request);

    List<VideoVo> queryByReleaseTimes();

    List<VideoVo1> queryByLikeVideoName(String content);

    List<VideoVoAttention> queryByUserMoving(String userId);

    List<Video> querySearchs(String content);

    List<Video> querySearch(String content);
}

package com.baizhi.zcy.dao;


import com.baizhi.zcy.entity.Video;
import com.baizhi.zcy.vo.VideoVo;
import com.baizhi.zcy.vo.VideoVo1;
import com.baizhi.zcy.vo.VideoVoAttention;
import java.util.List;
import tk.mybatis.mapper.common.Mapper;

public interface VideoMapper extends Mapper<Video> {
    List<VideoVo> queryByReleaseTime();

    List<VideoVo1> queryByLikeVideoName(String content);

    List<VideoVoAttention> queryByUserMoving(String userId);
}
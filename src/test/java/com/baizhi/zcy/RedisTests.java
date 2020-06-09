package com.baizhi.zcy;

import com.baizhi.zcy.dao.VideoMapper;
import com.baizhi.zcy.entity.Video;
import com.baizhi.zcy.repository.VideoRepository;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTests {
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    VideoMapper videoMapper;
    @Resource
    VideoRepository videoRepository;
    @Resource
    ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void test1() {
        List<Video> videos = videoMapper.selectAll();

        for (Video video : videos) {
            videoRepository.save(video);

    /*  String name = (String) value.get("name");
      System.out.println(name);*/
        }
    }

    @Test
    public void test2() {
  /*    Iterable<Video> all = videoRepository.findAll();
      for (Video video : all) {
         System.out.println(video);
      }*/
        Optional<Video> byId = videoRepository.findById("552aa98f-6a47-472d-baa7-67ac6ef9dcd6");
        System.out.println(byId);
    }

}

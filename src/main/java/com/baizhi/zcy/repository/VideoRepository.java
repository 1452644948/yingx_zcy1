package com.baizhi.zcy.repository;


import com.baizhi.zcy.entity.Video;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

//泛型   <操作对象类型,序列化主键的类型>
public interface VideoRepository extends ElasticsearchRepository<Video, String> {

    List<Video> findByTitle(String title);
}

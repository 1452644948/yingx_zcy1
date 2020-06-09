package com.baizhi.zcy.service;

import com.baizhi.annotation.AddLog;
import com.baizhi.zcy.dao.VideoMapper;
import com.baizhi.zcy.entity.UserExample;
import com.baizhi.zcy.entity.Video;
import com.baizhi.zcy.entity.VideoExample;
import com.baizhi.zcy.util.AliyunOSSUtil;
import com.baizhi.zcy.vo.VideoVo;
import com.baizhi.zcy.vo.VideoVo1;
import com.baizhi.zcy.vo.VideoVoAttention;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.ibatis.session.RowBounds;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {
    @Resource
    VideoMapper videoMapper;

    @Override
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //总条数   records
        UserExample example = new UserExample();
        Integer records = videoMapper.selectCountByExample(example);
        map.put("records", records);

        //总页数  totals    总条数/每页展示条数
        Integer totals = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", totals);

        //当前页  page
        map.put("page", page);
        //数据   rows   分页
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Video> videos = videoMapper.selectByRowBounds(new Video(), rowBounds);
        map.put("rows", videos);
        return map;
    }

    @Override
    @AddLog(description = "添加视频")
    public String add(Video video) {
        video.setId(UUID.randomUUID().toString());
        video.setPulishDate(new Date());
        videoMapper.insertSelective(video);
        System.out.println("添加Service  video = " + video);
        return video.getId();
    }

    @Override
    @AddLog(description = "修改视频")
    public String update(Video video) {
        System.out.println("修改视频原来的video = " + video);
        if (video.getVideoPath() != null) {
            delete(video);
        }
        /*//修改之前要将原有的图片在阿里云上删除
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(video.getId());
        Video video1 = videoMapper.selectOneByExample(example);
       // System.out.println("修改service  video = " + video);
        //System.out.println("修改service  video1 = " + video1);
        String fileName=video1.getVideoPath();
        String[] split = fileName.split("/");
        *//*for (String substring : split) {
            System.out.println("substring = " + substring);
        }*//*
        String name=split[split.length-1];
        //System.out.println("name = " + name);
        AliyunOSSUtil.deleteFile("yingx-zcy","video/"+name);*/

        videoMapper.updateByPrimaryKeySelective(video);
        return video.getId();
    }

    @Override
    @AddLog(description = "删除视频")
    public void delete(Video video) {
        //System.out.println("删除video"+video);
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(video.getId());
        Video video1 = videoMapper.selectOneByExample(example);
        //System.out.println("video1 = " + video1);
        String fileName = video1.getVideoPath();
        String coverName = video1.getCoverPath();
        //String[] split = fileName.split("/");
        /*for (String substring : split) {
            System.out.println("substring = " + substring);
        }*/
        //String name=split[split.length-1];
        //System.out.println("name = " + name);
        String name = fileName.replace("https://yingx-zcy.oss-cn-beijing.aliyuncs.com/", "");
        String names = coverName.replace("https://yingx-zcy.oss-cn-beijing.aliyuncs.com/", "");
        AliyunOSSUtil.deleteFile("yingx-zcy", name);
        AliyunOSSUtil.deleteFile("yingx-zcy", names);
        videoMapper.delete(video);

    }

    @Override
    public void uploadFile(MultipartFile videoPath, String id, HttpServletRequest request) {
        //1.获取文件名
        String filename = videoPath.getOriginalFilename();
        //创建一个新的名字    原名称-时间戳  10.jpg-1590390153130
        String newName = new Date().getTime() + "-" + filename;
        System.out.println("filename = " + filename);
        //2.将文件上传至阿里云
        String bucketName = "yingx-zcy";         //存储空间名
        String fileName = "video/" + newName;   //文件名
        AliyunOSSUtil.uploadBytes(videoPath, bucketName, fileName);

        //截取封面图片
        String imagePath = AliyunOSSUtil.videoInterceptImage(bucketName, fileName);
        //上传视频封面路径 获取封面图片名称
        String[] coverNames = newName.split("\\.");
        String names = coverNames[0];
        String coverName = names + ".jpg";
        System.out.println("coverName = " + coverName);
        //上传
        try {
            AliyunOSSUtil.uploadNet("yingx-zcy", "cover/" + coverName, imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //3.修改图片路径
        //修改的条件
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(id);

        Video video = new Video();
        String netPath = "https://yingx-zcy.oss-cn-beijing.aliyuncs.com/" + fileName;
        video.setVideoPath(netPath); //设置修改的结果
        video.setCoverPath("https://yingx-zcy.oss-cn-beijing.aliyuncs.com/cover/" + coverName);
        //修改
        videoMapper.updateByExampleSelective(video, example);
    }

    @Override
    public List<VideoVo> queryByReleaseTimes() {
        List<VideoVo> videoVoList = videoMapper.queryByReleaseTime();
        for (VideoVo videoVo : videoVoList) {
            //获取视频id
            String videoId = videoVo.getId();
            //根据视频id查询视频点赞数
            Integer likeCount = 18;
            //将视频点赞数放入对象
            videoVo.setLikeCount(likeCount);
        }
        return videoVoList;
    }

    @Override
    public List<VideoVo1> queryByLikeVideoName(String content) {
        List<VideoVo1> videoVoList = videoMapper.queryByLikeVideoName(content);
        for (VideoVo1 videoVo : videoVoList) {
            //获取视频id
            String videoId = videoVo.getId();
            //根据视频id查询视频点赞数
            Integer likeCount = 18;
            //将视频点赞数放入对象
            videoVo.setLikeCount(likeCount);
        }
        return videoVoList;
    }

    @Override
    public List<VideoVoAttention> queryByUserMoving(String userId) {
        List<VideoVoAttention> voAttentionList = videoMapper.queryByUserMoving(userId);
        for (VideoVoAttention videoVo : voAttentionList) {
            //获取视频id
            String videoId = videoVo.getId();
            //根据视频id查询视频点赞数
            Integer likeCount = 18;
            //将视频点赞数放入对象
            videoVo.setLikeCount(likeCount);
        }
        return voAttentionList;
    }

    @Resource
    ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<Video> querySearchs(String content) {

        HighlightBuilder.Field field = new HighlightBuilder.Field("*");
        field.preTags("<span style='color:red'>"); //前缀
        field.postTags("</span>"); //后缀

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingx") //指定索引
                .withTypes("video")  //指定类型
                .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("brief")) //指定查询条件，匹配的字段
                .withHighlightFields(field) //处理高亮
                //.withFields("id","title","brief") //查询结果只要这些字段
                .build();

        //调用搜索方法 参数：searchQuery(查询参数，条件),返回集合的泛型
        AggregatedPage<Video> videos = elasticsearchTemplate.queryForPage(searchQuery, Video.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {

                ArrayList<Video> videos = new ArrayList<>();

                //获取查询结果
                SearchHit[] hits = searchResponse.getHits().getHits();

                for (SearchHit hit : hits) {

                    //没有高亮的结果
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();

                    String id = sourceAsMap.get("id") != null ? sourceAsMap.containsKey("id") ? sourceAsMap.get("id").toString() : null : null;
                    String title = sourceAsMap.get("title") != null ? sourceAsMap.containsKey("title") ? sourceAsMap.get("title").toString() : null : null;
                    String brief = sourceAsMap.get("brief") != null ? sourceAsMap.containsKey("brief") ? sourceAsMap.get("brief").toString() : null : null;
                    String videoPath = sourceAsMap.get("videoPath") != null ? sourceAsMap.containsKey("videoPath") ? sourceAsMap.get("videoPath").toString() : null : null;
                    String coverPath = sourceAsMap.get("coverPath") != null ? sourceAsMap.containsKey("coverPath") ? sourceAsMap.get("coverPath").toString() : null : null;
                    String categoryId = sourceAsMap.get("categoryId") != null ? sourceAsMap.containsKey("categoryId") ? sourceAsMap.get("categoryId").toString() : null : null;
                    String groupId = sourceAsMap.get("groupId") != null ? sourceAsMap.containsKey("groupId") ? sourceAsMap.get("groupId").toString() : null : null;
                    String userId = sourceAsMap.get("userId") != null ? sourceAsMap.containsKey("userId") ? sourceAsMap.get("userId").toString() : null : null;
                    boolean publishDate1 = sourceAsMap.containsKey("pulishDate");
                    // System.out.println("日期 " + sourceAsMap.get("pulishDate").toString());
                    Date pulishDate = null;
                    if (sourceAsMap.get("pulishDate") != null) {
                        if (publishDate1) {
                            String pulishDates = sourceAsMap.get("pulishDate").toString();
                            //将时间戳转化为日期格式
                            long parseDate = Long.parseLong(pulishDates);
                            System.out.println("video service 的 pulishDates = " + pulishDates);
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String date = sdf.format(new Date(Long.parseLong(String.valueOf(parseDate))));
                                pulishDate = sdf.parse(date);
                                System.out.println("video service 的 " + date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    Video video = new Video(id, title, brief, videoPath, coverPath, pulishDate, categoryId, groupId, userId);
                    //高亮的结果
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    if (title != null) {
                        if (highlightFields.get("title") != null) {
                            String titles = highlightFields.get("title").fragments()[0].toString();
                            video.setTitle(titles);
                        }
                    }
                    if (brief != null) {
                        if (highlightFields.get("brief") != null) {
                            String briefs = highlightFields.get("brief").fragments()[0].toString();
                            video.setBrief(briefs);
                        }
                    }
                    //System.out.println("==高亮的结果==" + video);
                    //将对象放入集合
                    videos.add(video);
                }
                //强转 返回
                return new AggregatedPageImpl<T>((List<T>) videos);
            }
        });
        List<Video> videoList = videos.getContent();
        return videoList;
    }

    @Override
    public List<Video> querySearch(String content) {

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingx") //指定索引
                .withTypes("video")  //指定类型
                .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("brief")) //指定查询条件，匹配的字段
                //.withQuery(QueryBuilders.termQuery("title",content))
                .build();

        //调用搜索方法 参数：searchQuery(查询参数，条件),返回集合的泛型
        List<Video> videos = elasticsearchTemplate.queryForList(searchQuery, Video.class);
        return videos;
    }
}

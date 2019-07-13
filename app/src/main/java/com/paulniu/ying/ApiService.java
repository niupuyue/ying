package com.paulniu.ying;

import com.paulniu.ying.model.data.MovieDetailModel;
import com.paulniu.ying.model.data.MovieModel;
import com.paulniu.ying.model.data.MoviePhotosModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 18:59
 * Desc: 网络请求
 * Version:
 */
public interface ApiService {

    String baseUrl = "https://api.douban.com/v2/movie/";

    int START_INDEX = 0;
    int LIMIT = 10;

    /**
     * 获取电影列表
     * https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&start=0&count=100&client=somemessage&udid=dddddddddddddddddddddd
     */
    @GET("in_theaters")
    Observable<MovieModel> getMovieList(
            @Query("city") String city,
            @Query("apikey") String apikey,
            @Query("start") int start,
            @Query("count") int count
    );

    /**
     * 电影详情
     * http://api.douban.com/v2/movie/subject/ + 电影 id
     */
    @GET("subject/{id}")
    Observable<MovieDetailModel> getMovieDetail(
            @Path("id") String movieId,
            @Query("apikey") String apiKey,
            @Query("city") String city
    );

    /**
     * 电影的图片，剧照
     * http://api.douban.com/v2/movie/subject/26865690?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&client=something&udid=dddddddddddddddddddddd
     */
    @GET("subject/{id}/photos")
    Observable<MoviePhotosModel> getMoviePhotos(
            @Path("id") String movieId,
            @Query("apikey") String apiKey,
            @Query("city") String city
    );

}

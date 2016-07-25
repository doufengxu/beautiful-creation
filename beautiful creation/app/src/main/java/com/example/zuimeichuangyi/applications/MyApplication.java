package com.example.zuimeichuangyi.applications;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.zhy.http.okhttp.OkHttpUtils;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Created by Administrator on 2016/4/31.
 */
public class MyApplication extends Application{
    private int from;// 属于来自谁的list,(1,2,3,...)
    // ------------------------所有---------------------
    // from=1
    private List<Map<String, Object>> all_ad_list = new ArrayList<Map<String, Object>>();// 广告列表(所有)
    // from=2
    private List<Map<String, Object>> all_listview_list = new ArrayList<Map<String, Object>>();// ListView列表(所有)
    // 向您推荐
    private List<Map<String, Object>> all_recommend_list = new ArrayList<Map<String, Object>>();// ListView列表(推荐)
    // from=3
    private List<Map<String, Object>> album_ad_list = new ArrayList<Map<String, Object>>();// 广告列表(所有)
    // from=4
    private List<Map<String, Object>> album_listview_list = new ArrayList<Map<String, Object>>();// ListView列表(所有)
    // 列表
    private List<Map<String, Object>> album_recommend_list = new ArrayList<Map<String, Object>>();// ListView列表(推荐)
    // 评论
    private List<Map<String, Object>> all_comment_list = new ArrayList<Map<String, Object>>();// ListView列表(推荐)
    // 数据库收藏列表
    private List<Map<String, Object>> db_list = new ArrayList<Map<String, Object>>();



    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public List<Map<String, Object>> getAll_ad_list() {
        return all_ad_list;
    }

    public void setAll_ad_list(List<Map<String, Object>> all_ad_list) {
        this.all_ad_list = all_ad_list;
    }

    public List<Map<String, Object>> getAll_listview_list() {
        return all_listview_list;
    }

    public void setAll_listview_list(List<Map<String, Object>> all_listview_list) {
        this.all_listview_list = all_listview_list;
    }



    public List<Map<String, Object>> getAll_recommend_list() {
        return all_recommend_list;
    }

    public void setAll_recommend_list(
            List<Map<String, Object>> all_recommend_list) {
        this.all_recommend_list = all_recommend_list;
    }

    public List<Map<String, Object>> getAlbum_ad_list() {
        return album_ad_list;
    }

    public void setAlbum_ad_list(List<Map<String, Object>> album_ad_list) {
        this.album_ad_list = album_ad_list;
    }

    public List<Map<String, Object>> getAlbum_listview_list() {
        return album_listview_list;
    }

    public void setAlbum_listview_list(
            List<Map<String, Object>> album_listview_list) {
        this.album_listview_list = album_listview_list;
    }

    public List<Map<String, Object>> getAlbum_recommend_list() {
        return album_recommend_list;
    }

    public void setAlbum_recommend_list(
            List<Map<String, Object>> album_recommend_list) {
        this.album_recommend_list = album_recommend_list;
    }

    public List<Map<String, Object>> getAll_comment_list() {
        return all_comment_list;
    }

    public void setAll_comment_list(List<Map<String, Object>> all_comment_list) {
        this.all_comment_list = all_comment_list;
    }

    public List<Map<String, Object>> getDb_list() {
        return db_list;
    }

    public void setDb_list(List<Map<String, Object>> db_list) {
        this.db_list = db_list;
    } @Override
    public void onCreate() {
        super.onCreate();
       OkHttpUtils.getInstance().debug("OkHttpUtils").setConnectTimeout(100000, TimeUnit.MILLISECONDS);
       Fresco.initialize(this);
        x.Ext.init(this);
    }

}

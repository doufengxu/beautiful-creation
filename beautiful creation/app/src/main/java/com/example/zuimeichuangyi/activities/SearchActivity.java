package com.example.zuimeichuangyi.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zuimeichuangyi.MainActivity;
import com.example.zuimeichuangyi.R;
import com.example.zuimeichuangyi.adapters.MyItemsListAdapter;
import com.example.zuimeichuangyi.applications.MyApplication;
import com.example.zuimeichuangyi.parse.ParseAdJson;
import com.example.zuimeichuangyi.urlutils.Url;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private EditText searchedit;
    private PullToRefreshListView pullToRefreshListView;
    private int page = 1;
    private MyItemsListAdapter itemsListAdapter;
    private List<Map<String, Object>> allList = new ArrayList<Map<String, Object>>();
    private MyApplication myApplication;
    private String url="";
    private String str="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        myApplication = (MyApplication) SearchActivity.this.getApplication();
        initview();
    }
    private void initview() {
        searchedit= (EditText) findViewById(R.id.search_edit);
        pullToRefreshListView= (PullToRefreshListView) findViewById(R.id.pulltorefresh_all);
        searchedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //编辑框及时刷新


               if (!(searchedit.getText().toString().trim().equals(""))){
                   try {
                       str = URLEncoder.encode(searchedit.getText().toString().trim(), "UTF-8");
                       url = Url.SEARCH1 + str + Url.SEARCH2;
                   } catch (UnsupportedEncodingException e) {
                       e.printStackTrace();
                   }
                   allList.clear();
                   init();
                   itemsListAdapter.notifyDataSetChanged();
               }else {
                   //Toast.makeText(SearchActivity.this,"____"+searchedit.getText(), Toast.LENGTH_LONG).show();
                   allList.clear();
                   url="";
                   itemsListAdapter.notifyDataSetChanged();
               }


            }
        });
    }
    private void init() {

        //      volleyGson=new VolleyGson(MainActivity.this);
        //设置都可以拉动
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        //设置刷新的监听
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉的回调
                // 下拉刷新
                // 设置属性
                ILoadingLayout layout = pullToRefreshListView
                        .getLoadingLayoutProxy();
                layout.setLoadingDrawable(getResources().getDrawable(
                        R.mipmap.ic_launcher));
                allList.clear();
                page = 1;
                new Thread(new RefreshThread()).start();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉的回调，实现分页的逻辑
                // 上拉加载
                // 设置属性
                ILoadingLayout layout = pullToRefreshListView
                        .getLoadingLayoutProxy();
                layout.setLoadingDrawable(getResources().getDrawable(
                        R.mipmap.ic_launcher));
                // 从网络请求新的数据进行加载
                page += 10;
                new Thread(new RefreshThread()).start();
            }
        });
        new Thread(new RefreshThread()).start();
        itemsListAdapter=new MyItemsListAdapter(SearchActivity.this,allList);
        pullToRefreshListView.setAdapter(itemsListAdapter);
        pullToRefreshListView.setOnItemClickListener(SearchActivity.this);
    }

    public void search_clearedit(View view) {

        searchedit.setText("");
    }

    public void search_goback(View view) {
        Intent intent=new Intent();
        intent.setClass(SearchActivity.this, MainActivity.class);
        startActivity(intent);

    }

    private class RefreshThread implements Runnable {
        @Override
        public void run() {
            getData(page, 1);
        }
    }

    public void getData(int pageNo, final int type) {



        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                String json = response.body().string();

                Log.e("xxxx", "----->" + json);
                // 获取JSON数据
                List<Map<String, Object>> list = ParseAdJson.getAllListJson(json);
                if (type == 1) {
                    // 下拉刷新1
                    allList.addAll(list);
                } else {
                    // 上拉加载2
                    allList.addAll(list);
                }
                myApplication.setAll_listview_list(allList);
                // TODO: 2016/5/1  用post代替handler发送消息,不行的话再换成handler发送消息

                pullToRefreshListView.post(new Runnable() {
                    // TODO: 2016/4/30 加载适配出错了?????
                    @Override
                    public void run() {

                        itemsListAdapter.notifyDataSetChanged();

                        pullToRefreshListView.onRefreshComplete();
                    }
                });
            }
        });
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        myApplication.setFrom(1);
        Intent intent = new Intent(SearchActivity.this, VideoClassifyActivity.class);
        intent.putExtra("position", id);

        intent.putExtra("rsId",(String) allList.get((int)id).get("rsId"));
        intent.putExtra("albumId",(String) allList.get((int)id).get("albumId"));
        intent.putExtra("modulesId",(String) allList.get((int)id).get("modulesId"));
        intent.putExtra("modulesColor",(String) allList.get((int)id).get("modulesColor"));
        intent.putExtra("title",(String) allList.get((int)id).get("title"));
        intent.putExtra("description",(String) allList.get((int)id).get("description"));
        intent.putExtra("duration", (Double)allList.get((int)id).get("duration"));
        intent.putExtra("jsonString", (String) allList.get((int) id).get("jsonString"));
        intent.putExtra("url",url);

        startActivity(intent);
    }
}

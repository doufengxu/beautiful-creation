package com.example.zuimeichuangyi.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zuimeichuangyi.R;
import com.example.zuimeichuangyi.activities.VideoActivity;
import com.example.zuimeichuangyi.adapters.AllListViewAdapter;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment implements AdapterView.OnItemClickListener {


    private PullToRefreshListView pullToRefreshListView;
    private View ret;
    private List<Map<String, Object>> allList = new ArrayList<Map<String, Object>>();
    private int pageNo = 0;
    private AllListViewAdapter adapter;
    private MyApplication myApplication;

    public AllFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ret = null;
        ret = inflater.inflate(R.layout.fragment_all, container, false);
        init();
        initpullToRefreshListView();
        return ret;
    }

    private void initpullToRefreshListView() {
        // 设置模式
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
                pageNo = 0;
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
                pageNo += 10;
                new Thread(new RefreshThread()).start();
            }
        });
        // 获取数据源
        new Thread(new RefreshThread()).start();

        adapter = new AllListViewAdapter(getActivity(), allList);

        pullToRefreshListView.setAdapter(adapter);

        pullToRefreshListView.setOnItemClickListener(this);

    }

    /**
     * 获取数据源
     */
    public void getData(int pageNo, final int type) {

        String url = Url.ALL_LIST1 + pageNo + Url.ALL_LIST2;

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

                Toast.makeText(getContext(), "网络连接错误", Toast.LENGTH_SHORT).show();

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

                            adapter.notifyDataSetChanged();

                            pullToRefreshListView.onRefreshComplete();

                        }
                });
            }
        });
    }

    private void init() {

        //初始化查找控件
        pullToRefreshListView = (PullToRefreshListView) ret.findViewById(R.id.pulltorefresh_all);

    }

    //点击listview条目进行跳转
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        myApplication.setFrom(1);
        Intent intent = new Intent(getActivity(), VideoActivity.class);
        // TODO: 2016/4/30  看看头部广告和刷新动画2个item,如果有,就在position的面减2
       // Toast.makeText(getContext(),""+position,Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),""+id,Toast.LENGTH_SHORT).show();
        intent.putExtra("position", (int)id);
        startActivity(intent);
    }

    //分页加载

    private class RefreshThread implements Runnable {
        @Override
        public void run() {
            getData(pageNo, 1);
        }
    }
}

package com.example.zuimeichuangyi.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.zuimeichuangyi.R;
import com.example.zuimeichuangyi.activities.ClassifyActivity;
import com.example.zuimeichuangyi.adapters.GridViewAdapter;
import com.example.zuimeichuangyi.parse.ParseAdJson;
import com.example.zuimeichuangyi.urlutils.Url;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassifyFragment extends Fragment {

    private GridView gridView;
    private String[] arr;
    private GridViewAdapter gridViewAdapter;
    private List<String> list=new ArrayList<>();
    public ClassifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_classify, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView= (GridView) view.findViewById(R.id.gridview_classify);
        arr=getResources().getStringArray(R.array.classify);
        list= Arrays.asList(arr);
        Log.d("flag","=11="+list.size());
        gridViewAdapter=new GridViewAdapter(list,getContext());
        gridView.setAdapter(gridViewAdapter);

        initClick();
    }

    private void initClick() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(getContext()," "+list.get(position),Toast.LENGTH_SHORT).show();
                switch (list.get(position)) {
                    case "公益/社会":
                        Toast.makeText(getContext()," "+list.get(position),Toast.LENGTH_SHORT).show();
                        initclick(12);
                        break;
                    case "生活/烦恼":
                        Toast.makeText(getContext(), " " + list.get(position), Toast.LENGTH_SHORT).show();
                        initclick(11);;
                        break;
                    case "游戏/动漫":
                        Toast.makeText(getContext(), " " + list.get(position), Toast.LENGTH_SHORT).show();
                        initclick(1);
                        break;
                    case "时尚/经典":
                        Toast.makeText(getContext(), " " + list.get(position), Toast.LENGTH_SHORT).show();
                        initclick(7);
                        break;
                    case "奇趣/欢乐":
                        Toast.makeText(getContext(), " " + list.get(position), Toast.LENGTH_SHORT).show();
                        initclick(5);
                        break;
                    case "科技/产品":
                        Toast.makeText(getContext(), " " + list.get(position), Toast.LENGTH_SHORT).show();
                        initclick(6);
                        break;
                    case "旅游/指南":
                        Toast.makeText(getContext(), " " + list.get(position), Toast.LENGTH_SHORT).show();
                        initclick(2);
                        break;
                    case "运动/极限":
                        Toast.makeText(getContext(), " " + list.get(position), Toast.LENGTH_SHORT).show();
                        initclick(3);
                        break;
                    case "汽车/安全":
                        Toast.makeText(getContext(), " " + list.get(position), Toast.LENGTH_SHORT).show();
                        initclick(4);
                        break;
                    case "亲子/情感":
                        Toast.makeText(getContext(), " " + list.get(position), Toast.LENGTH_SHORT).show();
                        initclick(9);
                        break;
                    case "读书/教育":
                        Toast.makeText(getContext(), " " + list.get(position), Toast.LENGTH_SHORT).show();
                        initclick(10);
                        break;
                    case "工艺/设计":
                        Toast.makeText(getContext(), " " + list.get(position), Toast.LENGTH_SHORT).show();
                        initclick(14);
                        break;
                }

            }
        });

    }

    private void initclick(int i) {
        String url= Url.CLASSIFYITEM1+0+Url.CLASSIFYITEM2+i+Url.CLASSIFYITEM3;
        Log.d("flag","url======"+url);

        Intent intent=new Intent(getContext(),ClassifyActivity.class);
        intent.putExtra("modulesId",i);
       // intent.putExtra("url",url);
        startActivity(intent);
    }
}

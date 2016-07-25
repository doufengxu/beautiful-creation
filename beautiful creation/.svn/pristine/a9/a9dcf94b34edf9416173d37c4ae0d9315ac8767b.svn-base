package com.example.zuimeichuangyi.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zuimeichuangyi.R;

import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class GridViewAdapter extends BaseAdapter{

    private List<String> list;
    private Context context;

    public GridViewAdapter(List<String>  list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        Log.d("flag","======================"+list.size());
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.classify_item,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.imageViewClassify= (ImageView) convertView.findViewById(R.id.image_classify);
            viewHolder.textViewClassify= (TextView) convertView.findViewById(R.id.textview_classify);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Log.d("flag", "===" + position + "===" + list.get(position));
        viewHolder.textViewClassify.setText(list.get(position));
        viewHolder.imageViewClassify.setImageResource(R.mipmap.ic_launcher);
        return convertView;
    }

    class ViewHolder{
        ImageView imageViewClassify;
        TextView textViewClassify;
    }
}

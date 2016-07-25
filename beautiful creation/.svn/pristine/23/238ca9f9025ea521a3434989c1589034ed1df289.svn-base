package com.example.zuimeichuangyi.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zuimeichuangyi.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * Created by Administrator on 2016/4/30.
 * 所有(列表)
 * 
 * 所有列表 自定义适配器
 * 
 * @author mgc
 * 
 */
public class AllListViewAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	public AllListViewAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_mylistview, null);
			holder.imageView = (SimpleDraweeView) convertView
					.findViewById(R.id.iv_item_mylistview_pic);
			holder.textView_play_count = (TextView) convertView
					.findViewById(R.id.tv_item_mylistview_playcount);
			holder.textView_comment_count = (TextView) convertView
					.findViewById(R.id.tv_item_mylistview_comment_count);
			holder.textView_duration = (TextView) convertView
					.findViewById(R.id.tv_item_mylistview_duration);
			holder.textView_title = (TextView) convertView
					.findViewById(R.id.tv_item_mylistview_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		/**
		 * 设置文本 格式化时间xx:xx
		 */
		double duration = (Double) list.get(position).get("duration");
		int min = (int) (duration / 60);// 分钟
		int s = (int) (duration % 60);// 秒
		String strMin = "";
		String strS = "";
		if (min <= 9) {
			strMin = "0" + min;
		} else {
			strMin = "" + min;
		}
		if (s <= 9) {
			strS = "0" + s;
		} else {
			strS = "" + s;
		}

		// 播放次数
		holder.textView_play_count.setText(list.get(position).get("viewCount")
				.toString());
		// 评论条数
		holder.textView_comment_count.setText(list.get(position)
				.get("commentcount").toString());
		// 视频时长
		holder.textView_duration.setText(strMin + ":" + strS);
		// 标题
		holder.textView_title.setText(list.get(position).get("title")
				.toString());
		// 设置背景图片
		holder.imageView.setImageURI(Uri.parse(list.get(position).get("thumbnail").toString()));

		return convertView;
	}

	class ViewHolder {
		private SimpleDraweeView imageView;// 背景图片
		private TextView textView_play_count;// 播放次数
		private TextView textView_comment_count;// 评论
		private TextView textView_duration;// 视频时长
		private TextView textView_title;// 标题

	}
}

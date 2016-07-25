package com.example.zuimeichuangyi.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.zuimeichuangyi.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 向您推荐
 * 
 * 向您推荐，横向列表自定义适配器
 * 
 * @author mgc
 *
 */
public class VideoRecommendAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	public VideoRecommendAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();

			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_videolistview, null);

			holder.imageView = (SimpleDraweeView) convertView
					.findViewById(R.id.iv_itemvideo);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.imageView.setImageURI(Uri.parse(list.get(position).get("thumbnail").toString()));

		return convertView;
	}

	class ViewHolder {
		private SimpleDraweeView imageView;
	}
}

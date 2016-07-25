package com.example.zuimeichuangyi.parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseAdJson {

	/**
	 *  2016/5/1
	 * 获取json数据 JSON解析
	 * 所有的json数据解析不建实体类,已post请求发送,返回字符串
	 * @return
	 */
	public static List<Map<String, Object>> getAlbumAdvertisementJson(String str) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			JSONObject obj = new JSONObject(str);

			JSONArray array = obj.getJSONArray("album");

			for (int i = 0; i < array.length(); i++) {

				Map<String, Object> map = new HashMap<String, Object>();

				JSONObject obj2 = array.getJSONObject(i);

				String albumImg = obj2.getString("albumImg");// 图片

				String albumColor = obj2.getString("albumColor");// 颜色

				map.put("albumImg", albumImg);

				map.put("albumColor", albumColor);

				JSONArray jsonarray = obj2.getJSONArray("resourceses");// 内容数据

				for (int j = 0; j < jsonarray.length(); j++) {

					JSONObject obj3 = jsonarray.getJSONObject(j);

					String jsonString = obj3.toString();

					String rsId = obj3.getString("rsId");// id

					String title = obj3.getString("title");// 标题

					String thumbnail = obj3.getString("thumbnail");// 视频未播放时显示的图片

					double duration = obj3.getDouble("duration");// 播放时长

					String description = "";

					try {

						description = obj3.getString("description");// 文字内容

					} catch (Exception e) {

						// TODO: handle exception
					}

					String viewCount = obj3.getString("viewCount");// 视频点击次数
					String commentcount = obj3.getString("commentcount");// 评论数
					String link = obj3.getString("link");
					map.put("rsId", rsId);
					JSONObject objx = obj3.getJSONObject("modules");
					String modulesId = objx.getString("modulesId");
					String modulesColor = objx.getString("modulesColor");
					if (modulesColor.equals("")) {
						modulesColor = "#474747";
					}
					map.put("modulesId", modulesId);
					map.put("modulesColor", modulesColor);
					map.put("title", title);
					map.put("jsonString", jsonString);
					map.put("link", link);
					map.put("thumbnail", thumbnail);
					map.put("duration", duration);
					map.put("description", description);
					map.put("viewCount", viewCount);
					map.put("commentcount", commentcount);
				}

				list.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 每一项的内容(广告栏和列表项)
	 *  2016/5/1
	 * @param str
	 * @return
	 */

	public static List<Map<String, Object>> getItemContent(String str) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONArray array;
		try {
			array = new JSONArray(str);
			for (int i = 0; i < array.length(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject obj = array.getJSONObject(i);
				String jsonString = obj.toString();
				String rsId = obj.getString("rsId");
				JSONObject objx = obj.getJSONObject("modules");
				String modulesId = "";
				String modulesColor = objx.getString("modulesColor");

				String title = obj.getString("title");// 标题
				String thumbnail = obj.getString("thumbnail");// 视频未播放时显示的图片
				double duration = obj.getDouble("duration");// 播放时长
				
				String description = "";
				try {
					description = obj.getString("description");// 文字内容
				} catch (Exception e) {
					// TODO: handle exception
				}

				String viewCount = obj.getString("viewCount");// 视频点击次数
				String commentcount = obj.getString("commentcount");// 评论数
				String tag = obj.getString("tag");// 搜索标签
				String link = obj.getString("link");
				map.put("rsId", rsId);
				map.put("jsonString", jsonString);
				map.put("modulesId", modulesId);
				map.put("modulesColor", modulesColor);
				map.put("title", title);
				map.put("link", link);
				map.put("thumbnail", thumbnail);
				map.put("duration", duration);
				map.put("description", description);
				map.put("viewCount", viewCount);
				map.put("commentcount", commentcount);
				map.put("tag", tag);
				list.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取列表标题
	 *  2016/5/1
	 * @param str
	 * @return
	 */

	public static List<Map<String, Object>> getAubumListTitle(String str) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			JSONObject obj = new JSONObject(str);
			JSONArray array = obj.getJSONArray("album");
			for (int i = 0; i < array.length(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject obj2 = array.getJSONObject(i);
				String albumId = obj2.getString("albumId");
				String albumName = obj2.getString("albumName");
				String albumColor = obj2.getString("albumColor");
				String resourceses = obj2.getJSONArray("resourceses")
						.toString();
				map.put("albumName", albumName);
				map.put("albumColor", albumColor);
				map.put("resourceses", resourceses);
				map.put("albumId", albumId);
				list.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 所有
	 *  2016/5/1
	 * @param json
	 * @return
	 */
	public static List<Map<String, Object>> getAllAdvertisementJson(String json) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			JSONObject obj = new JSONObject(json);
			JSONArray array = obj.getJSONArray("resources");
			for (int i = 0; i < array.length(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject obj1 = array.getJSONObject(i);
				String rsId = obj1.getString("rsId");// 获取ID
				String title = obj1.getString("title");// 标题
				String thumbnail = obj1.getString("thumbnail");// 默认图片
				double duration = obj1.getDouble("duration");// 视频时长
				String description = "";
				String jsonString = obj1.toString();
				String link = obj1.getString("link");
				try {
					description = obj1.getString("description");// 描述
				} catch (Exception e) {
				}

				int viewCount = obj1.getInt("viewCount");// 播放次数
				int commentcount = obj1.getInt("commentcount");// 评论
				String hotCover = obj1.getString("hotCover");// 广告图片
				JSONObject obj2 = obj1.getJSONObject("modules");
				String modulesId = obj2.getString("modulesId");
				String modulesColor = obj2.getString("modulesColor");// 背景色

				map.put("rsId", rsId);
				map.put("title", title);
				map.put("thumbnail", thumbnail);
				map.put("duration", duration);
				map.put("description", description);
				map.put("viewCount", viewCount);
				map.put("commentcount", commentcount);
				map.put("hotCover", hotCover);
				map.put("modulesColor", modulesColor);
				map.put("modulesId", modulesId);
				map.put("link", link);
				map.put("jsonString", jsonString);
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取所有的列表(ListView)
	public static List<Map<String, Object>> getAllListJson(String json) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			JSONObject obj = new JSONObject(json);
			JSONArray array = obj.getJSONArray("resources");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj1 = array.getJSONObject(i);
				String rsId = obj1.getString("rsId");// 获取ID
				String title = obj1.getString("title");// 标题
				String thumbnail = obj1.getString("thumbnail");// 默认图片
				double duration = obj1.getDouble("duration");// 视频时长
				String jsonString = obj1.toString();
				String link = obj1.getString("link");
				String description = "";
				try {
					description = obj1.getString("description");// 描述
				} catch (Exception e) {
					// TODO: 2016/5/1 handle exception
				}

				int viewCount = obj1.getInt("viewCount");// 播放次数
				int commentcount = obj1.getInt("commentcount");// 评论
				JSONObject obj2 = obj1.getJSONObject("modules");
				String modulesId = obj2.getString("modulesId");
				String modulesColor = obj2.getString("modulesColor");// 背景色
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("rsId", rsId);
				map.put("title", title);
				map.put("thumbnail", thumbnail);
				map.put("duration", duration);
				map.put("description", description);
				map.put("viewCount", viewCount);
				map.put("commentcount", commentcount);
				map.put("modulesId", modulesId);
				map.put("modulesColor", modulesColor);
				map.put("link", link);
				map.put("jsonString", jsonString);
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<Map<String, Object>> getKindJson(String json) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			JSONObject j1 = new JSONObject(json);
			JSONArray j2 = j1.getJSONArray("modules");
			for (int i = 0; i < j2.length(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject j3 = j2.getJSONObject(i);
				String modulesId = j3.getString("modulesId");
				map.put("modulesId", modulesId);
				String modulesName = j3.getString("modulesName");
				map.put("modulesName", modulesName);
				String sort = j3.getString("sort");
				map.put("sort", sort);
				String modulsCover = j3.getString("modulsCover");
				map.put("modulsCover", modulsCover);
				String modulesColor = j3.getString("modulesColor");
				map.put("modulesColor", modulesColor);
				list.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取视频链接
	public static String getVideoJSON(String json) {
		String player = "";
		try {
			JSONObject obj = new JSONObject(json);
			player = obj.getString("player");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return player;
	}

	// 评论
	public static List<Map<String, Object>> getCommentJSON(String json) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			JSONObject obj = new JSONObject(json);
			JSONArray array = obj.getJSONArray("comments");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj_1 = array.getJSONObject(i);
				String commentId = obj_1.getString("commentId");
				String content = obj_1.getString("content");
				String published = obj_1.getString("published");
				JSONObject obj_2 = obj_1.getJSONObject("users");
				String nickname = obj_2.getString("nickname");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("commentId", commentId);
				map.put("content", content);
				map.put("published", published);
				map.put("nickname", nickname);
				list.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<Map<String, Object>> getJsonObjectData(String str) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject obj = new JSONObject(str);
			String rsId = obj.getString("rsId");
			JSONObject obj2 = obj.getJSONObject("modules");
			String modulesId = obj2.getString("modulesId");
			String modulesColor = obj2.getString("modulesColor");
			String title = obj.getString("title");
			String link = obj.getString("link");
			String thumbnail = obj.getString("thumbnail");
			double duration = obj.getDouble("duration");
			String description = "";
			try {
				description = obj.getString("description");
			} catch (Exception e) {
				// TODO: 2016/5/1  handle exception
			}

			int viewCount = obj.getInt("viewCount");
			int commentcount = obj.getInt("commentcount");

			map.put("rsId", rsId);
			map.put("title", title);
			map.put("thumbnail", thumbnail);
			map.put("duration", duration);
			map.put("description", description);
			map.put("viewCount", viewCount);
			map.put("commentcount", commentcount);
			map.put("modulesColor", modulesColor);
			map.put("modulesId", modulesId);
			map.put("link", link);
			list.add(map);
		} catch (JSONException e) {
			// TODO: 2016/5/1 Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}

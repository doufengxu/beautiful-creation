package com.example.zuimeichuangyi.db;


import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "collect")
public class Collect {

	@Id(column = "_id")
	private int _id;
	@Column(column = "rsId")
	private String rsId;
	@Column(column = "collecttime")
	private String collecttime;
	@Column(column = "jsonString")
	private String jsonString;
	@Column(column = "albumId")
	private String albumId;

	public Collect(int _id, String rsId, String collecttime, String jsonString,
			String albumId) {
		super();
		this._id = _id;
		this.rsId = rsId;
		this.collecttime = collecttime;
		this.jsonString = jsonString;
		this.albumId = albumId;
	}

	public Collect() {
		super();
		// TODO: 2016/5/1  
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getRsId() {
		return rsId;
	}

	public void setRsId(String rsId) {
		this.rsId = rsId;
	}

	public String getCollecttime() {
		return collecttime;
	}

	public void setCollecttime(String collecttime) {
		this.collecttime = collecttime;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

}

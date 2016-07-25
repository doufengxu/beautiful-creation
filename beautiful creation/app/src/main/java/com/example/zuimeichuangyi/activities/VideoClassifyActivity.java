package com.example.zuimeichuangyi.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zuimeichuangyi.R;
import com.example.zuimeichuangyi.adapters.VideoRecommendAdapter;
import com.example.zuimeichuangyi.applications.MyApplication;
import com.example.zuimeichuangyi.customview.HorizontalListView;
import com.example.zuimeichuangyi.db.CollectDbHelper;
import com.example.zuimeichuangyi.db.SharedPreferencesHelp;
import com.example.zuimeichuangyi.parse.ParseAdJson;
import com.example.zuimeichuangyi.urlutils.Url;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class VideoClassifyActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        MediaPlayer.OnCompletionListener, MediaRecorder.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    public static Object obj = new Object();
    private String url;
    private MyApplication myApplication;
    private Button btn_play;
    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private SurfaceHolder holder;
    private HorizontalListView listview_recommend;
    private LinearLayout layout_seekbar;
    private RelativeLayout video_layout;// 导航栏颜色设置
    private ProgressBar progressBar;// 视频加载
    private TextView textView;// 视频加载百分比
    private SeekBar seekBar;// 视频播放进度条
    private TextView tv_videotitle;// 标题
    private TextView tv_videoplaycount;// 播放次数
    private TextView tv_videocomment;// 评论数
    private TextView tv_videocontent;// 描述
    private TextView tv_videoDuration;// 播放时长
    private ImageView iv_good;// 点赞
    private TextView tv_videoPosition;
    private EditText edittext_videocomment;

    @ViewInject(R.id.btn_star)
    private Button btn_star;
    public static DbUtils dbUtils;

    private int currentPosition = 0;// 当前播放位置
    private int itemId;// List集合中item项的位置
    private String rsId;
    private String modulesId;
    private String albumId;
    private String playerUrl;// 视频链接
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();// 存在其他页面的数据
    private List<Map<String, Object>> allList = new ArrayList<Map<String, Object>>();// 存在其他页面的数据
    private List<Map<String, Object>> list_recommend = new ArrayList<Map<String, Object>>();// 向您推荐
    private VideoRecommendAdapter adapter;
    private Handler handler_play;
    private Handler handler_seek;
    private boolean flag = true;// seekBar更新
    private boolean isGood = true;// 点赞
    private boolean isFullScreen = false;// 是否全屏
    private boolean isFirst = true;// 第一次播放
    private boolean isPrepare;// 是否准备完成可以播放
    private double duration;
    private SharedPreferencesHelp help;
    private String path;

    // 定时设置控件可见性
    private Handler handler = new Handler();

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // handler自带方法实现定时器设置按钮不可见
            synchronized (obj) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        btn_play.setVisibility(View.GONE);
                        layout_seekbar.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);

        ViewUtils.inject(VideoClassifyActivity.this);

        dbUtils = DbUtils.create(VideoClassifyActivity.this, "collectdb.db");

        this.overridePendingTransition(R.anim.push_left_in, 0);

        help = new SharedPreferencesHelp(this);

        // 获取list中的item位置
        Intent intent = getIntent();

        itemId = intent.getIntExtra("position", 0);
        path=intent.getStringExtra("url");
        rsId = intent.getStringExtra("rsId");

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url(path).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

                Toast.makeText(VideoClassifyActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(Response response) throws IOException {

                String json = response.body().string();

                List<Map<String, Object>> list = ParseAdJson.getAllListJson(json);
                Log.e("flag", "----->" + json);
                // 获取JSON数据
                allList.addAll(list);
                Log.d("flag", "这里越界了?" + allList.get(itemId));
                //myApplication.setClassify_listview_list(allList);

            }
        });

        // 判断获取来自于谁的点击请求播放
       // initList();
        // 初始化控件
        initView();
        // 初始化listview_recommend(推荐列表)
        initListView();
//         准备好视频播放
        initSurfaceView();
    }

    // 准备好视频播放
    private void initSurfaceView() {
        // 获取视频播放对象
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(this);// 播放完成时监听
        mediaPlayer.setOnErrorListener(this);// 播放失败
        mediaPlayer.setOnPreparedListener(this);// 播放准备
        // 设置播放时打开屏幕
        holder = surfaceView.getHolder();
        // 获取播放内容资源
        synchronized (obj) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    getUrl(rsId);
                }
            }).start();
        }
        // 播放视频
        handler_play = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x002) {
                    flag = true;// 进度更新进程的标识
                    synchronized (obj) {
                        // 准备开始播放
                        Thread t = new Thread(new MyThread());
                        t.setPriority(10);// 优先级最高
                        t.start();
                        if (isPrepare) {
                            // 启动线程更新seekbar
                            seekBar.setMax(mediaPlayer.getDuration());
                            new Thread(new ProgressThread()).start();
                        }
                        textView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }
                } else if (msg.what == 0x001) {
                    progressBar.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                } else if (msg.what == 0x003) {
                    Toast.makeText(VideoClassifyActivity.this, "网络连接错误",
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
        // 初始化seekBar
        initSeekBar();
        handler_seek = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int progress = msg.arg1;
                long time = msg.arg2;
                seekBar.setProgress(progress);
                tv_videoPosition.setText(toTime(time));
            }
        };
        try {
            isCollect();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    // 是否在收藏夹中存在
    private void isCollect() throws DbException {
        if (rsId != null) {
            boolean b = CollectDbHelper.queryOne(dbUtils, rsId);
            if (b) {
                btn_star.setBackgroundResource(R.mipmap.ic_favorite_white);
            } else {
                btn_star.setBackgroundResource(R.mipmap.ic_star);
            }
        }
    }

    // 拖动seekBar
    private void initSeekBar() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (fromUser) {
                    if (mediaPlayer != null) {
                        mediaPlayer.seekTo(progress);
                        // 获取时长
                        seekBar.setMax(mediaPlayer.getDuration());
                    }
                    // 启动线程
                    new Thread(new ProgressThread()).start();
                }
            }
        });
    }

    @Override
    public void onError(MediaRecorder mr, int what, int extra) {

    }

    // 更新seekBar进度
    public class ProgressThread implements Runnable {

        @Override
        public void run() {
            while (flag) {
                try {
                    // 获取当前位置
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekBar.setMax(mediaPlayer.getDuration());
                        // 发送消息
                        Message msg = Message.obtain();
                        msg.arg1 = currentPosition;
                        msg.arg2 = currentPosition;
                        handler_seek.sendMessage(msg);
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    // 准备播放
    private void preplay() {
        HttpURLConnection connection = null;
        // 播放网络文件
        try {
            if (playerUrl != null && !playerUrl.equals("")) {
                URL url = new URL(playerUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int code = connection.getResponseCode();
                if (code == 200) {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(getApplicationContext(),
                            Uri.parse(playerUrl));
                    if (isFirst) {
                        // 把视频输出到SurfaceView上
                        mediaPlayer.setDisplay(holder);
                    }
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    isFirst = false;
                    isPrepare = true;// 允许play
                } else {
                    isPrepare = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            isPrepare = false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    // 开始播放视频
    private void play(final int position) {
        if (isPrepare) {// 准备好了可以播放了
            holder.addCallback(new SurfaceHolder.Callback() {

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                }

                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    if (position >= 0) {
                        try {
                            if (rsId != null && mediaPlayer != null) {
                                mediaPlayer.seekTo(position);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
                @Override
                public void surfaceChanged(SurfaceHolder holder, int format,
                                           int width, int height) {
                }

            });
        }
    }

    // 格式化时间
    public static String toTime(long time) {
        time /= 1000.0;
        long minute = time / 60;
        long second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d", minute, second);
    }

    // 初始化listview_recommend(推荐列表)
    private void initListView() {

//        if (myApplication.getFrom() == 4) {
//
            url = Url.ALBULM_RECOMMEND1 + albumId + Url.ALBULM_RECOMMEND2;
//
//        } else {

//            url = Url.ALL_RECOMMEND1 + modulesId + Url.ALL_RECOMMEND2;

//        }

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO: 2016/5/1
                // 获取数据源
                OkHttpClient okHttpClient = new OkHttpClient();

                Request request = new Request.Builder().url(url).build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Toast.makeText(VideoClassifyActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {

                        String jsonplayer = response.body().string();

                        List<Map<String, Object>> list = ParseAdJson.getAllListJson(jsonplayer);

                        // TODO: 2016/5/3 在子线程里面加载 (Handler和post)


                        list_recommend.addAll(list);

                        //myApplication.setAll_recommend_list(list);

//                        adapter.notifyDataSetChanged();

                    }
                });
            }
        });
        t.setPriority(1);// 优先级最低
        t.start();
        // 构建适配器
        adapter = new VideoRecommendAdapter(VideoClassifyActivity.this, list_recommend);
        // 设置适配器
        listview_recommend.setAdapter(adapter);
        // 设置监听器
        listview_recommend.setOnItemClickListener(VideoClassifyActivity.this);
    }

    // 判断获取来自于谁的点击请求播放
    private void initList() {
        myApplication = (MyApplication) getApplication();
        if (myApplication.getFrom() == 1) {// 所有，广告
            list = myApplication.getAll_listview_list();
        } else if (myApplication.getFrom() == 2) {// 所有，列表
            list = myApplication.getAll_recommend_list();
            try {
                //albumId = list.get(itemId).get("albumId").toString();
                albumId = getIntent().getStringExtra("albumId");
            } catch (Exception e) {
            }
        } else {// 数据库收藏列表
            list = myApplication.getDb_list();
        }
        // TODO: 2016/5/3 异常IndexOutOfBoundsException ??????

        //modulesId = list.get(itemId).get("modulesId").toString();
        modulesId = getIntent().getStringExtra("modulesId");
    }

    // 找到控件
    private void initView() {
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        iv_good = (ImageView) findViewById(R.id.iv_videogood);
        layout_seekbar = (LinearLayout) findViewById(R.id.layout_videoSeekBar);
        btn_play = (Button) findViewById(R.id.btn_play);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_videoloading);
        textView = (TextView) findViewById(R.id.tv_videoloading);
        seekBar = (SeekBar) findViewById(R.id.seekBar_video);
        edittext_videocomment = (EditText) findViewById(R.id.edittext_videocomment);
        listview_recommend = (HorizontalListView) findViewById(R.id.lv_videoRecommend);
        video_layout = (RelativeLayout) findViewById(R.id.video_layout);
        tv_videotitle = (TextView) findViewById(R.id.tv_videotitle);
        tv_videoplaycount = (TextView) findViewById(R.id.tv_videoplaycount);
        tv_videocomment = (TextView) findViewById(R.id.tv_videocomment);
        tv_videocontent = (TextView) findViewById(R.id.tv_videocontent);
        tv_videoDuration = (TextView) findViewById(R.id.tv_videoDuration);
        tv_videoPosition = (TextView) findViewById(R.id.tv_videoPosition);
        btn_star = (Button) findViewById(R.id.btn_star);
        // 设置内容
        // TODO: 2016/5/3
       /////// setView(list);
        setView(allList);
    }
    // 设置内容
    private void setView(List<Map<String, Object>> list) {
        // 设置导航栏背景色
        try {
//            video_layout.setBackgroundColor(Color.parseColor(list.get(itemId)
//                    .get("modulesColor").toString()));
            video_layout.setBackgroundColor(Color.parseColor(getIntent().getStringExtra("modulesColor")));
        } catch (Exception e) {
            // TODO: handle exception
        }
        // 设置标题
//        tv_videotitle.setText(list.get(itemId).get("title").toString());
        tv_videotitle.setText(getIntent().getStringExtra("title"));
        // 设置播放次数
        //////////////////////////////////////////////////////////////////////
//        tv_videoplaycount.setText("播放："
//                + list.get(itemId).get("viewCount").toString());
//        // 设置评论次数
//        tv_videocomment.setText("评论："
//                + list.get(itemId).get("commentcount").toString());
        try {
            // 设置描述
            //tv_videocontent.setText(list.get(itemId).get("description").toString());
            tv_videocontent.setText(getIntent().getStringExtra("description"));
        } catch (Exception e) {
            // TODO: handle exception
        }
        // 设置播放总时长
        //duration = (Double) list.get(itemId).get("duration");
        duration = getIntent().getDoubleExtra("duration",0);
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
        tv_videoDuration.setText(strMin + ":" + strS);
    }

    // 按钮监听
    public void click(View v) throws DbException, DbException {
        switch (v.getId()) {

            case R.id.surfaceView:// 播放按钮可视化控制
                layout_seekbar.setVisibility(View.VISIBLE);
                btn_play.setVisibility(View.VISIBLE);
                handler.postDelayed(runnable, 3000); // 每隔3s执行
                break;

            case R.id.btn_play:// 暂停播放
                if (mediaPlayer != null && mediaPlayer.isPlaying()
                        && currentPosition != 0) {
                    mediaPlayer.pause();
                    seekBar.setProgress(currentPosition);
                    btn_play.setBackgroundResource(R.mipmap.ic_pause_normal);
                    btn_play.setVisibility(View.VISIBLE);
                    layout_seekbar.setVisibility(View.VISIBLE);
                } else {
                    mediaPlayer.start();
                    btn_play.setBackgroundResource(R.mipmap.ic_play_normal);
                    btn_play.setVisibility(View.GONE);
                    layout_seekbar.setVisibility(View.GONE);
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        // 获取时长
                        seekBar.setMax(mediaPlayer.getDuration());
                    }
                    // 启动线程
                    new Thread(new ProgressThread()).start();
                }
                if (isFirst) {
                    btn_play.setBackgroundResource(R.mipmap.ic_pause_normal);
                    mediaPlayer.stop();
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            getUrl(rsId);
                        }
                    }).start();
                    handler.postDelayed(runnable, 3000); // 每隔3s执行
                    progressBar.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.btn_back:// 返回
                finish();
                break;

            case R.id.btn_star:// 收藏//--------------------------------------------------------------------------
                if (help.getLogin()) {
                    if (rsId != null) {
                        boolean flag_content = CollectDbHelper.queryOne(dbUtils, rsId);
                        if (flag_content) {
                            boolean isDel = CollectDbHelper.delete(dbUtils, rsId);
                            if (isDel) {
                                btn_star.setBackgroundResource(R.mipmap.ic_star);
                                Toast.makeText(getApplicationContext(), "已从收藏中移除",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                                    "yyyy-MM-dd HH:mm:ss");
                            Date date = new Date(System.currentTimeMillis());
                            String collecttime = simpleDateFormat.format(date);
                            ///////////////////////////////////////////
//                            boolean flag1 = CollectDbHelper.add(dbUtils, rsId,
//                                    collecttime, allList.get(itemId).get("jsonString")
//                                            .toString(), albumId);
                            String jsonString=getIntent().getStringExtra("jsonString");
                            boolean flag1 = CollectDbHelper.add(dbUtils, rsId,
                                    collecttime, jsonString, albumId);
                            if (flag1) {
                                btn_star.setBackgroundResource(R.mipmap.ic_favorite_white);
                                Toast.makeText(getApplicationContext(), "已加入收藏",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }else{
                    Intent intent = new Intent(VideoClassifyActivity.this,LoginActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                }
                break;

            case R.id.btn_share:// 分享
                Intent intent = new Intent(VideoClassifyActivity.this, ShareActivity.class);
                intent.putExtra("itemId", itemId);
                startActivity(intent);
                break;

            case R.id.btn_menu:// 菜单
                PopupMenu popupMenu = new PopupMenu(VideoClassifyActivity.this, v);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                Menu menu = popupMenu.getMenu();
                menuInflater.inflate(R.menu.main, menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.refresh:// 刷新
                                mediaPlayer.stop();
                                new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        getUrl(rsId);
                                    }
                                }).start();
                                handler.postDelayed(runnable, 3000); // 每隔3s执行
                                progressBar.setVisibility(View.VISIBLE);
                                break;
                            case R.id.opencomments:// 展开评论
                                Intent intent = new Intent(VideoClassifyActivity.this,
                                        CommentActivity.class);
                                intent.putExtra("rsId", rsId);
                                startActivity(intent);
                                break;

                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                break;
            case R.id.btn_fullscreen:// 全屏播放
                if (isFullScreen) {// 如果已经是横屏则切换到竖屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 设置竖向显示效果

                } else {// 否则切换到横屏全屏播放
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // 横向显示效果
                    // 获取手机屏幕分辨率
                    WindowManager wm = (WindowManager) getSystemService(this.WINDOW_SERVICE);
                    DisplayMetrics outMetrics = new DisplayMetrics();
                    wm.getDefaultDisplay().getMetrics(outMetrics);
                    int mScreenWidth = outMetrics.widthPixels;
                    int mScreenHeight = outMetrics.heightPixels;
                    surfaceView.setLayoutParams(new RelativeLayout.LayoutParams(
                            mScreenWidth, mScreenHeight));
                }
                isFullScreen = !isFullScreen;
                break;
            case R.id.iv_videogood:// 点赞
                if (isGood) {
                    iv_good.setBackgroundResource(R.mipmap.ic_resource_light);
                } else {
                    iv_good.setBackgroundResource(R.mipmap.ic_resource_grey);
                }
                isGood = !isGood;
                break;

            case R.id.btn_videosend:// 发表评论
                String str = edittext_videocomment.getText().toString();
                if (str == null || str.equals("")) {
                    Toast.makeText(VideoClassifyActivity.this, "评论内容不能为空" + str,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VideoClassifyActivity.this, "已发表:" + str,
                            Toast.LENGTH_SHORT).show();
                }
                edittext_videocomment.setText("");
                break;
            default:
                break;
        }
    }

    // 获取视频链接
    public void getUrl(String rsId) {
        String url = Url.VEDIOSEARCH1 + rsId + Url.VEDIOSEARCH2 + rsId
                + Url.VEDIOSEARCH3;

        Log.d("flag","什么路径"+url);
        Log.d("flag","ID======"+rsId);

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler_play.sendEmptyMessage(0x003);
            }

            @Override
            public void onResponse(Response response) throws IOException {

                String jsonpalyerulr = response.body().string();

                Log.d("flag", "==-=-=-=--=-" + jsonpalyerulr);
                playerUrl = ParseAdJson.getVideoJSON(jsonpalyerulr);

                handler_play.sendEmptyMessage(0x002);
            }
        });
    }

    // 销毁释放
    @Override
    protected void onDestroy() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        super.onDestroy();
    }

    // 失去焦点时暂停播放
    @Override
    protected void onPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            seekBar.setProgress(currentPosition);
            btn_play.setBackgroundResource(R.mipmap.ic_pause_normal);
            btn_play.setVisibility(View.VISIBLE);
            layout_seekbar.setVisibility(View.VISIBLE);
            flag = false;
        }
        super.onPause();
    }

    // 重新获得焦点时播放
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        if (mediaPlayer != null) {
            mediaPlayer.start();
            btn_play.setBackgroundResource(R.mipmap.ic_play_normal);
            btn_play.setVisibility(View.GONE);
            layout_seekbar.setVisibility(View.GONE);
            flag = true;
            // 获取时长
            seekBar.setMax(mediaPlayer.getDuration());
            // 启动线程
            new Thread(new ProgressThread()).start();
        }
        super.onResume();
    }

    // 保存状态
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        isFirst = true;// mediaPlayer.pre...
    }

    // listview_recommend的点击选取监听器
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                            long id) {
        flag = false;
        currentPosition = 0;
        synchronized (obj) {

            if (mediaPlayer.isPlaying()
                    || mediaPlayer.getCurrentPosition() != 0) {
                mediaPlayer.stop();


                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        getUrl(rsId);
                    }
                }).start();
                progressBar.setVisibility(View.VISIBLE);
                btn_play.setVisibility(View.GONE);
                btn_play.setBackgroundResource(R.mipmap.ic_play_normal);
                handler.postDelayed(runnable, 3000); // 每隔3s执行隐藏控件
     //           itemId = position;
                setView(list_recommend);
            }
        }
    }

    // 结束动画
    @Override
    public void finish() {
        super.finish();
        // 关闭窗体动画显示
        this.overridePendingTransition(R.anim.push_left_out, 0);
        flag = false;
        // mediaPlayer.release();
    }

    // 播放视频的线程
    class MyThread implements Runnable {

        @Override
        public void run() {
            if (flag) {
                // 播放准备
                preplay();
                // 开始播放
                play(currentPosition);
            }
        }
    }

    // 视频播放完成
    @Override
    public void onCompletion(MediaPlayer mp) {
        if (progressBar.isShown()) {
            btn_play.setVisibility(View.GONE);
            layout_seekbar.setVisibility(View.GONE);
        } else {
            btn_play.setVisibility(View.VISIBLE);
            layout_seekbar.setVisibility(View.VISIBLE);
            // progressBar.setVisibility(View.GONE);
        }
    }

    // 视频播放失败
    @Override
    public boolean onError(MediaPlayer mediaPlayer, int whatError, int extra) {
        // TODO Auto-generated method stub
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        return false;
    }

    // 横竖屏切换
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // "当前屏幕为横屏"
            edittext_videocomment.setVisibility(View.GONE);// 评论框隐藏
            findViewById(R.id.btn_videosend).setVisibility(View.GONE);
            video_layout.setVisibility(View.GONE);// 导航栏部分隐藏

        } else {
            // "当前屏幕为竖屏"
            edittext_videocomment.setVisibility(View.VISIBLE);
            findViewById(R.id.btn_videosend).setVisibility(View.VISIBLE);
            video_layout.setVisibility(View.VISIBLE);
        }
    }

    // 播放准备
    @Override
    public void onPrepared(MediaPlayer arg0) {
        // 控件可视化否
        btn_play.setBackgroundResource(R.mipmap.ic_play_normal);
        btn_play.setVisibility(View.GONE);
        layout_seekbar.setVisibility(View.GONE);
    }
}

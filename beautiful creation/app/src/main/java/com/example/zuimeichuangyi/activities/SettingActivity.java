package com.example.zuimeichuangyi.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.zuimeichuangyi.MainActivity;
import com.example.zuimeichuangyi.R;
import com.example.zuimeichuangyi.urlutils.Url;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

// TODO: 2016/5/3 点击退出登录,不知道如何处理逻辑(数据库,xutils)

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView img_autoplay;
    private TextView textView3;

    /**
     * 设置Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        this.overridePendingTransition(R.anim.push_left_in, 0);
        img_autoplay = (ImageView) findViewById(R.id.img_play);
        ShareSDK.initSDK(this);
        textView3 = (TextView) findViewById(R.id.textView_fenxiangang);
    }

    /**
     * 点击事件
     *
     * @param view
     */
    public void clickButton(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.autoplay:
                if (Url.is_autoplay) {
                    img_autoplay.setImageResource(R.mipmap.toggle_button_off);
                    Url.is_autoplay = false;
                } else {
                    img_autoplay.setImageResource(R.mipmap.toggle_button_on);
                    Url.is_autoplay = true;
                }
                break;
            case R.id.img_play:
                if (Url.is_autoplay) {
                    img_autoplay.setImageResource(R.mipmap.toggle_button_off);
                    Url.is_autoplay = false;
                } else {
                    img_autoplay.setImageResource(R.mipmap.toggle_button_on);
                    Url.is_autoplay = true;
                }
                break;


            case R.id.button:
                Intent intent = new Intent(SettingActivity.this,
                        MainActivity.class);
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_weibo:
                SinaWeibo.ShareParams sp2 = new SinaWeibo.ShareParams();
                sp2.setTitle("测试分享的标题");
                sp2.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                sp2.setText("测试分享的文本");
                sp2.setImageUrl("http://avatar.csdn.net/F/F/5/1_lmj623565791.jpg");
                sp2.setSite("发布分享的网站名称");
                sp2.setSiteUrl("发布分享网站的地址");


                Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);

                weibo.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                });
                 // 设置分享事件回调
                // 执行图文分享
                weibo.share(sp2);

                break;
            case R.id.bt_qqquenplatform:
//                ShareSDK.initSDK(this);
//                Toast.makeText(SettingActivity.this, "click", Toast.LENGTH_SHORT).show();
                QQ.ShareParams sp = new QQ.ShareParams();
                sp.setTitle("测试分享的标题");
                sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                sp.setText("测试分享的文本");
                sp.setImageUrl("http://www.someserver.com/测试图片网络地址.jpg");
                sp.setSite("发布分享的网站名称");
                sp.setSiteUrl("发布分享网站的地址");

                Platform qzone = ShareSDK.getPlatform (QQ.NAME);
                qzone. setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                }); // 设置分享事件回调
                qzone.share(sp);

        }

    }

    private void creatPopUpWindow() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_layoutshare, null);

        PopupWindow popupWindow=new PopupWindow(inflate,800,800,true);

        popupWindow.setBackgroundDrawable(new ColorDrawable());

        Button btQQ = (Button) inflate.findViewById(R.id.bt_weibo);

        Button qqQuen = (Button) inflate.findViewById(R.id.bt_qqquenplatform);

        btQQ.setOnClickListener(this);

        qqQuen.setOnClickListener(this);

        popupWindow.showAsDropDown(textView3);
    }
    @Override
    public void finish() {
        // TODO: 2016/5/3
        super.finish();
        this.overridePendingTransition(R.anim.push_left_out, 0);
    }

    public void onClicksetting(View view) {
        creatPopUpWindow();
    }
}

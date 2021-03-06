package com.example.zuimeichuangyi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.zuimeichuangyi.MainActivity;
import com.example.zuimeichuangyi.R;
import com.example.zuimeichuangyi.db.SharedPreferencesHelp;

import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

public class LoginActivity extends AppCompatActivity
    implements PlatformActionListener {
//
    private Handler handler;
    private SharedPreferencesHelp help;
    private int type;// 判断是从哪里进行登录的0是从MainActivity抽屉来的，1是从视屏播放VideoActivity来的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ShareSDK.initSDK(this);

        help = new SharedPreferencesHelp(this);

        Intent intent_t = getIntent();
        type = intent_t.getIntExtra("type", 0);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO: 2016/5/4
                super.handleMessage(msg);
                if (msg.what == 0x00) {
                    Toast.makeText(LoginActivity.this, "登录失败",
                            Toast.LENGTH_SHORT).show();
                } else if (msg.what == 0x11) {// 登录成功
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map = (Map<String, Object>) msg.obj;
                    String nickname = map.get("nickname").toString();
                    String imgUrl = map.get("figureurl_qq_2").toString();
                    help.save(nickname, imgUrl);
                    help.setLogin(true);

                    if (type == 0) {//到收藏夹
                        Intent intent = new Intent(LoginActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                    } else if (type == 1) {//回到播放界面
                        Intent intent = new Intent(LoginActivity.this,
                                VideoActivity.class);
                        startActivity(intent);
                    }
                    finish();

                } else if (msg.what == 0x01) {
                    Toast.makeText(LoginActivity.this, "取消登录",
                            Toast.LENGTH_SHORT).show();
                }
            }
        };

    }

    // 按钮监听
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_login_back:// 返回
                finish();
                break;

            case R.id.btn_login:// 登录
                //TODO 逻辑未处理
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_qqlogin:// 授权QQ登录
                Platform qq = ShareSDK.getPlatform(LoginActivity.this, QQ.NAME);
                qq.setPlatformActionListener(this);
                qq.showUser(null);
                break;

            default:
                break;
        }
    }

    // 授权登录回调
    @Override
    public void onCancel(Platform plat, int arg1) {
        plat.removeAccount();//移除可能缓存的信息
        Message msg = Message.obtain();
        msg.what = 0x01;
        msg.obj = plat;
        handler.sendMessage(msg);
    }

    @Override
    public void onComplete(Platform plat, int arg1, HashMap<String, Object> res) {
        Message msg = Message.obtain();
        msg.what = 0x11;
        msg.obj = res;//获取到登录的信息
        handler.sendMessage(msg);
    }

    @Override
    public void onError(Platform plat, int arg1, Throwable arg2) {
        plat.removeAccount();
        Message msg = Message.obtain();
        msg.what = 0x00;
        msg.obj = plat;
        handler.sendMessage(msg);

    }

}
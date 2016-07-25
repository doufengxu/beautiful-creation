package com.example.zuimeichuangyi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zuimeichuangyi.activities.AnnouncementActivity;
import com.example.zuimeichuangyi.activities.CollectActivity;
import com.example.zuimeichuangyi.activities.FeedBackActivity;
import com.example.zuimeichuangyi.activities.HotActivity;
import com.example.zuimeichuangyi.activities.LoginActivity;
import com.example.zuimeichuangyi.activities.SearchActivity;
import com.example.zuimeichuangyi.activities.SettingActivity;
import com.example.zuimeichuangyi.activities.SupplyMaterialActivity;
import com.example.zuimeichuangyi.adapters.ViewPagerAdapter;
import com.example.zuimeichuangyi.db.SharedPreferencesHelp;
import com.example.zuimeichuangyi.fragments.AllFragment;
import com.example.zuimeichuangyi.fragments.ClassifyFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private LinearLayout linearLayout_color;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private ImageView iv_drawer_headpic;
    private TextView tv_drawer_name;
    private FragmentManager fragmentManager;
    private List<Fragment> list_fragment = new ArrayList<Fragment>();
    private ViewPagerAdapter adapter;
    private SharedPreferencesHelp help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.push_left_out, 0);
        help = new SharedPreferencesHelp(this);

        // 初始化控件
        initView();
        // 获取数据源
        initList();
        // 构建适配器
        adapter = new ViewPagerAdapter(fragmentManager, list_fragment);
        // 设置适配器
        viewPager.setAdapter(adapter);
        // 设置滑动监听器
        viewPager.setOnPageChangeListener(this);
        // fragment切换不会再次执行onCreateView()
        viewPager.setOffscreenPageLimit(2);
    }

    @Override
    protected void onResume() {

        if (help.getLogin()) {// 重新获取焦点时更新
            List<String> list = new ArrayList<String>();
            list = help.get();
            String nickname = list.get(0).toString();
            String imgUrl = list.get(1).toString();
            if (!nickname.equals("")) {
                tv_drawer_name.setText(nickname);
            }
            if (!imgUrl.equals("")) {
                Toast.makeText(MainActivity.this,"暂时不知道怎么处理......",Toast.LENGTH_LONG).show();
            }
        } else {
            tv_drawer_name.setText("Hello,最美陌生人");
            iv_drawer_headpic.setImageResource(R.mipmap.ic_launcher);
        }
        super.onResume();
    }

    private void initList() {
        fragmentManager = getSupportFragmentManager();
        AllFragment fragment_All = new AllFragment();
        list_fragment.add(fragment_All);
        ClassifyFragment fragment_Class = new ClassifyFragment();
        list_fragment.add(fragment_Class);
    }

    private void initView() {
        getSupportActionBar().hide();
//        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setDisplayShowTitleEnabled(false);
        linearLayout_color = (LinearLayout) findViewById(R.id.linearLayout_colorUp);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        iv_drawer_headpic = (ImageView) findViewById(R.id.iv_drawer_headpic);
        tv_drawer_name = (TextView) findViewById(R.id.tv_drawer_name);
        if (help.getLogin()) {
            List<String> list = new ArrayList<String>();
            list = help.get();
            String nickname = list.get(0).toString();
            String imgUrl = list.get(1).toString();
            if (!nickname.equals("")) {
                tv_drawer_name.setText(nickname);
            }
            if (!imgUrl.equals("")) {
                Toast.makeText(MainActivity.this,"暂时不知道怎么处理......",Toast.LENGTH_LONG).show();
            }
        }
    }
    // 导航栏按钮监听
    public void click(View v) {
        switch (v.getId()) {
            case R.id.iv_drawer:// 抽屉
                drawerLayout.openDrawer(Gravity.LEFT);
                break;

            case R.id.iv_notification:// 通告
                Intent intent = new Intent(MainActivity.this,
                        AnnouncementActivity.class);
                startActivity(intent);
                break;

            case R.id.iv_menu:// 搜索、设置
                Intent intent1=new Intent();
                intent1.setClass(MainActivity.this, SearchActivity.class);
                startActivity(intent1);

//                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
//                MenuInflater menuInflater = popupMenu.getMenuInflater();
//                Menu menu = popupMenu.getMenu();
//                menuInflater.inflate(R.menu.action, menu);
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.search:
//                                Intent intent_search = new Intent(MainActivity.this,
//                                        HotActivity.class);
//                                intent_search.putExtra("hotword", "");
//                                startActivity(intent_search);
//                                break;
//                            case R.id.setting:
//                                Intent intent = new Intent(MainActivity.this,
//                                        SettingActivity.class);
//                                startActivity(intent);
//                                break;
//
//                            default:
//                                break;
//                        }
//                        return false;
//                    }
//                });
//                popupMenu.show();
                break;

            default:
                break;
        }
    }
    // 分类
    public void clcikNavigation(View v) {
        switch (v.getId()) {
            case R.id.tv_all:// 所有
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_class:// 分类
                viewPager.setCurrentItem(1);
                break;

            default:
                break;
        }
    }
    // ViewPager的滑动监听器
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < list_fragment.size(); i++) {
            TextView textView_color = (TextView) linearLayout_color
                    .getChildAt(i);
            if (position == i) {
                // 设置选中下面横线背景色绿色
                textView_color.setBackgroundColor(Color.WHITE);
            } else {
                // 设置未选中下面横线背景色白色
                textView_color.setBackgroundColor(Color.rgb(77, 210, 152));
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    // 抽屉按钮监听
    public void clickDrawer(View v) {
            switch (v.getId()) {
                case R.id.drawer_relativelayout:// 点击图片
                case R.id.iv_drawer_headpic:// 点击头像
                case R.id.tv_drawer_name:// 点击昵称
                    if (help.getLogin()) {// 已经登录
                        Intent intent = new Intent(MainActivity.this,
                                CollectActivity.class);
                        startActivity(intent);
                    } else {// 未登录
                        Intent intent_login = new Intent(MainActivity.this,
                                LoginActivity.class);
                        intent_login.putExtra("type", 0);
                        startActivity(intent_login);
                    }
                    break;
                //在此处加一个按钮,用于第三方登录
                case R.id.tv_main_removecount:// 取消授权登录信息
                    break;
                case R.id.tv_drawer_hot:// 热门
                    Intent intent = new Intent(getApplicationContext(),
                            HotActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_drawer_feedback:// 反馈意见
                    Intent intent5 = new Intent(MainActivity.this,
                            FeedBackActivity.class);
                    startActivity(intent5);
                    break;
                case R.id.tv_drawer_upload:// 提供素材
                    Intent intent6 = new Intent(MainActivity.this,
                            SupplyMaterialActivity.class);
                    startActivity(intent6);
                    break;
                case R.id.tv_drawer_setting:// 设置
                    Intent intent7 = new Intent(MainActivity.this,
                            SettingActivity.class);
                    startActivity(intent7);
                    break;

                default:
                    break;
            }
        }
    }

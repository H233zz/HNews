package com.example.rain.haohao.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rain.haohao.Adapter.MyPagerAdapter;
import com.example.rain.haohao.R;
import com.example.rain.haohao.utils.Utils;
import com.example.rain.haohao.fragment.Fragment1;
import com.example.rain.haohao.fragment.tianqiFragment;
import com.umeng.socialize.UMShareAPI;
import com.youth.banner.Banner;
import com.yzq.zxinglibrary.android.CaptureActivity;
import java.util.ArrayList;

import cn.sharesdk.onekeyshare.OnekeyShare;


public class MainActivity extends AppCompatActivity{
    private TabLayout tab_layout;
    private ViewPager viewpager;
    private DrawerLayout drawer_layout;
    private ActionBarDrawerToggle mtoggle;
    private int theme = 0;
    private UMShareAPI mShareAPI = null;
    private Banner banner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme = Utils.getAppTheme(this);
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initActionBar();
        initFragment();
        if(theme==R.style.AppThemeDark){

        }else if(theme==R.style.AppTheme){
        }
    }

    private void initView() {
        mShareAPI = UMShareAPI.get(this);
        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        banner = (Banner) findViewById(R.id.banner);
    }

    private void initActionBar() {
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        mtoggle = new ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close);
        mtoggle.syncState();
        drawer_layout.addDrawerListener(mtoggle);

    }
 //此方法用于初始化菜单，其中menu参数就是即将要显示的Menu实例。 返回true则显示该menu,false 则不显示;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mtoggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            //响应每个菜单项(通过菜单项的ID)
            case R.id.image_actionbar:
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.START);//关闭抽屉
                } else {
                    drawer_layout.openDrawer(GravityCompat.END);
                }
                break;
            case R.id.action_tianqi:
                startActivity(new Intent(MainActivity.this,tianqiFragment.class));
                Toast.makeText(this, "天气", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_share:
                OnekeyShare oks = new OnekeyShare();
                //关闭sso授权
                oks.disableSSOWhenAuthorize();

                // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
                //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用

                // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                oks.setTitleUrl("http://sharesdk.cn");
                // text是分享文本，所有平台都需要这个字段
                oks.setText("我是分享文本");
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                // url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl("http://sharesdk.cn");
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks.setComment("我是测试评论文本");
                // site是分享此内容的网站名称，仅在QQ空间使用
                oks.setSite(getString(R.string.app_name));
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                oks.setSiteUrl("http://sharesdk.cn");

                // 启动分享GUI
                oks.show(this);

                break;
            case R.id.action_yejian:
                Utils.switchAppTheme(this);
                Intent intent = getIntent();
                overridePendingTransition(0, 0);//不设置进入退出动画
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
                break;
            case R.id.action_sousuo:
                startActivity(new Intent(MainActivity.this,SeachActivity.class));
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_saoyisao:
                if (Build.VERSION.SDK_INT >= 17) {
                    //然后使用上下文兼容ContextCompat中的一个方法checkSelfPermission 检查本工程中是否有所要求的权限  用一个int类型的值接收

                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        //通过判断此致是否与包管理者  packageManager 中的静态常量 权限授予值  是够相等 来判断是否需要请求权限  再通过 Activity兼容
                        // ActivityCompat 中的请求权限方法 requestPermissions  将你需要的权限名  详细写上就可以了
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
                        startActivity(new Intent(MainActivity.this, CaptureActivity.class));
                    } else {
                        //上面已经写好的拨号方法
                        startActivity(new Intent(MainActivity.this, CaptureActivity.class));
                    }
                } else {
                    //上面已经写好的拨号方法
                    startActivity(new Intent(MainActivity.this, CaptureActivity.class));
                }

                break;
            case R.id.action_shezhi:
                break;
            case R.id.image:
                Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
                drawer_layout.openDrawer(GravityCompat.END);
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.START);
                    return super.onOptionsItemSelected(item);
                }
                break;
        }

        if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
            drawer_layout.closeDrawer(GravityCompat.END);
          //对没有处理的事件，交给父类来处理
            return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    private void initFragment() {
        ArrayList<Fragment> fragment = new ArrayList<>();
        Fragment1 fragmenttoutiao = new Fragment1();
        fragmenttoutiao.setpath("http://v.juhe.cn/toutiao/index?type=toutiao&key=d4c18a18c3391f90dc971633f6e6f445");
        fragment.add(fragmenttoutiao);
        Fragment1 fragmentyule = new Fragment1();
        fragmentyule.setpath("http://v.juhe.cn/toutiao/index?type=yule&key=d4c18a18c3391f90dc971633f6e6f445");
        fragment.add(fragmentyule);
        Fragment1 fragmentredian = new Fragment1();
        fragmentredian.setpath("http://v.juhe.cn/toutiao/index?type=redian&key=d4c18a18c3391f90dc971633f6e6f445");
        fragment.add(fragmentredian);
        Fragment1 fragmenttiyu = new Fragment1();
        fragmenttiyu.setpath("http://v.juhe.cn/toutiao/index?type=tiyu&key=d4c18a18c3391f90dc971633f6e6f445");
        fragment.add(fragmenttiyu);
        Fragment1 fragmenbeijing = new Fragment1();
        fragmenbeijing.setpath("http://v.juhe.cn/toutiao/index?type=beijing&key=d4c18a18c3391f90dc971633f6e6f445");
        fragment.add(fragmenbeijing);
        Fragment1 fragmencaijing = new Fragment1();
        fragmencaijing.setpath("http://v.juhe.cn/toutiao/index?type=caijing&key=d4c18a18c3391f90dc971633f6e6f445");
        fragment.add(fragmencaijing);
        Fragment1 fragmenkeji = new Fragment1();
        fragmenkeji.setpath("http://v.juhe.cn/toutiao/index?type=keji&key=d4c18a18c3391f90dc971633f6e6f445");
        fragment.add(fragmenkeji);
        Fragment1 fragmenduanzi = new Fragment1();
        fragmenduanzi.setpath("http://v.juhe.cn/toutiao/index?type=duanzi&key=d4c18a18c3391f90dc971633f6e6f445");
        fragment.add(fragmenduanzi);
        Fragment1 fragmentupian = new Fragment1();
        fragmentupian.setpath("http://v.juhe.cn/toutiao/index?type=tupian&key=d4c18a18c3391f90dc971633f6e6f445");
        fragment.add(fragmentupian);
        Fragment1 fragmenqiche = new Fragment1();
        fragmenqiche.setpath("http://v.juhe.cn/toutiao/index?type=qiche&key=d4c18a18c3391f90dc971633f6e6f445");
        fragment.add(fragmenqiche);
        Fragment1 fragmenshishang = new Fragment1();
        fragmenshishang.setpath("http://v.juhe.cn/toutiao/index?type=shishang&key=d4c18a18c3391f90dc971633f6e6f445");
        fragment.add(fragmenshishang);
        Fragment1 fragmenqingsongyike = new Fragment1();
        fragmenqingsongyike.setpath("http://v.juhe.cn/toutiao/index?type=qingsongyike&key=d4c18a18c3391f90dc971633f6e6f445");
        fragment.add(fragmenqingsongyike);
        Fragment1 fragmenjunshi = new Fragment1();
        fragmenjunshi.setpath("http://v.juhe.cn/toutiao/index?type=junshi&key=d4c18a18c3391f90dc971633f6e6f445");
        fragment.add(fragmenjunshi);
        Fragment1 fragmenlishi = new Fragment1();
        fragmenlishi.setpath("http://v.juhe.cn/toutiao/index?type=lishi&key=d4c18a18c3391f90dc971633f6e6f445");
        fragment.add(fragmenlishi);

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        myPagerAdapter.init(fragment);
        viewpager.setAdapter(myPagerAdapter);
        for (int i = 0; i < fragment.size(); i++) {
            tab_layout.addTab(tab_layout.newTab());
        }
        tab_layout.setupWithViewPager(viewpager);
        tab_layout.getTabAt(0).setText("头条");
        tab_layout.getTabAt(1).setText("娱乐");
        tab_layout.getTabAt(2).setText("热点");
        tab_layout.getTabAt(3).setText("体育");
        tab_layout.getTabAt(4).setText("北京");
        tab_layout.getTabAt(5).setText("财经");
        tab_layout.getTabAt(6).setText("科技");
        tab_layout.getTabAt(7).setText("段子");
        tab_layout.getTabAt(8).setText("图片");
        tab_layout.getTabAt(9).setText("汽车");
        tab_layout.getTabAt(10).setText("时尚");
        tab_layout.getTabAt(11).setText("轻松一刻");
        tab_layout.getTabAt(12).setText("军事");
        tab_layout.getTabAt(13).setText("历史");

    }



    //这里是在登录界面label上右上角添加三个点，里面可添加其他功能
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /**
         *
         * (只会在第一次初始化菜单时调用) Inflate the menu; this adds items to the action bar
         * if it is present.
         */
        getMenuInflater().inflate(R.menu.actionbar, menu);//这里是调用menu文件夹中的main.xml，在登陆界面label右上角的三角里显示其他功能
        return true;
    }
    //记得要重写这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }


}

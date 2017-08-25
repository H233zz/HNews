package com.example.rain.haohao.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rain.haohao.utils.GlideImageloader;
import com.example.rain.haohao.Adapter.MyBaseAdapter;
import com.example.rain.haohao.bean.MyBean;
import com.example.rain.haohao.R;
import com.example.rain.haohao.xlistview.XListView;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Fragment1 extends Fragment implements XListView.IXListViewListener{
    private XListView xListView;
    private View view;
    private List<MyBean.ResultBean.DataBean> list;
    private String path;
  private MyBaseAdapter myBaseAdapter;
   private Banner banner;
    private MyBaseAdapter myBaseAdapter1;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment1, container, false);
        x.view().inject(getActivity());
        xListView = (XListView) view.findViewById(R.id.xlistview);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
        RequestParams params = new RequestParams(path);
        x.http().get(params, new Callback.CacheCallback<String>() {

            @Override
            public void onSuccess(String result) {
                list = new ArrayList<MyBean.ResultBean.DataBean>();
                Gson gson = new Gson();
                MyBean myBean = gson.fromJson(result, MyBean.class);
                list = myBean.result.data;
                View view1 = View.inflate(getActivity(), R.layout.header, null);
                xListView.addHeaderView(view1);
                myBaseAdapter1 = new MyBaseAdapter(getActivity(), list);
                xListView.setAdapter(myBaseAdapter1);
                List<String> list2 = new ArrayList<String>();
                list2.add(list.get(6).thumbnail_pic_s);
                list2.add(list.get(6).thumbnail_pic_s02);
                list2.add(list.get(6).thumbnail_pic_s03);
                banner = (Banner) view1.findViewById(R.id.banner);
                banner.setImageLoader(new GlideImageloader());
                banner.setImages(list2);
                banner.start();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
        return view;
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            myBaseAdapter1.notifyDataSetChanged();
            close();
        }
    };

    int a=15;
    public void setpath(String str) {
        this.path = str;
    }
    @Override
    public void onRefresh() {
        a-=1;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("000000000000000:"+a);
                System.out.println("11111111111111111111:"+list.size());

                list.add(0,list.get(a));
                System.out.println("4676767676776898989");
                handler.sendEmptyMessage(0);

            }
        },2000);
    }
    @Override
    public void onLoadMore() {

    }
    private void close() {
        xListView.stopRefresh();
        getCurrentTime();
    }
    private  String getCurrentTime(){
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(date);
        return time;
    };


}

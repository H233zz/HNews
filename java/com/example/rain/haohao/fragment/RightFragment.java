package com.example.rain.haohao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rain.haohao.utils.ImageLoaderUtils;
import com.example.rain.haohao.R;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class RightFragment extends Fragment implements View.OnClickListener {
    private UMShareAPI mShareAPI;
    private SHARE_MEDIA platform;
    private ImageView imageview;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right, container, false);
        imageview = (ImageView) view.findViewById(R.id.circleimageview);
        mShareAPI = UMShareAPI.get(getActivity());
        imageview.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {
        platform = SHARE_MEDIA.QQ;
        mShareAPI.getPlatformInfo(getActivity(), SHARE_MEDIA.QQ, umAuthListener);
        mShareAPI.deleteOauth(getActivity(), platform, umAuthListener);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA arg0, int arg1,
                               Map<String, String> data) {
            if (data != null) {
                Set<String> keySet = data.keySet();
                //得到头像
                String iconurl = new String();
                //得到昵称
                String screenname = new String();

                for (String string : keySet) {


                    if (string.equals("profile_image_url")) {
                        //获取登录的图片
                        System.out.println(00000000000000000000000);
                        iconurl = data.get("profile_image_url");
                        ImageLoaderUtils.setImageView(iconurl, getActivity(), imageview);
                    }
                }
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            Toast.makeText(getActivity(), "授权失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Toast.makeText(getActivity(), "授权取消", Toast.LENGTH_SHORT).show();
        }
    };


}



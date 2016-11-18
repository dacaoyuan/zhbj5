package com.everyoo.zhbj5.utils;

import android.widget.ImageView;

import com.everyoo.zhbj5.R;

import org.xutils.image.ImageOptions;

/**
 * Created by abc on 2016/11/18.
 */
public class Xutils {

    /**
     * 截取字符串
     *
     * @return
     */
    public synchronized static String processString(String url) {
        String mUrl = url.replace("http://10.0.2.2:8080/zhbj", "");
        return mUrl;
    }

    public static ImageOptions setPic() {
        ImageOptions options = new ImageOptions.Builder()
                .setFadeIn(true)
                .setFailureDrawableId(R.mipmap.topnews_item_default)
                .setLoadingDrawableId(R.mipmap.topnews_item_default)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .build();

        return options;
    }


}

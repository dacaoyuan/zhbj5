package com.everyoo.zhbj5.domain;

import java.util.ArrayList;

/**
 * Created by abc on 2016/11/10.
 */
public class PhotosData {

    public int retcode;
    public PhoData data;

    @Override
    public String toString() {
        return "PhotosData{" +
                "retcode=" + retcode +
                ", data=" + data +
                '}';
    }

    public class PhoData {

        public String countcommenturl;
        public String more;
        public String title;
        public ArrayList<photoNews> news;

        @Override
        public String toString() {
            return "PhoData{" +
                    "news=" + news +
                    ", title='" + title + '\'' +
                    '}';
        }
    }


    public class photoNews {
        public boolean comment;
        public String commentlist;
        public String commenturl;
        public int id;
        public String largeimage;
        public String listimage;
        public String pubdate;
        public String smallimage;
        public String title;
        public String type;
        public String url;

        @Override
        public String toString() {
            return "photoNews{" +
                    "title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    ", id=" + id +
                    '}';
        }
    }


}

package com.everyoo.zhbj5.domain;

import java.util.ArrayList;

/**
 * Created by abc on 2016/11/1.
 */
public class TabData {


    public String retcode;
    public TabNewsData data;

    @Override
    public String toString() {
        return "TabData{" +
                "data=" + data +
                '}';
    }

    public class TabNewsData {
        public String more;
        public String countcommenturl;
        public String title;
        public ArrayList<NewsData> news;
        public ArrayList<TopNewsData> topnews;

        @Override
        public String toString() {
            return "TabNewsData{" +
                    "title='" + title + '\'' +
                    ", news=" + news +
                    ", topnews=" + topnews +
                    '}';
        }
    }


    public class NewsTab {
        public boolean comment;
        public String commentlist;
        public String commenturl;
        public int id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;

        @Override
        public String toString() {
            return "NewsTab{" +
                    "title='" + title + '\'' +
                    ", type='" + type + '\'' +
                    ", pubdate='" + pubdate + '\'' +
                    ", listimage='" + listimage + '\'' +
                    ", commentlist='" + commentlist + '\'' +
                    ", commenturl='" + commenturl + '\'' +
                    '}';
        }
    }

    public class TopNewsData {
        public boolean comment;
        public String commentlist;
        public String commenturl;
        public int id;
        public String listimage;
        public String pubdate;
        public String title;
        public String topimage;
        public String type;
        public String url;

        @Override
        public String toString() {
            return "TopNewsData{" +
                    "title='" + title + '\'' +
                    ", topimage='" + topimage + '\'' +
                    ", commentlist='" + commentlist + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }


}

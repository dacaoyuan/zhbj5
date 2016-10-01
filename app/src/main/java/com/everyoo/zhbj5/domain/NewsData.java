package com.everyoo.zhbj5.domain;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-9-26.
 */
public class NewsData {

    public int retcode;

    public ArrayList<NewsMenuData> data;

    @Override
    public String toString() {
        return "NewsData{" +
                "retcode=" + retcode +
                ", data=" + data +
                '}';
    }

    public class NewsMenuData {
        public String id;
        public String title;
        public int type;
        public ArrayList<NewsTabData> children;


        /*public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public ArrayList<NewsTabData> getChildren() {
            return children;
        }

        public void setChildren(ArrayList<NewsTabData> children) {
            this.children = children;
        }*/

        @Override
        public String toString() {
            return "NewsMenuData{" +
                    "children=" + children +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

    public class NewsTabData {
        public String id;
        public String title;
        public int type;
        public String url;

        /*public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }*/

        @Override
        public String toString() {
            return "NewsTabData{" +
                    "url='" + url + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}

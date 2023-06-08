package cn.senseless.androidutilcode.news;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NewsResp {
    public int code;
    public String msg;
    @Nullable
    public Result result;

    public static class Result {
        public int curpage;
        public int allnum;
        @Nullable
        public List<News> newslist;

        @NonNull
        @Override
        public String toString() {
            return "Result{" +
                    "curpage=" + curpage +
                    ", allnum=" + allnum +
                    ", newslist=" + newslist +
                    '}';
        }

        public static class News {
            public String id;
            public String ctime;
            public String title;
            public String description;
            public String source;
            public String picUrl;
            public String url;

            @NonNull
            @Override
            public String toString() {
                return "News{" +
                        "id='" + id + '\'' +
                        ", ctime='" + ctime + '\'' +
                        ", title='" + title + '\'' +
                        ", description='" + description + '\'' +
                        ", source='" + source + '\'' +
                        ", picUrl='" + picUrl + '\'' +
                        ", url='" + url + '\'' +
                        '}';
            }
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "NewsResp{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}

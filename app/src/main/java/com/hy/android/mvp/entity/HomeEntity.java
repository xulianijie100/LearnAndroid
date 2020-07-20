package com.hy.android.mvp.entity;

import java.util.List;

public class HomeEntity {
    public DataBean data;
    public int errorCode;
    public String errorMsg;

    public static class DataBean {

        public int curPage;
        public int offset;
        public boolean over;
        public int pageCount;
        public int size;
        public int total;
        public List<DatasBean> datas;

        public static class DatasBean {
            public String apkLink;
            public int audit;
            public String author;
            public boolean canEdit;
            public int chapterId;
            public String chapterName;
            public boolean collect;
            public int courseId;
            public String desc;
            public String descMd;
            public String envelopePic;
            public boolean fresh;
            public int id;
            public String link;
            public String niceDate;
            public String niceShareDate;
            public String origin;
            public String prefix;
            public String projectLink;
            public long publishTime;
            public int realSuperChapterId;
            public int selfVisible;
            public long shareDate;
            public String shareUser;
            public int superChapterId;
            public String superChapterName;
            public String title;
            public int type;
            public int userId;
            public int visible;
            public int zan;
            public List<TagsBean> tags;

            public static class TagsBean {
                public String name;
                public String url;

            }
        }
    }
}

package com.hy.android.mvp.entity;

import java.util.List;

public class BannerEntity {

    /**
     * data : [{"desc":"享学~","id":29,"imagePath":"https://wanandroid.com/blogimgs/2087429c-f6ac-43dd-9ffe-8af871b6deb8.png","isVisible":1,"order":0,"title":"&ldquo;学不动了，也得学！&rdquo;","type":0,"url":"https://mp.weixin.qq.com/s/PRv6SAZlklz4DRm1EsBdew"},{"desc":"","id":6,"imagePath":"https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png","isVisible":1,"order":1,"title":"我们新增了一个常用导航Tab~","type":1,"url":"https://www.wanandroid.com/navi"},{"desc":"一起来做个App吧","id":10,"imagePath":"https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png","isVisible":1,"order":1,"title":"一起来做个App吧","type":1,"url":"https://www.wanandroid.com/blog/show/2"},{"desc":"","id":20,"imagePath":"https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png","isVisible":1,"order":2,"title":"flutter 中文社区 ","type":1,"url":"https://flutter.cn/"}]
     * errorCode : 0
     * errorMsg :
     */
    public int errorCode;
    public String errorMsg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * desc : 享学~
         * id : 29
         * imagePath : https://wanandroid.com/blogimgs/2087429c-f6ac-43dd-9ffe-8af871b6deb8.png
         * isVisible : 1
         * order : 0
         * title : &ldquo;学不动了，也得学！&rdquo;
         * type : 0
         * url : https://mp.weixin.qq.com/s/PRv6SAZlklz4DRm1EsBdew
         */

        public String desc;
        public int id;
        public String imagePath;
        public int isVisible;
        public int order;
        public String title;
        public int type;
        public String url;
    }
}

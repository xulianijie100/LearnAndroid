package com.hy.learn;

import android.util.Log;

/**
 * 实体类
 */
public class UserBean {
    public String id;
    public String name;
    public String pwd;
    public String tel;
    public int age;
    public static int score=100;

    public UserBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static void plusFun(int x,int y){
        int sum=x+y;
        Log.d("静态方法--",String.valueOf(sum));
    }

    public void getUser(String name,int age,String tel){
        Log.d("userBean ----",name+ " : "+age+ " : "+tel);
    }

    public static int getUserInfo(byte[]data,int length){
        for(byte b:data){
            Log.d("data ----",String.valueOf(b));
        }
        return length;
    }

}

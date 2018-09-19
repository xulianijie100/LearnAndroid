package com.hy.android.activity;

import android.os.Environment;
import android.util.Log;

import com.hy.android.Base.BaseActivity;
import com.hy.android.R;
import com.hy.android.bean.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TestActivity extends BaseActivity {
    @Override
    public int bindLayout() {
        return R.layout.activity_rx;
    }

    @Override
    public void initView() {}

    @Override
    protected void initData() {
        //writeObj();
        readObj();
    }

    //序列化
    private void writeObj(){
        User user=new User("jack",20,"man");
        try {
            String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            FileOutputStream fos = new FileOutputStream(sdCardDir+"/user.text");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //反序列化
    private void readObj(){
        try {
            String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            FileInputStream fis = new FileInputStream(sdCardDir+"/user.text");
            ObjectInputStream ois = new ObjectInputStream(fis);
            User bean = (User) ois.readObject();
            Log.e("---",bean.toString());
            //打印值为  E/---: User{name='jack', age=20, gender='man'}

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 当序列化没有设置uid的时候，给User实体添加 private String Address
     */

}

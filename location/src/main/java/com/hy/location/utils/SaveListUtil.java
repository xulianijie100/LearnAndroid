package com.hy.location.utils;

import android.os.Environment;

import com.hy.location.bean.LocalBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/24.
 */

public class SaveListUtil {
    /**
     * 数据存放在本地
     *
     * @param tArrayList
     */
    public static void saveList2SDCard(ArrayList tArrayList, String fileName) {

        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            File file = getFile(fileName);
            fileOutputStream = new FileOutputStream(file.toString());  //新建一个内容为空的文件
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(tArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (objectOutputStream != null) {
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取本地的List数据
     *
     * @return
     */
    public static ArrayList<LocalBean> getStorageEntities(String fileName) {
        ObjectInputStream objectInputStream = null;
        FileInputStream fileInputStream = null;
        ArrayList<LocalBean> savedArrayList = new ArrayList<>();
        try {
            File file = getFile(fileName);
            fileInputStream = new FileInputStream(file.toString());
            objectInputStream = new ObjectInputStream(fileInputStream);
            savedArrayList = (ArrayList<LocalBean>) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savedArrayList;
    }

    /**
     * 创建文件
     *
     * @param fileName
     */
    public static File getFile(String fileName) {
        //获取SDCard根目录
        String filePath = Environment.getExternalStorageDirectory().toString() + "/" + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}

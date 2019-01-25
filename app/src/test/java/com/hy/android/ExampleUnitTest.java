package com.hy.android;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void testMap1(){
        long maxMemory = Runtime.getRuntime().maxMemory();
        int maxSize= (int) (maxMemory/8);
        LinkedHashMap<String,Integer> linkedHashMap=new LinkedHashMap<>(maxSize,0.75f,true);
        linkedHashMap.put("A",100);
        linkedHashMap.put("B",101);
        linkedHashMap.put("C",102);
        linkedHashMap.put("D",103);
        linkedHashMap.put("E",104);
        linkedHashMap.get("A");
        linkedHashMap.get("B");
        for(Map.Entry<String,Integer> entry:linkedHashMap.entrySet()){
            System.out.println(entry.getKey()+"---"+entry.getValue());
        }
    }
}
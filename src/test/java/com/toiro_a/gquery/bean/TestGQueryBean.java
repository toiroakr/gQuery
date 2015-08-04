package com.toiro_a.gquery.bean;

import com.google.gson.Gson;
import com.toiro_a.gquery.annotation.GSelect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@GSelect("results sample")
public class TestGQueryBean {
    @GSelect("name")
    public String name;
    @GSelect("a")
    public boolean a;
    @GSelect("b")
    public int[][] b;
    @GSelect("b")
    public ArrayList<int[]> intArrayList;
    @GSelect("b")
    public List<List<Integer>> listOfIntegerList;
    @GSelect("c")
    public List<Map<String, Integer>> c;
    @GSelect("c foo")
    public List<Integer> singleKeyInListOfMap;
    @GSelect("c foo")
    public int[] singleKeyInArrayOfMap;
    @GSelect("d bar")
    public List<Object> dBar;
    @GSelect("d bar")
    public Object[] singleKeyInObjectArrayOfMap;
    @GSelect("img mobile")
    public Map<String, String> mobileImgs;
    @GSelect("img pc")
    public HashMap<String, String> pcImgs;
    public static final String L = "l";
    public static final String M = "m";
    public static final String S = "s";

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(" {\n");
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            builder.append("\t");
            builder.append(field.getName());
            builder.append(" : ");
            field.setAccessible(true);
            try {
                builder.append(new Gson().toJson(field.get(this)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            builder.append("\n");
        }
        builder.append("}");
        return builder.toString();
    }
}

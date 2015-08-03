package com.toiro_a.gquery;

import com.google.gson.Gson;
import com.toiro_a.gquery.annotation.GSelect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@GSelect("results sample")
public class GQuerySample {
    @GSelect("name")
    private String name;
    @GSelect("a")
    private boolean a;
    @GSelect("b")
    private int[][] int2DArray;
    @GSelect("b")
    private List<int[]> intArrayList;
    @GSelect("b")
    private List<List<Integer>> listOfIntegerList;
    @GSelect("c")
    private List<Map<String, Integer>> listOfMap;
    @GSelect("c foo")
    private List<int[]> singleKeyInListOfMap;
    @GSelect("c foo")
    private int[][] singleKeyInArrayOfMap;
    @GSelect("d bar")
    private List<String> singleKeyInListOfObjectMap;
    @GSelect("d bar")
    private Object[][] singleKeyInObjectArrayOfMap;
    @GSelect("img mobile")
    private Map<String, String> mobileImgs;
    @GSelect("img pc")
    private HashMap<String, String> pcImgs;
    public static final String L = "l";
    public static final String M = "m";
    public static final String S = "s";
    public static Object UNDEFINED_OOBJECT;
    public static int UNDEFINED_INT;
    public static char UNDEFINED_CHAR;
    public static boolean UNDEFINED_BOOLEAN;

    public static void main(String[] args) throws IOException, NoSuchFieldException, SecurityException {
        BufferedReader br = new BufferedReader(new FileReader("src/test/resources/sample.json"));
        String json = "";
        String line = br.readLine();
        do {
            json += line;
            line = br.readLine();
        } while (line != null);
        br.close();

        GQuerySample sample = new GQuery().select(json, GQuerySample.class);
        System.out.println(sample);

        String pcPhotoL = new GQuery().get(json, "results sample img pc l", String.class);
        System.out.println(pcPhotoL);
    }

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

package com.toiroakr.gquery;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.toiroakr.gquery.annotation.GSelect;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

/**
 * class for parsing json
 * Created by higuchiakira on 2015/08/03.
 */
public class GQueryParser {
    private Gson gson;
    private Class<?> targetClass;

    protected void init(Gson gson, Class<?> targetClass) {
        this.gson = gson;
        this.targetClass = targetClass;
    }

    @SuppressWarnings("unchecked")
    public <Target> List<Target> selectList(String json) {
        List<Target> list = (List<Target>) getList(targetClass);
        JsonArray baseArray = (JsonArray) getBaseElement(json);

        for (JsonElement baseElement : baseArray) {
            Target target = newTargetInstance();
            setFields(target, baseElement);
            list.add(target);
        }

        return list;
    }

    @SuppressWarnings("unused")
    private <Target> List<Target> getList(Class<Target> targetClass) {
        return new ArrayList<Target>();
    }

    protected <Target> Target select(String json) {
        Target target = newTargetInstance();
        JsonElement baseElement = getBaseElement(json);
        setFields(target, baseElement);
        return target;
    }

    @SuppressWarnings("unchecked")
    private <Target> Target newTargetInstance() {
        Target target = null;
        try {
            target = (Target) targetClass.newInstance();
        } catch (Exception e) {
            System.err.println("error on creating target class instance : " + targetClass.getName());
            e.printStackTrace();
        }
        return target;
    }

    private JsonElement getBaseElement(String json) {
        JsonElement jsonElement = gson.fromJson(json, JsonElement.class);
        GSelect gSelect = targetClass.getAnnotation(GSelect.class);
        String[] selectors = gSelect.value().split(" ");
        return select(jsonElement, selectors);
    }

    private String toString(Object obj) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            builder.append("\"");
            builder.append(field.getName());
            builder.append("\"");
            builder.append(":");
            field.setAccessible(true);
            try {
                builder.append(gson.toJson(field.get(obj)));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            builder.append(", ");
        }
        builder.delete(builder.length() - ", ".length(), builder.length());
        builder.append("}");
        return builder.toString();
    }

    private void setFields(Object target, JsonElement baseElement) {
        Field[] fields = targetClass.getDeclaredFields();
        for (Field field : fields) {
            GSelect gSelect = field.getAnnotation(GSelect.class);
            if (gSelect == null) {
                continue;
            }
            setField(target, field, baseElement);
        }
    }

    private void setField(Object target, Field field, JsonElement jsonElement) {
        GSelect gSelect = field.getAnnotation(GSelect.class);
        String[] selectors = gSelect.value().split(" ");
        JsonElementValuePair selected = getValue(jsonElement, selectors, field);

        if (selected.valueObject == null) {
            System.out.println("value not found : " + field.getName());
            return;
        }
        try {
            field.setAccessible(true);
            field.set(target, selected.valueObject);
//            System.out.println("set : " + field.getName() + " <- " + gson.toJson(selected.jsonElement));
        } catch (Exception e) {
            System.err.println("error on accessing field : " + field.getName());
            e.printStackTrace();
        }
    }

    private JsonElementValuePair getValue(JsonElement jsonElement, String[] selectors, Field field) {
        Class<?> type = field.getType();
        JsonElementValuePair selected = new JsonElementValuePair();
        selected.jsonElement = select(jsonElement, selectors);

        String selectedJsonString = gson.toJson(selected.jsonElement);
        try {
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            selected.valueObject = gson.fromJson(selectedJsonString, parameterizedType);
        } catch (ClassCastException e) {
            selected.valueObject = gson.fromJson(selectedJsonString, type);
        } catch (JsonSyntaxException e) {
            String message = "type mismatch\n"
                    + "The type you specifyied : " + type + ", but actually type is different.\n"
                    + "Json scoped now is below:\n"
                    + gson.toJson(selected.jsonElement);
            System.err.println(message);
            e.printStackTrace();
        }
        return selected;
    }

    protected Object getValue(String json, String[] selectors, Class<?> type) {
        JsonElement jsonElement = gson.fromJson(json, JsonElement.class);
        jsonElement = select(jsonElement, selectors);
        return gson.fromJson(gson.toJson(jsonElement), type);
    }

    private JsonElement select(JsonElement jsonElement, String[] selectors) {
        for (String selector : selectors) {
            try {
                jsonElement = select(jsonElement, selector);
            } catch (NullPointerException e) {
                System.err.println("error on selecting json element : " + selector + " in " + Arrays.toString(selectors));
                e.printStackTrace();
            }
        }
        return jsonElement;
    }

    private JsonElement select(JsonElement jsonElement, String selector) {
        JsonElement selectedElement = null;
        String tempJson = gson.toJson(jsonElement);
        if (jsonElement instanceof JsonObject) {
            selectedElement = gson.fromJson(tempJson, JsonElement.class).getAsJsonObject().get(selector);
        } else if (jsonElement instanceof JsonArray) {
            JsonArray jsonArray = gson.fromJson(tempJson, JsonElement.class).getAsJsonArray();
            List<JsonElement> elmList = new ArrayList<JsonElement>();
            for (JsonElement elm : jsonArray) {
                elmList.add(select(elm, selector));
            }
            tempJson = gson.toJson(elmList);
            selectedElement = gson.fromJson(tempJson, JsonElement.class);
        }
        return selectedElement;
    }
}

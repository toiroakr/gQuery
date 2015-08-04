package com.toiroakr.gquery;

import com.google.gson.Gson;

import java.util.List;

/**
 * class for user to select
 * Created by higuchiakira on 2015/08/03.
 */
public class GQuery {
    private GQueryParser parser = null;
    private Gson gson = null;

    public GQuery() {
        this.gson = new Gson();
        this.parser = new GQueryParser();
    }

    public GQuery(Gson gson) {
        this.gson = gson;
        this.parser = new GQueryParser();
    }

    private GQuery init(Class<?> targetClass) {
        this.parser.init(gson, targetClass);
        return this;
    }

    public <Target> Target select(String json, Class<Target> targetClass) {
        init(targetClass);
        return parser.select(json);
    }

    public <Target> List<Target> selectList(String json, Class<Target> targetClass) {
        init(targetClass);
        return parser.selectList(json);
    }

    @SuppressWarnings("unchecked")
    public <Type> Type get(String json, String query, Class<Type> targetClass) {
        return get(json, query.split(" "), targetClass);
    }

    @SuppressWarnings("unchecked")
    public <Type> Type get(String json, String[] queries, Class<Type> targetClass) {
        init(targetClass);
        return (Type) parser.getValue(json, queries, targetClass);
    }
}

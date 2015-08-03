package com.toiro_a.gquery;

import com.google.gson.Gson;

/**
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

    private GQuery(Class<?> targetClass) {
        parser = new GQueryParser();
        parser.init(gson, targetClass);
    }

    public <Target> Target select(String json, Class<Target> targetClass) {
        this.init(targetClass);
        return parser.select(json);
    }

    @SuppressWarnings("unchecked")
    public <Type> Type get(String json, String query, Class<Type> targetClass) {
        return get(json, query.split(" "), targetClass);
    }

    @SuppressWarnings("unchecked")
    public <Type> Type get(String json, String[] queries, Class<Type> targetClass) {
        this.init(targetClass);
        return (Type) parser.getValue(json, queries, targetClass);
    }
}

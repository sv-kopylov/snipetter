package ru.kopylov.snippeter.utils;

import lombok.Singular;

import java.util.HashMap;


public class Context {
    private HashMap<String, Object> map = new HashMap<>();
    private static Context instance;
    private Context(){
    }

    public static Context getInstance(){
        if (instance==null){
            instance=new Context();
        }
        return instance;
    }

    public void put(String name, Object obj){
        map.put(name, obj);
    }

    public Object get (String name){
        return map.get(name);
    }
}

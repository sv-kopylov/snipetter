package ru.kopylov.snippeter.context;

import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;


public class Context {
    private HashMap<String, Object> map = new HashMap<>();
    private static Context instance;
    private Context(){
    }

    @Getter
    @Setter
    private Stage primaryStage;


    public static Context getInstance(){
        if (instance==null){
            instance=new Context();
        }
        return instance;
    }

    public void put(String name, Object obj){
        map.put(name, obj);
    }
    public void put(Object obj){
        map.put(obj.getClass().getName(), obj);
    }

    public Object get (String name){
        return map.get(name);
    }

    public <T> T get(Class clazz){
        T result =  (T)(map.get(clazz.getName()));
        return result;
    }
}

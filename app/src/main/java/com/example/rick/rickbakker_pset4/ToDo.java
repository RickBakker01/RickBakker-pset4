package com.example.rick.rickbakker_pset4;


import java.io.Serializable;

/**
 * Created by Rick on 26-9-2017.
 */

public class ToDo implements Serializable{
    private String todoText;
    private int _id;


    public ToDo(String todoItem) {
        todoText = todoItem;
    }

    public ToDo(String todoItem, int ToDoid) {
        todoText = todoItem;
        _id = ToDoid;
    }

    public String getItem() {
        return todoText;
    }

    public int getID() {
        return _id;
    }
}

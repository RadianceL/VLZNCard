package com.example.eddie.card.PoJo;

/**
 * Created by eddie on 12/08/2017.
 */

public class card {

    private String name;
    private String array;

    public card(String name, String array) {
        this.name = name;
        this.array = array;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArray() {
        return array;
    }

    public void setArray(String array) {
        this.array = array;
    }
}

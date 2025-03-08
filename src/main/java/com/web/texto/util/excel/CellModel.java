package com.example.demo.util.excel;

import lombok.Data;

@Data
public class CellModel {
    private String data;
    private String color;
    private String backgroundColor;

    public CellModel(){
        init();

    }
    public CellModel(String data){
        init();
        this.data = data;
    }
    public CellModel(String data, String color){
        init();
        this.data = data;
        this.color = color;
    }
    public CellModel(String data, String color, String backgroundColor){
        init();
        this.data = data;
        this.color = color;
        this.backgroundColor = backgroundColor;
    }
    private void init(){
        this.data = "";
        this.color = "black";
        this.backgroundColor = "white";
    }



}

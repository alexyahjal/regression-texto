package com.example.demo.util.excel;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class CellModel {
    @Getter @Setter
    private String data;
    @Getter
    private String color;
    @Getter
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
        this.color = (isValidColor(color))?color:this.color;
    }
    public CellModel(String data, String color, String backgroundColor){
        init();
        this.data = data;
        this.color = (isValidColor(color))?color:this.color;
        this.backgroundColor = (isValidColor(backgroundColor))?backgroundColor:this.backgroundColor;
    }
    private void init(){
        this.data = "";
        this.color = CellColorEnum.BLACK.name();
        this.backgroundColor = CellColorEnum.WHITE.name();
    }

    private boolean isValidColor(String color){
        for( CellColorEnum col : CellColorEnum.values() ){
            if (color.equals( col.name() )) return true;
        }
        return false;
    }

    public void setColor( String color ){
        if( isValidColor(color)) this.color = color;

    }
    public void setBackgroundColor( String color ){
        if( isValidColor(color)) this.backgroundColor = color;
    }

    @Override
    public String toString(){
        return String.format(
                "[%s,%s,%s]",
                this.data,
                this.color,
                this.backgroundColor
        );
    }
}

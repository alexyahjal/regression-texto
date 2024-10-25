package com.web.texto.model;

import lombok.Data;

import java.util.Map;

@Data
public class HttpReqModel {

    private boolean error;
    private StringBuilder errorMsg;
    private Object context;
    private Object result;



    public void updateError(String errorMessage) {
        if (this.errorMsg == null) errorMsg = new StringBuilder();
        this.errorMsg.append(errorMessage).append("\n");
    }

    public String getContextValue( String key ){
        return ((Map<String,String>) this.context).get( key );
    }

}

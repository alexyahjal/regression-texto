package com.web.texto.util;

import java.lang.reflect.Field;
import java.util.Map;

public class Util {
    /**
     *
     * @param inputData
     * @param target
     * @throws Exception
     */
    public static void setModelFromMap(Object inputData, Object target) throws Exception{
        for(Field field : target.getClass().getDeclaredFields()){
            field.setAccessible( true );
            field.set( target , ((Map<String,String>) inputData).getOrDefault( field.getName() , null ) );
            field.setAccessible( false );
        }
    }

}

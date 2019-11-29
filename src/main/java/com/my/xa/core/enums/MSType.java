/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.my.xa.core.enums;

/**
 *
 * @author jack2
 */
public enum MSType {
    
    SUCCESS( "成功" ),
    ERROR( "错误" );
    
    private final String description;
    
    private MSType( String description ){
        this.description = description;
    }

    public String getName() {
        return name();
    }
    
    public String getDescription(){
        return description;
    }
    
}

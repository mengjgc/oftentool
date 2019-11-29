/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.my.xa.core.enums;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Jack2
 */
public enum DateFormatEnum {
    
    ISOYMDate( new SimpleDateFormat("yyyyMM") ),
    ISOTerseDate( new SimpleDateFormat("yyyyMMdd") ),
    ISOTerseOnlyTime( new SimpleDateFormat("HHmmss") ),
    ISODate( new SimpleDateFormat("yyyy-MM-dd") ),
    ISOTime( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ),
    ISOMinuteTime( new SimpleDateFormat("yyyy-MM-dd HH:mm") ),
    ISOOnlyTime( new SimpleDateFormat("HH:mm:ss") ),
    CN_ISODate( new SimpleDateFormat("yyyy年MM月dd日") ),
    CN_ISOTime( new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒") ),
    CN_MinuteTime( new SimpleDateFormat("yyyy年MM月dd日 HH时mm分") );

    private DateFormatEnum(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }
    
    private DateFormat dateFormat;

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }
    
    public synchronized Calendar getCalendar( String s ) throws ParseException{
        Calendar c = null;
        Date d = getDate(s);
        if( d != null ){
            c = Calendar.getInstance();
            c.setTime( d );
        }
        return c;
    }
    
    public synchronized String getString( Calendar c ) throws ParseException{
        return getDateFormat().format( c.getTime() );
    }
    
    public synchronized Date getDate( String s ) throws ParseException{
        return getDateFormat().parse( s );
    }
}

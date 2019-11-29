/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.my.xa.core.application.converter;

import java.text.ParseException;
import java.util.Calendar;

import com.my.xa.core.enums.DateFormatEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author jack2
 */
public class StringToCalendar implements Converter<String, Calendar> {

    private static final Logger LOG = LoggerFactory.getLogger( StringToCalendar.class.getName() );

    @Override
    public Calendar convert(String s) {
        Calendar c = null;
        try {
            c = DateFormatEnum.ISOTime.getCalendar( s );
        } catch (ParseException ex) {
            LOG.error( "字符到日期转换错误", ex );
        }
        if( c != null ){
            return c;
        }
        try {
            c = DateFormatEnum.ISOMinuteTime.getCalendar( s );
        } catch (ParseException ex) {
            LOG.error( "字符到日期转换错误", ex );
        }
        if( c != null ){
            return c;
        }
        try {
            c = DateFormatEnum.ISODate.getCalendar( s );
        } catch (ParseException ex) {
            LOG.error( "字符到日期转换错误", ex );
        }
        return c;
    }
    
}

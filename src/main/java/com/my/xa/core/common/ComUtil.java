package com.my.xa.core.common;

/**
 * @author jack2 <hxd@kenyte.com>
 */
public class ComUtil {

    public static String nullOrStr( Object obj ){
        return obj == null ? null : obj.toString();
    }

    public static String str( Object obj ){
        return obj == null ? "" : obj.toString();
    }

}

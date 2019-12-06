package com.my.xa.core.practice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

/**
 * @author ：xuluzs
 * @date ：Created in 2019/12/6 15:38
 * @description：test
 * @modified By：
 * @version: 1.0.0
 */
public class Test {
    private static final Logger log = LoggerFactory.getLogger( Test.class.getName() );
    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        log.info(""+stack.pop());
    }
}

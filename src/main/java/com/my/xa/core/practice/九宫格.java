package com.my.xa.core.practice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;


public class 九宫格 {
	private static final Logger log = LoggerFactory.getLogger( Test.class.getName() );
	public static void main(String[] args) throws Exception {
		Stack<Integer> stack = new Stack<Integer>();		//使用栈记录每一位的可能性
		for (int i = 1; ; i++) {
			if (i < 10) {
				if (stack.contains(i)) continue;
				stack.push(i);
				System.out.println(stack);
				Thread.sleep(100);
				if (stack.size() == 9 && check(stack)) {
					//找到了一组解
					print(stack);
					stack.pop();	//继续寻找下一组解
				} else {
					i = 0;	//填充下一位的时候，从第一种可能性开始
				}
			} else {
				//i的所有可能性都已经尝试过了，退到上一位
				if (stack.isEmpty()) return;
				i = stack.pop();
			}
		}
	}

	private static void print(Stack<Integer> stack) {
		int a0 = stack.get(0);
		int a1 = stack.get(1);
		int a2 = stack.get(2);
		int a3 = stack.get(3);
		int a4 = stack.get(4);
		int a5 = stack.get(5);
		int a6 = stack.get(6);
		int a7 = stack.get(7);
		int a8 = stack.get(8);
		System.out.println(a0 + " " + a1 + " " + a2);
		System.out.println(a3 + " " + a4 + " " + a5);
		System.out.println(a6 + " " + a7 + " " + a8);
		System.out.println();
	}

	private static boolean check(Stack<Integer> stack) {
		int a0 = stack.get(0);
		int a1 = stack.get(1);
		int a2 = stack.get(2);
		int a3 = stack.get(3);
		int a4 = stack.get(4);
		int a5 = stack.get(5);
		int a6 = stack.get(6);
		int a7 = stack.get(7);
		int a8 = stack.get(8);
		if (a0+a1+a2!=15) return false;
		if (a3+a4+a5!=15) return false;
		if (a6+a7+a8!=15) return false;
		if (a0+a3+a6!=15) return false;
		if (a1+a4+a7!=15) return false;
		if (a2+a5+a8!=15) return false;
		if (a0+a4+a8!=15) return false;
		if (a2+a4+a6!=15) return false;
		return true;
	}

}

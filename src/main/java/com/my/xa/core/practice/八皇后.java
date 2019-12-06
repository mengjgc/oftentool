package com.my.xa.core.practice;
import java.util.Stack;

public class 八皇后 {
	static int count = 0;
	public static void main(String[] args) {
		Stack<Integer> stack = new Stack<Integer>();		//使用栈记录每一位的可能性
		int n = 8;
		for (int i = 0; ; i++) {
			if (i < n) {
				if (stack.contains(i)) continue;	//检测是否在同一行
				if (check(stack, i)) continue;		//检测对角线
				stack.push(i);
				if (stack.size() == n) {
					//找到了一组解
					count++;
					print(stack);
					stack.pop();	//继续寻找下一组解
				} else {
					i = -1;	//填充下一位的时候，从第一种可能性开始
				}
			} else {
				//i的所有可能性都已经尝试过了，退到上一位
				if (stack.isEmpty()) {
					System.out.println(count);
					return;
				}
				i = stack.pop();
			}
		}
	}

	private static boolean check(Stack<Integer> stack, int col) {
		int row = stack.size();
		for (int i = 0; i < stack.size(); i++) {
			int d1 = Math.abs(i - row);
			int d2 = Math.abs(col - stack.get(i));
			if (d1==d2) return true;
		}
		return false;
	}

	private static void print(Stack<Integer> stack) {
		for (int i = 0; i < 8; i++) {
			int col = stack.get(i);
			for (int j = 0; j < 8; j++) {
				if (j==col) {
					System.out.print("回 ");
				} else {
					System.out.print("口 ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
}

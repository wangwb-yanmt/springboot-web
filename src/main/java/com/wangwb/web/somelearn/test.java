package com.wangwb.web.somelearn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class test {

	public static void main(String[] args) throws IOException {
		
		double aa = (Math.random() * 9 + 1) * 100000;
		System.out.println(aa);
		int num = (int) aa;
		System.out.println(num);
		
		
		
		char c;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("输入字符, 按下 'q' 键退出。");
        
        do {
            c = (char) br.read();
            System.out.println(c);
        } while (c != 'q');

	}

}

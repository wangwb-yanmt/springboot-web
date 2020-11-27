package com.wangwb.web.somelearn;

import java.util.Arrays;

public class Suanf {

	public static void main(String[] args) {
		insertSort();
	}
	
	/**
	 * 	直接插入排序   O(n^2)
	 */
	public static void insertSort(){
		int[] data = {1,5,6,8,9,4,7,2,3};
		int temp;
		for (int i = 1; i < data.length; i++) {
			temp = data[i];//待插入数据
			int j;
			for(j = i - 1; j >= 0; j--) {
				//判断是否大于temp，大于则后移一位
				if(data[j] > temp) {
					data[j+1] = data[j];
				}else{
					break;
				}
			}
			data[j + 1] = temp;
		}
		System.out.println(Arrays.toString(data));
	}
	
	/**
	 * 	简单选择排序    O(n^2)
	 */
	public static void selectSort() {
		int[] data = {1,5,6,8,9,4,7,2,3};
		for (int i = 0; i < data.length; i++) {
			int temp = data[i];
			int flag = i; //将当前下标定义为最小值下标
			for (int j = i + 1; j < data.length; j++) {
				if (data[j] < temp) { //a[j] < temp 从小到大排序；a[j] >temp 从大到小排序
					temp = data[j];
					flag = j; //如果有小于当前最小值的关键字将此关键字的下标赋值给flag
				}
			}
			if (flag != i) {
				data[flag] = data[i];
				data[i] = temp;
			}
		}
		System.out.println(Arrays.toString(data));
	}
	
	/**
	 * 冒泡排序	O(n^2)
	 */
	public static void bubbleSort(){
		int[] data = {2,5,6,8,9,4,7,1,3};
		int j , k;
		int flag = data.length ;//flag来记录最后交换的位置，也就是排序的尾边界
		while (flag > 0){//排序未结束标志
			k = flag; //k 来记录遍历的尾边界
			flag = 0;
			for(j=1; j<k; j++){
				if(data[j-1] > data[j]){//前面的数字大于后面的数字就交换
					//交换a[j-1]和a[j]
					int temp;
					temp = data[j-1];
					data[j-1] = data[j];
					data[j]=temp;
					//表示交换过数据
					flag = j;//记录最新的尾边界
				}
			}
		}
	}
	
	/**
	 * 	归并排序
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

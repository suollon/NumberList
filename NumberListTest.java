package com.suollon.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 从n个数中任意取出m个数,求这m个数之和最接近给定值sum的组合;
 * n一旦给定,不能更改;0<m<=n;
 */
public class NumberListTest {
	
	//此处考虑原始数据数量大于等于2个时的情况;
	//系统给定的值sum;(调整sum的值已测试,sum>0;)
//	public static double sum = 70;
	public static double sum = 150.2;
	
	public static void main(String[] args) {
		//准备数据;
		List<Double> oldList = new ArrayList<Double>();
		for (Double i = 93.0; i > 0; i=i-5) {
			oldList.add(i);
		}
		
		//测试;
		long start = System.currentTimeMillis();
		getAnswer(oldArray(oldList));
		long end = System.currentTimeMillis();
		System.out.println("\n运行时间:"+(end-start)+"ms");
	}
	
	/**
	 * 1.将数据存入原始数据数组,并排序,得到oldArray;
	 */
	public static Object[] oldArray(List<Double> oldList){
		Object[] oldArray = oldList.toArray();
		Arrays.sort(oldArray);
		System.out.print("oldArray-----");
		for (int i = 0; i < oldArray.length; i++) {
			System.out.print(oldArray[i]+" , ");
		}
		System.out.println();
		return oldArray;
	}
	
	/**
	 * 2.处理数组oldArray,过滤数据,得到newArray;
	 * @return
	 */
	public static Object[] newArray(Object[] oldArray){
		Object[] newArray = null;
		for (int i = 0; i < oldArray.length; i++) {
			if ((double)oldArray[oldArray.length-1] < sum) {
				newArray = oldArray;
				break;
			}else if ((double)oldArray[i] >= sum) {
				newArray = Arrays.copyOfRange(oldArray, 0, i+1);
				break;
			}
		}
		System.out.print("newArray-----");
		for (int i = 0; i < newArray.length; i++) {
			System.out.print(newArray[i]+" , ");
		}
		System.out.println();
		return newArray;
	}
	
	/**
	 * 3.如何从n个数中随机抽取m个;
	 */
	public static void getAnswer(Object[] oldArray){
		List<Double> sumList = new ArrayList<Double>();
		List<Integer[]> keyList = new ArrayList<Integer[]>();
		Object[] newArray = newArray(oldArray);
		int n = newArray.length;
		//共有(Math.pow(2,n)-1)个组合方式;
		for (int i = 0; i < Math.pow(2,n)-1;i++) {
			String string = Integer.toBinaryString(i);
			//第string个组合方式,对应的二进制数组;
			String[] strArray = string.split("");
			//将数组前位补零,使string[]长度与newArray长度相同,得到lastArr;
			Integer[] lastArray = new Integer[n];
			for (int j = 0; j < n; j++) {
				lastArray[j] = 0;
			}
			for (int j = 0; j < strArray.length; j++) {
				if ("".equals(strArray[j])) {
					continue;
				}else {
					lastArray[j+n-strArray.length] = Integer.parseInt(strArray[j]);
				}
			}
			
			double subsum = 0;
			for (int j = 0; j < n; j++) {
				subsum = subsum + lastArray[j] * (double)newArray[j];
			}
			
			if (subsum >= sum) {
				if (sumList.size() == 0) {
					sumList.add(subsum);
					keyList.add(lastArray);
				} else {
					//此处逻辑有缺陷,删除第一位一定是最精确的嘛?待考证;
					if (subsum < sumList.get(0)) {
						sumList.remove(0);
						keyList.remove(0);
						sumList.add(subsum);
						keyList.add(lastArray);
					} else if (subsum == sumList.get(0)) {
						sumList.add(subsum);
						keyList.add(lastArray);
					}
				}
			}
		}
		printAnswer(sumList, keyList, newArray);
	}
	
	/**
	 * 4.打印结果;
	 * @param sumList
	 * @param keyList
	 * @param newArray
	 */
	public static void printAnswer(List<Double> sumList, List<Integer[]> keyList, Object[] newArray) {
		double closestSum = Collections.min(sumList);
		System.out.print("给定值sum="+sum+", 最接近sum的数字组合之和为subsum="+closestSum+", 所有组合方式如下:\n");
		int num = 0;
		for (int i = 0; i < sumList.size(); i++) {
			if (closestSum != sumList.get(i)) {
				continue;
			}
			Integer[] arr = keyList.get(i);
			System.out.print("组合方式"+ ++num + ":[");
			for (int j = 0; j < arr.length; j++) {
				if (arr[j] != 0) {
					System.out.print(arr[j]*((double)newArray[j])+"  ");
				}
			}
			System.out.println("]");
		}
	}
}

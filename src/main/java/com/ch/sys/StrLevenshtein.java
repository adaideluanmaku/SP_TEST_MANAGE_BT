package com.ch.sys;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/*
 * 字符串对比
 */
@Service
public class StrLevenshtein {
	public static void main(String[] args) {
		StrLevenshtein lt = new StrLevenshtein();
		String str = "123申请单";
		String target = "申请单002";
//		System.out.println("similarityRatio=" + lt.getSimilarityRatio(str, target));
		System.out.println(lt.str2tosame(str,target));
	}
	
	private int compare(String str, String target) {
		int d[][]; // 矩阵
		int n = str.length();
		int m = target.length();
		int i; // 遍历str的
		int j; // 遍历target的
		char ch1; // str的
		char ch2; // target的
		int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
		if (n == 0) {
			return m;
		}
		if (m == 0) {
			return n;
		}
		d = new int[n + 1][m + 1];
		for (i = 0; i <= n; i++) { // 初始化第一列
			d[i][0] = i;
		}

		for (j = 0; j <= m; j++) { // 初始化第一行
			d[0][j] = j;
		}

		for (i = 1; i <= n; i++) { // 遍历str
			ch1 = str.charAt(i - 1);
			// 去匹配target
			for (j = 1; j <= m; j++) {
				ch2 = target.charAt(j - 1);
				if (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2) {
					temp = 0;
				} else {
					temp = 1;
				}
				// 左边+1,上边+1, 左上角+temp取最小
				d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
			}
		}
		return d[n][m];
	}

	private int min(int one, int two, int three) {
		return (one = one < two ? one : two) < three ? one : three;
	}

	/**
	 * 获取两字符串的相似度，字符对比
	 */

	public float getSimilarityRatio(String str, String target) {
		return 1 - (float) compare(str, target) / Math.max(str.length(), target.length());
	}
	
	public int str2tosame(String str1,String str2){
		int same=0;
		if(str1.length()==0 && str2.length()==0){
			return 1;
		}
		
		if(str1.length()==0 && str2.length()>0){
			return 0;
		}
		
		if(str1.length()>0 && str2.length()==0){
			return 0;
		}
		
		char[] stra=str1.toCharArray();
		char[] strb=str2.toCharArray();
		
		List strlist1= new ArrayList();
		for(int i=0;i<stra.length;i++){
			strlist1.add(stra[i]);
		}
		
		List strlist2= new ArrayList();
		for(int i=0;i<strb.length;i++){
			strlist2.add(strb[i]);
		}
		
		for(int i=0;i<strlist1.size();i++){
			for(int i1=0;i1<strlist2.size();i1++){
				if(strlist1.get(i).equals(strlist2.get(i1))){
					same+=1;
					strlist1.remove(i);
					strlist2.remove(i1);
					i-=1;
					i1-=1;
					break;
				}
			}
		}
		return same;
	}
}

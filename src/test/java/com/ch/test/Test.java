package com.ch.test;

public class Test {
	public static void main(String args[]){
//		Object a="0";
//		if(a instanceof Integer){
//			System.out.println("11111");
//		}
		
		String frequency="1/1/1";
		
		int frequency_int=-1;
		try{
			frequency_int=Integer.parseInt(frequency);
		}catch(Exception e){
			
		}
		
		String[] frequencys=null;
		int times=0;
		int days=0;
		if(frequency_int==-1){
			frequencys=frequency.split("/");
			if(frequencys.length==2){
				try{
					times=Integer.parseInt(frequencys[0].toString());
				}catch(Exception e){
					times=-1;
				}
				try{
					days=Integer.parseInt(frequencys[1].toString());
				}catch(Exception e){
					days=-1;
				}
			}else{
				times=-1;
				days=-1;
			}
		}else{
			times=frequency_int;
			days=frequency_int;
		}
		
		System.out.println(times+":"+days);
		
		System.out.println("22222");
	}
}

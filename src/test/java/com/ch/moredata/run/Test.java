package com.ch.moredata.run;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ch.dao.Mysqlconnection;

public class Test {
	public static void main(String args[]){
		boolean aa=true;
		int bb=0;
		while(aa){
			
			System.out.println(bb);
			bb++;
			if(bb==10){
				aa=false;
			}
		}
	}
}

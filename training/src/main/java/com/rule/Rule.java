package com.rule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Rule {

	public static String StringDate(LocalDateTime DateTime) {
		
		//フォーマットを指定
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
		//yyyy年MM月dd日に変換
        String StringDate = DateTime.format(f);
        
		return StringDate;
	}
	
}

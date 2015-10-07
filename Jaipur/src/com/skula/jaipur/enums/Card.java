package com.skula.jaipur.enums;

import java.util.ArrayList;
import java.util.List;

public enum Card {
	DIAMANT, GOLD, SILVER, FABRICS, SPICES, LEATHER, CAMEL;
	
	public static List<Card> getCards(){
		List<Card> res = new ArrayList<Card>();
		for(int i=0; i<11; i++){
			res.add(CAMEL);
		}
		for(int i=0; i<6; i++){
			res.add(DIAMANT);
		}
		for(int i=0; i<6; i++){
			res.add(GOLD);
		}
		for(int i=0; i<6; i++){
			res.add(SILVER);
		}
		for(int i=0; i<8; i++){
			res.add(FABRICS);
		}
		for(int i=0; i<8; i++){
			res.add(SPICES);
		}
		for(int i=0; i<10; i++){
			res.add(LEATHER);
		}
		return res;
	}
}

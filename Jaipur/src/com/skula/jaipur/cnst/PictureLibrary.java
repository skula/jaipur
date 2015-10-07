package com.skula.jaipur.cnst;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.skula.jaipur.R;

public class PictureLibrary{
	private Map<Integer, Bitmap> map;

	@SuppressLint("UseSparseArrays")
	public PictureLibrary(Resources res) {
		this.map = new HashMap<Integer, Bitmap>();
		/*this.map.put(R.drawable.card_diamant, BitmapFactory.decodeResource(res, R.drawable.card_diamant));
		this.map.put(R.drawable.card_gold, BitmapFactory.decodeResource(res, R.drawable.card_gold));
		this.map.put(R.drawable.card_silver, BitmapFactory.decodeResource(res, R.drawable.card_silver));
		this.map.put(R.drawable.card_fabric, BitmapFactory.decodeResource(res, R.drawable.card_fabric));
		this.map.put(R.drawable.card_spices, BitmapFactory.decodeResource(res, R.drawable.card_spices));
		this.map.put(R.drawable.card_leather, BitmapFactory.decodeResource(res, R.drawable.card_leather));
		this.map.put(R.drawable.card_camel, BitmapFactory.decodeResource(res, R.drawable.card_camel));
		this.map.put(R.drawable.card_back, BitmapFactory.decodeResource(res, R.drawable.card_back));
		
		this.map.put(R.drawable.token_diamant, BitmapFactory.decodeResource(res, R.drawable.token_diamant));
		this.map.put(R.drawable.token_gold, BitmapFactory.decodeResource(res, R.drawable.token_gold));
		this.map.put(R.drawable.token_silver, BitmapFactory.decodeResource(res, R.drawable.token_silver));
		this.map.put(R.drawable.token_fabric, BitmapFactory.decodeResource(res, R.drawable.token_fabric));
		this.map.put(R.drawable.token_spices, BitmapFactory.decodeResource(res, R.drawable.token_spices));
		this.map.put(R.drawable.token_leather, BitmapFactory.decodeResource(res, R.drawable.token_leather));
		this.map.put(R.drawable.token_camel, BitmapFactory.decodeResource(res, R.drawable.token_camel));
		
		this.map.put(R.drawable.token_bonus_3, BitmapFactory.decodeResource(res, R.drawable.token_bonus_3));
		this.map.put(R.drawable.token_bonus_4, BitmapFactory.decodeResource(res, R.drawable.token_bonus_4));
		this.map.put(R.drawable.token_bonus_5, BitmapFactory.decodeResource(res, R.drawable.token_bonus_5));
		
		this.map.put(R.drawable.token_seal, BitmapFactory.decodeResource(res, R.drawable.token_seal));*/
	}

	public Bitmap get(int id) {
		return map.get(id);
	}
}

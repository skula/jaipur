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
		this.map.put(R.drawable.card_diamant, BitmapFactory.decodeResource(res, R.drawable.card_diamant));
		this.map.put(R.drawable.card_gold, BitmapFactory.decodeResource(res, R.drawable.card_gold));
		this.map.put(R.drawable.card_silver, BitmapFactory.decodeResource(res, R.drawable.card_silver));
		this.map.put(R.drawable.card_fabrics, BitmapFactory.decodeResource(res, R.drawable.card_fabrics));
		this.map.put(R.drawable.card_spices, BitmapFactory.decodeResource(res, R.drawable.card_spices));
		this.map.put(R.drawable.card_leather, BitmapFactory.decodeResource(res, R.drawable.card_leather));
		this.map.put(R.drawable.card_camel, BitmapFactory.decodeResource(res, R.drawable.card_camel));
		this.map.put(R.drawable.card_back, BitmapFactory.decodeResource(res, R.drawable.card_back));
		
		this.map.put(R.drawable.card_sel, BitmapFactory.decodeResource(res, R.drawable.card_sel));
		
		this.map.put(R.drawable.token_diamant_7, BitmapFactory.decodeResource(res, R.drawable.token_diamant_7));
		this.map.put(R.drawable.token_diamant_5, BitmapFactory.decodeResource(res, R.drawable.token_diamant_5));
		this.map.put(R.drawable.token_gold_6, BitmapFactory.decodeResource(res, R.drawable.token_gold_6));
		this.map.put(R.drawable.token_gold_5, BitmapFactory.decodeResource(res, R.drawable.token_gold_5));
		this.map.put(R.drawable.token_silver_5, BitmapFactory.decodeResource(res, R.drawable.token_silver_5));
		this.map.put(R.drawable.token_fabrics_5, BitmapFactory.decodeResource(res, R.drawable.token_fabrics_5));
		this.map.put(R.drawable.token_fabrics_3, BitmapFactory.decodeResource(res, R.drawable.token_fabrics_3));
		this.map.put(R.drawable.token_fabrics_2, BitmapFactory.decodeResource(res, R.drawable.token_fabrics_2));
		this.map.put(R.drawable.token_fabrics_1, BitmapFactory.decodeResource(res, R.drawable.token_fabrics_1));
		this.map.put(R.drawable.token_spices_5, BitmapFactory.decodeResource(res, R.drawable.token_spices_5));
		this.map.put(R.drawable.token_spices_3, BitmapFactory.decodeResource(res, R.drawable.token_spices_3));
		this.map.put(R.drawable.token_spices_2, BitmapFactory.decodeResource(res, R.drawable.token_spices_2));
		this.map.put(R.drawable.token_spices_1, BitmapFactory.decodeResource(res, R.drawable.token_spices_1));
		this.map.put(R.drawable.token_leather_4, BitmapFactory.decodeResource(res, R.drawable.token_leather_4));
		this.map.put(R.drawable.token_leather_3, BitmapFactory.decodeResource(res, R.drawable.token_leather_3));
		this.map.put(R.drawable.token_leather_2, BitmapFactory.decodeResource(res, R.drawable.token_leather_2));
		this.map.put(R.drawable.token_leather_1, BitmapFactory.decodeResource(res, R.drawable.token_leather_1));
		this.map.put(R.drawable.token_camel, BitmapFactory.decodeResource(res, R.drawable.token_camel));
		
		this.map.put(R.drawable.token_bonus_3, BitmapFactory.decodeResource(res, R.drawable.token_bonus_3));
		this.map.put(R.drawable.token_bonus_4, BitmapFactory.decodeResource(res, R.drawable.token_bonus_4));
		this.map.put(R.drawable.token_bonus_5, BitmapFactory.decodeResource(res, R.drawable.token_bonus_5));
		
		this.map.put(R.drawable.token_seal, BitmapFactory.decodeResource(res, R.drawable.token_seal));

		this.map.put(R.drawable.background, BitmapFactory.decodeResource(res, R.drawable.background));
		
		this.map.put(R.drawable.btn_buy, BitmapFactory.decodeResource(res, R.drawable.btn_buy));
		this.map.put(R.drawable.btn_trade, BitmapFactory.decodeResource(res, R.drawable.btn_trade));
		this.map.put(R.drawable.btn_sale, BitmapFactory.decodeResource(res, R.drawable.btn_sale));
		this.map.put(R.drawable.btn_play, BitmapFactory.decodeResource(res, R.drawable.btn_play));
	}

	public Bitmap get(int id) {
		return map.get(id);
	}
}

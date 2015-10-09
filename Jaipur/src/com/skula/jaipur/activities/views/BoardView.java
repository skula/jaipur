package com.skula.jaipur.activities.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.skula.jaipur.services.Drawer;
import com.skula.jaipur.services.GameEngine;

public class BoardView extends View {
	public static final int AREA_NONE = -1;
	public static final int AREA_MARKET_1 = 0;
	public static final int AREA_MARKET_2 = 1;
	public static final int AREA_MARKET_3 = 2;
	public static final int AREA_MARKET_4 = 3;
	public static final int AREA_MARKET_5 = 4;
	
	public static final int AREA_HAND_1 = 5;
	public static final int AREA_HAND_2 = 6;
	public static final int AREA_HAND_3 = 7;
	public static final int AREA_HAND_4 = 8;
	public static final int AREA_HAND_5 = 9;
	public static final int AREA_HAND_6 = 10;
	public static final int AREA_HAND_7 = 11;
	
	public static final int AREA_CAMEL = 12;
	
	public static final int AREA_BTN_SALE = 13;
	public static final int AREA_BTN_TRADE = 14;
	public static final int AREA_BTN_BUY = 15;

	private Paint paint;
	private Resources res;
	private Drawer drawer;
	private GameEngine engine;
	
	private String msg;

	public BoardView(Context context) {
		super(context);
		this.engine = new GameEngine();
		this.drawer = new Drawer(getResources(), engine);
		this.paint = new Paint();
		this.msg = "";
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			int area = getArea(x, y);
			msg = area+"";
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		invalidate();
		return true;
	}
	
	public int getArea(int xp, int yp){
		// marche
		int x = Drawer.X0_MARKET;
		int dx = Drawer.CARD_WIDTH + 10;
		int y = Drawer.Y0_MARKET;
		Rect destRect = null;
		for (int i = 0; i < 5; i++) {
			if (engine.getMarket()[i] != null) {
				destRect = new Rect(x, y, x + Drawer.CARD_WIDTH, y + Drawer.CARD_HEIGHT);
				if(destRect.contains(xp, yp)){
					switch(i){
					case 0:
						return AREA_MARKET_1;
					case 1:
						return AREA_MARKET_2;
					case 2:
						return AREA_MARKET_3;
					case 3:
						return AREA_MARKET_4;
					case 4:
						return AREA_MARKET_5;
					default:
						break;
					}
				}
			}
			x += dx;
		}
		
		// main
		x = Drawer.X0_HAND;
		dx = Drawer.CARD_WIDTH + 10;
		y = Drawer.Y0_HAND;
		destRect = null;		
		for(int i=0; i<engine.getCurrentPlayer().getHand().size(); i++){
			destRect = new Rect(x, y, x + Drawer.CARD_WIDTH, y + Drawer.CARD_HEIGHT);
			if(destRect.contains(xp, yp)){
				switch (i) {
				case 0:
					return AREA_HAND_1;
				case 1:
					return AREA_HAND_2;
				case 2:
					return AREA_HAND_3;
				case 3:
					return AREA_HAND_4;
				case 4:
					return AREA_HAND_5;
				case 5:
					return AREA_HAND_6;
				case 6:
					return AREA_HAND_7;
				}
			}
			x += dx;
		}

		// chameaux
		x = Drawer.X0_HAND + 7 * dx + 30;
		if (engine.getCurrentPlayer().getnCamels() > 1) {
			destRect = new Rect(x, Drawer.Y0_HAND, x + Drawer.CARD_WIDTH, Drawer.Y0_HAND + Drawer.CARD_HEIGHT);
			if(destRect.contains(xp, yp)){
				return AREA_CAMEL;
			}
		}
		
		// boutons
		
		return AREA_NONE;
	}

	@Override
	public void draw(Canvas canvas) {
		drawer.draw(canvas);
		paint.setColor(Color.RED);
		paint.setTextSize(33f);
		canvas.drawText(msg, 200, 200, paint);
	}
}

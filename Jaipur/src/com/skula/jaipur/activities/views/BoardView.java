package com.skula.jaipur.activities.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.skula.jaipur.cnst.Cnst;
import com.skula.jaipur.services.Drawer;
import com.skula.jaipur.services.GameEngine;

public class BoardView extends View {
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
			msg = area + "";
			engine.process(area);
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		invalidate();
		return true;
	}
	
	private int getArea(int xp, int yp){
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
						return Cnst.AREA_MARKET_1;
					case 1:
						return Cnst.AREA_MARKET_2;
					case 2:
						return Cnst.AREA_MARKET_3;
					case 3:
						return Cnst.AREA_MARKET_4;
					case 4:
						return Cnst.AREA_MARKET_5;
					default:
						break;
					}
				}
			}
			x += dx;
		}
		
		// main
		x = Drawer.X0_HAND;
		dx = Drawer.CARD_WIDTH * 2 / 3;
		y = Drawer.Y0_HAND;
		destRect = null;		
		for(int i=0; i<engine.getCurrentPlayer().getHand().size(); i++){
			y = i % 2 == 0 ? Drawer.Y0_HAND - 5 : Drawer.Y0_HAND + 5;
			destRect = new Rect(x, y, x + dx, y + Drawer.CARD_HEIGHT);
			if(destRect.contains(xp, yp)){
				switch (i) {
				case 0:
					return Cnst.AREA_HAND_1;
				case 1:
					return Cnst.AREA_HAND_2;
				case 2:
					return Cnst.AREA_HAND_3;
				case 3:
					return Cnst.AREA_HAND_4;
				case 4:
					return Cnst.AREA_HAND_5;
				case 5:
					return Cnst.AREA_HAND_6;
				case 6:
					return Cnst.AREA_HAND_7;
				}
			}
			x += dx;
		}

		// chameaux
		x = Drawer.X0_HAND + (Drawer.CARD_WIDTH * 2 / 3) * 7 + 60;
		if (engine.getCurrentPlayer().getnCamels() > 1) {
			// ajouter chameau
			destRect = new Rect(x, Drawer.Y0_HAND, x + Drawer.CARD_WIDTH, Drawer.Y0_HAND + Drawer.CARD_HEIGHT/2);
			if(destRect.contains(xp, yp)){
				return Cnst.AREA_CAMEL_ADD;
			}
			// enlever chameau
			destRect = new Rect(x, Drawer.Y0_HAND + Drawer.CARD_HEIGHT/2, x + Drawer.CARD_WIDTH, Drawer.Y0_HAND + Drawer.CARD_HEIGHT);
			if(destRect.contains(xp, yp)){
				return Cnst.AREA_CAMEL_REMOVE;
			}
		}
		
		// boutons
		
		return Cnst.AREA_NONE;
	}

	@Override
	public void draw(Canvas canvas) {
		drawer.draw(canvas);
		paint.setColor(Color.RED);
		paint.setTextSize(33f);
		canvas.drawText(msg, 200, 200, paint);
	}
}

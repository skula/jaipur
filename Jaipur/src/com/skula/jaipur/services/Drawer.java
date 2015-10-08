package com.skula.jaipur.services;

import java.util.List;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.skula.jaipur.R;
import com.skula.jaipur.cnst.PictureLibrary;
import com.skula.jaipur.enums.Card;

public class Drawer {
	public static final int X0_TOKEN = 10;
	public static final int Y0_TOKEN = 100;

	public static final int X0_MARKET = 550;
	private static final int Y0_MARKET = 350;

	public static final int X0_HAND = 350;
	public static final int Y0_HAND = 550;

	public static final int CARD_WIDTH = 99;
	public static final int CARD_HEIGHT = 140;
	private static final Rect CARD_RECT = new Rect(0, 0, CARD_WIDTH, CARD_HEIGHT);
	public static final int TOKEN_SIZE = 68;
	private static final Rect TOKEN_RECT = new Rect(0, 0, TOKEN_SIZE, TOKEN_SIZE);

	private PictureLibrary lib;
	private Paint paint;
	private GameEngine engine;

	public Drawer(Resources res, GameEngine engine) {
		this.engine = engine;
		this.paint = new Paint();
		this.lib = new PictureLibrary(res);
	}

	public void draw(Canvas c) {
		drawTokens(c);
		drawMarket(c);
		drawPlayers(c);
		// drawButtons(c);

		paint.setColor(Color.RED);
		paint.setTextSize(33f);
		int w = lib.get(R.drawable.card_back).getWidth();
		int h = lib.get(R.drawable.card_back).getHeight();
		c.drawText("card: " + w + ", " + h, 300, 30, paint);
		w = lib.get(R.drawable.token_bonus_3).getWidth();
		h = lib.get(R.drawable.token_bonus_3).getHeight();
		c.drawText("card: " + w + ", " + h, 300, 70, paint);
	}

	private void drawTokens(Canvas c) {
		int x = X0_TOKEN;
		int dx = 20;
		int y = Y0_TOKEN;
		int dy = TOKEN_SIZE + 10;

		// jetons de marchandises
		int cpt = 0;
		int bonusCpt = 0;
		Rect destRect = null;
		for (List<Integer> l : engine.getWareTokens()) {
			x = X0_TOKEN;

			for (int i = l.size() - 1; i >= 0; i--) {
				destRect = new Rect(x, y, x + TOKEN_SIZE, y + TOKEN_SIZE);
				switch (cpt) {
				case GameEngine.TOKEN_DIAMANT:
					if (l.get(i) == 7) {
						c.drawBitmap(lib.get(R.drawable.token_diamant_7), TOKEN_RECT, destRect, paint);
					} else {
						c.drawBitmap(lib.get(R.drawable.token_diamant_5), TOKEN_RECT, destRect, paint);
					}
					break;
				case GameEngine.TOKEN_GOLD:
					if (l.get(i) == 6) {
						c.drawBitmap(lib.get(R.drawable.token_gold_6), TOKEN_RECT, destRect, paint);
					} else {
						c.drawBitmap(lib.get(R.drawable.token_gold_5), TOKEN_RECT, destRect, paint);
					}
					break;
				case GameEngine.TOKEN_SILVER:
					c.drawBitmap(lib.get(R.drawable.token_silver_5), TOKEN_RECT, destRect, paint);
					break;
				case GameEngine.TOKEN_FABRICS:
					if (l.get(i) == 5) {
						c.drawBitmap(lib.get(R.drawable.token_fabrics_5), TOKEN_RECT, destRect, paint);
					} else if (l.get(i) == 3) {
						c.drawBitmap(lib.get(R.drawable.token_fabrics_3), TOKEN_RECT, destRect, paint);
					} else if (l.get(i) == 2) {
						c.drawBitmap(lib.get(R.drawable.token_fabrics_2), TOKEN_RECT, destRect, paint);
					} else {
						c.drawBitmap(lib.get(R.drawable.token_fabrics_1), TOKEN_RECT, destRect, paint);
					}
					break;
				case GameEngine.TOKEN_SPICES:
					if (l.get(i) == 5) {
						c.drawBitmap(lib.get(R.drawable.token_spices_5), TOKEN_RECT, destRect, paint);
					} else if (l.get(i) == 3) {
						c.drawBitmap(lib.get(R.drawable.token_spices_3), TOKEN_RECT, destRect, paint);
					} else if (l.get(i) == 2) {
						c.drawBitmap(lib.get(R.drawable.token_spices_2), TOKEN_RECT, destRect, paint);
					} else {
						c.drawBitmap(lib.get(R.drawable.token_spices_1), TOKEN_RECT, destRect, paint);
					}
					break;
				case GameEngine.TOKEN_LEATHER:
					if (l.get(i) == 4) {
						c.drawBitmap(lib.get(R.drawable.token_leather_4), TOKEN_RECT, destRect, paint);
					} else if (l.get(i) == 3) {
						c.drawBitmap(lib.get(R.drawable.token_leather_3), TOKEN_RECT, destRect, paint);
					} else if (l.get(i) == 2) {
						c.drawBitmap(lib.get(R.drawable.token_leather_2), TOKEN_RECT, destRect, paint);
					} else {
						c.drawBitmap(lib.get(R.drawable.token_leather_1), TOKEN_RECT, destRect, paint);
					}
					break;
				}
				x += dx;
			}

			y += dy;
			cpt++;
			bonusCpt++;
		}

		x = X0_TOKEN;
		// jetons de bonus
		if (!engine.getBonusTokens().get(0).isEmpty()) {
			destRect = new Rect(x, y, x + TOKEN_SIZE, y + TOKEN_SIZE);
			c.drawBitmap(lib.get(R.drawable.token_bonus_3), TOKEN_RECT, destRect, paint);
		}
		x += TOKEN_SIZE + 10;
		if (!engine.getBonusTokens().get(1).isEmpty()) {
			destRect = new Rect(x, y, x + TOKEN_SIZE, y + TOKEN_SIZE);
			c.drawBitmap(lib.get(R.drawable.token_bonus_4), TOKEN_RECT, destRect, paint);
		}
		x += TOKEN_SIZE + 10;
		if (!engine.getBonusTokens().get(2).isEmpty()) {
			destRect = new Rect(x, y, x + TOKEN_SIZE, y + TOKEN_SIZE);
			c.drawBitmap(lib.get(R.drawable.token_bonus_5), TOKEN_RECT, destRect, paint);
		}
	}

	// TODO: faire cadre cartes selectionnées
	private void drawMarket(Canvas c) {
		int x = X0_MARKET;
		int dx = CARD_WIDTH + 10;
		int y = Y0_MARKET;

		Rect destRect = null;
		for (int i = 0; i < 5; i++) {
			if (engine.getMarket()[i] != null) {
				destRect = new Rect(x, y, x + CARD_WIDTH, y + CARD_HEIGHT);
				switch (engine.getMarket()[i]) {
				case DIAMANT:
					c.drawBitmap(lib.get(R.drawable.card_diamant), CARD_RECT, destRect, paint);
					break;
				case GOLD:
					c.drawBitmap(lib.get(R.drawable.card_gold), CARD_RECT, destRect, paint);
					break;
				case SILVER:
					c.drawBitmap(lib.get(R.drawable.card_silver), CARD_RECT, destRect, paint);
					break;
				case FABRICS:
					c.drawBitmap(lib.get(R.drawable.card_fabrics), CARD_RECT, destRect, paint);
					break;
				case SPICES:
					c.drawBitmap(lib.get(R.drawable.card_spices), CARD_RECT, destRect, paint);
					break;
				case LEATHER:
					c.drawBitmap(lib.get(R.drawable.card_leather), CARD_RECT, destRect, paint);
					break;
				case CAMEL:
					c.drawBitmap(lib.get(R.drawable.card_camel), CARD_RECT, destRect, paint);
					break;
				}
			}
			x += dx;
		}
	}

	// TODO: faire cadre cartes selectionnées
	private void drawPlayers(Canvas c) {
		int x = X0_HAND;
		int dx = CARD_WIDTH + 10;
		int y = Y0_HAND;

		Rect destRect = null;
		for (Card i : engine.getCurrentPlayer().getHand()) {
			destRect = new Rect(x, y, x + CARD_WIDTH, y + CARD_HEIGHT);
			switch (i) {
			case DIAMANT:
				c.drawBitmap(lib.get(R.drawable.card_diamant), CARD_RECT, destRect, paint);
				break;
			case GOLD:
				c.drawBitmap(lib.get(R.drawable.card_gold), CARD_RECT, destRect, paint);
				break;
			case SILVER:
				c.drawBitmap(lib.get(R.drawable.card_silver), CARD_RECT, destRect, paint);
				break;
			case FABRICS:
				c.drawBitmap(lib.get(R.drawable.card_fabrics), CARD_RECT, destRect, paint);
				break;
			case SPICES:
				c.drawBitmap(lib.get(R.drawable.card_spices), CARD_RECT, destRect, paint);
				break;
			case LEATHER:
				c.drawBitmap(lib.get(R.drawable.card_leather), CARD_RECT, destRect, paint);
				break;
			case CAMEL:
				c.drawBitmap(lib.get(R.drawable.card_camel), CARD_RECT, destRect, paint);
				break;
			}
			x += dx;
		}

		x = X0_HAND + 7 * dx + 30;
		if (engine.getCurrentPlayer().getnCamels() > 1) {
			destRect = new Rect(x, Y0_HAND, x + CARD_WIDTH, Y0_HAND + CARD_HEIGHT);
			c.drawBitmap(lib.get(R.drawable.card_camel), CARD_RECT, destRect, paint);
			paint.setColor(Color.BLACK);
			paint.setTextSize(40f);
			x += 50;
			y += 130;
			c.drawText("" + engine.getCurrentPlayer().getnCamels(), x, y, paint);
		}
	}

	public void drawButtons(Canvas c) {

	}
}

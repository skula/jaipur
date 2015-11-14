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
	public static final int X0_TOKEN = 370;
	public static final int Y0_TOKEN = 130;

	public static final int X0_MARKET = 450;
	public static final int Y0_MARKET = 320;

	public static final int X0_HAND = 450;
	public static final int Y0_HAND = 500;

	public static final int X0_BUTTON = 500;
	public static final int Y0_BUTTON = 250;

	public static final int BUTTON_WIDTH = 99;
	public static final int BUTTON_HEIGHT = 36;
	private static final Rect BUTTON_RECT = new Rect(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);

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
		c.drawBitmap(lib.get(R.drawable.background), new Rect(0, 0, 1243, 709), new Rect(0, 0, 1243, 709), paint);
		drawTokens(c);
		drawMarket(c);
		drawPlayers(c);
		drawButtons(c);

		//drawLog(c);
	}
	
	private void drawLog(Canvas c){
		paint.setColor(Color.RED);
		paint.setTextSize(33f);
		int w = lib.get(R.drawable.card_back).getWidth();
		int h = lib.get(R.drawable.card_back).getHeight();
		c.drawText("card: " + w + ", " + h, 300, 30, paint);
		w = lib.get(R.drawable.token_bonus_3).getWidth();
		h = lib.get(R.drawable.token_bonus_3).getHeight();
		c.drawText("token: " + w + ", " + h, 300, 70, paint);
		w = lib.get(R.drawable.background).getWidth();
		h = lib.get(R.drawable.background).getHeight();
		c.drawText("background: " + w + ", " + h, 300, 110, paint);
		w = lib.get(R.drawable.btn_buy).getWidth();
		h = lib.get(R.drawable.btn_buy).getHeight();
		c.drawText("btn: " + w + ", " + h, 300, 160, paint);
	}

	private void drawTokens(Canvas c) {
		int x = X0_TOKEN - TOKEN_SIZE - engine.getWareTokens().get(0).size() * 20;

		int y = Y0_TOKEN;
		int dy = TOKEN_SIZE + 10;

		// jetons de marchandises
		int cpt = 0;
		Rect destRect = null;

		drawTokensColumn(c, x, y, 0, engine.getWareTokens().get(0));
		y += dy;
		x = X0_TOKEN - TOKEN_SIZE - engine.getWareTokens().get(1).size() * 20;
		drawTokensColumn(c, x, y, 1, engine.getWareTokens().get(1));
		y += dy;
		x = X0_TOKEN - TOKEN_SIZE - engine.getWareTokens().get(2).size() * 20;
		drawTokensColumn(c, x, y, 2, engine.getWareTokens().get(2));
		y += dy;
		x = X0_TOKEN - 3 * (TOKEN_SIZE + 10) - 10;

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

		// jetons de marchandises
		y += dy;
		x = X0_TOKEN - TOKEN_SIZE - engine.getWareTokens().get(3).size() * 20;
		drawTokensColumn(c, x, y, 3, engine.getWareTokens().get(3));
		y += dy;
		x = X0_TOKEN - TOKEN_SIZE - engine.getWareTokens().get(4).size() * 20;
		drawTokensColumn(c, x, y, 4, engine.getWareTokens().get(4));
		y += dy;
		x = X0_TOKEN - TOKEN_SIZE - engine.getWareTokens().get(5).size() * 20;
		drawTokensColumn(c, x, y, 5, engine.getWareTokens().get(5));
	}

	private void drawTokensColumn(Canvas c, int x, int y, int n, List<Integer> l) {
		int dx = 20;
		Rect destRect = null;
		for (int i = l.size() - 1; i >= 0; i--) {
			destRect = new Rect(x, y, x + TOKEN_SIZE, y + TOKEN_SIZE);
			switch (n) {
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
	}

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

			if (engine.getSelCardsMarket().contains(i)) {
				c.drawBitmap(lib.get(R.drawable.card_sel), CARD_RECT, destRect, paint);
			}
			x += dx;
		}

		x += 50;
		int n = engine.getDeck().size();
		n = n > 5 ? 5 : n;
		for (int i = 0; i < n; i++) {
			destRect = new Rect(x, y, x + CARD_WIDTH, y + CARD_HEIGHT);
			c.drawBitmap(lib.get(R.drawable.card_back), CARD_RECT, destRect, paint);
			x += 5;
		}
		paint.setColor(Color.WHITE);
		paint.setTextSize(40f);
		x += 30;
		y += 130;
		c.drawText("" + engine.getDeck().size(), x, y, paint);

	}

	private void drawPlayers(Canvas c) {
		int x = X0_HAND;
		int dx = CARD_WIDTH * 2 / 3;
		int y = Y0_HAND;

		Rect destRect = null;
		List<Card> hand = engine.getCurrentPlayer().getHand();
		for (int i = 0; i < hand.size(); i++) {
			y = i % 2 == 0 ? Y0_HAND - 5 : Y0_HAND + 5;
			destRect = new Rect(x, y, x + CARD_WIDTH, y + CARD_HEIGHT);
			int id = 0;
			switch (hand.get(i)) {
			case DIAMANT:
				id = R.drawable.card_diamant;
				break;
			case GOLD:
				id = R.drawable.card_gold;
				break;
			case SILVER:
				id = R.drawable.card_silver;
				break;
			case FABRICS:
				id = R.drawable.card_fabrics;
				break;
			case SPICES:
				id = R.drawable.card_spices;
				break;
			case LEATHER:
				id = R.drawable.card_leather;
				break;
			case CAMEL:
				id = R.drawable.card_camel;
				break;
			}

			if (engine.isEndOfTurn()) {
				c.drawBitmap(lib.get(R.drawable.card_back), CARD_RECT, destRect, paint);
			} else {
				c.drawBitmap(lib.get(id), CARD_RECT, destRect, paint);
				if (engine.getSelCardsHand().contains(i)) {
					c.drawBitmap(lib.get(R.drawable.card_sel), CARD_RECT, destRect, paint);
				}
			}

			x += dx;
		}

		if (!engine.isEndOfTurn()) {
			x = X0_HAND + (CARD_WIDTH * 2 / 3) * 7 + 60;
			if (engine.getCurrentPlayer().getnCamels() > 0) {
				destRect = new Rect(x, Y0_HAND, x + CARD_WIDTH, Y0_HAND + CARD_HEIGHT);
				c.drawBitmap(lib.get(R.drawable.card_camel), CARD_RECT, destRect, paint);

				if (engine.getSelCamels() > 0) {
					c.drawBitmap(lib.get(R.drawable.card_sel), CARD_RECT, destRect, paint);
					paint.setColor(Color.RED);
					paint.setTextSize(40f);
					c.drawText("(" + engine.getSelCamels() + ")", x+100, y+130, paint);
				}

				paint.setColor(Color.WHITE);
				paint.setTextSize(40f);
				x += 60;
				y += 130;
				c.drawText("" + engine.getCurrentPlayer().getnCamels(), x, y, paint);
			}
		}

		x = X0_HAND;
		y = 100;
		int n = 0;
		if (engine.getToken() == 0) {
			n = engine.getPlayers()[1].getHand().size();
		} else {
			n = engine.getPlayers()[0].getHand().size();
		}

		for (int i = 0; i < n; i++) {
			y = i % 2 == 0 ? 75 - 5 : 75 + 5;
			destRect = new Rect(x, y, x + CARD_WIDTH, y + CARD_HEIGHT);
			c.drawBitmap(lib.get(R.drawable.card_back), CARD_RECT, destRect, paint);
			x += CARD_WIDTH * 2 / 3;
		}
	}

	public void drawButtons(Canvas c) {
		int x = X0_BUTTON;
		int y = Y0_BUTTON;
		Rect destRect = null;

		if (!engine.isEndOfTurn()) {
			if (engine.canBuy()) {
				destRect = new Rect(x, y, x + BUTTON_WIDTH, y + BUTTON_HEIGHT);
				c.drawBitmap(lib.get(R.drawable.btn_buy), BUTTON_RECT, destRect, paint);
			}

			x += BUTTON_WIDTH + 20;
			if (engine.canTrade()) {
				destRect = new Rect(x, y, x + BUTTON_WIDTH, y + BUTTON_HEIGHT);
				c.drawBitmap(lib.get(R.drawable.btn_trade), BUTTON_RECT, destRect, paint);
			}

			x += BUTTON_WIDTH + 20;
			if (engine.canSale()) {
				destRect = new Rect(x, y, x + BUTTON_WIDTH, y + BUTTON_HEIGHT);
				c.drawBitmap(lib.get(R.drawable.btn_sale), BUTTON_RECT, destRect, paint);
			}
		} else {
			x += BUTTON_WIDTH + 20;
			destRect = new Rect(x, y, x + BUTTON_WIDTH, y + BUTTON_HEIGHT);
			c.drawBitmap(lib.get(R.drawable.btn_play), BUTTON_RECT, destRect, paint);
		}
	}
}

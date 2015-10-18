package com.skula.jaipur.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.skula.jaipur.cnst.Cnst;
import com.skula.jaipur.enums.Card;
import com.skula.jaipur.models.Player;

public class GameEngine {
	public static final int TOKEN_DIAMANT = 0;
	public static final int TOKEN_GOLD = 1;
	public static final int TOKEN_SILVER = 2;
	public static final int TOKEN_FABRICS = 3;
	public static final int TOKEN_SPICES = 4;
	public static final int TOKEN_LEATHER = 5;

	private int token;
	private List<Card> deck;
	private Card[] market;
	private Player[] players;

	private List<List<Integer>> wareTokens;
	private List<List<Integer>> bonusTokens;

	private List<Integer> selCardsHand;
	private List<Integer> selCardsMarket;
	private int selCamels;

	private boolean endOfTurn;

	public GameEngine() {
		initRound();
		this.endOfTurn = false;
	}

	private void initRound() {
		this.token = 0;
		// pioche
		this.deck = Card.getCards();
		// marche
		this.market = new Card[5];
		this.market[0] = deck.remove(0);
		this.market[1] = deck.remove(0);
		this.market[2] = deck.remove(0);
		Collections.shuffle(deck);
		this.market[3] = deck.remove(0);
		this.market[4] = deck.remove(0);
		// joueurs
		this.players = new Player[2];
		this.players[0] = new Player();
		this.players[1] = new Player();

		for (int i = 0; i < 5; i++) {
			Card c = deck.remove(0);
			if (c == Card.CAMEL) {
				players[0].addCamel(1);
			} else {
				players[0].addCard(c);
			}
			c = deck.remove(0);
			if (c == Card.CAMEL) {
				players[1].addCamel(1);
			} else {
				players[1].addCard(c);
			}
		}

		// jetons marchandises
		this.wareTokens = new ArrayList<List<Integer>>();
		// - diamant
		List<Integer> tmp = new ArrayList<Integer>();
		tmp.add(7);
		tmp.add(7);
		tmp.add(5);
		tmp.add(5);
		tmp.add(5);
		wareTokens.add(tmp);
		// - or
		tmp = new ArrayList<Integer>();
		tmp.add(6);
		tmp.add(6);
		tmp.add(5);
		tmp.add(5);
		tmp.add(5);
		wareTokens.add(tmp);
		// - argent
		tmp = new ArrayList<Integer>();
		for (int i = 0; i < 5; i++) {
			tmp.add(5);
		}
		wareTokens.add(tmp);
		// - tissus
		tmp = new ArrayList<Integer>();
		tmp.add(5);
		tmp.add(3);
		tmp.add(3);
		tmp.add(2);
		tmp.add(2);
		tmp.add(1);
		tmp.add(1);
		wareTokens.add(tmp);
		// - epices
		tmp = new ArrayList<Integer>();
		tmp.add(5);
		tmp.add(3);
		tmp.add(3);
		tmp.add(2);
		tmp.add(2);
		tmp.add(1);
		tmp.add(1);
		wareTokens.add(tmp);
		// - cuir
		tmp = new ArrayList<Integer>();
		tmp.add(4);
		tmp.add(3);
		tmp.add(2);
		for (int i = 0; i < 6; i++) {
			tmp.add(1);
		}
		wareTokens.add(tmp);

		// jetons bonus
		this.bonusTokens = new ArrayList<List<Integer>>();
		// - 3 cartes
		tmp = new ArrayList<Integer>();
		for (int i = 0; i < 2; i++) {
			tmp.add(1);
			tmp.add(2);
			tmp.add(3);
		}
		bonusTokens.add(tmp);
		Collections.shuffle(tmp);
		// - 4 cartes
		tmp = new ArrayList<Integer>();
		for (int i = 0; i < 2; i++) {
			tmp.add(4);
			tmp.add(5);
			tmp.add(6);
		}
		bonusTokens.add(tmp);
		Collections.shuffle(tmp);
		// - 5 cartes
		tmp = new ArrayList<Integer>();
		for (int i = 0; i < 2; i++) {
			tmp.add(8);
			tmp.add(9);
			tmp.add(10);
		}
		Collections.shuffle(tmp);
		bonusTokens.add(tmp);

		this.selCardsHand = new ArrayList<Integer>();
		this.selCardsMarket = new ArrayList<Integer>();
		this.selCamels = 0;
	}

	// TODO: gerer les chameaux
	public boolean canBuy() {
		if (selCardsMarket.size() == 0) {
			return false;
		}

		if (selCardsMarket.size() > 1) {
			for (Integer i : selCardsMarket) {
				if (market[i] != Card.CAMEL) {
					return false;
				}
			}
			return true;
		}

		return players[token].getHand().size() < 7;
	}

	private void buy() {
		if (market[selCardsMarket.get(0)] == Card.CAMEL) {
			for (Integer i : selCardsMarket) {
				players[token].addCamel(1);
				market[i] = null;
			}
		} else {
			players[token].getHand().add(market[selCardsMarket.get(0)]);
			market[selCardsMarket.get(0)] = null;
		}
	}

	public boolean canSale() {
		if (selCardsHand.isEmpty()) {
			return false;
		}

		if (selCamels > 0) {
			return false;
		}

		// si cartes differentes
		List<Card> hand = players[token].getHand();
		Card c = hand.get(selCardsHand.get(0));
		for (Integer i : selCardsHand) {
			if (c != hand.get(i)) {
				return false;
			}
		}

		// si moins de 2 cartes pour diamant, or ou argent
		if (c.equals(Card.DIAMANT) || c.equals(Card.GOLD) || c.equals(Card.SILVER)) {
			if (selCardsHand.size() < 2) {
				return false;
			}
		}
		return true;
	}

	private void sale() {
		Card c = players[token].getHand().get(selCardsHand.get(0));
		int n = selCardsHand.size();
		int ware = 0;

		switch (c) {
		case DIAMANT:
			ware = TOKEN_DIAMANT;
			break;
		case GOLD:
			ware = TOKEN_GOLD;
			break;
		case SILVER:
			ware = TOKEN_SILVER;
			break;
		case FABRICS:
			ware = TOKEN_FABRICS;
			break;
		case SPICES:
			ware = TOKEN_SPICES;
			break;
		case LEATHER:
			ware = TOKEN_LEATHER;
			break;
		default:
			break;
		}
		
		// on retire les cartes de la main
		List<Card> tmpl = new ArrayList<Card>();
		for(int i =0; i<players[token].getHand().size(); i++){
			if(!selCardsHand.contains(i)){
				tmpl.add(players[token].getHand().get(i));
			}
		}
		players[token].changeHand(tmpl);

		// prendre jetons marchandises
		int tmp = wareTokens.get(ware).size() < n ? wareTokens.get(ware).size() : n;
		for (int i = 0; i < tmp; i++) {
			players[token].addPoints(wareTokens.get(ware).remove(0));
		}

		// prendre jetons bonus
		if (tmp >= 5) {
			if (!bonusTokens.get(0).isEmpty()) {
				players[token].addPoints(bonusTokens.get(0).remove(0));
			}
		} else if (tmp == 4) {
			if (!bonusTokens.get(1).isEmpty()) {
				players[token].addPoints(bonusTokens.get(1).remove(0));
			}
		} else if (tmp == 3) {
			if (!bonusTokens.get(2).isEmpty()) {
				players[token].addPoints(bonusTokens.get(2).remove(0));
			}
		}
	}

	// TODO: gerer chameaux
	public boolean canTrade() {
		if (selCardsHand.size() < 2 || selCardsMarket.size() < 2) {
			return false;
		}

		if (selCardsHand.size() != selCardsMarket.size()) {
			return false;
		}

		for (Integer i1 : selCardsMarket) {
			for (Integer i2 : selCardsHand) {
				if (market[i1] == players[token].getHand().get(i2)) {
					return false;
				}
			}
		}
		return true;
	}

	// TODO: gerer chameaux
	private void trade() {
		Card[] tmp = market;
		for (int i = 0; i < selCardsHand.size(); i++) {
			market[selCardsMarket.get(i)] = players[token].getHand().get(selCardsHand.get(i));
		}

		for (int i = 0; i < selCardsMarket.size(); i++) {
			players[token].setCard(selCardsHand.get(i), tmp[selCardsMarket.get(i)]);
		}
	}

	public int getWinner() {
		if (players[0].getnSeals() == 2) {
			return 0;
		} else {
			return 1;
		}
	}

	public boolean isEndOfGame() {
		return players[0].getnSeals() == 2 || players[1].getnSeals() == 2;
	}

	public boolean isEndOfRound() {
		// si 3 type de marchandises vides
		int cpt = 0;
		for (List<Integer> l : wareTokens) {
			if (l.size() == 0) {
				cpt++;
			}
		}
		if (cpt == 3) {
			return true;
		}

		// si defausse vide
		for (int i = 0; i < 5; i++) {
			if (market[i] == null && deck.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public boolean isEndOfTurn() {
		return endOfTurn;
	}

	private void nextPlayer() {
		selCamels = 0;

		token = token == 0 ? 1 : 0;

		for (int i = 0; i < 5; i++) {
			if (market[i] == null) {
				market[i] = deck.remove(0);
			}
		}
	}

	private boolean isSelCamels() {
		for (Integer i : selCardsMarket) {
			if (market[i] != Card.CAMEL) {
				return false;
			}
		}
		return true;
	}

	private void selAllCamels() {
		for (int i = 0; i < 5; i++) {
			if (market[i] == Card.CAMEL) {
				selCardsMarket.add(i);
			}
		}
	}

	private void handleSelMarket(int i) {
		if (selCardsMarket.isEmpty()) {
			if (market[i] == Card.CAMEL) {
				selAllCamels();
			} else {
				if (selCardsMarket.contains(i)) {
					selCardsMarket.remove(new Integer(i));
				} else {
					selCardsMarket.add(i);
				}
			}
		} else {
			if (isSelCamels()) {
				if (market[i] == Card.CAMEL) {
					if (selCardsMarket.contains(i)) {
						selCardsMarket.clear();
					} else {
						selCardsMarket.add(i);
					}
				}
			} else {
				if (market[i] != Card.CAMEL) {
					if (selCardsMarket.contains(i)) {
						selCardsMarket.remove(new Integer(i));
					} else {
						selCardsMarket.add(i);
					}
				}
			}
		}

	}

	private void handleSelHand(int i) {
		if (selCardsHand.contains(i)) {
			selCardsHand.remove(new Integer(i));
		} else {
			selCardsHand.add(i);
		}
	}

	private void handleSelCamel(int i) {
		if (i == 1) {
			if (selCamels < players[token].getnCamels()) {
				selCamels++;
			}
		} else {
			selCamels = selCamels == 0 ? selCamels : selCamels - 1;
		}
	}

	public void process(int area) {
		switch (area) {
		case Cnst.AREA_MARKET_1:
			handleSelMarket(0);
			break;
		case Cnst.AREA_MARKET_2:
			handleSelMarket(1);
			break;
		case Cnst.AREA_MARKET_3:
			handleSelMarket(2);
			break;
		case Cnst.AREA_MARKET_4:
			handleSelMarket(3);
			break;
		case Cnst.AREA_MARKET_5:
			handleSelMarket(4);
			break;
		case Cnst.AREA_HAND_1:
			handleSelHand(0);
			break;
		case Cnst.AREA_HAND_2:
			handleSelHand(1);
			break;
		case Cnst.AREA_HAND_3:
			handleSelHand(2);
			break;
		case Cnst.AREA_HAND_4:
			handleSelHand(3);
			break;
		case Cnst.AREA_HAND_5:
			handleSelHand(4);
			break;
		case Cnst.AREA_HAND_6:
			handleSelHand(5);
			break;
		case Cnst.AREA_HAND_7:
			handleSelHand(6);
			break;
		case Cnst.AREA_CAMEL_ADD:
			handleSelCamel(1);
			break;
		case Cnst.AREA_CAMEL_REMOVE:
			handleSelCamel(-1);
			break;
		case Cnst.AREA_BTN_SALE:
			sale();
			selCardsHand.clear();
			selCardsMarket.clear();
			endOfTurn = true;
			nextPlayer();
			break;
		case Cnst.AREA_BTN_TRADE:
			trade();
			selCardsHand.clear();
			selCardsMarket.clear();
			endOfTurn = true;
			nextPlayer();
			break;
		case Cnst.AREA_BTN_BUY:
			buy();
			selCardsHand.clear();
			selCardsMarket.clear();
			endOfTurn = true;
			nextPlayer();
			break;
		case Cnst.AREA_BTN_PLAY:
			endOfTurn = false;
			break;
		default:
			break;
		}
	}

	public Player getCurrentPlayer() {
		return players[token];
	}

	public List<Card> getDeck() {
		return deck;
	}

	public void setDeck(List<Card> deck) {
		this.deck = deck;
	}

	public Card[] getMarket() {
		return market;
	}

	public void setMarket(Card[] market) {
		this.market = market;
	}

	public List<List<Integer>> getWareTokens() {
		return wareTokens;
	}

	public void setWareTokens(List<List<Integer>> wareTokens) {
		this.wareTokens = wareTokens;
	}

	public List<List<Integer>> getBonusTokens() {
		return bonusTokens;
	}

	public void setBonusTokens(List<List<Integer>> bonusTokens) {
		this.bonusTokens = bonusTokens;
	}

	public List<Integer> getSelCardsHand() {
		return selCardsHand;
	}

	public void setSelCardsHand(List<Integer> selCardsHand) {
		this.selCardsHand = selCardsHand;
	}

	public List<Integer> getSelCardsMarket() {
		return selCardsMarket;
	}

	public void setSelCardsMarket(List<Integer> selCardsMarket) {
		this.selCardsMarket = selCardsMarket;
	}

	public int getToken() {
		return token;
	}

	public Player[] getPlayers() {
		return players;
	}

	public int getSelCamels() {
		return selCamels;
	}

	public void setSelCamels(int selCamels) {
		this.selCamels = selCamels;
	}
}

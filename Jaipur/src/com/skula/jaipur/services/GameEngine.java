package com.skula.jaipur.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	public GameEngine() {
		initRound();
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
				players[0].addCard(deck.remove(0));
			}
			c = deck.remove(0);
			if (c == Card.CAMEL) {
				players[1].addCamel(1);
			} else {
				players[1].addCard(deck.remove(0));
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
	}

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

	public void buy() {
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

	public void sale() {
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

	public void trade() {
		Card[] tmp = market;
		for (int i = 0; i < selCardsHand.size(); i++) {
			market[selCardsMarket.get(i)] = players[token].getHand().get(selCardsHand.get(i));
		}

		for (int i = 0; i < selCardsMarket.size(); i++) {
			players[token].setCard(selCardsHand.get(i), tmp[selCardsMarket.get(i)]);
		}
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

	public void nextPlayer() {
		selCardsHand.clear();
		selCardsMarket.clear();

		token = token == 0 ? 1 : 0;

		for (int i = 0; i < 5; i++) {
			if (market[i] == null) {
				market[i] = deck.remove(0);
			}
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
	
	public int getToken(){
		return token;
	}
	
	public Player[] getPlayers(){
		return players;
	}
}

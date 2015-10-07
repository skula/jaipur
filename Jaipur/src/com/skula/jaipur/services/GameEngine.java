package com.skula.jaipur.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.skula.jaipur.enums.Card;
import com.skula.jaipur.models.Player;

public class GameEngine {
	private static final int TOKEN_DIAMANT = 0;
	private static final int TOKEN_GOLD = 1;
	private static final int TOKEN_SILVER = 2;
	private static final int TOKEN_FABRICS = 3;
	private static final int TOKEN_SPICES = 4;
	private static final int TOKEN_LEATHER = 5;

	private int token;
	private List<Card> deck;
	private Card[] market;
	private Player[] players;

	private List<List<Integer>> wareToken;
	private List<List<Integer>> bonusToken;

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
			players[0].addCard(deck.remove(0));
			players[1].addCard(deck.remove(0));
		}

		// jetons marchandises
		this.wareToken = new ArrayList<List<Integer>>();
		// - diamant
		List<Integer> tmp = new ArrayList<Integer>();
		tmp.add(7);
		tmp.add(7);
		tmp.add(5);
		tmp.add(5);
		tmp.add(5);
		wareToken.add(tmp);
		// - or
		tmp = new ArrayList<Integer>();
		tmp.add(6);
		tmp.add(6);
		tmp.add(5);
		tmp.add(5);
		tmp.add(5);
		wareToken.add(tmp);
		// - argent
		tmp = new ArrayList<Integer>();
		for (int i = 0; i < 5; i++) {
			tmp.add(5);
		}
		wareToken.add(tmp);
		// - tissus
		tmp = new ArrayList<Integer>();
		tmp.add(5);
		tmp.add(3);
		tmp.add(3);
		tmp.add(2);
		tmp.add(2);
		tmp.add(1);
		tmp.add(1);
		wareToken.add(tmp);
		// - epices
		tmp = new ArrayList<Integer>();
		tmp.add(5);
		tmp.add(3);
		tmp.add(3);
		tmp.add(2);
		tmp.add(2);
		tmp.add(1);
		tmp.add(1);
		wareToken.add(tmp);
		// - cuir
		tmp = new ArrayList<Integer>();
		tmp.add(4);
		tmp.add(3);
		tmp.add(2);
		for (int i = 0; i < 6; i++) {
			tmp.add(1);
		}
		wareToken.add(tmp);

		// jetons bonus
		this.bonusToken = new ArrayList<List<Integer>>();
		// - 3 cartes
		tmp = new ArrayList<Integer>();
		for (int i = 0; i < 2; i++) {
			tmp.add(1);
			tmp.add(2);
			tmp.add(3);
		}
		bonusToken.add(tmp);
		Collections.shuffle(tmp);
		// - 4 cartes
		tmp = new ArrayList<Integer>();
		for (int i = 0; i < 2; i++) {
			tmp.add(4);
			tmp.add(5);
			tmp.add(6);
		}
		bonusToken.add(tmp);
		Collections.shuffle(tmp);
		// - 5 cartes
		tmp = new ArrayList<Integer>();
		for (int i = 0; i < 2; i++) {
			tmp.add(8);
			tmp.add(9);
			tmp.add(10);
		}
		Collections.shuffle(tmp);
		bonusToken.add(tmp);

		this.selCardsHand = new ArrayList<Integer>();
		this.selCardsMarket = new ArrayList<Integer>();
	}

	public boolean canBuy() {
		// TODO: gerer les chameaux
		return players[token].getHand().size() < 7;
	}

	public void buy() {
		// TODO: gerer les chameaux
		players[token].getHand().add(market[selCardsMarket.get(0)]);
		market[selCardsMarket.get(0)] = null;
	}

	public boolean canSale() {
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

	// TODO: a continuer
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

		if (wareToken.get(ware).size() < n) {

		} else {

		}
	}

	public boolean canTrade() {
		if (selCardsHand.size() < 2) {
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

	// TODO: todo
	public void trade() {

	}

	public boolean isEndOfRound() {
		// si 3 type de marchandises vides
		int cpt = 0;
		for (List<Integer> l : wareToken) {
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
		token = token == 0 ? 1 : 0;

		for (int i = 0; i < 5; i++) {
			if (market[i] == null) {
				market[i] = deck.remove(0);
			}
		}
	}
}

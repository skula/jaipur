package com.skula.jaipur.models;

import java.util.ArrayList;
import java.util.List;

import com.skula.jaipur.enums.Card;

public class Player {
	private List<Card> hand;
	private int nCamels;
	private int nSeals;
	private int score;
	private int nBonus;

	public Player() {
		this.hand = new ArrayList<Card>();
		this.nCamels = 0;
		this.nSeals = 0;
		this.score = 0;
		this.nBonus = 0;
	}

	public void addPoints(int points) {
		this.score += points;
	}

	public void setCard(int i, Card card) {
		hand.set(i, card);
	}

	public void addCard(Card card) {
		this.hand.add(card);
	}
	

	public void changeHand(List<Card> l) {
		this.hand.clear();
		this.hand.addAll(l);
	}

	public Card removeCard(int i) {
		return hand.remove(i);
	}

	public List<Card> getHand() {
		return hand;
	}

	public void setHand(List<Card> hand) {
		this.hand = hand;
	}

	public void addCamel(int n) {
		this.nCamels += n;
	}

	public int getnCamels() {
		return nCamels;
	}

	public void setnCamels(int nCamels) {
		this.nCamels = nCamels;
	}

	public int getnSeals() {
		return nSeals;
	}

	public void setnSeals(int nSeals) {
		this.nSeals = nSeals;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getnBonus() {
		return nBonus;
	}

	public void setnBonus(int nBonus) {
		this.nBonus = nBonus;
	}
}

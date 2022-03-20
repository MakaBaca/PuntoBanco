package com.baca.boot.model;

public class PlayerScore {
	
	double amountWon;
	
	double amountInvested;

	public double getAmountWon() {
		return amountWon;
	}

	public void setAmountWon(double amountWon) {
		this.amountWon = amountWon;
	}

	public double getAmountInvested() {
		return amountInvested;
	}

	public void setAmountInvested(double amountInvested) {
		this.amountInvested = amountInvested;
	}

	@Override
	public String toString() {
		return "PlayerScore [amountWon=" + amountWon + ", amountInvested=" + amountInvested + "]";
	}

	public PlayerScore(double amountWon, double amountInvested) {
		super();
		this.amountWon = amountWon;
		this.amountInvested = amountInvested;
	}
}

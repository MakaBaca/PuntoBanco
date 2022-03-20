package com.baca.boot.model;

public class PlayerShoe {
	
	Integer shoeNumber;
	
	Integer playerId;
	
	Double score;

	public Integer getShoeNumber() {
		return shoeNumber;
	}

	public void setShoeNumber(Integer shoeNumber) {
		this.shoeNumber = shoeNumber;
	}
	
	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public PlayerShoe(Integer shoeNumber, Integer playerId) {
		super();
		this.shoeNumber = shoeNumber;
		this.playerId = playerId;
	}
	
	public PlayerShoe(Integer shoeNumber, Integer playerId, Double score) {
		super();
		this.shoeNumber = shoeNumber;
		this.playerId = playerId;
		this.score = score;
	}

	@Override
	public String toString() {
		return "PlayerShoe [shoeNumber=" + shoeNumber + ", playerId=" + playerId + ", score=" + score + "]";
	}


	
}

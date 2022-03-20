package com.baca.boot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Player {

	@Id
	@GeneratedValue(generator="player_seq")
	@SequenceGenerator(name="player_seq",sequenceName="PLAYER_SEQ", allocationSize=1, initialValue=100)
	int playerId;
	
	String name;

	double balance;

	double winings;

	double invested;

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getWinings() {
		return winings;
	}

	public void setWinings(double winings) {
		this.winings = winings;
	}

	public double getInvested() {
		return invested;
	}

	public void setInvested(double invested) {
		this.invested = invested;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Player [playerId=" + playerId + ", name=" + name + ", balance=" + balance + ", winings=" + winings
				+ ", invested=" + invested + "]";
	}

}

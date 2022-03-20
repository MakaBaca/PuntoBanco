package com.baca.boot.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries(value={
		@NamedQuery(name="ph.player.refresh.score", 
		query ="SELECT SUM(ph.amountWon), SUM(ph.betAmount) FROM PlayedHands ph "
				+ "WHERE ph.pk.playerId = :playerId")
})
public class PlayedHands {

	@EmbeddedId
	PlayedHandPK pk;

	//@MapsId("playerId")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="playerId", nullable=false, insertable=false, updatable = false)
	Player player;

	//@MapsId("sh")
	@JoinColumns({
		@JoinColumn(name="shoeNumber", referencedColumnName="shoeNumber", nullable=false, insertable=false, updatable = false),
		@JoinColumn(name="handNumber", referencedColumnName="handNumber", nullable=false, insertable=false, updatable = false)
	})
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	BacaShoe bacaShoe;

	long betAmount = 0;

	double amountWon = 0;

	boolean didWin = false;

	public PlayedHandPK getPk() {
		return pk;
	}

	public void setPk(PlayedHandPK pk) {
		this.pk = pk;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public BacaShoe getBacaShoe() {
		return bacaShoe;
	}

	public void setBacaShoe(BacaShoe bacaShoe) {
		this.bacaShoe = bacaShoe;
	}

	public long getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(long betAmount) {
		this.betAmount = betAmount;
	}

	public double getAmountWon() {
		return amountWon;
	}

	public void setAmountWon(double amountWon) {
		this.amountWon = amountWon;
	}

	public boolean isDidWin() {
		return didWin;
	}

	public void setDidWin(boolean didWin) {
		this.didWin = didWin;
	}

	@Override
	public String toString() {
		return "PlayedHands [pk="+ pk + ", player=" + player + ", bacaShoe=" + bacaShoe + ", betAmount=" + betAmount
				+ ", amountWon=" + amountWon + ", didWin=" + didWin + "]";
	}
}

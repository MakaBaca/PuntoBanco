package com.baca.boot.model;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.mak.casino.Baccarat.Outcome;

@Entity
@Table(name="player_played_shoes")
@NamedQueries(value={
		@NamedQuery(name="pps.distinct.shoe",
				query="SELECT DISTINCT pps.pk.shoeNumber, pps.playerId FROM PlayerPlayedShoes pps WHERE pps.playerId = :playerId"),
		@NamedQuery(name="pps.player.played.shoe", 
				query ="SELECT pps FROM PlayerPlayedShoes pps WHERE pps.pk.shoeNumber = :shoeNumber AND (pps.playerId = :playerId OR pps.playerId IS NULL)"),
		@NamedQuery(name="pps.player.played.shoe.score", 
		query ="SELECT pps.pk.shoeNumber, pps.playerId, SUM(pps.amountWon) FROM PlayerPlayedShoes pps "
				+ "WHERE pps.playerId = :playerId "
				+ "GROUP BY pps.pk.shoeNumber, pps.playerId "
				+ "ORDER BY pps.pk.shoeNumber"),
		@NamedQuery(name="pps.player.refresh.score", 
		query ="SELECT SUM(pps.amountWon), SUM(pps.betAmount) FROM PlayerPlayedShoes pps "
				+ "WHERE pps.playerId = :playerId")
})
@SqlResultSetMapping(
		name="WinRatioResultMapping",
		classes = @ConstructorResult(
				targetClass=PlayerWinRatio.class,
				columns={
						@ColumnResult(name="did_win", type = Boolean.class),
						@ColumnResult(name="percentage", type = Double.class)
				})
		)
public class PlayerPlayedShoes {
	
	@EmbeddedId
	PhsPK pk;
	/*@Column(name="hand_number")
	int handNumber;
	
	@Column(name="shoe_number")
	int shoeNumber;*/
	
	@Column(name="outcome")
	Outcome outcome;
	
	@Column(name="player_id")
	Integer playerId;
	
	@Column(name="amount_won", nullable=true)
	Double amountWon;
	
	@Column(name="bet_amount", nullable=true)
	Long betAmount;
	
	@Column(name="did_win", nullable=true)
	Boolean didWin;

	/*public int getHandNumber() {
		return handNumber;
	}

	public void setHandNumber(int handNumber) {
		this.handNumber = handNumber;
	}

	public int getShoeNumber() {
		return shoeNumber;
	}

	public void setShoeNumber(int shoeNumber) {
		this.shoeNumber = shoeNumber;
	}*/

	public Outcome getOutcome() {
		return outcome;
	}

	public void setOutcome(Outcome outcome) {
		this.outcome = outcome;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}
	
	public PhsPK getPk() {
		return pk;
	}

	public void setPk(PhsPK pk) {
		this.pk = pk;
	}

	public Double getAmountWon() {
		return amountWon;
	}

	public void setAmountWon(Double amountWon) {
		this.amountWon = amountWon;
	}

	public Long getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(Long betAmount) {
		this.betAmount = betAmount;
	}

	public Boolean getDidWin() {
		return didWin;
	}

	public void setDidWin(Boolean didWin) {
		this.didWin = didWin;
	}

	@Override
	public String toString() {
		return "PlayerPlayedShoes [pk=" + pk + ", outcome=" + outcome + ", playerId=" + playerId + ", amountWon="
				+ amountWon + ", betAmount=" + betAmount + ", didWin=" + didWin + "]";
	}

	/*public PlayerPlayedShoes(int shoeNumber, Integer playerId) {
		super();
		this.shoeNumber = shoeNumber;
		this.playerId = playerId;
	}

	public PlayerPlayedShoes(int handNumber, int shoeNumber, Outcome outcome, Integer playerId, Double amountWon,
			Long betAmount, Boolean didWin) {
		super();
		this.handNumber = handNumber;
		this.shoeNumber = shoeNumber;
		this.outcome = outcome;
		this.playerId = playerId;
		this.amountWon = amountWon;
		this.betAmount = betAmount;
		this.didWin = didWin;
	}*/

}

package com.baca.boot.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.mak.casino.Baccarat.Outcome;

@Entity
public class BacaShoe {

	@EmbeddedId
	BacaShoeAndHandPK sh;

	Outcome outcome;

	/*@ManyToOne
	@MapsId("shoeNumber")*/
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="shoeNumber", nullable=false, insertable=false, updatable = false)
	Shoe shoe;

	public Shoe getShoe() {
		return shoe;
	}

	public void setShoe(Shoe shoe) {
		this.shoe = shoe;
	}

	public Outcome getOutcome() {
		return outcome;
	}

	public void setOutcome(Outcome outcome) {
		this.outcome = outcome;
	}

	public BacaShoeAndHandPK getSh() {
		return sh;
	}

	public void setSh(BacaShoeAndHandPK sh) {
		this.sh = sh;
	}

	@Override
	public String toString() {
		return "BacaShoe [sh=" + sh + ", outcome=" + outcome + "]";
	}

}

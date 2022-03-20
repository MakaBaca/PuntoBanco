package com.baca.boot.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PhsPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 837367;

	@Column(name="hand_number")
	int handNumber;

	@Column(name="shoe_number")
	int shoeNumber;

	public int getHandNumber() {
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
	}

	@Override
	public String toString() {
		return "PhsPK [handNumber=" + handNumber + ", shoeNumber=" + shoeNumber + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PhsPK))
			return false;
		PhsPK that = (PhsPK) obj;
		if(this.handNumber == that.getHandNumber() &&
				this.shoeNumber == that.getShoeNumber()){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getShoeNumber(), this.getHandNumber());
	}

}

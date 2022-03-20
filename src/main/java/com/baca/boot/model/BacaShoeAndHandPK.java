package com.baca.boot.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BacaShoeAndHandPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	int shoeNumber;

	int handNumber;

	public int getShoeNumber() {
		return shoeNumber;
	}

	public void setShoeNumber(int shoeNumber) {
		this.shoeNumber = shoeNumber;
	}

	public void setHandNumber(int handNumber) {
		this.handNumber = handNumber;
	}

	public BacaShoeAndHandPK() {
	}

	public int getHandNumber() {
		return handNumber;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
        if (!(obj instanceof BacaShoeAndHandPK)) return false;
        BacaShoeAndHandPK that = (BacaShoeAndHandPK) obj;
        return Objects.equals(getShoeNumber(), that.getShoeNumber()) &&
                Objects.equals(getHandNumber(), that.getHandNumber());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getShoeNumber(), getHandNumber());
	}
}
